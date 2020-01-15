package com.bae.manager.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bae.manager.persistence.domain.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

}
