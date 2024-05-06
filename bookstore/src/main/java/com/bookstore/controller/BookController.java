package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bookstore.entity.Book;
import com.bookstore.entity.MyBookList;
import com.bookstore.service.BookService;
import com.bookstore.service.MyBookListService;

@Controller
public class BookController {

	@Autowired
	private BookService service;
	
	@Autowired
	private MyBookListService myBookService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}
	
	@GetMapping("/avaliable_books")
	public ModelAndView getAllBook() {
		List<Book> list=service.getAllBook();
		ModelAndView m= new ModelAndView();
		m.setViewName("bookList");
		m.addObject("book", list);
		return m;
	}
	
	@PostMapping("/save")
	public String addBook(@ModelAttribute Book b) {
		service.save(b);
		return "redirect:/avaliable_books";
	}
	
	@GetMapping("/my_books")
	public String getMyBooks(Model model) {
		List<MyBookList> list=myBookService.getAllBooks();
		model.addAttribute("book", list);
		return "MyBooks";
	}
	
	@RequestMapping("/myList/{id}")
	public String getMyList(@PathVariable("id") int id) {
		Book b= service.getBookById(id);
		MyBookList mb= new MyBookList(b.getId(),b.getName(), b.getAuthor(), b.getPrice());
		myBookService.saveMyBooks(mb);
		return "redirect:/my_books";
	}
	
	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id, Model model) {
		Book b=service.getBookById(id);
		model.addAttribute("book", b);
		return "editBook";
	}
	
	@RequestMapping("/deleteBook/{id}")
	public String DeleteBook(@PathVariable("id") int id) {
		service.deleteById(id);
		return "redirect:/avaliable_books";
	}
}
