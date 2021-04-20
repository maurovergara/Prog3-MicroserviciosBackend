package com.utnfrm.microservicios.app.cursos.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utnfrm.microservicios.app.cursos.clients.AlumnoFeingClient;
import com.utnfrm.microservicios.app.cursos.clients.RespuestaFeingClient;
import com.utnfrm.microservicios.app.cursos.entities.Curso;
import com.utnfrm.microservicios.app.cursos.repository.CursoRepository;
import com.utnfrm.microservicios.commons.alumnos.entity.Alumno;
import com.utnfrm.microservicios.commons.services.CommonServiceImpl;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

	@Autowired
	protected RespuestaFeingClient client;
	@Autowired
	protected AlumnoFeingClient clientAlumno;
	
	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return repository.findCursoByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestaAlumno(Long alumnoId) {
		return client.obtenerExamenesIdsConRespuestaAlumno(alumnoId);
	}

	@Override
	public Iterable<Alumno> getAlumnosByCurso(Iterable<Long> ids) {
		return clientAlumno.getAlumnosByCurso(ids);
	}

	@Override
	@Transactional
	public void deleteCursoAlumnoById(Long id) {
		repository.deleteCursoAlumnoById(id);
	}

}
