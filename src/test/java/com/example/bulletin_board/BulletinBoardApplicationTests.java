package com.example.bulletin_board;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class BulletinBoardApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private static Integer postId;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@Order(1)
	void testCreatePost() throws Exception {
		String postJson = """
				{
				    "title": "Testing post",
				    "content": "Lorem ipsum dolor si amet",
				    "author": "Author Test",
				    "password": "test123"
				}
				""";
		MvcResult result = mockMvc.perform(post("/post").contentType(MediaType.APPLICATION_JSON).content(postJson))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.postId").exists()).andReturn();

		String content = result.getResponse().getContentAsString();
		JsonNode jsonResponse = objectMapper.readTree(content);
		postId = jsonResponse.path("postId").asInt();
	}

	@Test
	@Order(2)
	void testGetPost() throws Exception {
		mockMvc.perform(get("/post")).andExpect(status().isOk());
	}

	@Test
	@Order(3)
	void testGetPostById() throws Exception {
		mockMvc.perform(get(String.format("/post/%d", postId))).andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void testGetPostByIdExpectNotFound() throws Exception {
		mockMvc.perform(get("/post/{postId}", -1)).andExpect(status().isNotFound());
	}

	@Test
	@Order(6)
	void testUpdatePostContentById() throws Exception {
		String postJson = """
				{
					"content": "Lorem ipsum dolor si amet, test 123",
				    "password": "test123"
				}
				""";
		mockMvc.perform(
				put(String.format("/post/%d", postId)).contentType(MediaType.APPLICATION_JSON).content(postJson))
				.andExpect(status().isOk());
	}

	@Test
	void testUpdatePostContentByIdExpectNotFound() throws Exception {
		String postJson = """
				{
					"content": "Lorem ipsum dolor si amet, test 123",
				    "password": "test123"
				}
				""";
		mockMvc.perform(put(String.format("/post/%d", -1)).contentType(MediaType.APPLICATION_JSON).content(postJson))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(7)
	void testUpdatePostContentByIdExpectFailed() throws Exception {
		String postJson = """
				{
				    "password": "test1234",
					"content": "Lorem ipsum dolor si amet, test 123"
				}
				""";
		mockMvc.perform(
				put(String.format("/post/%d", postId)).contentType(MediaType.APPLICATION_JSON).content(postJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(8)
	void testDeletePostByIdExpectFailed() throws Exception {
		String postJson = """
				{
				    "password": "test1234"
				}
				""";
		mockMvc.perform(
				delete(String.format("/post/%d", postId)).contentType(MediaType.APPLICATION_JSON).content(postJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(9)
	void testDeletePostByIdExpectSuccess() throws Exception {
		String postJson = """
				{
				    "password": "test123"
				}
				""";
		mockMvc.perform(
				delete(String.format("/post/%d", postId)).contentType(MediaType.APPLICATION_JSON).content(postJson))
				.andExpect(status().isOk());
	}

	@Test
	void testDeletePostByIdExpectNotFound() throws Exception {
		String postJson = """
				{
				    "password": "test123"
				}
				""";
		mockMvc.perform(delete(String.format("/post/%d", -1)).contentType(MediaType.APPLICATION_JSON).content(postJson))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(10)
	void testCreatePostKoreanTitleTooLong() throws Exception {
		String postJson = """
				{
				    "title": "로렘 입숨, 고통이 있고, 앉아 있는 장소에서의 그 의미, 로렘 입숨, 고통이 있고, 앉아 있는 장소에서의 그 의미 로렘 입숨, 고통이 있고, 앉아 있는 장소에서의 그 의미",
				    "content": "Lorem ipsum dolor si amet",
				    "author": "Author Test",
				    "password": "test123"
				}
				""";
		mockMvc.perform(post("/post").contentType(MediaType.APPLICATION_JSON).content(postJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(11)
	void testCreatePostKoreanAuthorTooLong() throws Exception {
		String postJson = """
				{
				    "title": "hello world",
				    "content": "Lorem ipsum dolor si amet",
				    "author": "로렘 입숨, 고통이 있고, 앉아 있는 장소에",
				    "password": "test123"
				}
				""";
		mockMvc.perform(post("/post").contentType(MediaType.APPLICATION_JSON).content(postJson))
				.andExpect(status().isBadRequest());
	}
}
