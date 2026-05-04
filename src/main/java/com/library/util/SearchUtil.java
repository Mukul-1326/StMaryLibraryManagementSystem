package com.library.util;

import java.util.ArrayList;
import java.util.List;

import com.library.model.Book;
import com.library.model.Borrowing;
import com.library.model.Member;

public class SearchUtil {

    // BOOK ADVANCED FILTER
    public static List<Book> filterBooks(List<Book> books, String title, String author, String category) {
        List<Book> result = new ArrayList<>();

        for (Book b : books) {
            boolean match = b.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                    b.getAuthor().toLowerCase().contains(author.toLowerCase()) &&
                    b.getCategory().toLowerCase().contains(category.toLowerCase());

            if (match) {
                result.add(b);
            }
        }

        return result;
    }

    // MEMBER FILTER
    public static List<Member> filterMembers(List<Member> members, String name, String type) {
        List<Member> result = new ArrayList<>();

        for (Member m : members) {
            boolean match = m.getName().toLowerCase().contains(name.toLowerCase()) &&
                    m.getMembershipType().toLowerCase().contains(type.toLowerCase());

            if (match) {
                result.add(m);
            }
        }

        return result;
    }

    // BORROWING FILTER BY DATE RANGE
    public static List<Borrowing> filterBorrowingsByDate(List<Borrowing> list, String startDate, String endDate) {
        List<Borrowing> result = new ArrayList<>();

        for (Borrowing b : list) {
            String date = b.getBorrowDate();

            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                result.add(b);
            }
        }

        return result;
    }

    // GET OVERDUE (IN-MEMORY CHECK)
    public static List<Borrowing> getOverdue(List<Borrowing> list) {
        List<Borrowing> result = new ArrayList<>();

        for (Borrowing b : list) {
            if ("Overdue".equalsIgnoreCase(b.getStatus())) {
                result.add(b);
            }
        }

        return result;
    }
}