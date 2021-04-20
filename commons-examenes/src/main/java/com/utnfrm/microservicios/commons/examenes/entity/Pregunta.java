package com.utnfrm.microservicios.commons.examenes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "preguntas")
@Getter
@Setter
public class Pregunta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "texto")
	private String texto;
	
	@JsonIgnoreProperties(value = {"preguntas"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examen_id")
	private Examen examen;

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof Pregunta)) {
			return false;
		}
		Pregunta al = (Pregunta) obj;
		
		return this.id != null && this.id.equals(al.getId());
	}
	
	
}
