package com.abernathyclinic.mediscreen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abernathyclinic.mediscreen.controller.PatientController;
import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.ServiceSqlFeignClient;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ServiceSqlFeignClient serviceSqlFeignClient;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(serviceSqlFeignClient).isNotNull();
	}

	@DisplayName("GET : /")
	@Test
	void givenGettingTheIndex_whenIndex_thenItReturnTheIndex() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(200, status);
	}

	@DisplayName("GET : /patient{lastName}{firstName}")
	@Test
	void givenGettingASpecificPatient_whenGetPatient_thenItReturnTheRightPatientFromTheDataBase() throws Exception {
		// ARRANGE
		when(serviceSqlFeignClient.getPatient(any(String.class), any(String.class))).thenReturn(new Patient());

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patient?lastName=lastName&firstName=firstName")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
		verify(serviceSqlFeignClient, times(1)).getPatient(any(String.class), any(String.class));
	}

	@DisplayName("POST : /patient")
	@Test
	void givenSavingAPatient_whenSavePatient_thenItSaveThePatientInTheDataBase() throws Exception {
		// ARRANGE
		Patient patientToSave = new Patient("lastName", "firstName", "dateOfBirth", "gender", "homeAddress",
				"phoneNumber");
		when(serviceSqlFeignClient.savePatient(patientToSave)).thenReturn(any(String.class));

		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/patient").contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"lastName\"}"))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(201, response.getStatus());
	}

	@DisplayName("PUT : /patient/{uuid}")
	@Test
	void givenUpdatingAPatient_whenUpdatePatient_thenItUpdateThePatientInTheDataBase() throws Exception {
		// ARRANGE
		Patient patientToUpdate = new Patient("lastName", "firstName", "dateOfBirth", "gender", "homeAddress",
				"phoneNumber");
		UUID randomUUID = UUID.randomUUID();
		when(serviceSqlFeignClient.updatePatient(randomUUID, patientToUpdate)).thenReturn("");

		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/patient/" + randomUUID).contentType(MediaType.APPLICATION_JSON)
				.content("{\"lastName\": \"nameLast\"}")).andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
	}

	@DisplayName("DELETE : /patient/{uuid}")
	@Test
	void givenDeletingAPatient_whenDeletePatient_thenItDeleteThePatientInTheDataBase() throws Exception {
		// ARRANGE
		UUID randomUUID = UUID.randomUUID();
		when(serviceSqlFeignClient.deletePatient(randomUUID)).thenReturn(any(String.class));

		// ACT
		MvcResult mvcResult = mockMvc.perform(delete("/patient/" + randomUUID).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		verify(serviceSqlFeignClient, times(1)).deletePatient(randomUUID);
	}

}
