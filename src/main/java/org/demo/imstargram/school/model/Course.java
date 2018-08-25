package org.demo.imstargram.school.model;

public class Course {

    private String courseNum;
    private Subject subject;
    private String classNum;
    private Professor professor;
    private String semester;
    private String time;

    public Course() {
        subject = new Subject();
        professor = new Professor();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public void setSubjectNum(String subjectNum){
        this.subject.setSubjectNum(subjectNum);
    }


    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public void setProfessorNum(String professorNum){
        this.professor.setProfessorNum(professorNum);
    }


    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }



    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }


    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return courseNum + '/'
                + subject.getSubjectNum() + '/'
                + classNum + '/'
                + professor.getProfessorNum() + '/'
                + semester + '/'
                + time + '\n';
    }
}