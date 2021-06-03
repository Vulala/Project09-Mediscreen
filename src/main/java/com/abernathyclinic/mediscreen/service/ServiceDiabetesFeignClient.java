package com.abernathyclinic.mediscreen.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abernathyclinic.mediscreen.controller.DiabetesReportController;
import com.abernathyclinic.mediscreen.model.PatientAndPatientNote;
import com.abernathyclinic.mediscreen.model.PatientDiabetesReport;

/**
 * ServiceDiabetesFeignClient is used by Feign to build HTTP requests. <br>
 * The target of the requests is the Service-Diabetes micro-service. <br>
 * For more details concerning the request, see
 * {@link DiabetesReportController}. <br>
 */
@FeignClient(name = "service-diabetes", url = "localhost:8083")
public interface ServiceDiabetesFeignClient {

	@PostMapping("/assess")
	PatientDiabetesReport getDiabetesAssessment(@RequestBody PatientAndPatientNote patientAndPatientNote);

}