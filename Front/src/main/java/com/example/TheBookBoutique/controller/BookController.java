package com.example.TheBookBoutique.controller;

import com.example.TheBookBoutique.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class BookController {

	private static ArrayList<Book> books = new ArrayList<Book>();

	private static ArrayList<Book> manageBooks(Book book) {
		init();
		if (book != null)
			books.add(book);
		return books;
	}

	private static ArrayList<Book> init() {
		books = new ArrayList<>();
		final String uri = "http://backend:8080/books/";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(uri, Object[].class);
		Object[] objects = responseEntity.getBody();
		ObjectMapper mapper = new ObjectMapper();
		for (Object o : objects) {
			Book book = mapper.convertValue(o, Book.class);
			books.add(book);
		}
		
		return books;
	}

	@GetMapping("/books")
	public String books(Model model) {
		model.addAttribute("books", manageBooks(null));
		model.addAttribute("book", new Book());
		return "books_page";
	}

	@PostMapping("createBook")
	public String createBook(@ModelAttribute Book book, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/books";
		}
		final String uri = "http://backend:8080/books/create";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject(uri, book, Book.class);
		return "redirect:/books";

	}

	@RequestMapping(value = "/deleteBook", method = RequestMethod.GET)
	public String handleDeleteBook(@RequestParam(name = "bookId") String bookId) {
		final String uri = "http://backend:8080/books";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri + "/" + bookId);
		return "redirect:/books";
	}

	@GetMapping("/edit/{id}")
	public String editBook(@PathVariable("id") int id, Model model) {
		
		final String uri = "http://backend:8080/books/"+id;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Book> responseEntity = restTemplate.getForEntity(uri, Book.class);
		Book editingBook = responseEntity.getBody();

		//Book editingBook = new Book();
		editingBook.setId(id);
		model.addAttribute("book", editingBook);
		return "edit_book_page";
	}

	@PostMapping("/update/{id}")
	public String updateBook(@PathVariable("id") int id, Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			book.setId(id);
			return "edit_book_page";
		}
		final String uri = "http://backend:8080/books";
		Book editedBook = (Book) model.getAttribute("book");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(uri, editedBook, Book.class);
		return "redirect:/books";
	}

}
