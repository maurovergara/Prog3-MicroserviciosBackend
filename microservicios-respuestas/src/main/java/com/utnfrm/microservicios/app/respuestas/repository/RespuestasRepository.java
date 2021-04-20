package com.utnfrm.microservicios.app.respuestas.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.utnfrm.microservicios.app.respuestas.entity.Respuesta;

public interface RespuestasRepository extends MongoRepository<Respuesta, String> {
	
	@Query("{'alumnoId' : ?0, 'preguntaId' { $in : ?1} }")
	public List<Respuesta> findRespuestaByAlumnoByPreguntaIds(Long alumnoId, List<Long> preguntaIds);
	
	@Query("{ 'alumnoId' : ?0 }")
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
	
	@Query("{ 'alumnoId' : ?0, 'pregunta.examen.id': ?1 }")
	public List<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
	
	@Query(value = "{ 'alumnoId' : ?0 }", fields = "{ 'pregunta.examen.id' : 1	}")
	public Iterable<Respuesta> findExamenesIdsConRespuestasByAlumno(Long alumnoId);

	/*@Query("select r from Respuesta r join fetch r.pregunta p join fetch p.examen e where r.alumnoId=?1 and e.id=?2")
	public List<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
	
	@Query("select e.id from Respuesta r join r.pregunta p join p.examen e where r.alumnoId=?1 group by e.id")
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);*/
}
