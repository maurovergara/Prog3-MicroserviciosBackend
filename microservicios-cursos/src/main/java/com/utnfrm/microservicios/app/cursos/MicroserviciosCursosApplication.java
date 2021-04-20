package com.utnfrm.microservicios.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({ "com.utnfrm.microservicios.commons.alumnos.entity", 
	"com.utnfrm.microservicios.app.cursos.entities", 
	"com.utnfrm.microservicios.commons.examenes.entity"})
@EnableFeignClients
public class MicroserviciosCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosCursosApplication.class, args);
	}

}
