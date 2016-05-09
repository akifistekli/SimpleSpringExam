package org.spring.exam.service;

import org.spring.exam.dao.InstructorDao;
import org.spring.exam.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InstructorService extends GenericService<Instructor> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private InstructorDao instructorDao;

	public void create(Instructor instructor, String branch_name) {
		instructor.setBranch(branch_name);
		String pwd = instructor.getPassword();
		instructor.setPassword(MD5(pwd));
		this.instructorDao.create(instructor);
	}
	
	public void update(Instructor instructor, String branch_name) {
		instructor.setBranch(branch_name);
		String pwd = instructor.getPassword();
		instructor.setPassword(MD5(pwd));
		this.instructorDao.update(instructor);
	}

	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

}
