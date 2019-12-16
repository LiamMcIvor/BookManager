package com.bae.manager.service;

import java.util.ArrayList;
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
	private List<Author> filteredAuthors = new ArrayList<>();

	@Autowired
	public AuthorService(AuthorRepo repo) {
		super();
		this.repo = repo;
	}

	public Author createAuthor(Author author) throws InvalidEntryException, DuplicateValueException {
		if (author.getPenName().length() > 250) {
			throw new InvalidEntryException();
		} else if (findRepeatedAuthor(author)) {
			throw new DuplicateValueException();
		}
		return this.repo.save(author);
	}

	public Boolean findRepeatedAuthor(Author author) {
		filteredAuthors = this.getAllAuthors();
		if (filteredAuthors.contains(author)) {
			return true;
		} else {
			return false;
		}
	}

	public List<Author> getAllAuthors() {
		return this.repo.findAll();
	}
	
	public Author findAuthorById(Long id) throws EntryNotFoundException{
		return this.repo.findById(id).orElseThrow(
				() -> new EntryNotFoundException());	
	}

}
