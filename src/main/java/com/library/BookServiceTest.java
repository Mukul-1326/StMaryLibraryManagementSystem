package com.library;

import java.util.List;

import com.library.model.Book;
import com.library.service.BookService;

public class BookServiceTest {

    public static void main(String[] args) {

        BookService service = new BookService();

        // CREATE
        Book b1 = new Book(1, "Java Basics", "John", "Programming", "Available");
        Book b2 = new Book(2, "Python Guide", "Alice", "Programming", "Available");
        Book b3 = new Book(3, "History Book", "Mark", "History", "Available");

        service.addBook(b1);
        service.addBook(b2);
        service.addBook(b3);

        // READ
        System.out.println("\nAll Books:");
        service.getAllBooks().forEach(book -> System.out.println(book.getBookId() + " " + book.getTitle()));

        // SEARCH
        System.out.println("\nSearch 'Java':");
        service.search("Java").forEach(book -> System.out.println(book.getTitle()));

        // ADVANCED SEARCH
        System.out.println("\nAdvanced Search (Programming):");
        List<Book> adv = service.advancedSearch("", "", "Programming");
        adv.forEach(book -> System.out.println(book.getTitle()));

        // FILTER
        System.out.println("\nFilter by Category 'History':");
        service.filterByCategory("History").forEach(book -> System.out.println(book.getTitle()));

        // SORT
        System.out.println("\nSorted Books:");
        service.getSortedBooks().forEach(book -> System.out.println(book.getTitle()));

        // UPDATE
        Book updated = new Book(1, "Java Advanced", "John", "Programming", "Available");
        service.updateBook(updated);

        System.out.println("\nAfter Update:");
        service.getAllBooks().forEach(book -> System.out.println(book.getBookId() + " " + book.getTitle()));

        // DELETE
        service.deleteBook(1);
        service.deleteBook(2);
        service.deleteBook(3);

        System.out.println("\nAfter Deletion:");
        service.getAllBooks().forEach(book -> System.out.println(book.getBookId() + " " + book.getTitle()));
    }
}