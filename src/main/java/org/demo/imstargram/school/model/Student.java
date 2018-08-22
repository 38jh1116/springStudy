package org.demo.imstargram.school.model;

public class Student {
    private String studentNum;
    private String name;
    private String RRN;
    private String phoneNum;
    private String email;

    public Student(){

    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRRN() {
        return RRN;
    }

    public void setRRN(String RRN) {
        this.RRN = RRN;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {

        return studentNum + "/"
                + name + "/"
                + RRN + "/"
                + phoneNum + "/"
                + email
                +"\n";
    }
}
