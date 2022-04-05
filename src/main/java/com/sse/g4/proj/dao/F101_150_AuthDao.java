/**
 * @author 许白峰
 *
 * 权限控制模块
 * Controller号段：100101-100150
 * Service号段：1100101-1100150
 * Dao号段：2100101-2100150
 */

package com.sse.g4.proj.dao;


import com.sse.g4.proj.common.packer.ParamMap;

import java.util.List;

//@Mapper
public interface F101_150_AuthDao {

	/**
	 * 获取用户信息
	 * @return
	 */
	List<ParamMap<String, Object>> f2100101(String staffAcc);


	/**
	 * 查询用户可操作菜单列表
	 * @return
	 */
	List<ParamMap<String, Object>> f2100102(String staffAcc);

//	/**
//	 * 批量插入数据
//	 * @param paramMapList
//	 */
//	void f2105105(List<ParamMap<String, Object>> paramMapList);
}
