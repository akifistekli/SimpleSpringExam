package org.spring.exam.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.spring.exam.model.Question;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDao extends GenericDao<Question> {

	@SuppressWarnings("unchecked")
	public List<Question> getAllActive() {
		Criteria c = this.createCriteria();
		c.add(Restrictions.eq("isActive", true));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<Question> getQuestionsByExamId(Long exam_id) {
		Criteria c = this.createCriteria();
		c.add(Restrictions.eq("exam_id", exam_id));
		return c.list();
	}

}
