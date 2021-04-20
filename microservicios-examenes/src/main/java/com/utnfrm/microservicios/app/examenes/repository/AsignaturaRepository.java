package com.utnfrm.microservicios.app.examenes.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.utnfrm.microservicios.commons.examenes.entity.Asignatura;

public interface AsignaturaRepository extends PagingAndSortingRepository<Asignatura, Long> {
}
