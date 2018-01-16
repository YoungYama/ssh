package com.yzz.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.espeed.dao.CentreAccountDao;
import com.espeed.util.DateFormat;

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
		String classGeneratorConfigXML =projectPath + "\\src\\test\\java\\com\\espeed\\test\\yzz-class-generator.xml";
		
		CalssGeneratorConfig.setClassGeneratorConfigXML(classGeneratorConfigXML);
		Map<String, Object> map = CalssGeneratorConfig.getTableAndClassDatas();
		
		CalssGeneratorForHibernate.setTableAndClassDatas(map);
		CalssGeneratorForHibernate.generateEntityClass();
		CalssGeneratorForHibernate.generateDaoClass();
		CalssGeneratorForHibernate.generateDaoImplClass();
	}
	
	private static boolean isTuo = false;//属性是否驼峰化
	private static String AUTHOR = "杨志钊";

	private static List<String> tableNames;
	private static List<List<String>> tableColumnNames;
	private static List<List<String>> tableColumnJdbcTypes;
	private static List<List<String>> tableColumnRemarks;
	private static List<String> classNames;
	private static List<List<String>> classPropertyTypes;
	private static List<List<String>> propertyTypeFullNames;
	private static List<List<String>> classPropertyNames;
	private static String dtoTargetPackage;
	private static String utilTargetPackage;
	private static String entityTargetDir;
	private static String entityTargetPackage;
	private static String daoTargetDir;
	private static String daoTargetPackage;
	private static String daoImplTargetDir;
