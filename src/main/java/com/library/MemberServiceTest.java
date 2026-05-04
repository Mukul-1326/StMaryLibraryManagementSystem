package com.library;

import java.util.List;

import com.library.model.Member;
import com.library.service.MemberService;

public class MemberServiceTest {

    public static void main(String[] args) {

        MemberService service = new MemberService();

        // CREATE
        Member m1 = new Member(1, "Rahul", "rahul@test.com", "Student");
        Member m2 = new Member(2, "Amit", "amit@test.com", "Staff");
        Member m3 = new Member(3, "Neha", "neha@test.com", "Student");

        service.addMember(m1);
        service.addMember(m2);
        service.addMember(m3);

        // READ
        System.out.println("\nAll Members:");
        service.getAllMembers().forEach(m -> System.out.println(m.getMemberId() + " " + m.getName()));

        // SEARCH
        System.out.println("\nSearch 'Rahul':");
        service.search("Rahul").forEach(m -> System.out.println(m.getName()));

        // ADVANCED SEARCH
        System.out.println("\nAdvanced Search (Student):");
        List<Member> adv = service.advancedSearch("", "", "Student");
        adv.forEach(m -> System.out.println(m.getName()));

        // FILTER
        System.out.println("\nFilter by Type 'Staff':");
        service.filterByType("Staff").forEach(m -> System.out.println(m.getName()));

        // SORT
        System.out.println("\nSorted Members:");
        service.getSortedMembers().forEach(m -> System.out.println(m.getName()));

        // UPDATE
        Member updated = new Member(1, "Rahul Updated", "rahul@test.com", "Student");
        service.updateMember(updated);

        System.out.println("\nAfter Update:");
        service.getAllMembers().forEach(m -> System.out.println(m.getMemberId() + " " + m.getName()));

        // DELETE
        service.deleteMember(1);
        service.deleteMember(2);
        service.deleteMember(3);

        System.out.println("\nAfter Deletion:");
        service.getAllMembers().forEach(m -> System.out.println(m.getMemberId() + " " + m.getName()));
    }
}