package com.bae.manager.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bae.manager.persistence.domain.Author;
import com.bae.manager.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {
	
	private AuthorService service;
	
	@Autowired
	public AuthorController(AuthorService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/createAuthor")
	public Author createAuthor(@RequestBody Author author) {
		return this.service.createAuthor(author);
	}

	@GetMapping("/getAll")
	public List<Author> getAllAuthors() {
		return this.service.getAllAuthors();
	}
}
