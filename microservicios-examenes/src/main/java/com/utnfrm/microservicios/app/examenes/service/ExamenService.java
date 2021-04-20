package com.utnfrm.microservicios.app.examenes.service;

import java.util.List;

import com.utnfrm.microservicios.commons.examenes.entity.Asignatura;
import com.utnfrm.microservicios.commons.examenes.entity.Examen;
import com.utnfrm.microservicios.commons.services.CommonService;

public interface ExamenService extends CommonService<Examen>{

	public List<Examen> findByNombre(String term);
	
	public Iterable<Asignatura> findAllAsignaturas();
	
	public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds);
}
