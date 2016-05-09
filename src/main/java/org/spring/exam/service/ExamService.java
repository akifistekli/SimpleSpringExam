package org.spring.exam.service;

import java.util.List;

import org.spring.exam.dao.ExamDao;
import org.spring.exam.model.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExamService extends GenericService<Exam> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ExamDao examDao;

	public void create(Exam exam, String branch_name) {
		exam.setBranch(branch_name);
		this.examDao.create(exam);
	}
	
	public void update(Exam exam, String branch_name) {
		exam.setBranch(branch_name);
		this.examDao.update(exam);
	}
	
	public List<Exam> getAllActive() {
		return this.examDao.getAllActive();
	}
	
}
