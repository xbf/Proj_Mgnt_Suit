package com.sse.g4.proj.git;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Issue {
    private String owner;                    //isuue责任人
    private boolean isPlanned;               //是否为计划内任务
    private int unplannedIssueType;          //计划外任务类别
    private int uniqueId;                    //Wbs唯一标识号
    private String content;                  //问题内容
    private String issueId;                  //gitlab中的问题id
    private String done;                     //昨日完成任务
    private String toDo;                     //今日任务
    private String planToDo;                 //明日计划任务

    public void load() {

    }

}
