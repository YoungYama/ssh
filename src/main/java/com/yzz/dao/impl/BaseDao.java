package com.yzz.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;

import com.yzz.dto.Page;

public class BaseDao {

	@Resource
	SessionFactory sessionFactory;

	public void delete(Class<? extends Object> clz, Object id) throws DataAccessException {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		String className = clz.getSimpleName();
		String idName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length()) + "Id";
		String idType = id.getClass().getSimpleName();
		String hql = "";
		if (idType.equals("Integer") | idType.equals("Long")) {
			hql = "DELETE FROM " + className + " WHERE " + idName + " = " + id;
		} else {
			hql = "DELETE FROM " + className + " WHERE " + idName + " = '" + id + "'";
		}

		Query query = session.createQuery(hql);
		query.executeUpdate();
		tx.commit();
		session.close();
	}

	public void delete(Object entity) throws DataAccessException {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		session.close();
	}

	public void deleteBatch(Class<? extends Object> clz, Object[] ids) throws DataAccessException {
		StringBuffer condition = new StringBuffer();
		if (ids.length <= 0) {
			return;
		} else {
			String idType = ids[0].getClass().getSimpleName();
			if (idType.equals("Integer") | idType.equals("Long")) {
				for (Object id : ids) {
					condition.append(id + ",");
				}
			} else {
				for (Object id : ids) {
					condition.append("'" + id + "',");
				}
			}
		}

		String className = clz.getSimpleName();
		String idName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length()) + "Id";

		condition.deleteCharAt(condition.lastIndexOf(","));
		String hql = "DELETE FROM " + className + " WHERE " + idName + " in(" + condition + ")";
		System.out.println(hql);

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.executeUpdate();
		tx.commit();
		session.close();
	}

	public Object findByPrimaryKey(Class<? extends Object> clz, Object id) {
		Session session = getSession();
		
		Object result = session.get(clz, (Serializable) id);

		session.close();
		return result;
	}

	public List<?> findByExampleAndPage(Object entity, Page page) throws DataAccessException, Exception {
		StringBuffer hql = new StringBuffer();
		Class<? extends Object> clz = entity.getClass();

		String className = clz.getSimpleName();
		hql.append("FROM " + className + " WHERE 1 = 1");

		// 得到对象的所有私有属性
		Field[] fields = clz.getDeclaredFields();
		Method m = null;
		for (Field field : fields) {
			field.setAccessible(true);
			Object propertyType = field.getType().getSimpleName();
			String propertyName = field.getName();
			String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
			m = clz.getMethod(methodName);
			Object propertyValue = m.invoke(entity);
			if (propertyValue != null) {
				if (propertyType.equals("Integer") | propertyType.equals("Long")) {
					hql.append("AND " + propertyName + " = " + propertyValue);
				} else {
					hql.append("AND " + propertyName + " = '" + propertyValue + "'");
				}
			}

		}

		List<?> list = findByHqlAndPage(hql.toString(), page);

		return list;
	}

	public List<? extends Object> findByHqlAndPage(String hql, Page page) {
		hql = hql.replace("from", "FROM");
		Session session = getSession();
		Query query = null;
		if (page == null) {
			query = session.createQuery(hql);
		} else {
			String hqlCount = "SELECT COUNT(*) " + hql.substring(hql.indexOf("FROM"), hql.length());
			if (null != page.getOrderField()) {
				String hqlOrder = " ORDER BY " + page.getOrderField() + " " + page.getSort();
				hql += hqlOrder;
			}

			int start = (page.getCurrentPage() - 1) * page.getPageSize();

			query = session.createQuery(hqlCount);
			int count = Integer.parseInt(query.uniqueResult().toString());
			page.setTotalRecord(count);

			query = session.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(page.getPageSize());
		}

		List<?> list = query.list();

		session.close();

		return list;
	}

	public Serializable save(Object entity) throws DataAccessException {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Serializable result = session.save(entity);
		tx.commit();
		session.close();
		return result;
	}

	public void saveOrUpdate(Object entity) throws DataAccessException {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(entity);
		tx.commit();
		session.close();
	}

	public void update(Object entity) throws DataAccessException {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		session.close();
	}

	public Session getSession() {
//		Session session = sessionFactory.getCurrentSession();
		Session session = sessionFactory.openSession();
		
		return session;
	}

}
