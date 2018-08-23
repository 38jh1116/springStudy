package org.demo.imstargram.school.manager;

import org.demo.imstargram.school.dao.ProfessorDAO;
import org.demo.imstargram.school.model.Professor;
import org.demo.imstargram.school.model.ResultCode;

import java.util.List;

public class ProfessorManager {
    private ProfessorDAO professorDAO;

    public ProfessorManager(){
        professorDAO = new ProfessorDAO();
    }

    private boolean checkDuplication(String professorRRN,String exceptProfessor){
        return professorDAO.checkDupleProfessor(professorRRN,exceptProfessor);
    }


    private boolean checkValidation(Professor professor){

        boolean isValid = true;
        if("".equals(professor.getName())
                || "".equals(professor.getRRN())
                || "".equals(professor.getPhoneNum())
                || "".equals(professor.getEmail())
                || "".equals(professor.getOfficeAddress())
                || "".equals(professor.getOfficeNum())) {
            isValid = false;
        }
        if(professor.getName().contains("/")
                || professor.getRRN().contains("/")
                || professor.getPhoneNum().contains("/")
                || professor.getEmail().contains("/")
                || professor.getOfficeAddress().contains("/")
                || professor.getOfficeNum().contains("/")) {
            isValid = false;
        }

        return isValid;
    }

    public String getNewProfessorNum(boolean doIncrease) {
        return professorDAO.getNextProfessorNum(doIncrease);
    }

    public ResultCode saveProfessorInfo(Professor newProfessor) {
        ResultCode saveResult;
        if(!checkValidation(newProfessor)) return ResultCode.INVALID;
        if(checkDuplication(newProfessor.getRRN(),null)){
            return ResultCode.ALREADY_EXIST;
        }
        newProfessor.setProfessorNum(getNewProfessorNum(true));
        saveResult = (professorDAO.insertProfessorInfo(newProfessor)) ? ResultCode.SAVE_SUCCESS : ResultCode.UNKNOWN_FAIL;
        return saveResult;
    }
    public ResultCode modifyProfessorInfo(Professor targetProfessor) {
        ResultCode modifyResult;
        if(!checkValidation(targetProfessor)) return ResultCode.INVALID;
        if(checkDuplication(targetProfessor.getRRN(),targetProfessor.getProfessorNum())) return ResultCode.ALREADY_EXIST;
        modifyResult = professorDAO.updateProfessorInfo(targetProfessor) ? ResultCode.MODIFY_SUCCESS : ResultCode.UNKNOWN_FAIL;
        return modifyResult;
    }

    public ResultCode removeProfessorInfo(String targetProfessorNum) {

        ResultCode removeResult;
        removeResult = professorDAO.deleteProfessorInfo(targetProfessorNum) ? ResultCode.REMOVE_SUCCESS : ResultCode.UNKNOWN_FAIL;
        return removeResult;

    }

    public List<Professor> inquireProfessorInfoByProfessorNum(String targetProfessorNum) {
        return professorDAO.selectProfessorInfo(targetProfessorNum);
    }

    public List<Professor> inquireProfessorsInfoByName(String targetProfessorName){
        return professorDAO.selectProfessorsInfoByName(targetProfessorName);
    }

    public List<Professor> sortProfessorsInfoByProfessorNum() {
        return professorDAO.sortProfessorsInfoByProfessorNum();
    }
    public List<Professor> sortProfessorInfoByName() {
        return professorDAO.sortProfessorInfoByName();
    }

    public List<Professor> getAllProfessorsInfo() {
        return professorDAO.getAllProfessorInfo();
    }

    public String getProfessorNum(String targetProfessorName) {
        return professorDAO.getProfessorId(targetProfessorName);
    }
}
