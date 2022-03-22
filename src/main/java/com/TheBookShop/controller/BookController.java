package com.TheBookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TheBookShop.model.Book;
import com.TheBookShop.repository.BookRepository;


@RestController
@RequestMapping("books")
public class BookController {

	@Autowired
	BookRepository repository;


	@PostMapping("/create")
	Book create(@RequestBody Book book) {
		Book savedBook = repository.save(book);
		return savedBook;

	}

	@PutMapping("/update/{id}")
	Book update(@RequestBody Book newbook, @PathVariable int id) {
		Book savedBook = repository.findById(id).map(book -> {
			book.setTitle(newbook.getTitle());
			book.setAuthor(newbook.getAuthor());
			book.setQuantity(newbook.getQuantity());
			book.setGenre(newbook.getGenre());
			return repository.save(book);
		}).orElseGet(() -> {
			newbook.setId(id);
			return repository.save(newbook);
		});
		return savedBook;

	}

	@DeleteMapping("/delete/{id}")
	ResponseEntity<Void> delete(@PathVariable int id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
