package com.library.ui.console;

import java.util.Scanner;

import com.library.model.Book;
import com.library.model.Borrowing;
import com.library.model.Member;
import com.library.service.BookService;
import com.library.service.BorrowingService;
import com.library.service.MemberService;
import com.library.util.ValidationUtil;

public class ConsoleMenu {

    private BookService bookService = new BookService();
    private MemberService memberService = new MemberService();
    private BorrowingService borrowingService = new BorrowingService();

    private Scanner sc = new Scanner(System.in);

    public void start() {

        while (true) {
            System.out.println("\n===== LIBRARY MENU =====");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Members");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Borrow Records");
            System.out.println("6. Search Borrow Records");
            System.out.println("7. Advanced Search");
            System.out.println("8. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> manageBooks();
                case 2 -> manageMembers();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 5 -> viewBorrowings();
                case 6 -> searchBorrowings();
                case 7 -> advancedSearch();
                case 8 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void manageBooks() {
        System.out.println("\n1. Add\n2. View\n3. Delete\n4. Update\n5. Sort");
        int ch = sc.nextInt();

        switch (ch) {
            case 1 -> {
                System.out.print("Book ID: ");
                String idStr = sc.next();

                if (!ValidationUtil.isNumeric(idStr)) {
                    System.out.println("Invalid ID");
                    return;
                }

                int id = Integer.parseInt(idStr);
                sc.nextLine();

                System.out.print("Title: ");
                String title = sc.nextLine();

                System.out.print("Author: ");
                String author = sc.nextLine();

                System.out.print("Category: ");
                String category = sc.nextLine();

                bookService.addBook(new Book(id, title, author, category, "Available"));
            }

            case 2 -> bookService.getAllBooks().forEach(
                    b -> System.out.println(b.getBookId() + " | " + b.getTitle() + " | " + b.getAvailabilityStatus()));

            case 3 -> {
                System.out.print("ID: ");
                int id = sc.nextInt();

                System.out.print("Confirm delete? (y/n): ");
                if (sc.next().equalsIgnoreCase("y")) {
                    bookService.deleteBook(id);
                }
            }

            case 4 -> {
                System.out.print("ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("New Title: ");
                String title = sc.nextLine();

                System.out.print("New Author: ");
                String author = sc.nextLine();

                System.out.print("New Category: ");
                String category = sc.nextLine();

                bookService.updateBook(new Book(id, title, author, category, "Available"));
            }

            case 5 -> bookService.getSortedBooks().forEach(b -> System.out.println(b.getTitle()));
        }
    }

    private void manageMembers() {
        System.out.println("\n1. Add\n2. View\n3. Delete\n4. Update\n5. Filter");
        int ch = sc.nextInt();

        switch (ch) {
            case 1 -> {
                System.out.print("ID: ");
                String idStr = sc.next();

                if (!ValidationUtil.isNumeric(idStr)) {
                    System.out.println("Invalid ID");
                    return;
                }

                int id = Integer.parseInt(idStr);
                sc.nextLine();

                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Email: ");
                String email = sc.nextLine();

                if (!ValidationUtil.isValidEmail(email)) {
                    System.out.println("Invalid Email");
                    return;
                }

                System.out.print("Type: ");
                String type = sc.nextLine();

                memberService.addMember(new Member(id, name, email, type));
            }

            case 2 -> memberService.getAllMembers().forEach(
                    m -> System.out.println(m.getMemberId() + " | " + m.getName()));

            case 3 -> {
                System.out.print("ID: ");
                int id = sc.nextInt();

                System.out.print("Confirm delete? (y/n): ");
                if (sc.next().equalsIgnoreCase("y")) {
                    memberService.deleteMember(id);
                }
            }

            case 4 -> {
                System.out.print("ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Type: ");
                String type = sc.nextLine();

                memberService.updateMember(new Member(id, name, email, type));
            }

            case 5 -> {
                System.out.print("Type: ");
                String type = sc.next();
                memberService.filterByType(type).forEach(m -> System.out.println(m.getName()));
            }
        }
    }

    private void borrowBook() {
        System.out.print("Book ID: ");
        String bookIdStr = sc.next();

        System.out.print("Member ID: ");
        String memberIdStr = sc.next();

        if (!ValidationUtil.isNumeric(bookIdStr) || !ValidationUtil.isNumeric(memberIdStr)) {
            System.out.println("Invalid IDs");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);
        int memberId = Integer.parseInt(memberIdStr);

        sc.nextLine();

        System.out.print("Borrow Date (YYYY-MM-DD): ");
        String borrowDate = sc.nextLine();

        System.out.print("Due Date (YYYY-MM-DD): ");
        String dueDate = sc.nextLine();

        if (!ValidationUtil.isValidDate(borrowDate) || !ValidationUtil.isValidDate(dueDate)) {
            System.out.println("Invalid Date Format");
            return;
        }

        //
        try {
            java.time.LocalDate borrow = java.time.LocalDate.parse(borrowDate);
            java.time.LocalDate due = java.time.LocalDate.parse(dueDate);

            if (due.isBefore(borrow)) {
                System.out.println("Due date must be after borrow date");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid date values");
            return;
        }

        borrowingService.addBorrowing(
                new Borrowing(bookId, memberId, borrowDate, dueDate, "Borrowed"));

        // Existing logic (UNCHANGED)
        bookService.markAsBorrowed(bookId);
    }

    private void returnBook() {
        System.out.print("Book ID: ");
        int id = sc.nextInt();

        borrowingService.returnBook(id);
        bookService.markAsAvailable(id);
    }

    private void viewBorrowings() {
        borrowingService.getAllBorrowings().forEach(b -> System.out.println(
                b.getBookId() + " | " +
                        b.getMemberId() + " | " +
                        b.getBorrowDate() + " | " +
                        b.getDueDate() + " | " +
                        b.getStatus()));

        System.out.print("\nDelete a record? (y/n): ");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();

            System.out.print("Confirm delete? (y/n): ");
            if (sc.next().equalsIgnoreCase("y")) {
                borrowingService.deleteBorrowing(id);
                System.out.println("Record deleted.");
            }
        }
    }

    private void searchBorrowings() {
        System.out.println("1. Search by Book ID\n2. Search by Member ID");
        int ch = sc.nextInt();

        if (ch == 1) {
            System.out.print("Book ID: ");
            int id = sc.nextInt();

            borrowingService.search(id, -1)
                    .forEach(b -> System.out.println(b.getBookId() + " | " + b.getStatus()));

        } else {
            System.out.print("Member ID: ");
            int id = sc.nextInt();

            borrowingService.search(-1, id)
                    .forEach(b -> System.out.println(b.getMemberId() + " | " + b.getStatus()));
        }
    }

    private void advancedSearch() {
        System.out.println("1. Book Search\n2. Member Search");
        int ch = sc.nextInt();

        sc.nextLine();

        if (ch == 1) {
            System.out.print("Title: ");
            String t = sc.nextLine();

            System.out.print("Author: ");
            String a = sc.nextLine();

            System.out.print("Category: ");
            String c = sc.nextLine();

            bookService.advancedSearch(t, a, c)
                    .forEach(b -> System.out.println(b.getTitle()));
        } else {
            System.out.print("Name: ");
            String n = sc.nextLine();

            System.out.print("Email: ");
            String e = sc.nextLine();

            System.out.print("Type: ");
            String t = sc.nextLine();

            memberService.advancedSearch(n, e, t)
                    .forEach(m -> System.out.println(m.getName()));
        }
    }
}