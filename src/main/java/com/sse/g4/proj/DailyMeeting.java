package com.sse.g4.proj;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * 站会日志类，用来完成站会日志的生成。
 * @author 许白峰
 */

@Component
@Scope(value = "prototype")
public class DailyMeeting {

    private String currDate;
    @Autowired
    private OrganInfo organInfo;
    @Autowired
    private Project project;

    @Value("${com.sse.xubf.dailyMeeting.path}")
    private String filePath;

    private HashMap<String, TeamWork> teamWorkMap = new HashMap<>();

    public DailyMeeting(String currDate) throws Exception {
        this.currDate = currDate;
    }

    /**
     * OrganInfo和Project这两个由容器管理的对象是不能在构造函数中访问的，因此在PostConstruct方法中执行初始化
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        for (String teamName : organInfo.getTeamInfos().keySet()) {
            Team team = organInfo.getTeamInfos().get(teamName);
            TeamWork teamWork = new TeamWork(project);
            teamWork.setTeamName(team.getTeamName());
            teamWork.setLeader(team.getLeader());
            teamWork.setMembers(team.getMembers());
            teamWork.getCurrentWork(currDate);
            teamWorkMap.put(teamName, teamWork);
        }
    }

    /**
     * 导出站立会议日志Excel文件
     * @throws Exception 抛出导出异常信息
     */
    public void export() throws Exception {
        String templateFileName = filePath + "\\站立会议日志模板.xlsx";
        String fileName = filePath + "\\PD075_" + currDate + "_站立会议日志.xlsx";

        ExcelWriter writer = EasyExcel.write(fileName).withTemplate(getTemplateFile(templateFileName)).registerConverter(new MemberConverter()).build(); //.sheet().doFill(teamWork);
        fillTeamInfo(writer);
        fillTaskInfo(writer);
        writer.finish();
    }

    /**
     * 根据模板
     * @param writer Excel写入类
     */
    private void fillTaskInfo(ExcelWriter writer) {
        ArrayList<TaskInfo> taskList = new ArrayList<>();
        FillConfig config = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        for (String teamName: teamWorkMap.keySet()) {
            taskList.clear();
            TeamWork teamWork = teamWorkMap.get(teamName);
            TaskInfo taskInfo = teamWork.getTeamTaskInfo();
            taskInfo.toList(taskList);
            WriteSheet writeSheet = EasyExcel.writerSheet(teamWork.teamName).build();
            writer.fill(taskList,config,writeSheet);
        }
    }

    /**
     * 向站会日志写入Issue信息
     * @param writer
     */
    private void fillIssue(ExcelWriter writer) {

    }


    /**
     * 写入小组信息
     * @param writer Excel写入类
     */
    private void fillTeamInfo(ExcelWriter writer) {
        Collection<TeamWork> teamWorks = teamWorkMap.values();

//        FillConfig config = FillConfig.builder().forceNewRow(true).build();
        for (TeamWork teamWork : teamWorks) {
            WriteSheet writeSheet = EasyExcel.writerSheet(teamWork.teamName).build();
            writer.fill(teamWork, writeSheet);
        }
    }

    /**
     * 获取Excel模板文件
     * @param filePath 模板文件地址
     * @return 返回模板文件InputStream
     * @throws IOException 抛出异常，后续处理
     */
    private InputStream getTemplateFile(String filePath) throws IOException {
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        for (int i = workbook.getNumberOfSheets()-1; i >=0 ; i--) {
            String sheetName = workbook.getSheetName(i);
            if (!sheetName.equals("站会模板")) {
                workbook.removeSheetAt(i);
            }
        }

        int index = workbook.getSheetIndex("站会模板");
        for (String teamName : teamWorkMap.keySet()) {
            Sheet newSheet = workbook.cloneSheet(index);
            int sheetIndex = workbook.getSheetIndex(newSheet);
            workbook.setSheetName(sheetIndex, teamName);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }
}
