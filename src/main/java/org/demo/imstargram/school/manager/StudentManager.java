package org.demo.imstargram.school.manager;

import org.demo.imstargram.school.dao.StudentDAO;
import org.demo.imstargram.school.model.ResultCode;
import org.demo.imstargram.school.model.Student;

import java.util.List;

public class StudentManager {
    private StudentDAO studentDAO;

    public StudentManager(){
        studentDAO = new StudentDAO();
    }

    private boolean checkDuplication(String studentRRN, String exceptStudent){
        return studentDAO.checkDupleStudent(studentRRN,exceptStudent);
    }

    private boolean checkValidation(Student student){

        boolean isValid = true;
        if("".equals(student.getName())
                || "".equals(student.getEmail())
                || "".equals(student.getRRN())
                || "".equals(student.getPhoneNum())) {
            isValid = false;
        }
        if(student.getName().contains("/")
                || student.getEmail().contains("/")
                || student.getPhoneNum().contains("/")
                || student.getRRN().contains("/")) {
            isValid = false;
        }

        return isValid;
    }

    public String getNewStudentNum(boolean doIncrease) {
        return studentDAO.getNextStudentNum(doIncrease);
    }

    public ResultCode saveStudentInfo(Student newStudent) {
        ResultCode saveResult;
        if(!checkValidation(newStudent)) return ResultCode.INVALID;
        if(checkDuplication(newStudent.getRRN(),null)){
            return ResultCode.ALREADY_EXIST;
        }
        newStudent.setStudentNum(getNewStudentNum(true));
        saveResult = (studentDAO.insertStudentInfo(newStudent)) ? ResultCode.SAVE_SUCCESS :ResultCode.UNKNOWN_FAIL;
        return saveResult;
    }
    public ResultCode modifyStudentInfo(Student targetStudent) {
        ResultCode modifyResult;
        if(!checkValidation(targetStudent)) return ResultCode.INVALID;
        if(checkDuplication(targetStudent.getRRN(),targetStudent.getStudentNum())) return ResultCode.ALREADY_EXIST;
        modifyResult = studentDAO.updateStudentInfo(targetStudent) ? ResultCode.MODIFY_SUCCESS : ResultCode.UNKNOWN_FAIL;
        return modifyResult;
    }

    public ResultCode removeStudentInfo(String targetStudentNum) {

        ResultCode removeResult;
        removeResult = studentDAO.deleteStudentInfo(targetStudentNum) ? ResultCode.REMOVE_SUCCESS : ResultCode.UNKNOWN_FAIL;
        return removeResult;

    }
    /*
    public Student searchStudentInfoByStudentNum(String targetStudentNum) {
        return studentDAO.selectStudentInfo(targetStudentNum);
    }

    public List<Student> searchStudentsInfoByName(String targetStudentName){
        return studentDAO.selectStudentsInfoByName(targetStudentName);
    }
    */

    public List<Student> sortStudentsInfoByStudentNum() {
        return studentDAO.sortStudentsInfoByStudentNum();
    }

    public List<Student> sortStudentsInfoByName() {
        return studentDAO.sortStudentsInfoByName();
    }

    public List<Student> getAllStudentsInfo() {
        return studentDAO.getAllStudentsInfo();
    }
}
