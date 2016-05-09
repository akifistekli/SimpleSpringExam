package org.spring.exam.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.spring.exam.model.Branch;
import org.springframework.stereotype.Repository;

@Repository
public class BranchDao extends GenericDao<Branch> {

	@SuppressWarnings("unchecked")
	public List<Branch> getAllActive() {
		Criteria c = this.createCriteria();
		c.add(Restrictions.eq("isActive", true));
		return c.list();
	}

}
