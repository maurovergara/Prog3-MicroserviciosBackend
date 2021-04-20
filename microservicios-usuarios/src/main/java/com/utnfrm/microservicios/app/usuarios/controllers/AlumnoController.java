package com.utnfrm.microservicios.app.usuarios.controllers;

import java.util.List;
//import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;

import com.utnfrm.microservicios.app.usuarios.services.AlumnoService;
import com.utnfrm.microservicios.commons.alumnos.entity.Alumno;
import com.utnfrm.microservicios.commons.controllers.CommonController;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEntity(@Valid @PathVariable Long id, @RequestBody Alumno alumno,
			BindingResult result) {
		try {

			if (result.hasErrors()) {
				return this.validar(result);
			}

			Optional<Alumno> op = Optional.ofNullable(service.findById(id));

			if (op.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
			}
			Alumno alumnoDb = op.get();
			alumnoDb.setNombre(alumno.getNombre());
			alumnoDb.setApellido(alumno.getApellido());
			alumnoDb.setEmail(alumno.getEmail());

			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}

	@GetMapping("/alumnos-por-curso")
	public ResponseEntity<?> getAlumnosByCurso(@RequestParam List<Long> ids) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findAllById(ids));
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}

	/*
	 * @GetMapping("/upload/img/{id}") public ResponseEntity<?>
	 * verFoto(@PathVariable Long id) { try { Optional<Alumno> op =
	 * Optional.ofNullable(service.findById(id));
	 * 
	 * if (op.isEmpty() || op.get().getFotoHashCode() == null) { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND)
	 * .body("{\"error\":\"Error. Por favor intente más tarde.\"}"); }
	 * 
	 * Resource imagen = new ByteArrayResource(op.get().getFotos());
	 * 
	 * return
	 * ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(
	 * imagen);
	 * 
	 * } catch (Exception e) {
	 * 
	 * return ResponseEntity.status(HttpStatus.NOT_FOUND)
	 * .body("{\"error\":\"Error. Por favor intente más tarde.\"}"); }
	 * 
	 * }
	 */

	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findByNombreOrApellido(term));
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error. Por favor intente más tarde.\"}");
		}
	}

	/*
	 * @PostMapping("/crear-con-fotos") public ResponseEntity<?> createPhoto(@Valid
	 * Alumno alumno, BindingResult result,
	 * 
	 * @RequestParam MultipartFile archivo) throws IOException {
	 * 
	 * if (!archivo.isEmpty()) { alumno.setFotos(archivo.getBytes()); } return
	 * super.createEntity(alumno, result); }
	 * 
	 * @PutMapping("/editar-con-fotos/{id}") public ResponseEntity<?>
	 * updatePhoto(@Valid @PathVariable Long id, Alumno alumno, BindingResult
	 * result,
	 * 
	 * @RequestParam MultipartFile archivo) throws Exception {
	 * 
	 * if (result.hasErrors()) { return this.validar(result); }
	 * 
	 * Optional<Alumno> op = Optional.ofNullable(service.findById(id));
	 * 
	 * if (op.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND)
	 * .body("{\"error\":\"Error. Por favor intente más tarde.\"}"); } Alumno
	 * alumnoDb = op.get(); alumnoDb.setNombre(alumno.getNombre());
	 * alumnoDb.setApellido(alumno.getApellido());
	 * alumnoDb.setEmail(alumno.getEmail());
	 * 
	 * if (!archivo.isEmpty()) { alumnoDb.setFotos(archivo.getBytes()); }
	 * 
	 * return
	 * ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb)); }
	 */
}
