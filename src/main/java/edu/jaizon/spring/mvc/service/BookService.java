package edu.jaizon.spring.mvc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.jaizon.spring.mvc.domain.Book;

public interface BookService {
	
	public List<Book> findAll();
	public Book findById(Long id);
	public Book save(Book book);
	public Page<Book> findAllByPage(Pageable pageable);	

}