package com.TheBookShop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.TheBookShop.model.Book;
import com.TheBookShop.repository.BookRepository;

@RestController
@RequestMapping("books")
public class BookController {

	@Autowired
	BookRepository repository;

	@GetMapping("")
	public Iterable<Book> getBooks() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Book getBook(@PathVariable int id) {
		return repository.findById(id).get();
	}

	@PostMapping("/create")
	Book create(@RequestBody Book book) {
		return repository.save(book);

	}

	@PostMapping("/total")
	double calculateTotal(@RequestBody Iterable<Book> books) {
		double total = 0;
		for (Book book : books) {
			total += book.getPrice();
		}
		return total;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	class InvalidRequestException extends RuntimeException {
		public InvalidRequestException(String s) {
			super(s);
		}
	}

	@PutMapping()
	Book update(@RequestBody Book book) throws InvalidRequestException {

		if (book == null) {
			throw new InvalidRequestException("Book or ID must not be null!");
		}
		Optional<Book> optionalBook = repository.findById(book.getId());
		if (optionalBook == null) {
			throw new InvalidRequestException("Book with ID " + book.getId() + " does not exist.");
		}
		Book existingBook = optionalBook.get();

		existingBook.setTitle(book.getTitle());
		existingBook.setAuthor(book.getAuthor());
		existingBook.setQuantity(book.getQuantity());
		existingBook.setGenre(book.getGenre());
		existingBook.setPrice(book.getPrice());

		return repository.save(existingBook);

	}

	@DeleteMapping("/{id}")
	ResponseEntity<Void> delete(@PathVariable int id) throws InvalidRequestException {

		if (repository.findById(id) == null) {
			throw new InvalidRequestException("Book with ID " + id + " does not exist.");
		}
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
