package com.utnfrm.microservicios.app.cursos.services;


import com.utnfrm.microservicios.app.cursos.entities.Curso;
import com.utnfrm.microservicios.commons.alumnos.entity.Alumno;
import com.utnfrm.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso> {
	
	public Curso findCursoByAlumnoId(Long id);
	
	public Iterable<Long> obtenerExamenesIdsConRespuestaAlumno(Long alumnoId);
	
	public Iterable<Alumno> getAlumnosByCurso(Iterable<Long> ids);
	
	public void deleteCursoAlumnoById(Long id);

}
