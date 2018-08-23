package org.demo.imstargram.school.dao;
import org.demo.imstargram.school.FilePath;
import org.demo.imstargram.school.model.Professor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProfessorDAO {

    File professorInfo = new File(FilePath.PROFESSOR_FILE_PATH);
    File professorNumberInfo = new File(FilePath.PROFESSOR_NUM_FILE_PATH);

    public List<Professor> getAllProfessorInfo(){
        List<Professor> professorList = new ArrayList<>();

        try {
            if(!professorInfo.exists()) return professorList;
            FileReader fileReader = new FileReader(professorInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                Professor professor = convertStringToProfessor(line);
                professorList.add(professor);
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return professorList;
    }

    private void writeAllProfessorInfo(List<Professor> professors){

        try {
            FileWriter fileWriter = new FileWriter(FilePath.PROFESSOR_FILE_PATH,false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Professor professor : professors){
                bufferedWriter.write(professor.toString());
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Professor convertStringToProfessor(String line) {
        String[] professorInfo = line.split("/");
        Professor professor = new Professor();
        professor.setProfessorNum(professorInfo[0]);
        professor.setName(professorInfo[1]);
        professor.setRRN(professorInfo[2]);
        professor.setPhoneNum(professorInfo[3]);
        professor.setEmail(professorInfo[4]);
        professor.setOfficeAddress(professorInfo[5]);
        professor.setOfficeNum(professorInfo[6]);
        return professor;
    }

    public boolean checkDupleProfessor(String professorRRN,String exceptProfessor) {
        Professor currentProfessor;
        boolean isExist = false;

        if(!professorInfo.exists()) return isExist;
        try {
            FileReader fileReader = new FileReader(professorInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentProfessor = convertStringToProfessor(line);
                if (currentProfessor.getRRN().equals(professorRRN)) {
                    if(exceptProfessor != null && currentProfessor.getProfessorNum().equals(exceptProfessor)){
                        continue;
                    }else{
                        isExist = true;
                        break;

                    }
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return isExist;
    }
    public boolean insertProfessorInfo(Professor newProfessor){
        String newProfessorInfo = newProfessor.toString();
        try {
            FileWriter fileWriter = new FileWriter(FilePath.PROFESSOR_FILE_PATH,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newProfessorInfo);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateProfessorInfo(Professor targetProfessor) {
        boolean isUpdated = false;
        List<Professor> professorList = getAllProfessorInfo();
        for(Professor professor : professorList){
            if(professor.getProfessorNum().equals(targetProfessor.getProfessorNum())){
                professor.setRRN(targetProfessor.getRRN());
                professor.setName(targetProfessor.getName());
                professor.setPhoneNum(targetProfessor.getPhoneNum());
                professor.setEmail(targetProfessor.getEmail());
                professor.setOfficeAddress(targetProfessor.getOfficeAddress());
                professor.setOfficeNum(targetProfessor.getOfficeNum());
                isUpdated = true;
                break;
            }
        }
        writeAllProfessorInfo(professorList);
        return isUpdated;
    }

    public boolean deleteProfessorInfo(String professorNum) {
        boolean isDeleted = false;
        List<Professor> professorList = getAllProfessorInfo();
        for(Professor professor : professorList){
            if(professor.getProfessorNum().equals(professorNum)){
                professorList.remove(professor);
                isDeleted = true;
                break;
            }
        }
        writeAllProfessorInfo(professorList);
        return isDeleted;
    }

    public String getNextProfessorNum(boolean doIncrease) {
        int nextProfessorNum = 6000001;
        try {
            if(professorNumberInfo.exists()){
                FileReader fileReader = new FileReader(professorNumberInfo);
                BufferedReader bufReader = new BufferedReader(fileReader);

                String line = "";
                if((line = bufReader.readLine()) != null){
                    nextProfessorNum = Integer.parseInt(line);
                }
                bufReader.close();
                fileReader.close();
            }
            if(doIncrease){
                FileWriter fileWriter = new FileWriter(FilePath.PROFESSOR_NUM_FILE_PATH,false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(Integer.toString(nextProfessorNum + 1));
                bufferedWriter.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.toString(nextProfessorNum);
    }

    public List<Professor> selectProfessorInfo(String professorNum) {
        List<Professor> selectedProfessor = new ArrayList<>();
        Professor currentProfessor;
        try {
            if(!professorInfo.exists()) return selectedProfessor;
            FileReader fileReader = new FileReader(professorInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentProfessor = convertStringToProfessor(line);
                if (currentProfessor.getProfessorNum().equals(professorNum)) {
                    selectedProfessor.add(currentProfessor);
                    break;
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedProfessor;
    }

    public List<Professor> selectProfessorsInfoByName(String targetProfessorName) {
        Professor currentProfessor;

        List<Professor> selectedProfessorList = new ArrayList<>();

        try {
            if(!professorInfo.exists()) return selectedProfessorList;
            FileReader fileReader = new FileReader(professorInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentProfessor = convertStringToProfessor(line);
                if (currentProfessor.getName().equals(targetProfessorName)) {
                    selectedProfessorList.add(currentProfessor);
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedProfessorList;

    }
    private Comparator<Professor> numComparator = new Comparator<Professor>(){
        @Override
        public int compare(Professor p1, Professor p2) {
            return p1.getProfessorNum().compareTo(p2.getProfessorNum());
        }
    };
    private Comparator<Professor> nameComparator = new Comparator<Professor>(){
        @Override
        public int compare(Professor p1, Professor p2) {
            return p1.getName().compareTo(p2.getName());
        }
    };
    public List<Professor> sortProfessorsInfoByProfessorNum() {
        List<Professor> professorList = getAllProfessorInfo();
        Collections.sort(professorList,numComparator);
        return professorList;
    }

    public List<Professor> sortProfessorInfoByName() {
        List<Professor> professorList = getAllProfessorInfo();
        Collections.sort(professorList,nameComparator);
        return professorList;
    }


    public String getProfessorId(String targetProfessorName) {
        Professor currentProfessor;
        String professorId = "";

        if(!professorInfo.exists()) return professorId;
        try {
            FileReader fileReader = new FileReader(professorInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentProfessor = convertStringToProfessor(line);
                if (currentProfessor.getName().equals(targetProfessorName)) {
                    professorId = currentProfessor.getProfessorNum();
                    break;
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return professorId;
    }

    public boolean findProfessor(String professorNum) {
        Professor currentProfessor;
        boolean isExist = false;

        if(!professorInfo.exists()) return isExist;
        try {
            FileReader fileReader = new FileReader(professorInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentProfessor = convertStringToProfessor(line);
                if (currentProfessor.getProfessorNum().equals(professorNum)) {
                    isExist = true;
                    break;
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return isExist;
    }
}

