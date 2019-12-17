package com.bae.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.InvalidEntryException;
import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.BookRepo;

@Service
public class BookService {
	
	private BookRepo repo;

	@Autowired
	public BookService(BookRepo repo) {
		super();
		this.repo = repo;
	}
	
	public Book createBook(Book book) throws InvalidEntryException, DuplicateValueException {
		/*if (book.getTitle().length() > 250) {
			throw new InvalidEntryException();
		} else if (findRepeatedBook(book)) {
			throw new DuplicateValueException();
		}*/
		return this.repo.save(book);
	}

	private boolean findRepeatedBook(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

}
