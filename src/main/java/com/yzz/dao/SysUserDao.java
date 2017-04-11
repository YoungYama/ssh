package com.yzz.dao;

import java.util.List;

import com.yzz.dto.Page;
import com.yzz.entity.SysUser;

public interface SysUserDao {

	void insert(SysUser entity);

	void deleteByPrimaryKey(String sysUserId);

	void deleteBatch(String[] sysUserIds);

	void updateByPrimaryKey(SysUser entity);

	SysUser selectByPrimaryKey(String sysUserId) throws Exception;

	List<SysUser> selectByEntityAndPage(SysUser entity, Page page) throws Exception;

	List<SysUser> selectByHqlAndPage(String hql, Page page);

}
