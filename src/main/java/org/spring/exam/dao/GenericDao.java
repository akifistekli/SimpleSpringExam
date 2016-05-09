package org.spring.exam.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.exam.model.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GenericDao<T extends AbstractModel> {
	
	@Autowired
	protected SessionFactory sessionFactory;

	protected Class<T> type;

	protected Logger logger;

	@SuppressWarnings("unchecked")
	public GenericDao() {
		try {
			this.type = (Class<T>) ( (ParameterizedType) this.getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		} catch (ClassCastException e) {
			e.printStackTrace();
		}

		Class<?> type = this.getClass().getSuperclass();
		this.logger = LoggerFactory.getLogger(type);
	}
	
	@SuppressWarnings("unchecked")
	public T getById(final Long id) {

		final Session session = this.sessionFactory.getCurrentSession();

		return (T) session.get(this.type, id);
	}

	public Long create(final T t) {
		final Session session = this.sessionFactory.getCurrentSession();

		return (Long) session.save(t);
	}
	
	@SuppressWarnings("unchecked")
	public T update(final T t) {

		final Session session = this.sessionFactory.getCurrentSession();

		return (T) session.merge(t);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {

		final Session session = this.sessionFactory.getCurrentSession();
		final Criteria criteria = session.createCriteria(this.type);
//		criteria.add(Restrictions.eq("isActive", true));
		criteria.addOrder(Order.desc("createDate"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setFetchMode("*", FetchMode.JOIN);

		return criteria.list();
	}
	
	protected Criteria createCriteria() {
		final Session session = this.sessionFactory.getCurrentSession();
		return session.createCriteria(this.type);
	}
}
