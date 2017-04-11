package com.yzz.util;

public class UploadFilesState {

	// 文件上传路径
	public static String UPLOAD_TARGETDIR = "/resources/uploadFiles/";

	// 上传文件限制小于10m
	public static long UPLOAD_SIZELIMITED = 10 * 1024 * 1024;

	// 文件上传状态
	public static String UPLOAD_SUCCESS = "上传成功";

	public static String UPLOAD_OVERSIZE = "大小超过10m";

	public static String UPLOAD_INNERERROR = "后台出错";

}