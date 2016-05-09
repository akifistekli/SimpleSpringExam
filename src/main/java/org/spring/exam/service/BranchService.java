package org.spring.exam.service;

import java.util.List;

import org.spring.exam.dao.BranchDao;
import org.spring.exam.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BranchService extends GenericService<Branch> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private BranchDao branchDao;

	public List<Branch> getAllActive() {
		return this.branchDao.getAllActive();
	}

}
