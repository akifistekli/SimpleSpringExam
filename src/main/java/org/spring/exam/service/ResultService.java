package org.spring.exam.service;

import java.util.List;

import org.spring.exam.dao.ResultDao;
import org.spring.exam.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResultService {
	
	@Autowired
	private ResultDao resultDao;

	public List<Result> getResultByExamId(Long exam_id) {
		return this.resultDao.getResultByExamId(exam_id);
	}
	
	public Result getByUserId(Long id){
		return this.resultDao.getByUserId(id);
	}
	
	public void create(int q, int c, int w, int b) {
		Result result = new Result();
		result.setExam_id(22L);
		result.setUser_id(23L);
		result.setNo_question(q);
		result.setNo_correct(c);
		result.setNo_wrong(w);
		result.setNo_blank(b);
		this.resultDao.create(result);
	}
}
