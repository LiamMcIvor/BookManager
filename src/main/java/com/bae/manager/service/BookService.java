package com.bae.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;


import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.EntryNotFoundException;
import com.bae.manager.exception.InvalidEntryException;
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
	
	public Book createBook(Book book) {
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
		return this.getAllBooks().contains(book);
	}

	public List<Book> getAllBooks() {
		return this.repo.findAll();
	}

	public Book updateBook(Book book, long id) {
		Book toUpdate = findBookById(id);
		toUpdate.setTitle(book.getTitle());
		toUpdate.setSeries(book.getSeries());
		toUpdate.setIsbn(book.getIsbn());
		toUpdate.setOwned(book.getOwned());
		toUpdate.setCompletion(book.getCompletion());
		return this.repo.save(toUpdate);
	}

	public Book findBookById(long id) {
		return this.repo.findById(id).orElseThrow(EntryNotFoundException::new);
	}

}
