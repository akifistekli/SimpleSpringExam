package org.spring.exam.model;

import javax.persistence.Entity;

@Entity(name = "exam_user")
public class User extends AbstractUser {

	private Long tcNo;

	public Long getTcNo() {
		return tcNo;
	}

	public void setTcNo(Long tcNo) {
		this.tcNo = tcNo;
	}

}
