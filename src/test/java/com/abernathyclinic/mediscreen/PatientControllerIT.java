package com.abernathyclinic.mediscreen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abernathyclinic.mediscreen.service.ServiceSqlFeignClient;

/**
 * Important note : This test class is working but nothing is rolledback after
 * each tests because we cannot rollback what is done by an other application.
 * <br>
 * So it might work the first time you launch the test, but will then not
 * anymore, unless you populate the database again (and delete the data posted).
 * <br>
 */
@SpringBootTest
@AutoConfigureMockMvc
@Disabled
class PatientControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ServiceSqlFeignClient serviceSqlFeignClient;

	private UUID uuidOfThePatientInDB = UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9");

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

	@DisplayName("GET : /patient/{uuid}")
	@Test
	void givenGettingASpecificPatientUsingTheUUID_whenGetPatientByUUID_thenItReturnTheRightPatientFromTheDataBase()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patient/b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("GET : /patient/{uuid} but it throw an exception because the entity is not present in the database")
	@Test
	void givenGettingASpecificPatientUsingTheUUIDWhoDoesntExist_whenGetPatientByUUID_thenItThrowAPatientNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patient/b42a8ef5-8baa-4bc2-89aa-d18cdc3239f8")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(404, status);
	}

	@DisplayName("GET : /patient/lastName&firstName")
	@Test
	void givenGettingASpecificPatient_whenGetPatient_thenItReturnTheRightPatientFromTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patient/lastName&firstName?lastName=lastName&firstName=firstName"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("GET : /patient/lastName&firstName but it throw an exception because the entity is not present in the database")
	@Test
	void givenGettingASpecificPatientWhoDoesntExist_whenGetPatient_thenItThrowAPatientNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patient/lastName&firstName?lastName=throw&firstName=exception"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(404, status);
	}

	@DisplayName("GET : /patient")
	@Test
	void givenGettingAllPatients_whenGetAllPatients_thenItReturnAllThePatientsFromTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patient")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("POST : /patient")
	@Test
	void givenSavingAPatient_whenSavePatient_thenItSaveThePatientInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/patient").contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"nameLast\"}"))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(201, response.getStatus());
		assertEquals("Patient sucessfully saved", response.getContentAsString());
	}

	@DisplayName("PUT : /patient/{uuid}")
	@Test
	void givenUpdatingAPatient_whenUpdatePatient_thenItUpdateThePatientInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/patient/" + uuidOfThePatientInDB)
				.contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"nameLast\"}")).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("Patient successfully updated", response.getContentAsString());
	}

	@DisplayName("PUT : /patient/{uuid} but it throw an exception because the patient is not present in the database")
	@Test
	void givenUpdatingAPatientWhoDoesntExist_whenUpdatePatient_thenItThrowAPatientNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/patient/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"nameLast\"}")).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(404, response.getStatus());
	}

	@DisplayName("DELETE : /patient/{uuid}")
	@Test
	void givenDeletingAPatient_whenDeletePatient_thenItDeleteThePatientInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/patient/" + uuidOfThePatientInDB).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("The patient has been successfully deleted in the database.", response.getContentAsString());
	}

	@DisplayName("DELETE : /patient/{uuid} but it throw an exception because the patient is not present in the database")
	@Test
	void givenDeletingAPatientWhoDoesntExist_whenDeletePatient_thenItThrowAPatientNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/patient/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(404, response.getStatus());
	}
}
