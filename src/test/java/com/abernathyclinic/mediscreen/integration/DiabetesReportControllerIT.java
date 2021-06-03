package com.abernathyclinic.mediscreen.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class DiabetesReportControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
	}

	@DisplayName("GET : /assess/{uuid}")
	@Test
	void givenGettingAPatientDiabetesAssessment_whenGetDiabetesAssessment_thenItReturnAnAccurateDiabetesAssessmentReport()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/assess/" + UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9")))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}
}
