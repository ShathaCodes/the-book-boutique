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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.TheBookShop.controller.BookController;
import com.TheBookShop.model.Book;
import com.TheBookShop.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;

	@MockBean
	BookRepository bookRepository;

	Book BOOK_1 = new Book(1, "Pride and Prejeduce", "Jane Austen", 4, "Romance", 10.99);
	Book BOOK_2 = new Book(2, "Divergent", "Veronica Roth", 1, "Science fiction", 15.05);
	Book BOOK_3 = new Book(3, "Crazy Rich Asians", "Kevin Kwan", 7, "Humor", 30.00);

	@Test
	public void getBooks_success() throws Exception {
		List<Book> books = new ArrayList<>(Arrays.asList(BOOK_1, BOOK_2, BOOK_3));

		Mockito.when(bookRepository.findAll()).thenReturn(books);

		mockMvc.perform(MockMvcRequestBuilders.get("/books").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[2].title", is("Crazy Rich Asians")));
	}

	@Test
	public void getBook_success() throws Exception {
		Mockito.when(bookRepository.findById(BOOK_1.getId())).thenReturn(java.util.Optional.of(BOOK_1));

		mockMvc.perform(MockMvcRequestBuilders.get("/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.title", is("Pride and Prejeduce"))).andExpect(jsonPath("$.quantity", is(4)));
	}

	@Test
	public void create_success() throws Exception {
		Book book = new Book(4, "A Dance with Dragons", "George R. R. Martin", 5, "Fantasy", 12.55);

		Mockito.when(bookRepository.save(book)).thenReturn(book);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/books/create")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(book));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.title", is("A Dance with Dragons")));
	}

	@Test
	public void update_success() throws Exception {
		Book updated_book = new Book(1, "Emma", "Jane Austen", 4, "Romance", 10.99);

		Mockito.when(bookRepository.findById(BOOK_1.getId())).thenReturn(Optional.of(BOOK_1));
		Mockito.when(bookRepository.save(updated_book)).thenReturn(updated_book);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/books")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updated_book));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.title", is("Emma")));
	}

	// test what to do in case the book is not found
	@Test
	public void update_bookNotFound() throws Exception {
		Book updated_book = new Book(8, "A Dance with Dragons", "George R. R. Martin", 5, "Fantasy", 12.55);

		Mockito.when(bookRepository.findById(updated_book.getId())).thenReturn(null);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/books")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updated_book));

		mockMvc.perform(mockRequest).andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception))
				.andExpect(result -> assertEquals("Book with ID 8 does not exist.",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void delete_success() throws Exception {
		Mockito.when(bookRepository.findById(BOOK_2.getId())).thenReturn(Optional.of(BOOK_2));

		mockMvc.perform(MockMvcRequestBuilders.delete("/books/2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void delete_notFound() throws Exception {
		Mockito.when(bookRepository.findById(BOOK_2.getId())).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.delete("/books/2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception))
				.andExpect(result -> assertEquals("Book with ID 2 does not exist.",
						result.getResolvedException().getMessage()));
	}

}