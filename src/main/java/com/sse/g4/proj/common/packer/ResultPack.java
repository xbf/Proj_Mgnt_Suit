package com.sse.g4.proj.common.packer;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 许白峰
 * 打包类，服务返回对象
 */

@Data
public class ResultPack {

	public ResultPack() {
		this.setErrorNo(0);
		this.setErrorInfo(" ");
		this.setServiceName(" ");
		this.setResultLst(new ArrayList<ParamMap<String,Object>>());
	}

	private int errorNo;
	private String errorInfo;
	private String serviceName;
	private List<ParamMap<String, Object>> resultLst;
	private Exception e;
}
