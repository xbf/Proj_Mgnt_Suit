package com.sse.g4.proj;

import com.alibaba.excel.EasyExcel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目组织信息类
 * 从干系人登记册-项目结构中读取每个小组的成员信息
 */

@Data
@Component
public class OrganInfo {
    private String director;
    private String projectManager;
    private String architect;
    private HashMap<String, Team> teamInfos;

    //在构造函数中读取属性文件的值时，使用这种方法赋值
    public OrganInfo(@Value("${com.sse.xubf.stakeholder.path}") String filePath) {
        List<Map<Integer, String>> resultLst;
        teamInfos = new HashMap<>();

        DataListener listener = new DataListener();
        EasyExcel.read(filePath,listener).sheet("新期权新竞价").doRead();

        resultLst = listener.getResultLst();

        if (resultLst.size() == 0) {
            return ;
        }

        for (Map<Integer,String> row: resultLst) {
//            if (row.get(0) != null ) {
//                this.director = row.get(0);
//            }

            if (row.get(2) != null) {
                this.projectManager = row.get(2);
            }

//            if (row.get(3) != null) {
//                this.architect = row.get(3);
//            }

            //组名字段不为空
            if (row.get(4) != null) {
                Team team = new Team();
                team.teamName = row.get(4);
                team.leader = row.get(6);
                ArrayList<String> members = new ArrayList<>();

                for (int i = 7; i < row.size(); i++) {
                    members.add(row.get(i));
                }
                team.members = members;
                teamInfos.put(team.teamName, team);
            } else {
                break;
            }
        }
    }
}
