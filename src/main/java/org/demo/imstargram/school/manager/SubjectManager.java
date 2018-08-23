package org.demo.imstargram.school.manager;

import org.demo.imstargram.school.dao.SubjectDAO;
import org.demo.imstargram.school.model.ResultCode;
import org.demo.imstargram.school.model.Subject;

import java.util.List;

public class SubjectManager {
    private SubjectDAO subjectDAO;

    public SubjectManager(){
        subjectDAO = new SubjectDAO();
    }

    private boolean checkDuplication(String subjectName,String exceptSubject){
        return subjectDAO.checkDupleSubject(subjectName,exceptSubject);
    }

    private boolean checkValidation(Subject subject){

        boolean isValid = true;
        if("".equals(subject.getName()) || "".equals(subject.getCredit())) {
            isValid = false;
        }
        if(subject.getName().contains("/") || subject.getCredit().contains("/")) {
            isValid = false;
        }

        return isValid;
    }

    public String getNewSubjectNum(boolean doIncrease) {
        return subjectDAO.getNextSubjectNum(doIncrease);
    }

    public ResultCode saveSubjectInfo(Subject newSubject) {
        ResultCode saveResult;
        if(!checkValidation(newSubject)) return ResultCode.INVALID;
        if(checkDuplication(newSubject.getSubjectNum(),null)){
            return ResultCode.ALREADY_EXIST;
        }
        newSubject.setSubjectNum(getNewSubjectNum(true));
        saveResult = (subjectDAO.insertSubjectInfo(newSubject)) ? ResultCode.SAVE_SUCCESS :ResultCode.UNKNOWN_FAIL;
        return saveResult;
    }
    public ResultCode modifySubjectInfo(Subject targetSubject) {
        ResultCode modifyResult;
        if(!checkValidation(targetSubject)) return ResultCode.INVALID;
        if(checkDuplication(targetSubject.getSubjectNum(),targetSubject.getSubjectNum())) return ResultCode.ALREADY_EXIST;
        modifyResult = subjectDAO.updateSubjectInfo(targetSubject) ? ResultCode.MODIFY_SUCCESS : ResultCode.UNKNOWN_FAIL;
        return modifyResult;
    }

    public ResultCode removeSubjectInfo(String targetSubjectNum) {

        ResultCode removeResult;
        removeResult = subjectDAO.deleteSubjectInfo(targetSubjectNum) ? ResultCode.REMOVE_SUCCESS : ResultCode.UNKNOWN_FAIL;
        return removeResult;

    }
    public List<Subject> searchSubjectInfo(String targetSubjectNum) {
        return subjectDAO.selectSubjectInfo(targetSubjectNum);
    }

    public List<Subject> searchSubjectsInfoByName(String targetSubjectName){
        return subjectDAO.selectSubjectsInfoByName(targetSubjectName);
    }

    public List<Subject> sortSubjectsInfoBySubjectNum() {
        return subjectDAO.sortSubjectsInfoBySubjectNum();
    }

    public List<Subject> sortSubjectsInfoByName() {
        return subjectDAO.sortSubjectsInfoByName();
    }

    public List<Subject> getAllSubjectsInfo() {
        return subjectDAO.getAllSubjectsInfo();
    }
}