package com.yzz.util;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

/**
 * 数据库字段命名规则[本类目标是把java【骆驼命名】 转换成 【下划线命名】]
 * @author 杨志钊
 * @date 2017-3-16 下午6:02:40
 *
 */
@SuppressWarnings("deprecation")
public class HibernateNamingStrategy extends ImprovedNamingStrategy implements
		NamingStrategy {
	private static final long serialVersionUID = 1L;

	@Override
	public String columnName(String columnName) {
		
		return addUnderscores(columnName).toUpperCase();
	}

	@Override
	public String tableName(String tableName) {
	
		return addUnderscores(tableName).toUpperCase();
	}
	
}