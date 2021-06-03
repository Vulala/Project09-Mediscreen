package com.abernathyclinic.mediscreen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abernathyclinic.mediscreen.controller.DiabetesReportController;
import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.model.PatientAndPatientNote;
import com.abernathyclinic.mediscreen.model.PatientDiabetesReport;
import com.abernathyclinic.mediscreen.model.PatientNote;
import com.abernathyclinic.mediscreen.service.ServiceDiabetesFeignClient;
import com.abernathyclinic.mediscreen.service.ServiceNoSqlFeignClient;
import com.abernathyclinic.mediscreen.service.ServiceSqlFeignClient;

@WebMvcTest(DiabetesReportController.class)
class DiabetesReportControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ServiceSqlFeignClient serviceSqlFeignClient;
	@MockBean
	private ServiceNoSqlFeignClient serviceNoSqlFeignClient;
	@MockBean
	private ServiceDiabetesFeignClient serviceDiabetesFeignClient;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(serviceNoSqlFeignClient).isNotNull();
	}

	@DisplayName("GET : /assess/{uuid}")
	@Test
	void givenGettingAPatientDiabetesAssessment_whenGetDiabetesAssessment_thenItReturnAnAccurateDiabetesAssessmentReport()
			throws Exception {
		// ARRANGE
		when(serviceSqlFeignClient.getPatientByUUID(any(UUID.class))).thenReturn(new Patient());
		when(serviceNoSqlFeignClient.getPatientNoteByUUID(any(UUID.class))).thenReturn(new PatientNote());
		when(serviceDiabetesFeignClient.getDiabetesAssessment(any(PatientAndPatientNote.class)))
				.thenReturn(new PatientDiabetesReport());

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/assess/" + UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9")))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
		verify(serviceSqlFeignClient, times(1)).getPatientByUUID(any(UUID.class));
		verify(serviceNoSqlFeignClient, times(1)).getPatientNoteByUUID(any(UUID.class));
		verify(serviceDiabetesFeignClient, times(1)).getDiabetesAssessment(any(PatientAndPatientNote.class));
	}

}
