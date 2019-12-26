package com.bae.manager.persistence.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authorId;

	@ManyToMany(mappedBy = "authors")
	@JsonIgnore
	private Set<Book> books;

	private String penName;
	
	
	public Author() {
		super();
	}

	public Author(String penName) {
		super();
		this.penName = penName;
	}

	public Long getId() {
		return authorId;
	}

	public void setId(Long id) {
		this.authorId = id;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public String getPenName() {
		return penName;
	}

	public void setPenName(String penName) {
		this.penName = penName;
	}



	@Override
	public String toString() {
		return "Author [Author ID = " + authorId + ", Pen Name = " + penName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((penName == null) ? 0 : penName.hashCode());
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
		Author other = (Author) obj;
		if (penName == null) {
			if (other.penName != null)
				return false;
		} else if (!penName.equals(other.penName))
			return false;
		return true;
	}

}
