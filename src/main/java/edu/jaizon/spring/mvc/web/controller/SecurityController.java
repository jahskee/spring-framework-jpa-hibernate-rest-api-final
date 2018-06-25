package edu.jaizon.spring.mvc.web.controller;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.jaizon.spring.mvc.domain.Book;
import edu.jaizon.spring.mvc.service.BookService;
import edu.jaizon.spring.mvc.web.util.Message;


@Controller
@RequestMapping("/security")
public class SecurityController {
	
	static Logger log = Logger.getLogger(SecurityController.class);
	
	private BookService bookService;	
	private MessageSource messageSource;
	 
	@RequestMapping("/loginfail")
	public String loginFail(Model uiModel, Locale locale) {
		log.info("Login failed detected");
		uiModel.addAttribute("message", new Message("error",
		messageSource.getMessage("message_login_fail", new Object[]{}, locale)));
		
		log.info("Listing books");
		List<Book> books = bookService.findAll();	
		uiModel.addAttribute("books", books);	
		log.info("No. of books: " + books.size());
	
		return "books/list";	
	}	
	
	@Autowired
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
	this.messageSource = messageSource;
	}

}
