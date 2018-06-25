package edu.jaizon.spring.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.jaizon.spring.mvc.domain.Book;
import edu.jaizon.spring.mvc.repository.BookRepository;

@Repository
@Transactional
@Service("bookService")
public class BookServiceImpl implements BookService {
	private BookRepository bookRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Book> findAll() {
		return Lists.newArrayList(bookRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Book findById(Long id) {
		return bookRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Book> findAllByPage(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}	

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	@Autowired
	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
}