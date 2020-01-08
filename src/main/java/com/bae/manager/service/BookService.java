package com.bae.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.EntryNotFoundException;
import com.bae.manager.exception.InvalidEntryException;
import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.BookRepo;

@Service
public class BookService {

	private BookRepo repo;
		
	private AuthorService authorService;
	
	@Autowired
	public BookService(BookRepo repo, AuthorService authorService) {
		super();
		this.repo = repo;
		this.authorService = authorService;
	}

	public Book createBook(Book book) {
		verifyValidBook(book);
		return this.repo.save(book);
	}

	public boolean findRepeatedBook(Book book) {
		return this.getAllBooks().contains(book);
	}

	public List<Book> getAllBooks() {
		return this.repo.findAll();
	}

	public Book updateBook(Book book, long id) {
		//verifyValidBook(book);
		Book toUpdate = findBookById(id);
		toUpdate.setTitle(book.getTitle());
		toUpdate.setSeries(book.getSeries());
		toUpdate.setOwned(book.getOwned());
		toUpdate.setCompletion(book.getCompletion());
		toUpdate.setTimesRead(book.getTimesRead());
		return this.repo.save(toUpdate);
	}

	public Book findBookById(long id) {
		return this.repo.findById(id).orElseThrow(EntryNotFoundException::new);
	}

	public Boolean verifyValidBook(Book book) {
		if (book.getTitle().length() > 250) {
			throw new InvalidEntryException();
		}
		else if (book.getSeries().length() > 100) {
			
		}
		else if (findRepeatedBook(book)) {
			throw new DuplicateValueException();
		}
		else if (book.getTimesRead() < 0 || book.getTimesRead() > 1000) {
			throw new InvalidEntryException();
		}
		return true;
	}

	public boolean deleteBook(long id) {
		if (!this.repo.existsById(id)) {
			throw new EntryNotFoundException();
		}
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}

	public Book addAuthorToBook(long id, Collection<Author> authors) {
		Book toUpdate = this.findBookById(id);

		toUpdate.getAuthors().addAll((authors));
		
		return this.repo.saveAndFlush(toUpdate);
	}
	
	public Book updateBookAuthors(long id, Collection<Author> authors) {
		Book toUpdate = this.findBookById(id);
		toUpdate.getAuthors().clear();

		for(Author author : authors) {
			if(!this.authorService.findRepeatedAuthor(author)) {
				author = this.authorService.createAuthor(author);
			}
		}
		return this.addAuthorToBook(id, authors);
	}

}
