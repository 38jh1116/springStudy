package org.demo.imstargram.school.manager;

import org.demo.imstargram.school.dao.CourseDAO;
import org.demo.imstargram.school.model.Course;
import org.demo.imstargram.school.model.Professor;
import org.demo.imstargram.school.model.ResultCode;
import org.demo.imstargram.school.model.Subject;

import java.util.List;

public class CourseManager {
    private CourseDAO courseDAO;

    public CourseManager(){
        courseDAO = new CourseDAO();
    }

    private boolean checkDuplication(Course course,String exceptCourse){
        return courseDAO.checkDupleCourse(course,exceptCourse);
    }

    private boolean checkValidation(Course course){

        boolean isValid = true;
        // check blank
        if( "".equals(course.getSubject().getSubjectNum())
                || "".equals(course.getClassNum())
                || "".equals(course.getProfessor().getProfessorNum())
                || "".equals(course.getSemester())
                || "".equals(course.getTime())) {
            isValid = false;
        }
        //check whether it contains a slash(delimiter)
        if(course.getSubject().getSubjectNum().contains("/")
                || course.getClassNum().contains("/")
                || course.getProfessor().getProfessorNum().contains("/")
                || course.getSemester().contains("/")
                || course.getTime().contains("/")){
            isValid = false;
        }

        return isValid;
    }
    private boolean checkProfessor(Professor professor) {
        return courseDAO.findProfessor(professor.getProfessorNum());
    }
    private boolean checkSubject(Subject subject) {
        return courseDAO.findSubject(subject.getSubjectNum());
    }

    public String getNewCourseNum(boolean doIncrease) {
        return courseDAO.getNextCourseNum(doIncrease);
    }

    public ResultCode saveCourseInfo(Course newCourse) {
        ResultCode saveResult;
        if(!checkValidation(newCourse)) return ResultCode.INVALID;
        if(checkDuplication(newCourse,null)){
            return ResultCode.ALREADY_EXIST;
        }
        /*
        if(!checkSubject(newCourse.getSubject())  || !checkProfessor(newCourse.getProfessor()) ) {
            return false;
        }
        */
        newCourse.setCourseNum(getNewCourseNum(true));
        saveResult = (courseDAO.insertCourseInfo(newCourse) ? ResultCode.SAVE_SUCCESS : ResultCode.UNKNOWN_FAIL);
        return saveResult;
    }

    public ResultCode modifyCourseInfo(Course targetCourse) {

        ResultCode modifyResult;
        if(!checkValidation(targetCourse)){ return ResultCode.INVALID;}
        if(checkDuplication(targetCourse, targetCourse.getCourseNum())) return ResultCode.ALREADY_EXIST;
        modifyResult = courseDAO.updateCourseInfo(targetCourse) ? ResultCode.MODIFY_SUCCESS : ResultCode.UNKNOWN_FAIL;
        return modifyResult;
    }

    public ResultCode removeCourseInfo(String targetCourseNum) {
        ResultCode removeResult;
        removeResult = courseDAO.deleteCourseInfo(targetCourseNum) ? ResultCode.REMOVE_SUCCESS : ResultCode.UNKNOWN_FAIL;
        return removeResult;
    }

    public Course inquireCourseInfo(String targetCourseNum) {

        return courseDAO.selectCourseInfo(targetCourseNum);
    }

    public List<Course> inquireCoursesInfoBySubjectNum(String subjectNum) {
        return courseDAO.selectCoursesInfoBySubjectNum(subjectNum);

    }
    public List<Course> inquireCoursesInfoBySubjectName(String targetSubjectName){
        return courseDAO.selectCoursesInfoBySubjectName(targetSubjectName);
    }
    public List<Course> inquireCoursesInfoByProfessorName(String targetCourseProfessorName){
        return courseDAO.selectCoursesInfoByProfessorName(targetCourseProfessorName);
    }

    public List<Course> sortCoursesInfoBySubjectNum() {
        return courseDAO.sortCoursesInfoBySubjectNum();
    }

    public List<Course> sortCoursesInfoBySubjectName() {
        return courseDAO.sortCoursesInfoBySubjectName();
    }

    public List<Course> getAllCoursesInfo() {

        return courseDAO.getAllCoursesInfo();
    }
}
