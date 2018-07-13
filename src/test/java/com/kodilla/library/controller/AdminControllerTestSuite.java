package com.kodilla.library.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import com.google.gson.Gson;
import com.kodilla.library.domain.Rental;
import com.kodilla.library.domain.User;
import com.kodilla.library.dto.UserDto;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryAdminController.class)
public class AdminControllerTestSuite {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private LibraryAdminController controller;

	@Test
	public void testListUsers() throws Exception {
		//Given
		List<UserDto> userList = new ArrayList<>();
		userList.add(new UserDto());
		when(controller.listUsers()).thenReturn(userList);
		
		//When&Then
		mockMvc.perform(get("/v1/library/admin/listUsers").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$" , hasSize(1)));
	}
	
	@Test
	public void testCreateUser() throws Exception {
		//Given
		List<Rental> list = new ArrayList();
		UserDto userDto = new UserDto("TestName", "TestSurname");
		when(controller.createUser(ArgumentMatchers.any())).thenReturn(userDto);
		Gson gson = new Gson();
		String jsonContent = gson.toJson(userDto);
		
		//When&Then
		mockMvc.perform(post("/v1/library/admin/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonContent))
		.andExpect(status().is(200))
		.andExpect(jsonPath("$.firstName" , is("TestName")))
		.andExpect(jsonPath("$.lastName" , is("TestSurname")));
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		//Given,When & Then
		mockMvc.perform(delete("/v1/library/admin/deleteUser")
				//.contentType(MediaType.APPLICATION_JSON)
				.param("Id", "5"))
		.andExpect(status().is(200));
	}

}
