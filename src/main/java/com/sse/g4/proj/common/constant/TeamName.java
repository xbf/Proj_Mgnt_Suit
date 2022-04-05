package com.sse.g4.proj.common.constant;

public enum TeamName {
    MEMO_DATA_GRP(1, "内存数据组件研发"),
    ARCH_MGNT_GRP(2, "分布式架构管理及探测"),
    EVENT_TRANS_GRP(3, "事件处理架构研发"),
    EVENT_PROC_GRP(4, "事件传输架构研发"),
    MATCH_ARCH_GRP(5, "撮合架构研发"),
    CONF_MGNT_GRP(6, "资源管理"),
    BASIC_LIB_GRP(7, "基础库研发");

    private int code;
    private String grpName;

    TeamName(int i, String s) {
        this.code = i;
        this.grpName = s;
    }

    public int getCode() {
        return code;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }
}
