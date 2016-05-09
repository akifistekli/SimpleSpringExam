package org.spring.exam.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.spring.exam.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ResultDao {

	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Result> getResultByExamId(Long exam_id) {
		final Session session = this.sessionFactory.getCurrentSession();
		final Criteria criteria = session.createCriteria(Result.class);
		criteria.add(Restrictions.eq("exam_id", exam_id));
		return criteria.list();
	}
	
	public Long create(Result result) {
		final Session session = this.sessionFactory.getCurrentSession();
		return (Long) session.save(result);
	}

	public Result getByUserId(final Long id) {
		final Session session = this.sessionFactory.getCurrentSession();
		return (Result) session.get(Result.class, id);
	}
}
