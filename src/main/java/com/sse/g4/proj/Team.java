package com.sse.g4.proj;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    protected String teamName; //小组名称
    protected String leader; //组长姓名

    protected String viceLeader; //副组长姓名

    protected List<String> members; //组员名单

}
