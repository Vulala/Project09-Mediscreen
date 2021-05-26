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

import java.util.ArrayList;
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

import com.abernathyclinic.mediscreen.controller.PatientNoteController;
import com.abernathyclinic.mediscreen.model.PatientNote;
import com.abernathyclinic.mediscreen.service.ServiceNoSqlFeignClient;

@WebMvcTest(PatientNoteController.class)
class PatientNoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ServiceNoSqlFeignClient serviceNoSqlFeignClient;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(serviceNoSqlFeignClient).isNotNull();
	}

	@DisplayName("GET : /patientHistory/{uuid}")
	@Test
	void givenGettingASpecificPatientNoteUsingTheUUID_whenGetPatientNoteByUUID_thenItReturnTheRightPatientNoteFromTheDataBase()
			throws Exception {
		// ARRANGE
		when(serviceNoSqlFeignClient.getPatientNoteByUUID(any(UUID.class))).thenReturn(new PatientNote());

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patientHistory/b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
		verify(serviceNoSqlFeignClient, times(1)).getPatientNoteByUUID(any(UUID.class));
	}

	@DisplayName("GET : /patientHistory")
	@Test
	void givenGettingAllPatientsNotes_whenGetAllPatientsNotes_thenItReturnAllThePatientsNotesFromTheDatabase()
			throws Exception {
		// ARRANGE
		when(serviceNoSqlFeignClient.getAllPatientsNotes()).thenReturn(new ArrayList<PatientNote>());

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patientHistory")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("POST : /patientHistory")
	@Test
	void givenSavingAPatientNote_whenSavePatientNote_thenItSaveThePatientNoteInTheDataBase() throws Exception {
		// ARRANGE
		PatientNote patientNoteToSave = new PatientNote(UUID.randomUUID(), "notesSave");
		when(serviceNoSqlFeignClient.savePatientNote(patientNoteToSave))
				.thenReturn("Patient's notes successfully saved.");

		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/patientHistory").contentType(MediaType.APPLICATION_JSON).content("{\"notes\": \"notesSave\"}"))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(201, response.getStatus());
	}

	@DisplayName("PUT : /patientHistory/{uuid}")
	@Test
	void givenUpdatingAPatientNote_whenUpdatePatientNote_thenItUpdateThePatientNoteInTheDataBase() throws Exception {
		// ARRANGE
		UUID randomUUID = UUID.randomUUID();
		PatientNote patientNoteToUpdate = new PatientNote(randomUUID, "notesUpdate");
		when(serviceNoSqlFeignClient.updatePatientNote(randomUUID, patientNoteToUpdate))
				.thenReturn("Patient's notes successfully updated.");

		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/patientHistory/" + randomUUID)
				.contentType(MediaType.APPLICATION_JSON).content("{\"notes\": \"notesUpdate\"}")).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
	}

	@DisplayName("DELETE : /patientHistory/{uuid}")
	@Test
	void givenDeletingAPatientNote_whenDeletePatientNote_thenItDeleteThePatientNoteInTheDataBase() throws Exception {
		// ARRANGE
		UUID randomUUID = UUID.randomUUID();
		PatientNote patientNoteToDelete = new PatientNote(randomUUID, "noteDelete");
		when(serviceNoSqlFeignClient.deletePatientNote(patientNoteToDelete.getUuid()))
				.thenReturn("The patient's notes has been successfully deleted in the database.");

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/patientHistory/" + randomUUID).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("The patient's notes has been successfully deleted in the database.",
				response.getContentAsString());
		verify(serviceNoSqlFeignClient, times(1)).deletePatientNote(patientNoteToDelete.getUuid());
	}

}
