package com.library.service;

import java.util.List;

import com.library.dao.BookDAO;
import com.library.model.Book;

public class BookService {

    private BookDAO dao = new BookDAO();

    // CREATE
    public void addBook(Book book) {
        dao.addBook(book);
    }

    // READ
    public List<Book> getAllBooks() {
        return dao.getAllBooks();
    }

    // UPDATE
    public void updateBook(Book book) {
        dao.updateBook(book);
    }

    // DELETE
    public void deleteBook(int id) {
        dao.deleteBook(id);
    }

    // BASIC SEARCH
    public List<Book> search(String keyword) {
        return dao.search(keyword);
    }

    // ADVANCED SEARCH (title + author + category)
    public List<Book> advancedSearch(String title, String author, String category) {
        return dao.advancedSearch(title, author, category);
    }

    // FILTER
    public List<Book> filterByCategory(String category) {
        return dao.filterByCategory(category);
    }

    // SORT
    public List<Book> getSortedBooks() {
        return dao.getSortedBooks();
    }

    // INVENTORY STATUS
    public void markAsBorrowed(int bookId) {
        dao.markAsBorrowed(bookId);
    }

    public void markAsAvailable(int bookId) {
        dao.markAsAvailable(bookId);
    }
}