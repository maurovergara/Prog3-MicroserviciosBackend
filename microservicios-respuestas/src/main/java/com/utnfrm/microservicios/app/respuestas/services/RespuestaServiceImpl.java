package com.utnfrm.microservicios.app.respuestas.services;

//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.utnfrm.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.utnfrm.microservicios.app.respuestas.entity.Respuesta;
import com.utnfrm.microservicios.app.respuestas.repository.RespuestasRepository;
//import com.utnfrm.microservicios.commons.examenes.entity.Examen;
//import com.utnfrm.microservicios.commons.examenes.entity.Pregunta;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	protected RespuestasRepository repository;
	
	//@Autowired
	//private ExamenFeignClient examenClient;
	
	@Override
	public List<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return repository.saveAll(respuestas);
	}

	@Override
	public List<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		/*Examen examen = examenClient.getExamenById(examenId);
		List<Pregunta> preguntas = examen.getPreguntas();
		List<Long> preguntasId = preguntas.stream().map(pr -> pr.getId()).collect(Collectors.toList());
		List<Respuesta> respuesta = repository.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntasId);
		respuesta = respuesta.stream().map(r -> {
			preguntas.forEach(p -> {
				if(p.getId() == r.getPreguntaId()) {
					r.setPregunta(p);
				}
			});
			return r;
		}).collect(Collectors.toList());*/
		List<Respuesta> respuesta = repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return respuesta;
	}

	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		/*List<Respuesta> respuestaAlumno = (List<Respuesta>) repository.findByAlumnoId(alumnoId);
		List<Long> examenIds = Collections.emptyList();
		if(respuestaAlumno.size() > 0) {
			List<Long> preguntaId = respuestaAlumno.stream().map(r -> r.getPreguntaId()).collect(Collectors.toList());
			examenIds = examenClient.getExamenesIdsByPreguntasIdRespondidas(preguntaId);
		}*/
		List<Respuesta> respuestaAlumno = (List<Respuesta>) repository.findByAlumnoId(alumnoId);
		List<Long> examenIds =respuestaAlumno
				.stream()
				.map(r -> r.getPregunta().getExamen().getId()).distinct()
				.collect(Collectors.toList());
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		return repository.findByAlumnoId(alumnoId);
	}

}
