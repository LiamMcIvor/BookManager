package com.bae.manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.InvalidEntryException;
import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.repo.AuthorRepo;

@RunWith(SpringRunner.class)
public class AuthorUnitTests {

	@InjectMocks
	private AuthorService service;

	@Mock
	private AuthorRepo repo;

	private List<Author> authorList;

	private Author testAuthor;

	private Author testAuthorWIthId;

	private Author testAuthorFail;

	private Author testAuthorFailWithId;

	final long id = 1L;
	private String length50 = "XhAcsWAgQPahSNCcaHuvsHNruwYTgtDynDOswCtEuHRCfAxpAh";
	private String length250 = "PbvtPIUTFWcyFFtucstLjqIhztILbbWHnoMZpfMupJsQjdqxDcpFfDtrJcdajvmqqocwlbzjROsLYcgZgWyboQPzxCdhVrvXnXJEXOhkzSGoEyeWFlkvHIkiDJIjsWRqZcVbpwZoRqsgdRVxDjWQvMPuIeYQnqxCDpdTkvaFnCdoPSYKWjPKIyOGbRJCurpbkoBgTmmc";

	@Before
	public void init() {
		this.authorList = new ArrayList<>();
		this.testAuthor = new Author("Terry", "Pratchett");
		this.testAuthorWIthId = new Author(testAuthor.getFirstName(), testAuthor.getLastName());
		this.testAuthorWIthId.setId(id);
		this.authorList.add(testAuthor);
		this.authorList.add(testAuthor);


	}

	@Test
	public void createAuthorTest() {
		when(this.repo.save(testAuthor)).thenReturn(testAuthorWIthId);
		assertEquals(this.testAuthorWIthId, this.service.createAuthor(testAuthor));
		verify(this.repo, times(1)).save(this.testAuthor);

		this.testAuthorFail = new Author(this.length250, this.length50);
		this.testAuthorFail.setId(id);
		this.testAuthorFailWithId = new Author(testAuthorFail.getFirstName(), testAuthorFail.getLastName());
		this.testAuthorFailWithId.setId(id);

		when(this.repo.save(testAuthorFail)).thenReturn(testAuthorFailWithId);
		assertThrows(InvalidEntryException.class, () -> {
			this.service.createAuthor(testAuthorFail);
		});

	}

	@Test
	public void findDuplicateAuthorTest() {
		when(this.repo.findByLastName(testAuthor.getLastName())).thenReturn(authorList);
		assertThrows(DuplicateValueException.class, () -> {
			this.service.createAuthor(testAuthor);
		});
	}

}
