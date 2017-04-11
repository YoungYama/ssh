package com.yzz.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CalssGeneratorConfig {

	public static String classGeneratorConfigXML;

	public static void setClassGeneratorConfigXML(String classGeneratorConfigXML) {
		CalssGeneratorConfig.classGeneratorConfigXML = classGeneratorConfigXML;
	}

	public static void outputClassFile(String distDir, String className, String classStr) throws Exception {
		File file = new File(distDir + "\\" + className);

		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(classStr.getBytes("UTF-8"));
		fos.close();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getTableAndClassDatas() throws Exception {
		Map<String, Object> map = parseClassGeneratorXML();
		Connection conn = (Connection) map.get("conn");
		if (conn == null) {
			conn = getConnection(map);
		}
		
		List<String> tableNames = (List<String>) map.get("tableNames");
		
		List<List<String>> tableColumnNames = new ArrayList<List<String>>();
		List<List<String>> tableColumnRemarks = new ArrayList<List<String>>();
		List<String> classNames = new ArrayList<String>();
		List<List<String>> classPropertyTypes = new ArrayList<List<String>>();
		List<List<String>> classPropertyNames = new ArrayList<List<String>>();
		for (String tableName : tableNames) {
			String className = HumpUtil.lineToHump(tableName);
			className = className.substring(0, 1).toUpperCase() + className.substring(1, className.length());
			classNames.add(className);

			List<String> columnNames = new ArrayList<String>();
			List<String> columnRemarks = new ArrayList<String>();
			List<String> propertyTypes = new ArrayList<String>();
			List<String> propertyNames = new ArrayList<String>();

			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
			while (resultSet.next()) {
				String tempTableName = resultSet.getString("TABLE_NAME");
				if (tableName.equals(tempTableName)) {
					// 其他数据库不需要这个方法的，直接传null，这个是oracle和db2这么用
					// ResultSet rs = dbmd.getColumns(null,
					// getSchema(conn),tableName.toUpperCase(), "%");
					ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
					while (rs.next()) {
						// 获得指定列的列名
						String columnName = rs.getString("COLUMN_NAME");
						String remark = rs.getString("REMARKS");
						if (remark.equals(null) | remark.equals("")) {
							remark = columnName;
						}
						String propertyName = HumpUtil.lineToHump(columnName);
						columnNames.add(columnName);
						columnRemarks.add(remark);
						propertyTypes.add(getBasicType(rs.getString("TYPE_NAME")));
						propertyNames.add(propertyName);
					}

					break;
				}
			}

			tableColumnNames.add(columnNames);
			tableColumnRemarks.add(columnRemarks);
			classPropertyTypes.add(propertyTypes);
			classPropertyNames.add(propertyNames);

		}

		map.put("classNames", classNames);
		map.put("tableColumnNames", tableColumnNames);
		map.put("tableColumnRemarks", tableColumnRemarks);
		map.put("classPropertyTypes", classPropertyTypes);
		map.put("classPropertyNames", classPropertyNames);
		
		return map;
	}

	// 其他数据库不需要这个方法 oracle和db2需要
	public static String getSchema(Connection conn) throws Exception {
		String schema;
		schema = conn.getMetaData().getUserName();
		if ((schema == null) || (schema.length() == 0)) {
			throw new Exception("ORACLE数据库模式不允许为空");
		}
		return schema.toUpperCase().toString();

	}

	public static String getBasicType(String sqlType) {
		String result = null;
		switch (sqlType) {
		case "VARCHAR":
			result = "String";
			break;
		case "TEXT":
			result = "String";
			break;
		case "MEDIUMTEXT":
			result = "String";
			break;
		case "LONGVARCHAR":
			result = "String";
			break;
		case "NUMERIC":
			result = "BigDecimal";
			break;
		case "BIT":
			result = "Boolean";
			break;
		case "INTEGER":
			result = "Integer";
			break;
		case "INT":
			result = "Integer";
			break;
		case "BIGINT":
			result = "Long";
			break;
		case "REAL":
			result = "Float";
			break;
		case "DOUBLE":
			result = "Double";
			break;
		case "VARBINARY":
			result = "byte[]";
			break;
		case "LONGVARBINARY":
			result = "byte[]";
			break;
		case "DATE":
			result = "Date";
			break;
		case "TIME":
			result = "Time";
			break;
		case "TIMESP":
			result = "Timestamp";
			break;
		default:
			result = sqlType;
			break;
		}

		return result;
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> parseClassGeneratorXML() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String xml = "";
		FileInputStream fis = new FileInputStream(new File(classGeneratorConfigXML));

		byte[] b = new byte[1024];

		int len;
		while ((len = fis.read(b)) != -1) {
			xml += new String(b, "UTF-8");
		}
		fis.close();

		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
		Document doc = docbuilder.parse(new ByteArrayInputStream(xml.trim().getBytes(Charset.defaultCharset())));

		Node jdbcConnection = doc.getElementsByTagName("jdbcConnection").item(0);
		NamedNodeMap map = jdbcConnection.getAttributes();
		String driverClass = map.getNamedItem("driverClass").getNodeValue();
		String url = map.getNamedItem("url").getNodeValue();
		String user = map.getNamedItem("user").getNodeValue();
		String password = map.getNamedItem("password").getNodeValue();

		Node javaEntityGenerator = doc.getElementsByTagName("javaEntityGenerator").item(0);
		String entityTargetPackage = javaEntityGenerator.getAttributes().getNamedItem("targetPackage").getNodeValue();
		String entityTargetDir = entityTargetPackage.replace(".", "\\");

		boolean stringTrim = Boolean
				.valueOf(javaEntityGenerator.getAttributes().getNamedItem("stringTrim").getNodeValue());

		Node javaDaoGenerator = doc.getElementsByTagName("javaDaoGenerator").item(0);
		String daoTargetPackage = javaDaoGenerator.getAttributes().getNamedItem("targetPackage").getNodeValue();
		String daoTargetDir = daoTargetPackage.replace(".", "\\");

		String daoImplTargetPackage = daoTargetPackage + ".impl";
		String daoImplTargetDir = daoTargetDir + "\\impl";

		NodeList tables = doc.getElementsByTagName("table");
		List<String> tableNames = new ArrayList<String>();
		for (int i = 0; i < tables.getLength(); i++) {
			String tableName = tables.item(i).getAttributes().getNamedItem("tableName").getNodeValue();
			tableNames.add(tableName);
		}

		result.put("driverClass", driverClass);
		result.put("url", url);
		result.put("user", user);
		result.put("password", password);
		result.put("entityTargetDir", entityTargetDir);
		result.put("entityTargetPackage", entityTargetPackage);
		result.put("stringTrim", stringTrim);
		result.put("daoTargetDir", daoTargetDir);
		result.put("daoTargetPackage", daoTargetPackage);
		result.put("daoImplTargetDir", daoImplTargetDir);
		result.put("daoImplTargetPackage", daoImplTargetPackage);

		if (tables.getLength() <= 0) {
			Connection conn = getConnection(result);
			tableNames = getTableNames(conn);
			result.put("conn", conn);
		}

		result.put("tableNames", tableNames);

		return result;
	}

	public static Connection getConnection(Map<String, Object> map) throws Exception {
		Connection conn = null;
		Class.forName(map.get("driverClass").toString());
		String url = map.get("url").toString();
		String user = map.get("user").toString();
		String password = map.get("password").toString();
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public static List<String> getTableNames(Connection conn) throws Exception {
		List<String> tableNames = new ArrayList<String>();
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet rs = metaData.getTables(conn.getCatalog(), "root", null, new String[] { "TABLE" });
		while (rs.next()) {
			tableNames.add(rs.getString("TABLE_NAME"));
		}
		return tableNames;
	}

}
