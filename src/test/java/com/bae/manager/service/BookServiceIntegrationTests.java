package com.bae.manager.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.BookRepo;

@RunWith(SpringRunner.class)
@DataJpaTest
class BookServiceIntegrationTests {
	@Autowired
	private BookRepo repo;
	
	private final String TEST_ISBN = "2432655555";
	
	private final Book TEST_BOOK = new Book("The Colour of Magic", TEST_ISBN, "Discworld", 2, Owned.OWNED, Completion.READING);
	
	private Book testSavedBook;

	@Before
	void init() {
		this.repo.deleteAll();
		this.testSavedBook = this.repo.save(this.TEST_BOOK);
		
	}
	
	//@Test
	//public void test() {
	//	assertThat(this.repo.findById(1L)).contains(this.testSavedBook);
	//}
	

}
