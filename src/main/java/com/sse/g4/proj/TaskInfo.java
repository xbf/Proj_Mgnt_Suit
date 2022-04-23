package com.sse.g4.proj;

import com.sse.g4.proj.common.constant.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import net.sf.mpxj.Task;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Project mpp文件中的任务类，建立完整的工作分解结构（work breakdown structure）。
 * @author  许白峰
 * @date 20210801
 */

@Getter
@Setter
public class TaskInfo implements Cloneable {

    private int id;                         //任务ID
    private int uniqueId;                   //唯一标识号
    private String wbs;                     //wbs编号
    private String taskName;                //任务名称
    private String teamName;                //小组名称
    private String deliverables;            //交付物
    private String owner;                   //责任人
    private TaskInfo parent;                //父任务
    private List<TaskInfo> children;        //子任务列表
    private String duration;                //工期  单位：天
    private int status;                  //任务状态
    private String resource;                //资源（参与人）
    private boolean milestone;              //是否为里程碑  0：不是  1：是
    private Double complete;                //完成百分比
    private String startDate;               //计划开始日期
    private String finishDate;              //计划完成日期
    private String actualStartDate;         //实际开始日期
    private String actualFinishDate;        //实际完成日期
    private String predecessors;            //前序任务
    private Boolean isLeafNode;             //是否为叶子任务，true：是   false：否

    private List<TaskInfo> taskList;        //任务的扁平化列表实力，用来插入Excel


    /**
     * 根据小组名称筛选出需要显示在站会任务清单中的任务
     * 1、检查当前任务是否为叶子任务
     * 1.1、如果是叶子任务
     * 1.1.1、是否为当前小组任务
     * 1.1.1.1、不是当前小组任务，则从结构中删除该任务
     * 1.1.1.2、是当前小组任务，判断是否为”将来任务“或”已完成“任务，如果是则删除该任务
     * 1.2、如果不是叶子任务
     * 1.2.1、取出children属性中的所有子任务，从最后一个任务开始向前遍历，递归执行filterByTeamName方法
     * 1.2.2、再次检查当前任务是否为叶子任务，如果是则执行filterByTeamName方法
     * @param teamName 小组名称
     */
    public void filterByTeamName(String teamName) {
        if ((this.children == null) || (this.children.size() == 0)) {
            if ((this.teamName != null) && (!this.teamName.equals(teamName))) {
                //对根任务进行保护，根任务的parent属性为空
                if (this.parent != null) {
                    this.parent.children.remove(this);
                }
            } else {
                if (this.status == TaskStatus.FUTURE_TASK.getCode() || this.status == TaskStatus.FINISHED_TASK.getCode()) {
                    //对根任务进行保护，根任务的parent属性为空
                    if (this.parent != null) {
                        this.parent.children.remove(this);
                    }
                }
            }
        } else {
            for (int i = this.children.size() -1; i >= 0; i--) {
                TaskInfo task = this.children.get(i);
                task.filterByTeamName(teamName);
            }

            if ((this.children == null) || (this.children.size() == 0)) {
                this.filterByTeamName(teamName);
            }
        }
    }

    /**
     * 初始化并装载整个工作分解机构。
     * @param task mpxj包中的task对象，代表从project mpp文件读出的一个任务记录
     * @param parent 该任务的父级任务，根任务的父级任务为null
     */
    public void load(Task task, TaskInfo parent, String currDate) {
        this.id = task.getID();
        this.uniqueId = task.getUniqueID();
        this.wbs = task.getWBS();
        this.taskName = task.getName();
        this.teamName = StringUtils.defaultIfBlank(task.getText(7), "");
        this.deliverables = StringUtils.defaultIfBlank(task.getText(3), "");
        this.owner = StringUtils.defaultIfBlank(task.getText(5), "");
        this.duration = task.getDuration().toString();
        this.resource = StringUtils.defaultIfBlank(task.getResourceNames(), "");
        this.milestone = task.getMilestone();
        this.complete = task.getPercentageComplete().doubleValue()/100;
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyyMMdd");
        this.startDate = task.getStart() == null? "" : dateFormate.format(task.getStart());
        this.finishDate = task.getFinish() == null? "" : dateFormate.format(task.getFinish());
        this.actualStartDate = task.getActualStart() == null? "" : dateFormate.format(task.getActualStart());
        this.actualFinishDate = task.getActualFinish() == null? "" : dateFormate.format(task.getActualFinish());

        //status  1 将来任务  2 延期  3 进行中  4 已完成
        if (Integer.parseInt(startDate) > Integer.parseInt(currDate)) {
            this.status = TaskStatus.FUTURE_TASK.getCode();
        } else if (actualStartDate.equals("")) {
            this.status = TaskStatus.DELAY_TASK.getCode();
        } else if (!actualFinishDate.equals("")) {
            this.status = TaskStatus.FINISHED_TASK.getCode();
        } else {
            this.status = TaskStatus.CURRENT_TASK.getCode();
        }

        this.predecessors = task.getPredecessors().toString();
        List<Task> tasks = task.getChildTasks();

        if (tasks.size() == 0) {
            this.isLeafNode = true;
        } else {
            this.isLeafNode = false;
        }

        children = new ArrayList<>();
        for (Task subTask: tasks) {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.load(subTask, this, currDate);
            children.add(taskInfo);
        }
        this.parent = parent;
    }

    public void writeToProject(Task task, TaskInfo taskInfo) {
        this.complete = taskInfo.getComplete() * 100;
        task.setPercentageComplete(this.complete);
    }

    /**
     * 对TaskInfo进行深拷贝，主要处理的是parent和children对象的深拷贝。
     * @return 深度拷贝后的TaskInfo对象
     */
    @Override
    public TaskInfo clone() {
        try {
            TaskInfo clone = (TaskInfo) super.clone();
            //parent 和 children需要做深度拷贝，其他元素不需要做深度拷贝
            List<TaskInfo> newTaskList = new ArrayList<>();
            for (TaskInfo task: this.children) {
                TaskInfo newTask = task.clone();
                newTask.parent = clone;
                newTaskList.add(newTask);
            }
            clone.children = newTaskList;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void toList(ArrayList<TaskInfo> taskList) {
        taskList.add(this);
        for (TaskInfo taskInfo: this.children) {
            taskInfo.toList(taskList);
        }
    }
}
