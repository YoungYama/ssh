package com.yzz.entity; 

import javax.persistence.Entity; 
import javax.persistence.Id; 
import javax.persistence.Table; 

/** 
* 
* @description: sys_user表SysUser实体类 
* 
* @author 杨志钊 
* @date 2017-04-11 14:08:34 
*/

@Entity 
@Table(name = "sys_user") 
public class SysUser { 

	@Id 
 	private String sysUserId;//系统用户编号

	private String wxPublicAccountId;//所属公账号ID

	private String sysUserName;//系统用户名

	private String password;//密码

	private String headUrl;//头像路径

	private String email;//电子邮件

	private String tel;//电话

	private String registerTime;//注册时间

	private Integer sysUserRole;//角色：0：系统管理员；1：系统员工；2：系统用户

	public String getSysUserId() { 
		return sysUserId; 
	} 

	public void setSysUserId(String sysUserId) { 
 		this.sysUserId = sysUserId == null ? null : sysUserId.trim(); 
	} 

	public String getWxPublicAccountId() { 
		return wxPublicAccountId; 
	} 

	public void setWxPublicAccountId(String wxPublicAccountId) { 
 		this.wxPublicAccountId = wxPublicAccountId == null ? null : wxPublicAccountId.trim(); 
	} 

	public String getSysUserName() { 
		return sysUserName; 
	} 

	public void setSysUserName(String sysUserName) { 
 		this.sysUserName = sysUserName == null ? null : sysUserName.trim(); 
	} 

	public String getPassword() { 
		return password; 
	} 

	public void setPassword(String password) { 
 		this.password = password == null ? null : password.trim(); 
	} 

	public String getHeadUrl() { 
		return headUrl; 
	} 

	public void setHeadUrl(String headUrl) { 
 		this.headUrl = headUrl == null ? null : headUrl.trim(); 
	} 

	public String getEmail() { 
		return email; 
	} 

	public void setEmail(String email) { 
 		this.email = email == null ? null : email.trim(); 
	} 

	public String getTel() { 
		return tel; 
	} 

	public void setTel(String tel) { 
 		this.tel = tel == null ? null : tel.trim(); 
	} 

	public String getRegisterTime() { 
		return registerTime; 
	} 

	public void setRegisterTime(String registerTime) { 
 		this.registerTime = registerTime == null ? null : registerTime.trim(); 
	} 

	public Integer getSysUserRole() { 
		return sysUserRole; 
	} 

	public void setSysUserRole(Integer sysUserRole) { 
 		this.sysUserRole = sysUserRole; 
	} 
 
 } 
