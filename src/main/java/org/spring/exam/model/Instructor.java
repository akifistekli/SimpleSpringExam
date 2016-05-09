package org.spring.exam.model;

import javax.persistence.Entity;

@Entity(name = "exam_instructor")
public class Instructor extends AbstractUser {

	private String branch;

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
}
