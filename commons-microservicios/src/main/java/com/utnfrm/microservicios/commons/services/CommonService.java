package com.utnfrm.microservicios.commons.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommonService<E> {
	
	public List<E> findAll() throws Exception; // Trae una lista de todas las persona registradas en la BD
	public Page<E> findAll(Pageable pageable) throws Exception;// Se crea una sub-lista de la lista de objetos
    public E findById(Long id) throws Exception; // Trae una entidad segun si ID
    public E save(E entity) throws Exception;// Crea una nueva entidad
    public E update(Long id, E entity) throws Exception; // Actualiza la entidad
    public void deleteById(Long id) throws Exception; // Elimina algun registro de la BD
}
