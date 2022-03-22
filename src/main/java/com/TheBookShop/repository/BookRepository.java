package com.TheBookShop.repository;

import org.springframework.data.repository.CrudRepository;

import com.TheBookShop.model.Book;


public interface BookRepository extends CrudRepository<Book, Integer> {

}