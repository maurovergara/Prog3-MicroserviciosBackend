package com.utnfrm.microservicios.app.respuestas.services;

import java.util.List;

import com.utnfrm.microservicios.app.respuestas.entity.Respuesta;

public interface RespuestaService {

	public List<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	
	public List<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
	
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
	
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
}
