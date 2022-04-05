package com.sse.g4.proj.common.packer;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * 将Mybatis从数据库中返回的全大写，“_”分隔的字段名改为驼峰命名规则的字段名，这样在参数注入过程中就不需要手工操作了?
 * @author 许白峰
 * @param <K> 参数名称
 * @param <V> 参数值
 */

public class ParamMap<K, V> extends HashMap<K, V> {

	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		String keyStr = (String)key;

		StringBuilder formattedStr = new StringBuilder();
		if (!Pattern.matches("^([a-z]([a-z0-9])*)([A-Z]([a-z0-9])+)*$", keyStr)) {

			String lowerCaseKey = keyStr.toLowerCase();
			String[] splitKeyArray = lowerCaseKey.split("_");

			for (int i = 0; i < splitKeyArray.length; i++) {
				if (i == 0) {
					formattedStr = new StringBuilder(splitKeyArray[i]);
				} else {
					formattedStr.append(splitKeyArray[i].substring(0, 1).toUpperCase().concat(splitKeyArray[i].substring(1).toLowerCase()));
				}
			}
		} else {
			formattedStr = new StringBuilder(keyStr);
		}

		super.put((K) formattedStr.toString(), value);

		return value;
	}
}
