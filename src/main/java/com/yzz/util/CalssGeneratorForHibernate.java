package com.yzz.util;

import java.util.List;
import java.util.Map;

/**
 * 使用该插件时必须注意：表ID必须是表名加_id；如sys_user表的ID一定要是sys_user_id
 * @description:自动生成复用性高的通用crud操作实体类、DAO接口、DAO实现类，适用于hibernate框架与MySQL数据库
 *
 * @author 杨志钊
 * @date 2017年4月3日 下午6:40:07
 */
@SuppressWarnings("unchecked")
public class CalssGeneratorForHibernate {
	
	private static String projectPath = System.getProperty("user.dir");
	
	public static void setProjectPath(String projectPath) {
		CalssGeneratorForHibernate.projectPath = projectPath;
	}

	public static void main(String[] args) throws Exception {
		String classGeneratorConfigXML =projectPath + "\\src\\main\\resources\\generator\\yzz-class-generator.xml";
		
		CalssGeneratorConfig.setClassGeneratorConfigXML(classGeneratorConfigXML);
		Map<String, Object> map = CalssGeneratorConfig.getTableAndClassDatas();
		
		CalssGeneratorForHibernate.setTableAndClassDatas(map);
		CalssGeneratorForHibernate.generateEntityClass();
	}

	private static List<String> tableNames;
	private static List<List<String>> tableColumnRemarks;
	private static List<String> classNames;
	private static List<List<String>> classPropertyTypes;
	private static List<List<String>> classPropertyNames;
	private static String entityTargetDir;
	private static String entityTargetPackage;
	private static boolean stringTrim;

	 public static void setTableAndClassDatas(Map<String, Object> map){
			tableNames = (List<String>) map.get("tableNames");
			tableColumnRemarks = (List<List<String>>) map.get("tableColumnRemarks");
			classNames = (List<String>) map.get("classNames");
			classPropertyTypes = (List<List<String>>) map.get("classPropertyTypes");
			classPropertyNames = (List<List<String>>) map.get("classPropertyNames");
			entityTargetDir = projectPath + "\\src\\main\\java\\" + map.get("entityTargetDir");
			entityTargetPackage = map.get("entityTargetPackage").toString();
			stringTrim = (boolean) map.get("stringTrim");
	 }


	public static String geTauthorInfo(String description, String author) {
		String authorInfo = "/** \n"+
				 "* \n"+
				 "* @description: " + description + " \n"+
				 "* \n"+
				 "* @author " + author + " \n"+
				 "* @date " + TimeUtil.getCurrentTimeString() + " \n"+
				 "*/";
		return authorInfo;
	}

	public static void generateEntityClass() throws Exception {
		for (int i = 0; i < tableNames.size(); i++) {
			String description = tableNames.get(i) + "表" + classNames.get(i) + "实体类";
			String author = "杨志钊";

			String classStr = "package " + entityTargetPackage
					+ "; \n\nimport javax.persistence.Entity; \nimport javax.persistence.Id; \nimport javax.persistence.Table; \n\n"
					+ geTauthorInfo(description, author) + "\n\n@Entity \n@Table(name = \"" + tableNames.get(i)
					+ "\") \npublic class " + classNames.get(i) + " { \n\n	@Id \n ";
			String propertyStr = "";
			String methodStr = "";
			for (int j = 0; j < classPropertyNames.get(i).size(); j++) {
				String classPropertyType = classPropertyTypes.get(i).get(j);
				String classPropertyName = classPropertyNames.get(i).get(j);
				String classPropertyRemark = tableColumnRemarks.get(i).get(j);
				String classPropertyNameUpper = classPropertyName.substring(0, 1).toUpperCase()
						+ classPropertyName.substring(1, classPropertyName.length());
				String tempStr = "";
				tempStr = "	private " + classPropertyType + " " + classPropertyName + ";//" + classPropertyRemark
						+ "\n\n";
				propertyStr += tempStr;
				tempStr = "	public " + classPropertyType + " get" + classPropertyNameUpper + "() { \n		return "
						+ classPropertyName + "; \n	} \n\n";
				methodStr += tempStr;

				if (classPropertyType.equals("String") & stringTrim) {
					tempStr = "	public void set" + classPropertyNameUpper + "(" + classPropertyType + " "
							+ classPropertyName + ") { \n " + "		this." + classPropertyName + " = "
							+ classPropertyName + " == null ? null : " + classPropertyName + ".trim(); \n	} \n\n";
					methodStr += tempStr;
				} else {
					tempStr = "	public void set" + classPropertyNameUpper + "(" + classPropertyType + " "
							+ classPropertyName + ") { \n " + "		this." + classPropertyName + " = "
							+ classPropertyName + "; \n	} \n";
					methodStr += tempStr;
				}
			}

			classStr += propertyStr;
			classStr += methodStr + " \n } \n";
			System.out.println( entityTargetDir);
			CalssGeneratorConfig.outputClassFile(entityTargetDir, classNames.get(i) + ".java", classStr);
			System.out.println("实体类" + classNames.get(i) + ".java自动生成完成...");
		}

	}
	
	
	public static void generateDaoClass(Map<String, Object> map) throws Exception {

	}

	public static void generateDaoImplClass(Map<String, Object> map) throws Exception {

	}



}
