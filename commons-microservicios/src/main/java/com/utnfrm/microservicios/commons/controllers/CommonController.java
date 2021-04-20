package com.utnfrm.microservicios.commons.controllers;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.utnfrm.microservicios.commons.services.CommonService;

//@CrossOrigin({"http://localhost:4200"})
public class CommonController<E, S extends CommonService<E>> {

	@Autowired
	protected S service;
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		try {
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(service.findAll());
		} catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@GetMapping("/pagina")
	public ResponseEntity<?> getAll(Pageable pageable){
		try {
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(service.findAll(pageable));
		} catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id){
		try {
			Optional<E> op = Optional.ofNullable(service.findById(id));
			
			if(op.isEmpty()) {
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
			}
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(op.get());
		} catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@PostMapping
	public ResponseEntity<?> createEntity(@Valid @RequestBody E entity, BindingResult result){
		try {
			if(result.hasErrors()) {
				return this.validar(result);
			}
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(service.save(entity));
		} catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEntity(@PathVariable Long id){ 
		try {
			service.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		 catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	protected ResponseEntity<?> validar(BindingResult result){
		try {
			Map<String, Object> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(), " El campo " + err.getField() + " " + err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
		} catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
		
	}
}
