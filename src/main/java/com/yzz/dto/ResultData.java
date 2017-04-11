package com.yzz.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class ResultData<T> {

	private T data;

	private int code = 200;

	private Boolean success = true;

	private String msg;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public void setData(List<?> list, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("list", list);

		this.data = (T) JSON.toJSON(map);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		if (code != 200) {
			success = false;
		}
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}