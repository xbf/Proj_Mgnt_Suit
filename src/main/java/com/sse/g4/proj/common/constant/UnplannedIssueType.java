package com.sse.g4.proj.common.constant;

public enum UnplannedIssueType {
    OTHER_PROJ_SUPPORT(1, "跨项目支持"),
    EMERGENCY(2, "应急"),
    OTHERS(3, "其他"),
    ;

    private int code;
    private String issueType;

    UnplannedIssueType(int i, String issueType) {
        this.code = i;
        this.issueType = issueType;
    }

    public int getCode() {
        return code;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
}

