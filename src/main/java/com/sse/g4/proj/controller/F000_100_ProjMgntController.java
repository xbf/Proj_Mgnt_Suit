package com.sse.g4.proj.controller;

import com.sse.g4.proj.DailyMeeting;
import com.sse.g4.proj.OrganInfo;
import com.sse.g4.proj.Project;
import com.sse.g4.proj.common.constant.ProjMgntErrorInfo;
import com.sse.g4.proj.common.packer.ResultPack;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 许白峰
 * 项目管理套件Controller层
 */
@RestController
@RequestMapping(value = "/g4/projMgnt", produces = {"application/json;charset=UTF-8"})
public class F000_100_ProjMgntController implements ApplicationContextAware {

    private ApplicationContext context;

    @Value("${com.sse.xubf.proj.path}")
    private String filePath;

    /**
     * 根据当前日期生成站会日志Excel文件
     * @return ResultPack 返回数据包
     **/
    @GetMapping("/f100001")
    public ResultPack f100001(@RequestParam(name = "currDate", defaultValue = " ") String currDate,
                              @RequestParam(name = "projFileName", defaultValue = " ") String projFileName) throws Exception {
        ResultPack resultPack = new ResultPack();

        Project project = new Project(filePath + "\\" + projFileName);
        OrganInfo organInfo = new OrganInfo(filePath + "\\PD_CT_55001_干系人登记册.xlsx");
        DailyMeeting dailyMeeting = new DailyMeeting(project,organInfo,currDate,filePath);

//        DailyMeeting dailyMeeting = (DailyMeeting) context.getBean("dailyMeeting",currDate);
//        DailyMeeting dailyMeeting = new DailyMeeting(currDate);

        try {
            dailyMeeting.export();
        } catch (Exception e) {
            e.printStackTrace();
            resultPack.setServiceName(ProjMgntErrorInfo.E1001.getServiceName());
            resultPack.setErrorInfo(ProjMgntErrorInfo.E1001.getErrorInfo());
            resultPack.setErrorNo(ProjMgntErrorInfo.E1001.getErrorNo());
        }


        return  resultPack;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
