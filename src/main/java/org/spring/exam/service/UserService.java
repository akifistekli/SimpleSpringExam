package org.spring.exam.service;

import org.spring.exam.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService extends GenericService<User> {

	private static final long serialVersionUID = 1L;
	
}
