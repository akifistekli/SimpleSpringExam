package org.spring.exam.service;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.exam.dao.GenericDao;
import org.spring.exam.model.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public abstract class GenericService<T extends AbstractModel> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Logger logger;

	public GenericService() {
		Class<?> type = this.getClass().getSuperclass();
		this.logger = LoggerFactory.getLogger(type);
	}

	@Autowired
	protected GenericDao<T> genericDao;

	public T getById(final Long id) {
		return (T) genericDao.getById(id);
	}

	public Long create(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

		return this.genericDao.create(t);
	}

	public T update(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

		return this.genericDao.update(t);
	}

	public List<T> getAll() {
		return this.genericDao.getAll();
	}
	
	public void changeActiveStatus(Long id) {
		T t = genericDao.getById(id);
		if (t.getIsActive() == true) {
			t.setIsActive(false);
		} else {
			t.setIsActive(true);
		}
		genericDao.update(t);
	}
}
