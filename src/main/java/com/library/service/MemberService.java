package com.library.service;

import java.util.List;

import com.library.dao.MemberDAO;
import com.library.model.Member;

public class MemberService {

    private MemberDAO dao = new MemberDAO();

    // CREATE
    public void addMember(Member m) {
        dao.addMember(m);
    }

    // READ
    public List<Member> getAllMembers() {
        return dao.getAllMembers();
    }

    // UPDATE
    public void updateMember(Member m) {
        dao.updateMember(m);
    }

    // DELETE
    public void deleteMember(int id) {
        dao.deleteMember(id);
    }

    // BASIC SEARCH
    public List<Member> search(String keyword) {
        return dao.search(keyword);
    }

    // ADVANCED SEARCH (name + email + type)
    public List<Member> advancedSearch(String name, String email, String type) {
        return dao.advancedSearch(name, email, type);
    }

    // FILTER BY TYPE
    public List<Member> filterByType(String type) {
        return dao.filterByType(type);
    }

    // SORT MEMBERS
    public List<Member> getSortedMembers() {
        return dao.getSortedMembers();
    }
}