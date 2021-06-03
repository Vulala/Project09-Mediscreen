package com.abernathyclinic.mediscreen.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.mediscreen.model.PatientAndPatientNote;
import com.abernathyclinic.mediscreen.model.PatientDiabetesReport;
import com.abernathyclinic.mediscreen.service.ServiceDiabetesFeignClient;
import com.abernathyclinic.mediscreen.service.ServiceNoSqlFeignClient;
import com.abernathyclinic.mediscreen.service.ServiceSqlFeignClient;

/**
 * Controller used to communicate with the Service-Diabetes micro-service. <br>
 * It use {@link ServiceDiabetesFeignClient} to build up the HTTP requests. <br>
 * It allow the user to generate a diabetes predisposition report for a patient.
 * <br>
 */
@RestController
public class DiabetesReportController {

	@Autowired
	private ServiceDiabetesFeignClient serviceDiabetesFeignClient;
	@Autowired
	private ServiceSqlFeignClient serviceSqlFeignClient;
	@Autowired
	private ServiceNoSqlFeignClient serviceNoSqlFeignClient;

	/**
	 * GET mapping to generate a diabetes assessment for a patient. <br>
	 * It need the patient's demographic information and the patient's practitioners
	 * notes unified in a single POJO: {@link PatientAndPatientNote}. <br>
	 * To achieve this, the method will retrieve the data from the 2 others
	 * micro-services: {@link PatientController} & {@link PatientNoteController}.
	 * 
	 * @param patientAndPatientNote
	 * @return {@link PatientDiabetesReport}
	 */
	@GetMapping("/assess/{uuid}")
	public PatientDiabetesReport getDiabetesAssessment(@PathVariable("uuid") UUID uuid) {
		PatientAndPatientNote patientAndPatientNote = new PatientAndPatientNote(
				serviceSqlFeignClient.getPatientByUUID(uuid), serviceNoSqlFeignClient.getPatientNoteByUUID(uuid));
		return serviceDiabetesFeignClient.getDiabetesAssessment(patientAndPatientNote);
	}

}
