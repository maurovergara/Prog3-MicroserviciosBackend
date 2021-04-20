package com.utnfrm.microservicios.app.usuarios.services;

import java.util.List;


import com.utnfrm.microservicios.commons.alumnos.entity.Alumno;
import com.utnfrm.microservicios.commons.services.CommonService;

public interface AlumnoService extends CommonService<Alumno>{
	
	public List<Alumno> findByNombreOrApellido(String term);
	public Iterable<Alumno> findAllById(Iterable<Long> ids);
	public void deleteCursoAlumnoById(Long id);
}
