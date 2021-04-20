package com.utnfrm.microservicios.app.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.utnfrm.microservicios.commons.alumnos.entity.Alumno;

@FeignClient(name = "microservicio-usuarios")
public interface AlumnoFeingClient {

	@GetMapping("/alumnos-por-curso")
	public Iterable<Alumno> getAlumnosByCurso(@RequestParam Iterable<Long> ids);
}
