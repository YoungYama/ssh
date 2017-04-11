package com.yzz.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.yzz.dao.SysUserDao;
import com.yzz.dto.ResultData;
import com.yzz.entity.SysUser;
import com.yzz.log.LogInfo;
import com.yzz.util.ConstantUtil;
import com.yzz.util.EncryptionUtil;
import com.yzz.util.IdGenerator;

public class SysUserCtrl {

	@Resource
	SysUserDao sysUserDao;
	
	private ResultData<Object> resultData;

	@LogInfo(modelTypeName = "系统用户", operationTypeName = "登录", operationContent = "用户登录了")
	public String login() throws Exception {
		System.out.println("in");
		HttpSession session = ServletActionContext.getRequest().getSession();
		SysUser entity = new SysUser();
		entity.setSysUserName("杨志钊");
		entity.setSysUserId(IdGenerator.generatesId());
		entity.setPassword(EncryptionUtil.encodePassword("123456"));
		// sysUserDao.insert(entity);

		resultData = new ResultData<>();

		entity = sysUserDao.selectByPrimaryKey("6784a1e7-425a-47e9-8b70-4363416bb00f-1483702770271");
		session.setAttribute(ConstantUtil.LOGINING_SYSUSER, entity);
		resultData.setData(entity);
		
		return "callbackData";
	}
	

	public ResultData<Object> getResultData() {
		return resultData;
	}

	public void setResultData(ResultData<Object> resultData) {
		this.resultData = resultData;
	}
	
}
