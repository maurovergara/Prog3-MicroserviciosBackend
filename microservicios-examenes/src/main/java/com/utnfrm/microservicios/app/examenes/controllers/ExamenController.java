package com.utnfrm.microservicios.app.examenes.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utnfrm.microservicios.app.examenes.service.ExamenService;
import com.utnfrm.microservicios.commons.controllers.CommonController;
import com.utnfrm.microservicios.commons.examenes.entity.Examen;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService>{

	@GetMapping("/repondidos-por-preguntas")
	public ResponseEntity<?> getExamenesIdsByPreguntasIdRespondidas(@RequestParam List<Long> preguntaIds){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id){
		try {
			
			if(result.hasErrors()) {
				return this.validar(result);
			}
			
			Optional<Examen> op = Optional.ofNullable(service.findById(id));
			
			if(op.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
			}
			Examen examendb = op.get();
			examendb.setNombre(examen.getNombre());
			
			examendb.getPreguntas()
			.stream()
			.filter(pdb -> !examen.getPreguntas().contains(pdb))
			.forEach(examendb::removePreguntas);
			
			examendb.setPreguntas(examen.getPreguntas());
			examendb.setAsignaturaPadre(examen.getAsignaturaPadre());
			examendb.setAsignaturaHija(examen.getAsignaturaHija());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examendb));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findByNombre(term));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findAllAsignaturas());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
}
