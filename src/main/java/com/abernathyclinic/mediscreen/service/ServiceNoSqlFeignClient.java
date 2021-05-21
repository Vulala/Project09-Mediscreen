package com.abernathyclinic.mediscreen.service;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abernathyclinic.mediscreen.controller.PatientNoteController;
import com.abernathyclinic.mediscreen.model.PatientNote;

/**
 * ServiceNoSqlFeignClient is used by Feign to build HTTP requests. <br>
 * The target of the requests is the Service-NoSQL micro-service. <br>
 * For more details concerning the request, see {@link PatientNoteController}.
 * <br>
 */
@FeignClient(name = "service-nosql", url = "localhost:8082")
public interface ServiceNoSqlFeignClient {

	@GetMapping("/patientHistory/{uuid}")
	PatientNote getPatientNoteByUUID(@PathVariable("uuid") UUID uuid);

	@GetMapping("/patientHistory")
	List<PatientNote> getAllPatientsNotes();

	@PostMapping("/patientHistory")
	String savePatientNote(@RequestBody PatientNote patientNote);

	@PutMapping("/patientHistory/{uuid}")
	String updatePatientNote(@PathVariable("uuid") UUID uuid, @RequestBody PatientNote patientNote);

	@DeleteMapping("/patientHistory/{uuid}")
	String deletePatientNote(@PathVariable("uuid") UUID uuid);

}
