package com.abernathyclinic.mediscreen.service;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.abernathyclinic.mediscreen.controller.PatientController;
import com.abernathyclinic.mediscreen.model.Patient;

/**
 * ServiceSqlFeignClient is used by Feign to build HTTP requests. <br>
 * The target of the requests is the Service-SQL micro-service. <br>
 * For more details concerning the request, see {@link PatientController}. <br>
 */
@FeignClient(name = "service-sql", url = "localhost:8081")
public interface ServiceSqlFeignClient {

	@GetMapping(value = "/patient{lastName}{firstName}")
	Patient getPatient(@RequestParam("lastName") String lastName, @RequestParam("firstName") String firstName);

	@PostMapping("/patient")
	String savePatient(@RequestBody Patient patient);

	@PutMapping("/patient/{uuid}")
	String updatePatient(@PathVariable("uuid") UUID uuid, @RequestBody Patient patient);

	@DeleteMapping("/patient/{uuid}")
	String deletePatient(@PathVariable("uuid") UUID uuid);
}
