package org.demo.imstargram.school.model;


public class Subject {
    private String subjectNum;
    private String name;
    private String credit;

    public String getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(String subjectNum) {
        this.subjectNum = subjectNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return  subjectNum + '/'
                + name + '/'
                + credit
                +"\n";
    }
}
