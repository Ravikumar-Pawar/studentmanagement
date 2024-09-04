package com.msrit.studentmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msrit.studentmanagement.model.Student;
import com.msrit.studentmanagement.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class StudentmanagementApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;

	@Test
	void testGetAllStudents() throws Exception {
		// Arrange
		Student student1 = new Student(null, "1ms24cs122", "Rohan", "rohan@gmail.com", "123444", "AI,ML");
		Student student2 = new Student(null, "1ms24cs123", "Amit", "amit@gmail.com", "567899", "AI,DL");
		when(studentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2));

		// Act & Assert
		mockMvc.perform(get("/api/students")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].usn", is("1ms24cs122")))
				.andExpect(jsonPath("$[1].usn", is("1ms24cs123")))
				.andDo(result -> System.out.println("testGetAllStudents passed!"));
	}

	@Test
	void testCreateStudent() throws Exception {
		// Arrange
		Student student = new Student(null, "1ms24cs125", "Sita", "sita@gmail.com", "9876543210", "CS, DS");
		when(studentService.saveStudent(student)).thenReturn(student);

		// Act & Assert
		mockMvc.perform(post("/api/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(student)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.usn", is("1ms24cs125")))
				.andExpect(jsonPath("$.name", is("Sita")))
				.andDo(result -> System.out.println("testCreateStudent passed!"));
	}
}
