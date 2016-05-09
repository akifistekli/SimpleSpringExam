package org.spring.exam.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.spring.exam.model.Exam;
import org.springframework.stereotype.Repository;

@Repository
public class ExamDao extends GenericDao<Exam> {

	@SuppressWarnings("unchecked")
	public List<Exam> getAllActive() {
		Criteria c = this.createCriteria();
		c.add(Restrictions.eq("isActive", true));
		return c.list();
	}
	
}
