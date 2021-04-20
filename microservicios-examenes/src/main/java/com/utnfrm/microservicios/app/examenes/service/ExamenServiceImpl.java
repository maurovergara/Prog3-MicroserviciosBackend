package com.utnfrm.microservicios.app.examenes.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utnfrm.microservicios.app.examenes.repository.AsignaturaRepository;
import com.utnfrm.microservicios.app.examenes.repository.ExamenRepository;
import com.utnfrm.microservicios.commons.examenes.entity.Asignatura;
import com.utnfrm.microservicios.commons.examenes.entity.Examen;
import com.utnfrm.microservicios.commons.services.CommonServiceImpl;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService {

	@Autowired
	private AsignaturaRepository asignaturaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Examen> findByNombre(String term) {
		return repository.findByNombre(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Asignatura> findAllAsignaturas() {
		return asignaturaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds) {
		return repository.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds);
	}

}
