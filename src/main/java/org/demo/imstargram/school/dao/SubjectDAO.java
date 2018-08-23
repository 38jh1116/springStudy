package org.demo.imstargram.school.dao;

import org.demo.imstargram.school.FilePath;
import org.demo.imstargram.school.model.Subject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SubjectDAO {


    File subjectInfo = new File(FilePath.SUBJECT_FILE_PATH);
    File subjectNumberInfo = new File(FilePath.SUBJECT_NUM_FILE_PATH);

    public List<Subject> getAllSubjectsInfo(){
        List<Subject> subjectList = new ArrayList<>();

        try {
            if(!subjectInfo.exists()) return subjectList;
            FileReader fileReader = new FileReader(subjectInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                Subject subject = convertStringToSubject(line);
                subjectList.add(subject);
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return subjectList;
    }

    public void writeAllSubjectInfo(List<Subject> subjects){

        try {
            FileWriter fileWriter = new FileWriter(FilePath.SUBJECT_FILE_PATH,false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Subject subject : subjects){
                bufferedWriter.write(subject.toString());
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Subject convertStringToSubject(String line) {
        String[] subjectInfo = line.split("/");
        Subject subject = new Subject();
        subject.setSubjectNum(subjectInfo[0]);
        subject.setName(subjectInfo[1]);
        subject.setCredit(subjectInfo[2]);
        return subject;
    }
    public boolean findSubject(String subjectNum){
        Subject currentSubject;
        boolean isExist = false;

        if(!subjectInfo.exists()) return isExist;
        try {
            FileReader fileReader = new FileReader(subjectInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentSubject = convertStringToSubject(line);
                if (currentSubject.getSubjectNum().equals(subjectNum)) {
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

    public boolean checkDupleSubject(String subjectName,String exceptSubject) {
        Subject currentSubject;
        boolean isExist = false;

        if(!subjectInfo.exists()) return isExist;
        try {
            FileReader fileReader = new FileReader(subjectInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentSubject = convertStringToSubject(line);
                if (currentSubject.getName().equals(subjectName)) {
                    if(exceptSubject != null && currentSubject.getSubjectNum().equals(exceptSubject)){
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
    public boolean insertSubjectInfo(Subject newSubject){
        String newSubjectInfo = newSubject.toString();
        try {
            FileWriter fileWriter = new FileWriter(FilePath.SUBJECT_FILE_PATH,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newSubjectInfo);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateSubjectInfo(Subject targetSubject) {
        boolean isUpdated = false;
        List<Subject> subjectList = getAllSubjectsInfo();
        for(Subject subject : subjectList){
            if(subject.getSubjectNum().equals(targetSubject.getSubjectNum())){
                subject.setName(targetSubject.getName());
                subject.setCredit(targetSubject.getCredit());
                isUpdated = true;
                break;
            }
        }
        writeAllSubjectInfo(subjectList);
        return isUpdated;
    }

    public boolean deleteSubjectInfo(String subjectNum) {
        boolean isDeleted = false;
        List<Subject> subjectList = getAllSubjectsInfo();
        for(Subject subject : subjectList){
            if(subject.getSubjectNum().equals(subjectNum)){
                subjectList.remove(subject);
                isDeleted = true;
                break;
            }
        }
        writeAllSubjectInfo(subjectList);
        return isDeleted;
    }

    public String getNextSubjectNum(boolean doIncrease) {
        int nextSubjectNum = 500001;
        try {
            if(subjectNumberInfo.exists()){
                FileReader fileReader = new FileReader(subjectNumberInfo);
                BufferedReader bufReader = new BufferedReader(fileReader);

                String line = "";
                if((line = bufReader.readLine()) != null){
                    nextSubjectNum = Integer.parseInt(line);
                }
                bufReader.close();
                fileReader.close();
            }
            if(doIncrease){
                FileWriter fileWriter = new FileWriter(FilePath.SUBJECT_NUM_FILE_PATH,false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(Integer.toString(nextSubjectNum + 1));
                bufferedWriter.close();
            }


        }catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.toString(nextSubjectNum);
    }

    public List<Subject> selectSubjectInfo(String subjectNum) {
        List<Subject> selectedSubject = new ArrayList<>();
        Subject currentSubject;
        try {
            if(!subjectInfo.exists()) return selectedSubject;

            FileReader fileReader = new FileReader(subjectInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentSubject = convertStringToSubject(line);
                if (currentSubject.getSubjectNum().equals(subjectNum)) {
                    selectedSubject.add(currentSubject);
                    break;
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedSubject;
    }

    public List<Subject> selectSubjectsInfoByName(String targetSubjectName) {
        Subject currentSubject;
        List<Subject> selectedSubjectList = new ArrayList<>();

        try {
            if(!subjectInfo.exists()) return selectedSubjectList;
            FileReader fileReader = new FileReader(subjectInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentSubject = convertStringToSubject(line);
                if (currentSubject.getName().equals(targetSubjectName)) {
                    selectedSubjectList.add(currentSubject);
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedSubjectList;

    }
    private Comparator<Subject> numComparator = new Comparator<Subject>(){
        @Override
        public int compare(Subject s1, Subject s2) {
            return s1.getSubjectNum().compareTo(s2.getSubjectNum());
        }
    };
    private Comparator<Subject> nameComparator = new Comparator<Subject>(){
        @Override
        public int compare(Subject s1, Subject s2) {
            return s1.getName().compareTo(s2.getName());
        }
    };
    public List<Subject> sortSubjectsInfoBySubjectNum() {
        List<Subject> subjectList = getAllSubjectsInfo();
        Collections.sort(subjectList,numComparator);
        return subjectList;
    }

    public List<Subject> sortSubjectsInfoByName() {
        List<Subject> subjectList = getAllSubjectsInfo();
        Collections.sort(subjectList,nameComparator);
        return subjectList;
    }

}