package com.yzz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yzz.dao.SysUserDao;
import com.yzz.dto.Page;
import com.yzz.entity.SysUser;

@Repository
public class SysUserDaoImpl extends BaseDao implements SysUserDao {

	@Override
	public void insert(SysUser entity) {
		super.save(entity);
	}

	@Override
	public void deleteByPrimaryKey(String sysUserId) {
		super.delete(SysUser.class, sysUserId);
	}

	@Override
	public void deleteBatch(String[] sysUserIds) {
		super.deleteBatch(SysUser.class, sysUserIds);
	}

	@Override
	public void updateByPrimaryKey(SysUser entity) {
		super.update(entity);
	}

	@Override
	public SysUser selectByPrimaryKey(String sysUserId) throws Exception {
		
		return (SysUser) super.findByPrimaryKey(SysUser.class, sysUserId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> selectByEntityAndPage(SysUser entity, Page page) throws Exception {
		return (List<SysUser>) super.findByExampleAndPage(entity, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> selectByHqlAndPage(String hql, Page page) {
		return (List<SysUser>) super.findByHqlAndPage(hql, page);
	}

}
