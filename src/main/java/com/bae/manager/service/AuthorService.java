package com.bae.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.EntryNotFoundException;
import com.bae.manager.exception.InvalidEntryException;
import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.repo.AuthorRepo;

@Service
public class AuthorService {
	private AuthorRepo repo;

	@Autowired
	public AuthorService(AuthorRepo repo) {
		super();
		this.repo = repo;
	}

	public Author createAuthor(Author author) {
		if (author.getPenName().length() > 250) {
			throw new InvalidEntryException();
		} else if (findRepeatedAuthor(author)) {
			throw new DuplicateValueException();
		}
		return this.repo.save(author);
	}

	public Boolean findRepeatedAuthor(Author author) {
		return this.getAllAuthors().contains(author);
	}

	public List<Author> getAllAuthors() {
		return this.repo.findAll();
	}
	
	public Author findAuthorById(Long id) {
		return this.repo.findById(id).orElseThrow(EntryNotFoundException::new);	
	}

}
