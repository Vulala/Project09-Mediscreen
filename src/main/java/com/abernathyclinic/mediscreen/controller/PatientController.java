package com.abernathyclinic.mediscreen.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.ServiceSqlFeignClient;

/**
 * Controller providing CRUD mapping for the {@link Patient} entity, allowing
 * the user to communicate with the micro-service which communicate with a
 * relational database. <br>
 * It use {@link ServiceSqlFeignClient} to build up the HTTP requests.
 */
@RestController
public class PatientController {

	@Autowired
	private ServiceSqlFeignClient serviceSqlFeignClient;

	@GetMapping("/")
	public String index() {
		return "Welcome on Mediscreen ! Head of the micro-services architecture, it allow the user to interact with the differents micro-services.";
	}

	/**
	 * GET mapping to retrieve a {@link Patient} from the database by using his last
	 * name and first name. <br>
	 * 
	 * @param lastName and firstName : of the patient to retrieve
	 * @return the patient if present in the database, else throw an error message
	 */
	@GetMapping("/patient{lastName}{firstName}")
	public Patient getPatient(@RequestParam("lastName") String lastName, @RequestParam("firstName") String firstName) {
		return serviceSqlFeignClient.getPatient(lastName, firstName);

	}

	/**
	 * POST mapping to save a {@link Patient} and save it in the database. <br>
	 * 
	 * @param patient : to save
	 * @return a success message if the request is a success, else throw an error
	 *         message
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/patient")
	public String savePatient(@RequestBody Patient patient) {
		return serviceSqlFeignClient.savePatient(patient);
	}

	/**
	 * PUT mapping to update an existing {@link Patient} in the database. <br>
	 * 
	 * @param uuid    : of the patient to update
	 * @param patient : the informations to update
	 * @return a success message if the request is a success, else throw an error
	 *         message
	 */
	@PutMapping("/patient/{uuid}")
	public String updatePatient(@PathVariable("uuid") UUID uuid, @Valid @RequestBody Patient patient) {
		return serviceSqlFeignClient.updatePatient(uuid, patient);
	}

	/**
	 * DELETE mapping used to delete a {@link Patient}. <br>
	 * 
	 * @param uuid : of the patient to delete
	 * @return a success message if the request is a success, else an error message
	 */
	@DeleteMapping("/patient/{uuid}")
	public String deletePatient(@PathVariable("uuid") UUID uuid) {
		return serviceSqlFeignClient.deletePatient(uuid);
	}
}
