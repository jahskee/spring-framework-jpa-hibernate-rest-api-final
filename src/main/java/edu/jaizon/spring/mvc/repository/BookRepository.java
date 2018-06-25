package edu.jaizon.spring.mvc.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import edu.jaizon.spring.mvc.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {
}