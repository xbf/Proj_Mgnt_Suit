package com.sse.g4.proj.common.constant;

/**
 * 错误信息枚举类，管理项目管理服务模块中的错误信息，错误号段81000000-81009999
 */

public enum ProjMgntErrorInfo {
    E1001(1001, "生成站会日志文件失败", "proj_mgnt");

    private int errorNo;
    private String errorInfo;
    private String serviceName;

    ProjMgntErrorInfo(int errorNo, String errorInfo, String serviceName) {
        this.errorNo = errorNo;
        this.errorInfo = errorInfo;
        this.serviceName = serviceName;
    }

    public int getErrorNo() {
        return errorNo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public String getServiceName() {
        return serviceName;
    }

}
