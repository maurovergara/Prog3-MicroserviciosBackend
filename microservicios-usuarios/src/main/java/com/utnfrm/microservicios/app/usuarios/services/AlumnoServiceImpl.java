package com.utnfrm.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utnfrm.microservicios.app.usuarios.client.CursoFeignClient;
import com.utnfrm.microservicios.app.usuarios.repositories.AlumnoRepository;
import com.utnfrm.microservicios.commons.alumnos.entity.Alumno;
import com.utnfrm.microservicios.commons.services.CommonServiceImpl;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

	@Autowired
	private CursoFeignClient cursoClient;
	
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByNombreOrApellido(String term) {
		return repository.findByNombreOrApellido(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAllById(Iterable<Long> ids) {
		return repository.findAllById(ids);
	}

	@Override
	public void deleteCursoAlumnoById(Long id) {
		cursoClient.deleteCursoAlumnoById(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) throws Exception {
		super.deleteById(id);
		this.deleteCursoAlumnoById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findAll() throws Exception {
		return repository.findAllByOrderByIdAsc();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Alumno> findAll(Pageable pageable) throws Exception {
		return repository.findAllByOrderByIdAsc(pageable);
	}
	
	
}
