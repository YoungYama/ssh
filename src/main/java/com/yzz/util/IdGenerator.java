package com.yzz.util;

import java.util.UUID;

public class IdGenerator {

	/**
	 * 
	 * @description:产生唯一身份id
	 *
	 * @return String
	 *
	 * @author 杨志钊
	 * @date 2016年12月28日 下午9:29:00
	 */
	public static String generatesId() {

		return UUID.randomUUID() + "-" + System.currentTimeMillis();
	}
	
}
