package com.abernathyclinic.mediscreen.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.mediscreen.model.PatientNote;
import com.abernathyclinic.mediscreen.service.ServiceNoSqlFeignClient;

/**
 * Controller providing CRUD mapping for the {@link PatientNote} entity,
 * allowing the user to communicate with the micro-service which communicate
 * with a MongoDB database. <br>
 * It use {@link ServiceNoSqlFeignClient} to build up the HTTP requests.
 */
@RestController
public class PatientNoteController {

	@Autowired
	private ServiceNoSqlFeignClient serviceNoSqlFeignClient;

	/**
	 * GET mapping to retrieve the history of a {@link PatientNote} from the
	 * database by using the UUID. <br>
	 * 
	 * @param UUID : of the patientNote to retrieve
	 * @return the patientNote if present in the database
	 */
	@GetMapping("/patientHistory/{uuid}")
	public PatientNote getPatientNoteByUUID(@PathVariable("uuid") UUID uuid) {
		return serviceNoSqlFeignClient.getPatientNoteByUUID(uuid);

	}

	/**
	 * GET mapping to retrieve the history of all {@link PatientNote} from the
	 * database. <br>
	 * 
	 * @return all the patients' history present in the database
	 */
	@GetMapping("/patientHistory")
	public List<PatientNote> getAllPatientsNotes() {
		return serviceNoSqlFeignClient.getAllPatientsNotes();

	}

	/**
	 * POST mapping to save the history of a {@link PatientNote} in the database.
	 * <br>
	 * 
	 * @param patientNote : to save
	 * @return a success message
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/patientHistory")
	public String savePatientNote(@RequestBody PatientNote patientNote) {
		return serviceNoSqlFeignClient.savePatientNote(patientNote);
	}

	/**
	 * PUT mapping to update an existing {@link PatientNote}'s history in the
	 * database. <br>
	 * 
	 * @param uuid        : of the patientNote to update
	 * @param patientNote : the notes to update
	 * @return a success message
	 */
	@PutMapping("/patientHistory/{uuid}")
	public String updatePatientNote(@PathVariable("uuid") UUID uuid, @RequestBody PatientNote patientNote) {
		return serviceNoSqlFeignClient.updatePatientNote(uuid, patientNote);
	}

	/**
	 * DELETE mapping to delete a {@link PatientNote}'s history. <br>
	 * 
	 * @param uuid : of the patientNote to delete the notes
	 * @return a success message
	 */
	@DeleteMapping("/patientHistory/{uuid}")
	public String deletePatientNote(@PathVariable("uuid") UUID uuid) {
		return serviceNoSqlFeignClient.deletePatientNote(uuid);
	}
}
