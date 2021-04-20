package com.utnfrm.microservicios.app.respuestas.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.utnfrm.microservicios.app.respuestas.entity.Respuesta;
import com.utnfrm.microservicios.app.respuestas.services.RespuestaService;

@RestController
public class RespuestaController {

	@Autowired
	protected RespuestaService service;
	
	@PostMapping
	public ResponseEntity<?> createEntity(@RequestBody List<Respuesta> respuesta){
		try {
			respuesta = respuesta.stream().map(r ->{
				r.setAlumnoId(r.getAlumno().getId());
				r.setPreguntaId(r.getPregunta().getId());
				return r;
			}).collect(Collectors.toList());
			List<Respuesta> respuestaDb = service.saveAll(respuesta);
			return ResponseEntity.status(HttpStatus.CREATED).body(respuestaDb);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	public ResponseEntity<?> obtenerRespByAlumnoAndExamen(@PathVariable Long alumnoId, @PathVariable Long examenId){
		try {
			List<Respuesta> respuesta = service.findRespuestaByAlumnoByExamen(alumnoId, examenId);
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenesIdsConRespuestaAlumno(@PathVariable Long alumnoId){
		try {
			Iterable<Long> examenesId = service.findExamenesIdsConRespuestasByAlumno(alumnoId);
			return ResponseEntity.status(HttpStatus.OK).body(examenesId);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
}
