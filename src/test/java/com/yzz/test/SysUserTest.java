package com.yzz.test;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yzz.dao.SysUserDao;
import com.yzz.dto.Page;
import com.yzz.dto.ResultData;
import com.yzz.entity.SysUser;
import com.yzz.util.EncryptionUtil;
import com.yzz.util.IdGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-hibernate.xml" })
public class SysUserTest {

	private static Logger logger = Logger.getLogger(SysUserTest.class);

	@Resource
	SysUserDao sysUserDao;

	@Test
	public void test() throws Exception {
		SysUser entity = new SysUser();
		entity.setSysUserName("杨志钊");
//		entity.setSysUserId(IdGenerator.generatesId());
		entity.setPassword(EncryptionUtil.encodePassword("123456"));
//		String hql = "from SysUser";
		Page page = new Page();
		page.setOrderField("sys_user_id");
//		List<SysUser> sysUsers = sysUserDao.selectByHqlAndPage(hql, page);
//		ResultData<Object> resultData = new ResultData<Object>();
//		resultData.setData(sysUsers, page);
//		logger.info("-----------------------" + JSON.toJSONString(resultData));
//		sysUserDao.insert(entity);
//		sysUserDao.deleteByPrimaryKey("bdd16568-c122-4df2-9c2c-d76a31ea5b54-1491209325812");
//		String[] ids = {"d18f347f-c1cf-4812-87b1-35e154071e0f-1491206068270","d6c18384-39ab-49fb-ac55-63a44f6f7ff0-1491207012479"};
//		sysUserDao.deleteBatch(ids);
//		entity.setSysUserName("o成o");
//		entity.setSysUserId("fe4c0063-4854-4e1c-bb86-85fd3aa875b0-1491206877532");
//		sysUserDao.updateByPrimaryKey(entity);
//		sysUserDao.deleteByPrimaryKey("fe4c0063-4854-4e1c-bb86-85fd3aa875b0-1491206877532");
//		logger.info("--------------完成");
		logger.info("-----------------------" + JSON
				.toJSONString(sysUserDao.selectByPrimaryKey("6784a1e7-425a-47e9-8b70-4363416bb00f-1483702770271")));
//		logger.info(sysUserDao.selectByEntityAndPage(entity,null).size() + "-----------------------"
//				+ JSON.toJSONString(sysUserDao.selectByEntityAndPage(entity,null)));
	}

}
