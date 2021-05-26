package com.abernathyclinic.mediscreen.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abernathyclinic.mediscreen.service.ServiceNoSqlFeignClient;

/**
 * Important note : This test class is working but nothing is rolledback after
 * each tests because we cannot rollback what is done by an other application.
 * <br>
 * So it might work the first time you launch the test if all the data for the
 * test are present in the DB, but will then not anymore, unless you populate
 * the database again with the value deleted (and delete the data posted). <br>
 */
@SpringBootTest
@AutoConfigureMockMvc
class PatientNoteControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ServiceNoSqlFeignClient serviceNoSqlFeignClient;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(serviceNoSqlFeignClient).isNotNull();
	}

	@DisplayName("GET : /")
	@Test
	void givenGettingTheIndex_whenIndex_thenItReturnTheIndex() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(200, status);
	}

	@DisplayName("GET : /patientHistory/{uuid}")
	@Test
	void givenGettingASpecificPatientNoteUsingTheUUID_whenGetPatientNoteByUUID_thenItReturnTheRightPatientNoteFromTheDataBase()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(get("/patientHistory/" + UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9")))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("GET : /patientHistory")
	@Test
	void givenGettingAllPatientsNotes_whenGetAllPatientsNotes_thenItReturnAllThePatientsNotesFromTheDataBase()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patientHistory")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("POST : /patientHistory")
	@Test
	void givenSavingAPatientNote_whenSavePatientNote_thenItSaveThePatientNoteInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/patientHistory").contentType(MediaType.APPLICATION_JSON).content("{\"notes\": \"notesSaved\"}"))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(201, response.getStatus());
		assertEquals("Patient's notes sucessfully saved", response.getContentAsString());
	}

	@DisplayName("PUT : /patientHistory/{uuid}")
	@Test
	void givenUpdatingAPatientNote_whenUpdatePatientNote_thenItUpdateThePatientNoteInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(put("/patientHistory/" + UUID.fromString("7798a960-ee17-4b83-b355-fc3549322cc6"))
						.contentType(MediaType.APPLICATION_JSON).content("{\"notes\": \"notesUpdated\"}"))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("Patient's notes successfully updated", response.getContentAsString());
	}

	@DisplayName("DELETE : /patientHistory/{uuid}")
	@Test
	void givenDeletingAPatientNote_whenDeletePatientNote_thenItDeleteThePatientNoteInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/patientHistory/" + UUID.fromString("097252bc-12c4-41ac-b831-8b9b8e5bba59"))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("The patient's notes has been successfully deleted in the database.",
				response.getContentAsString());
	}

}
