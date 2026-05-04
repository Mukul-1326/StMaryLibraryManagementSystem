package com.library.service;

import java.util.List;

import com.library.dao.BorrowingDAO;
import com.library.model.Borrowing;
import com.library.util.ThreadUtil;

public class BorrowingService {

    private BorrowingDAO dao = new BorrowingDAO();

    // CREATE
    public void addBorrowing(Borrowing b) {
        dao.addBorrowing(b);
    }

    // RETURN BOOK
    public void returnBook(int bookId) {
        dao.returnBook(bookId);
    }

    // DELETE RECORD
    public void deleteBorrowing(int bookId) {
        dao.deleteBorrowing(bookId);
    }

    // GET ALL (AUTO OVERDUE UPDATE)
    public List<Borrowing> getAllBorrowings() {
        dao.updateOverdueStatus();
        return dao.getAll();
    }

    // FIXED SEARCH (WITH OVERDUE UPDATE)
    public List<Borrowing> search(int bookId, int memberId) {
        dao.updateOverdueStatus();
        return dao.search(bookId, memberId);
    }

    // FILTER BY DATE RANGE
    public List<Borrowing> filterByDate(String startDate, String endDate) {
        return dao.filterByDateRange(startDate, endDate);
    }

    // GET OVERDUE
    public List<Borrowing> getOverdueRecords() {
        dao.updateOverdueStatus();
        return dao.getOverdueRecords();
    }

    // SORT
    public List<Borrowing> getSortedRecords() {
        return dao.getSortedByDate();
    }

    // FIXED MULTI-THREADING (USING ThreadUtil)
    public void loadBorrowingsAsync() {
        ThreadUtil.runAsync(() -> {
            System.out.println("Loading borrow records in background...");
            dao.updateOverdueStatus();
            List<Borrowing> list = dao.getAll();
            System.out.println("Loaded " + list.size() + " records.");
        });
    }
}