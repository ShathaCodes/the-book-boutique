package com.TheBookShop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.TheBookShop.controller.BookController;
import com.TheBookShop.model.Book;
import com.TheBookShop.repository.BookRepository;

@WebMvcTest(BookController.class)
public class UnitTest {
	
	@MockBean
	BookRepository bookRepository;

	@Autowired
	BookController bookController;

	@Test
	public void getBooks_success() throws Exception {
		// given a list of books
		Book book1 = new Book(1, "Pride and Prejeduce", "Jane Austen", 4, "Romance", 10.99);
		Book book2 = new Book(2, "Divergent", "Veronica Roth", 1, "Science fiction", 15.05);
		Book book3 = new Book(3, "Crazy Rich Asians", "Kevin Kwan", 7, "Humor", 30.00);
		List<Book> books = new ArrayList<>(Arrays.asList(book1, book2, book3));

		Mockito.when(bookRepository.findAll()).thenReturn(books);

		// when we execute getBooks function
		List<Book> result_books = new ArrayList<Book>();
		bookController.getBooks().forEach(result_books::add);

		// then return the same list of books
		assertEquals(books.size(), result_books.size());
		assertEquals(books.get(1).getTitle(), result_books.get(1).getTitle());

	}

	@Test
	public void getBook_success() throws Exception {

		// given a book
		Book book1 = new Book(1, "Pride and Prejeduce", "Jane Austen", 4, "Romance", 10.99);
		Mockito.when(bookRepository.findById(book1.getId())).thenReturn(java.util.Optional.of(book1));

		// when we execute getBook function
		Book result_book = bookController.getBook(book1.getId());

		// then return the same book
		assertEquals(book1.getTitle(), result_book.getTitle());
		assertEquals(book1.getQuantity(), result_book.getQuantity());
	}

	@Test
	public void create_success() throws Exception {
		// given a book
		Book book = new Book(4, "A Dance with Dragons", "George R. R. Martin", 5, "Fantasy", 12.55);

		Mockito.when(bookRepository.save(book)).thenReturn(book);

		// when we execute create function
		Book result_book = bookController.create(book);

		// then return the same book
		assertEquals(book.getTitle(), result_book.getTitle());
		assertEquals(book.getQuantity(), result_book.getQuantity());
	}

	@Test
	public void update_success() throws Exception {

		// given a book
		Book book = new Book(1, "Pride and Prejeduce", "Jane Austen", 4, "Romance", 10.99);
		Book updated_book = new Book(1, "Emma", "Jane Austen", 4, "Romance", 10.99);

		Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
		Mockito.when(bookRepository.save(updated_book)).thenReturn(updated_book);

		// when we execute update function
		Book result_book = bookController.update(updated_book);

		// then return the same book
		assertEquals(book.getTitle(), result_book.getTitle());
		assertEquals(book.getQuantity(), result_book.getQuantity());
	}


	@Test
	public void delete_success() throws Exception {
		// given a book
		Book book = new Book(2, "Divergent", "Veronica Roth", 1, "Science fiction", 15.05);
		Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

		// when we execute delete function
		bookController.delete(book.getId());

		// then nothing happens, no exception is thrown
	}


}