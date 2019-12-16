package com.bae.manager.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.persistence.repo.BookRepo;

@RunWith(SpringRunner.class)
class BookUnitTests {
	
	@InjectMocks
	private BookService service;

	@Mock
	private BookRepo repo;

	@Test
	void est() {
		
	}

}
