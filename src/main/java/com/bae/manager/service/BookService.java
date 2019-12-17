package com.bae.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.InvalidEntryException;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.BookRepo;

@Service
public class BookService {
	
	private BookRepo repo;
	private List<Book> filteredBooks = new ArrayList<>();


	@Autowired
	public BookService(BookRepo repo) {
		super();
		this.repo = repo;
	}
	
	public Book createBook(Book book) throws InvalidEntryException, DuplicateValueException {
		if (book.getTitle().length() > 250) {
			throw new InvalidEntryException();
		} else if (findRepeatedBook(book)) {
			throw new DuplicateValueException();
		}
		else if (!StringUtils.isNumeric(book.getIsbn())) {
			throw new InvalidEntryException();
		}
		else if (!(book.getIsbn().length() == 10 || book.getIsbn().length() == 13)) {
			throw new InvalidEntryException();
		}
		else if (book.getTimesRead() < 0 || book.getTimesRead() > 1000) {
			throw new InvalidEntryException();
		}
		
		return this.repo.save(book);
	}

	public boolean findRepeatedBook(Book book) {
			filteredBooks = this.getAllBooks();
			if (filteredBooks.contains(book)) {
				return true;
			} else {
				return false;
			}
	}

	public List<Book> getAllBooks() {
		return this.repo.findAll();
	}

}
