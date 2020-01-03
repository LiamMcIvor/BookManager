package com.bae.manager.rest;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	
	private BookService service;
	
	@Autowired
	public BookController(BookService service) {
		super();
		this.service = service;
	}

	@PostMapping("/createBook")
	public Book createBook(@RequestBody Book book) {
		return this.service.createBook(book);
	}

	@GetMapping("/getAll")
	public List<Book> getAllBooks() {
		return this.service.getAllBooks();
	}

	@GetMapping("/get/{id}")
	public Book getBook(@PathVariable Long id) {
		return this.service.findBookById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public Boolean deleteBook(@PathVariable Long id) {
		return this.service.deleteBook(id);
	}

	@PutMapping("/updateBook/{id}")
	public Book updateBook(@RequestBody Book book, @PathVariable("id") Long id) {
		return this.service.updateBook(book, id);
	}

	@PatchMapping("/appendAuthor/{id}")
	public Book addAuthorToBook(@PathVariable Long id, @RequestBody Collection<Author> authors) {
		return this.service.addAuthorToBook(id, authors);
	}

	@PatchMapping("/updateBookAuthors/{id}")
	public Book updateBookAuthors(@PathVariable Long id, @RequestBody Collection<Author> authors) {
		return this.service.updateBookAuthors(id, authors);
	}
	
	
	
}
