package com.library.model;

public class Member {
    private int memberId;
    private String name;
    private String email;
    private String membershipType;

    public Member(int memberId, String name, String email, String membershipType) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.membershipType = membershipType;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}