package com.bae.manager.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	

}
