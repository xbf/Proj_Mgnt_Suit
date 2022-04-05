package com.sse.g4.proj;

import lombok.Getter;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Project mpp文件装载类，读取mpp文件，初始化并装载TaskInfo对象。
 *
 * @author 许白峰
 * @date 20210801
 */
@Component
@Getter
public class Project {

    @Value("${com.sse.xubf.proj.path}")
    private String filePath;
    private String currDate;
    private TaskInfo taskInfo;

    /**
     * 根据当前日期载入Project文件中的WBS，构建taskInfo对象实例
     */
    public TaskInfo getTaskInfo(String currDate) throws Exception {
        this.currDate = currDate;
        MPPReader mppReader = new MPPReader();
        ProjectFile file = mppReader.read(filePath);
        List<Task> taskList = file.getChildTasks();

        Task task;
        if (!taskList.isEmpty()) {
            task = taskList.get(0);
        } else {
            throw new Exception("Project 文件为空。");
        }

        taskInfo = new TaskInfo();
        taskInfo.load(task, null, currDate);
        return taskInfo;
    }
}