//	private static String daoImplTargetPackage;
	private static String serviceTargetDir;
	private static String serviceTargetPackage;
	private static String serviceImplTargetDir;
	private static String serviceImplTargetPackage;
	private static String ctrlTargetDir;
	private static String ctrlTargetPackage;
	private static boolean stringTrim;

	public static void setTableAndClassDatas(Map<String, Object> map) {
		tableNames = (List<String>) map.get("tableNames");
		tableColumnNames = (List<List<String>>) map.get("tableColumnNames");
		tableColumnJdbcTypes = (List<List<String>>) map.get("tableColumnJdbcTypes");
		tableColumnRemarks = (List<List<String>>) map.get("tableColumnRemarks");
		classNames = (List<String>) map.get("classNames");
		classPropertyTypes = (List<List<String>>) map.get("classPropertyTypes");
		propertyTypeFullNames = (List<List<String>>) map.get("propertyTypeFullNames");
		classPropertyNames = (List<List<String>>) map.get("classPropertyNames");
		dtoTargetPackage = map.get("dtoTargetPackage").toString();
		utilTargetPackage = map.get("utilTargetPackage").toString();
		entityTargetDir = projectPath + map.get("entityTargetDir");
		entityTargetPackage = map.get("entityTargetPackage").toString();
		daoTargetDir = projectPath + map.get("daoTargetDir");
		daoTargetPackage = map.get("daoTargetPackage").toString();
		daoImplTargetDir = projectPath + map.get("daoImplTargetDir");
//		daoImplTargetPackage = map.get("daoImplTargetPackage").toString();
		serviceTargetDir = projectPath + map.get("serviceTargetDir").toString();
		serviceTargetPackage = map.get("serviceTargetPackage").toString();
		serviceImplTargetDir = projectPath + map.get("serviceImplTargetDir").toString();
		serviceImplTargetPackage = map.get("serviceImplTargetPackage").toString();
		ctrlTargetDir = projectPath + map.get("ctrlTargetDir").toString();
		ctrlTargetPackage = map.get("ctrlTargetPackage").toString();
		stringTrim = (Boolean) map.get("stringTrim");
	}
	 public static String geTauthorInfo(String description, String author) {
			String authorInfo = "/** \n"+
					 "* \n"+
					 "* @项目名称: 鹰眼搜搜索系统 \n"+
					 "* @版权所有: 深圳市科飞时速网络技术有限公司(0755-88843776) \n"+
					 "* @技术支持: info@21gmail.com \n"+
					 "* @单元名称: " + description + " \n"+
					 "* @开始时间: " + DateFormat.getNowDate() + " \n"+
					 "* @开发人员: " + author + " \n"+
					 "*/";
			return authorInfo;
	}

	public static void generateEntityClass() throws Exception {
		for (int i = 0; i < tableNames.size(); i++) {
			String description = tableNames.get(i) + "表" + classNames.get(i) + "实体类";

			String classStr = "package " + entityTargetPackage
					+ "; \n\nimport java.io.Serializable; \n\nimport javax.persistence.Entity; \nimport javax.persistence.Id; \nimport javax.persistence.Table; \n\n"
					+ geTauthorInfo(description, AUTHOR) + "\n\n@Entity \n@Table(name = \"" + tableNames.get(i)
					+ "\") \npublic class " + classNames.get(i) + " implements Serializable { \n\n	private static final long serialVersionUID = 1L; \n\n";
			String propertyStr = "";
			String methodStr = "	@Id \n";
			for (int j = 0; j < classPropertyNames.get(i).size(); j++) {
				String classPropertyType = classPropertyTypes.get(i).get(j);
				String classPropertyName = classPropertyNames.get(i).get(j);
				String tableColumnName = tableColumnNames.get(i).get(j);
				String classPropertyRemark = tableColumnRemarks.get(i).get(j);
				String classPropertyNameUpper = classPropertyName.substring(0, 1).toUpperCase()
						+ classPropertyName.substring(1, classPropertyName.length());
				
				//属性不驼峰
				if (!isTuo) {
					
					classPropertyName = tableColumnName;
				}
				
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
			
			CalssGeneratorConfig.outputClassFile(entityTargetDir, classNames.get(i) + ".java", classStr);
			System.out.println("实体类" + classNames.get(i) + ".java自动生成完成...");
		}

	}
	
	
	public static void generateDaoClass() throws Exception {
		for (int i = 0; i < classNames.size(); i++) {
			String description = "实体类" + classNames.get(i) + "的DAO接口";
			String info = geTauthorInfo(description, AUTHOR);
			String className = classNames.get(i);
			String entityClassName =className;
//			String entityIdName = entityClassName.substring(0, 1).toLowerCase()
//					+ entityClassName.substring(1, entityClassName.length()) + "Id";
			String entityIdName = classPropertyNames.get(i).get(0);
			className += "Dao";
			
			String classIdPropertyType = classPropertyTypes.get(i).get(0);
			
			String daoTemplate = "package " + daoTargetPackage + ";\n"+
					"\n"+
					"import java.util.List;\n"+
					"\n"+
					"import " + dtoTargetPackage + ".PageBean;\n"+
					"import " + entityTargetPackage + "." + entityClassName + ";\n"+
					"\n" + info + " \n"+
					"\n"+
					"public interface " + className + " {\n"+
					"\n	/**实体插入*/\n"+
					"	public int addPojo(" + entityClassName + " entity) throws Exception;\n"+
					"\n	/**实体编辑*/\n"+
					"	public void updatePojo(" + entityClassName + " entity) throws Exception;\n"+
					"\n	/**HQL查询*/\n"+
					"	public List<" + entityClassName + "> findByHql(String hql) throws Exception;\n"+
					"\n	/**HQL指定条查询*/\n"+
					"	public List<" + entityClassName + "> findByHqlSet(String hql,int num) throws Exception;\n"+
					"\n	/**SQL查询*/\n"+
					"	public List<Object> findBySqlFind(String sql) throws Exception;\n"+
					"\n	/**HQL查询分页*/\n"+
					"	public List<" + entityClassName + "> findByHqlPage(String hql,String hqlcount,PageBean pb) throws Exception;\n"+
					"\n	/**HQL更新*/\n"+
					"	public void updateByHql(String hql) throws Exception;\n"+
					"\n	/**sql更新*/\n"+
					"	public void updateBySql(String sql) throws Exception;\n"+
					"\n	/**HQL查询数量*/\n"+
					"	public int findByHqlCount(String hql) throws Exception;\n"+
					"\n"+
					"}\n";
			
			CalssGeneratorConfig.outputClassFile(daoTargetDir, className + ".java", daoTemplate);
			System.out.println("DAO接口" + className + ".java自动生成完成...");
		}
		
	}

	public static void generateDaoImplClass() throws Exception {
		for (int i = 0; i < classNames.size(); i++) {
			String description = "实体类" + classNames.get(i) + "的DAO接口";
			String info = geTauthorInfo(description, AUTHOR);
			String className = classNames.get(i);
			String entityClassName =className;
//			String entityIdName = entityClassName.substring(0, 1).toLowerCase()
//					+ entityClassName.substring(1, entityClassName.length()) + "Id";
			String entityIdName = classPropertyNames.get(i).get(0);
			
			className += "DaoImpl";
			
			String classIdPropertyType = classPropertyTypes.get(i).get(0);
			
			String daoTemplate = "package " + daoTargetPackage + ";\n"+
					"\n"+
					"import java.util.List;\n"+
					"\n"+
					"import " + dtoTargetPackage + ".PageBean;\n"+
					"import " + entityTargetPackage + "." + entityClassName + ";\n"+
					"import " + daoTargetPackage + "." + entityClassName + "Dao;\n"+
					"\n" + info + " \n"+
					"\n"+
					"public class " + className + " extends CentreHibernateBaseDAOImpl<" + entityClassName + ", Long> implements " + entityClassName + "Dao {\n"+
					"\n	/**实体插入*/\n"+
					"	public int addPojo(" + entityClassName + " entity) throws Exception {\n		super.add(entity);\n}\n"+
					"\n	/**实体编辑*/\n"+
					"	public void updatePojo(" + entityClassName + " entity) throws Exception {\n		super.update(entity);\n}\n"+
					"\n	/**HQL查询*/\n"+
					"	public List<" + entityClassName + "> findByHql(String hql) throws Exception {\n		return super.getAll(hql);\n}\n"+
					"\n	/**HQL指定条查询*/\n"+
					"	public List<" + entityClassName + "> findByHqlSet(String hql,int num) throws Exception {\n		return super.findBySet(hql, num);\n}\n"+
					"\n	/**SQL查询*/\n"+
					"	public List<Object> findBySqlFind(String sql) throws Exception {\n		return super.findBySql(sql);\n}\n"+
					"\n	/**HQL查询分页*/\n"+
					"	public List<" + entityClassName + "> findByHqlPage(String hql,String hqlcount,PageBean pb) throws Exception {\n		return super.findByPage(hql, hqlcount, pb);\n}\n"+
					"\n	/**HQL更新*/\n"+
					"	public void updateByHql(String hql) throws Exception {\n		super.updateorDelByHql(hql);\n}\n"+
					"\n	/**sql更新*/\n"+
					"	public void updateBySql(String sql) throws Exception {\n		super.updateorDelSql(sql);\n}\n"+
					"\n	/**HQL查询数量*/\n"+
					"	public int findByHqlCount(String hql) throws Exception {\n		return super.count(hql);\n}\n"+
					"\n"+
					"}\n";
			
			CalssGeneratorConfig.outputClassFile(daoImplTargetDir, className + ".java", daoTemplate);
			System.out.println("DAO接口实现类" + className + ".java自动生成完成...");
		}
		
	}



}
