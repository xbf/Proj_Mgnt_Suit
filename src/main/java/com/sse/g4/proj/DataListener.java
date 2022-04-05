package com.sse.g4.proj;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class DataListener extends AnalysisEventListener<Map<Integer,String>> {

    private List<Map<Integer,String>> resultLst;

    public DataListener() {
        this.resultLst = new ArrayList<Map<Integer, String>>();
    }


    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        resultLst.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
