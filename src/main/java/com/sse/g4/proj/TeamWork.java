package com.sse.g4.proj;

import lombok.Getter;
public class TeamWork extends Team {

    private Project project;

    @Getter
    private TaskInfo teamTaskInfo;

    public TeamWork(Project project) {
        this.project = project;
    }

    public void getCurrentWork(String currDate) throws Exception {
        teamTaskInfo = project.getTaskInfo(currDate).clone();
        teamTaskInfo.filterByTeamName(this.teamName);
    }
}
