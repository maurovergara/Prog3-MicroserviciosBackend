package com.utnfrm.microservicios.app.respuestas.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.utnfrm.microservicios.commons.examenes.entity.Examen;

@FeignClient(name = "microservicio-examenes")
public interface ExamenFeignClient {

	@GetMapping("/{id}")
	public Examen getExamenById(@PathVariable Long id);
	
	@GetMapping("/repondidos-por-preguntas")
	public List<Long> getExamenesIdsByPreguntasIdRespondidas(@RequestParam List<Long> preguntaIds);
}
