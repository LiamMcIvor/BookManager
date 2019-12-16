package com.bae.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bae.manager.persistence.repo.BookRepo;

@Service
public class BookService {
	
	private BookRepo repo;

	@Autowired
	public BookService(BookRepo repo) {
		super();
		this.repo = repo;
	}

}
