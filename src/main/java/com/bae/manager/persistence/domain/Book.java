package com.bae.manager.persistence.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long bookId;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "author_book_link", joinColumns = @JoinColumn(name = "bookId"), inverseJoinColumns = @JoinColumn(name = "AuthorId"))
	private Set<Author> authors;

	private String title;
	private String isbn;
	private String series;
	private int timesRead;
	private Owned owned;
	private Completion completion;

	public Book() {
		super();
	}

	public Book(String title, String isbn, String series, int timesRead, Owned owned, Completion completion) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.series = series;
		this.timesRead = timesRead;
		this.owned = owned;
		this.completion = completion;
	}

	public Long getId() {
		return bookId;
	}

	public void setId(Long id) {
		this.bookId = id;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public int getTimesRead() {
		return timesRead;
	}

	public void setTimesRead(int timesRead) {
		this.timesRead = timesRead;
	}

	public Owned getOwned() {
		return owned;
	}

	public void setOwned(Owned owned) {
		this.owned = owned;
	}

	public Completion getCompletion() {
		return completion;
	}

	public void setCompletion(Completion completion) {
		this.completion = completion;
	}

	@Override
	public String toString() {
		return "Book [id=" + bookId + ", Authors=" + authors + ", title=" + title + ", isbn=" + isbn + ", series=" + series
				+ ", timesRead=" + timesRead + ", owned=" + owned + ", completion=" + completion + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((series == null) ? 0 : series.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (series == null) {
			if (other.series != null)
				return false;
		} else if (!series.equals(other.series))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}


}
