package com.utnfrm.microservicios.app.respuestas.entity;


import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.utnfrm.microservicios.commons.alumnos.entity.Alumno;
import com.utnfrm.microservicios.commons.examenes.entity.Pregunta;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "respuestas")
@Getter
@Setter
public class Respuesta {

	@Id
	private String id;
	
	private String texto;
	
	private Alumno alumno;
	
	private Long alumnoId;
	
	private Pregunta pregunta;
	
	private Long preguntaId;
	
}

