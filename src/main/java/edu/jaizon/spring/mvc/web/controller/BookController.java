package edu.jaizon.spring.mvc.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import edu.jaizon.spring.mvc.domain.Book;
import edu.jaizon.spring.mvc.service.BookService;
import edu.jaizon.spring.mvc.web.util.BookGrid;
import edu.jaizon.spring.mvc.web.util.FormNotify;
import edu.jaizon.spring.mvc.web.util.Message;
import edu.jaizon.spring.mvc.web.util.UrlUtil;

@Controller
public class BookController {

	static Logger log = Logger.getLogger(BookController.class);

	private BookService bookService;
	private MessageSource messageSource;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String list(Model model) {
		log.info("Listing books");
		List<Book> books = bookService.findAll();

		model.addAttribute("books", books);

		log.info("No. of books: " + books.size());

		return "books/list";
	}

	@RequestMapping(value = "books/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model uiModel) {
		Book book = bookService.findById(id);
		uiModel.addAttribute("book", book);

		return "books/show";
	}

	// ******************* Update Book *********************
	@RequestMapping(value = "books/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		FormNotify formNotify = new FormNotify(FormNotify.UPDATEBOOK);

		uiModel.addAttribute("formNotify", formNotify);
		uiModel.addAttribute("book", bookService.findById(id));
		return "books/update";
	}

	@RequestMapping(value = "books/{id}", params = "form", method = RequestMethod.POST)
	public String update(@Valid Book book, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		log.info("Updating book");
		FormNotify formNotify = new FormNotify(FormNotify.UPDATEBOOK);

		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message",
					new Message("error", messageSource.getMessage("book_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("formNotify", formNotify);
			uiModel.addAttribute("book", book);
			return "books/update";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute("message",
				new Message("success", messageSource.getMessage("book_save_success", new Object[] {}, locale)));
		uiModel.addAttribute("formNotify", formNotify);
		try {
			bookService.save(book);
		} catch (Exception e) {
			uiModel.addAttribute("message", e.getMessage());
		}
		return "redirect:/books/" + UrlUtil.encodeUrlPathSegment(book.getId().toString(), httpServletRequest);
	}

	// ******************* Add Book *********************
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "books", params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		FormNotify formNotify = new FormNotify(FormNotify.ADDBOOK);

		Book book = new Book();
		uiModel.addAttribute("formNotify", formNotify);
		uiModel.addAttribute("book", book);
		return "books/create";
	}

	@RequestMapping(value = "books", params = "form", method = RequestMethod.POST)
	public String create(@Valid Book book, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		FormNotify formNotify = new FormNotify(FormNotify.ADDBOOK);

		log.info("Creating book");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message",
					new Message("error", messageSource.getMessage("book_add_fail", new Object[] {}, locale)));
			uiModel.addAttribute("book", book);
			uiModel.addAttribute("formNotify", formNotify);
			return "books/create";
		}

		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute("message",
				new Message("success", messageSource.getMessage("book_add_success", new Object[] {}, locale)));

		log.info("Book ID: " + book.getId());

		uiModel.addAttribute("formNotify", formNotify);
		bookService.save(book);

		return "redirect:/books/" + UrlUtil.encodeUrlPathSegment(book.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "books/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public BookGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		
		// Process order by
		Sort sort = null;
		String orderBy = sortBy;
		if (orderBy != null && orderBy.equals("id"))
			orderBy = "id";

		if (orderBy != null && order != null) {
			if (order.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, orderBy);
			} else
				sort = new Sort(Sort.Direction.ASC, orderBy);
		}

		// Constructs page request for current page
		// Note: page number for Spring Data JPA starts with 0, while jqGrid
		// starts with 1
		PageRequest pageRequest = null;
		
		//page=1;
		//rows=10;
		
		if (sort != null) {
			pageRequest = new PageRequest(page - 1, rows, sort);
		} else {
			pageRequest = new PageRequest(page - 1, rows);
		}

		Page<Book> bookPage = bookService.findAllByPage(pageRequest);

		// Construct the grid data that will return as JSON data
		BookGrid bookGrid = new BookGrid();

		bookGrid.setCurrentPage(bookPage.getNumber() + 1);
		bookGrid.setTotalPages(bookPage.getTotalPages());
		bookGrid.setTotalRecords(bookPage.getTotalElements());

		bookGrid.setBookData(Lists.newArrayList(bookPage.iterator()));

		return bookGrid;
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