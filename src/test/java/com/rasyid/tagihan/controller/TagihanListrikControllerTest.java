package com.rasyid.tagihan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.rasyid.tagihan.entity.TagihanListrik;

import lombok.SneakyThrows;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TagihanListrikControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TagihanListrikController tagihanListrikController;

	@Test
	@SneakyThrows
	void givenValidId_whenGetOne_thenReturnOk() {
		ResponseEntity<TagihanListrik> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/tagihan/listrik/read/1").toString(), TagihanListrik.class);
		TagihanListrik tagihanListrik = response.getBody();
		assertEquals(123, tagihanListrik.getDayaAlat());
	}

	@Test
	@SneakyThrows
	void test_whenGetAll_thenReturnOk() {
		ResponseEntity<String> response = restTemplate
				.getForEntity(new URI("http://localhost:" + port + "/tagihan/listrik/read"), String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(true, response.getBody().contains("123"));
	}

	@Test
	@SneakyThrows
	void test_whenAdd_thenReturnOk() {
		TagihanListrik tagihanListrik = new TagihanListrik((long) 3, (double) 123, (double) 123, (double) 123);
		ResponseEntity<String> response = restTemplate.postForEntity(
				new URI("http://localhost:" + port + "/tagihan/listrik/create"), tagihanListrik, String.class);
		assertEquals(201, response.getStatusCodeValue());
	}

	@Test
	@SneakyThrows
	void givenValidId_whenUpdate_thenReturnOk() {

		Map<String, Double> params = new HashMap<String, Double>();
		params.put("dayaAlat", (double) 666);
		ResponseEntity<TagihanListrik> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/tagihan/listrik/read/1").toString(), TagihanListrik.class,
				params);
		;
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	@SneakyThrows
	void givenValidId_whenDelete_thenReturnOk() {

		Map<String, Long> params = new HashMap<String, Long>();
		params.remove("id", (long) 4);
		ResponseEntity<TagihanListrik> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/tagihan/listrik/read/4").toString(), TagihanListrik.class,
				params);
		;
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void givenValidListSize_whenFindAll_thenReturnOk() throws ClassNotFoundException, IOException {
		ResponseEntity<List<TagihanListrik>> result = tagihanListrikController.read();
		assertThat(result.getBody()).hasSize(9);
	}

}
