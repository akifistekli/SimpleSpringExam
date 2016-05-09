package org.spring.exam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "exam_result")
public class Result {

	@Id
	@GeneratedValue
	private Long id;
	private Long exam_id;
	private Long user_id;
	private int no_question;
	private int no_correct;
	private int no_wrong;
	private int no_blank;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExam_id() {
		return exam_id;
	}

	public void setExam_id(Long exam_id) {
		this.exam_id = exam_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public int getNo_question() {
		return no_question;
	}

	public void setNo_question(int no_question) {
		this.no_question = no_question;
	}

	public int getNo_correct() {
		return no_correct;
	}

	public void setNo_correct(int no_correct) {
		this.no_correct = no_correct;
	}

	public int getNo_wrong() {
		return no_wrong;
	}

	public void setNo_wrong(int no_wrong) {
		this.no_wrong = no_wrong;
	}

	public int getNo_blank() {
		return no_blank;
	}

	public void setNo_blank(int no_blank) {
		this.no_blank = no_blank;
	}
	
}
