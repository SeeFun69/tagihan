package com.rasyid.tagihan.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.rasyid.tagihan.dto.TagihanListrikDto;
import com.rasyid.tagihan.entity.TagihanListrik;
import com.rasyid.tagihan.exception.ErrorObject;
import com.rasyid.tagihan.exception.InvalidDataException;
import com.rasyid.tagihan.exception.TagihanNotFoundException;
import com.rasyid.tagihan.service.TagihanListrikService;
import com.rasyid.tagihan.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tagihan/listrik")
public class TagihanListrikController {
	@Autowired
	private TagihanListrikService listrikService;

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public TagihanListrik create(@Valid @RequestBody TagihanListrikDto tagihanListrikDto) throws IOException {
		Objects.requireNonNull(tagihanListrikDto);
		TagihanListrik createTagihan = new TagihanListrik();
		ObjectUtils.copyProperties(createTagihan, tagihanListrikDto);
		return listrikService.create(createTagihan);
	}

	@GetMapping("/read")
	public ResponseEntity<List<TagihanListrik>> read() throws ClassNotFoundException, IOException{
		List<TagihanListrik> response = listrikService.read();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<TagihanListrik> findById(@PathVariable Long id) {
		TagihanListrik listrik = listrikService.findById(id);
		return ResponseEntity.ok(listrik);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id) throws IOException{
		listrikService.delete(id);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<TagihanListrik> update(@PathVariable Long id, @RequestBody TagihanListrik tagihanListrik){
		Objects.requireNonNull(tagihanListrik);
		TagihanListrik updateTagihan = new TagihanListrik();
		ObjectUtils.copyProperties(updateTagihan, tagihanListrik);
		listrikService.update(id, updateTagihan);
		return ResponseEntity.ok(updateTagihan);
	}

	@PatchMapping(path = "/update/{id}", consumes = "application/json-patch+json")
	public TagihanListrik updateCustomer(@PathVariable Long id, @RequestBody JsonPatch patch) throws IOException {
		try {
			TagihanListrik contact = listrikService.findById(id);
			TagihanListrikDto tagihanListrikDto = applyPatch(patch, contact, TagihanListrikDto.class);
			ObjectUtils.copyProperties(contact, tagihanListrikDto);

			listrikService.update(id, contact);
			return contact;
		} catch (JsonPatchException | JsonProcessingException e) {
			log.error(e.toString(), e);
			throw new InvalidDataException();
		}
	}

	public <T, R> R applyPatch(JsonPatch patch, T origObj, Class<R> classResult)
			throws JsonPatchException, JsonProcessingException, IllegalArgumentException {
		JsonNode contactDtoNode = objectMapper.convertValue(origObj, JsonNode.class);
		JsonNode patched = patch.apply(contactDtoNode);
		return objectMapper.treeToValue(patched, classResult);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleException(TagihanNotFoundException ex) {
		ErrorObject eObject = new ErrorObject();
		eObject.setStatus(HttpStatus.NOT_FOUND.value());
		eObject.setMessage(ex.getMessage());
		eObject.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(eObject, HttpStatus.NOT_FOUND);
	}
}
