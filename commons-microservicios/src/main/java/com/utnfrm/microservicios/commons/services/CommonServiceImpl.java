package com.utnfrm.microservicios.commons.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


public class CommonServiceImpl<E, R extends PagingAndSortingRepository<E, Long>> implements CommonService<E> {

	@Autowired
	protected R repository;

	@Override
	@Transactional(readOnly = true)
	public List<E> findAll() throws Exception {
		try {
			List<E> entity = (List<E>) repository.findAll();
			return entity;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public E findById(Long id) throws Exception {
		try {
			Optional<E> entityOptional = repository.findById(id);
			return entityOptional.get();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public E save(E entity) throws Exception {
		try {
			entity = repository.save(entity);
			return entity;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public E update(Long id, E entity) throws Exception {
		try {
			Optional<E> entityOptional = repository.findById(id);
			E alumnoUpdate = entityOptional.get();
			alumnoUpdate = repository.save(entity);
			return alumnoUpdate;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void deleteById(Long id) throws Exception {
		try {
			repository.deleteById(id);
	} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<E> findAll(Pageable pageable) throws Exception {
		return repository.findAll(pageable);
	}

}
