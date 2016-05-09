package org.spring.exam.service;

import org.spring.exam.model.Admin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService extends GenericService<Admin> {

	private static final long serialVersionUID = 1L;

}
