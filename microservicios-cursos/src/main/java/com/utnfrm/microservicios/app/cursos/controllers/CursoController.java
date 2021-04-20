package com.utnfrm.microservicios.app.cursos.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.utnfrm.microservicios.app.cursos.entities.Curso;
import com.utnfrm.microservicios.app.cursos.entities.CursoAlumno;
import com.utnfrm.microservicios.app.cursos.services.CursoService;
import com.utnfrm.microservicios.commons.alumnos.entity.Alumno;
import com.utnfrm.microservicios.commons.controllers.CommonController;
import com.utnfrm.microservicios.commons.examenes.entity.Examen;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@DeleteMapping("/eliminar-alumno/{id}")
	public ResponseEntity<?> deleteCursoAlumnoById(@PathVariable Long id){
		try {
			service.deleteCursoAlumnoById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@GetMapping
	@Override
	public ResponseEntity<?> getAll() {
		try {
			List<Curso> cursos = service.findAll().stream().map(c -> {
				c.getCursoAlumnos().forEach(ca ->{
					Alumno alumnos = new Alumno();
					alumnos.setId(ca.getAlumnoId());
					c.addAlumno(alumnos);
				});	
				return c;
			}).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(cursos);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@GetMapping("/{id}")
	@Override
	public ResponseEntity<?> getOne(@PathVariable Long id){
		try {
			Optional<Curso> op = Optional.ofNullable(service.findById(id));
			
			if(op.isEmpty()) {
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
			}
			Curso curso = op.get();
			
			if(curso.getCursoAlumnos().isEmpty() == false) {
				List<Long> ids = curso.getCursoAlumnos().stream().map(ca -> ca.getAlumnoId()).collect(Collectors.toList());
				
				List<Alumno> alumnos = (List<Alumno>) service.getAlumnosByCurso(ids); 
				
				curso.setAlumnos(alumnos);
			}
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(curso);
			
		} catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@Override
	@GetMapping("/pagina")
	public ResponseEntity<?> getAll(Pageable pageable){
		try {
			Page<Curso> cursos = service.findAll(pageable).map(curso -> {
				curso.getCursoAlumnos().forEach(ca ->{
					Alumno alumnos = new Alumno();
					alumnos.setId(ca.getAlumnoId());
					curso.addAlumno(alumnos);
				});	
				return curso;
			});
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(cursos);
		} catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEntity(@PathVariable Long id, @RequestBody Curso curso) {
		try {
			Optional<Curso> op = Optional.ofNullable(this.service.findById(id));

			if (op.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
			}
			Curso dbCurso = op.get();
			dbCurso.setNombre(curso.getNombre());

			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}

	}

	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		try {
			Optional<Curso> op = Optional.ofNullable(this.service.findById(id));

			if (op.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
			}
			Curso dbCurso = op.get();
			alumnos.forEach(al -> {
				CursoAlumno cursoAlumno = new CursoAlumno();
				cursoAlumno.setAlumnoId(al.getId());
				cursoAlumno.setCurso(dbCurso);
				dbCurso.addCursoAlumnos(cursoAlumno);
			});
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}

	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> deleteAlumnos(@RequestBody Alumno alumno, @PathVariable Long id) {
		try {

			Optional<Curso> op = Optional.ofNullable(this.service.findById(id));

			if (op.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
			}

			Curso dbCurso = op.get();
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(alumno.getId());
			dbCurso.removeCursoAlumno(cursoAlumno);

			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}

	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@Valid @RequestBody List<Examen> examenes, BindingResult result,
			@PathVariable Long id) {
		try {

			if (result.hasErrors()) {
				return this.validar(result);
			}

			Optional<Curso> op = Optional.ofNullable(this.service.findById(id));

			if (op.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
			}
			Curso dbCurso = op.get();
			examenes.forEach(e -> {
				dbCurso.addExamen(e);
			});
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}

	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> deleteExamen(@RequestBody Examen examen, @PathVariable Long id) {
		try {
			Optional<Curso> op = Optional.ofNullable(this.service.findById(id));

			if (op.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
			}

			Curso dbCurso = op.get();
			dbCurso.removeExamen(examen);

			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}

	@GetMapping("/alumnos/{id}")
	public ResponseEntity<?> searchByAlumnoId(@PathVariable Long id) {
		try {
			Curso curso = service.findCursoByAlumnoId(id);

			if (curso != null) {
				List<Long> examenesId = (List<Long>) service.obtenerExamenesIdsConRespuestaAlumno(id);
				if(examenesId != null && examenesId.size() > 0) {
				List<Examen> examanes = curso.getExamenes().stream().map(examen -> {
					if (examenesId.contains(examen.getId())) {
						examen.setRespondido(true);
					}
					return examen;
				}).collect(Collectors.toList());

				curso.setExamenes(examanes);
				}
			}

			return ResponseEntity.status(HttpStatus.OK).body(curso);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
}
