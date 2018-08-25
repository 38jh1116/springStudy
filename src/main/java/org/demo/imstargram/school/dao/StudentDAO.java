package org.demo.imstargram.school.dao;

import org.demo.imstargram.school.FilePath;
import org.demo.imstargram.school.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentDAO {

    File studentInfo = new File(FilePath.STUDENT_FILE_PATH);
    File studentNumberInfo = new File(FilePath.STUDENT_NUM_FILE_PATH);

    public List<Student> getAllStudentsInfo(){
        List<Student> studentList = new ArrayList<>();

        try {
            if(!studentInfo.exists()) return studentList;
            FileReader fileReader = new FileReader(studentInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                Student student = convertStringToStudent(line);
                studentList.add(student);
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public void writeAllStudentInfo(List<Student> students){

        try {
            FileWriter fileWriter = new FileWriter(FilePath.STUDENT_FILE_PATH,false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Student student : students){
                bufferedWriter.write(student.toString());
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Student convertStringToStudent(String line) {
        String[] studentInfo = line.split("/");
        Student student = new Student();
        student.setStudentNum(studentInfo[0]);
        student.setName(studentInfo[1]);
        student.setRRN(studentInfo[2]);
        student.setPhoneNum(studentInfo[3]);
        student.setEmail(studentInfo[4]);
        return student;
    }
    public boolean findStudent(String studentNum){
        Student currentStudent;
        boolean isExist = false;

        if(!studentInfo.exists()) return isExist;
        try {
            FileReader fileReader = new FileReader(studentInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentStudent = convertStringToStudent(line);
                if (currentStudent.getStudentNum().equals(studentNum)) {
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

    public boolean checkDupleStudent(String studentRRN,String exceptStudent) {
        Student currentStudent;
        boolean isExist = false;

        if(!studentInfo.exists()) return isExist;
        try {
            FileReader fileReader = new FileReader(studentInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentStudent = convertStringToStudent(line);
                if (currentStudent.getRRN().equals(studentRRN)) {
                    if(exceptStudent != null && currentStudent.getStudentNum().equals(exceptStudent)){
                        continue;
                    }else {
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
    public boolean insertStudentInfo(Student newStudent){
        String newStudentInfo = newStudent.toString();
        try {
            FileWriter fileWriter = new FileWriter(FilePath.STUDENT_FILE_PATH,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newStudentInfo);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateStudentInfo(Student targetStudent) {
        boolean isUpdated = false;
        List<Student> studentList = getAllStudentsInfo();
        for(Student student : studentList){
            if(student.getStudentNum().equals(targetStudent.getStudentNum())){
                student.setRRN(targetStudent.getRRN());
                student.setName(targetStudent.getName());
                student.setPhoneNum(targetStudent.getPhoneNum());
                student.setEmail(targetStudent.getEmail());
                isUpdated = true;
                break;
            }
        }
        writeAllStudentInfo(studentList);
        return isUpdated;
    }

    public boolean deleteStudentInfo(String studentNum) {
        boolean isDeleted = false;
        List<Student> studentList = getAllStudentsInfo();
        for(Student student : studentList){
            if(student.getStudentNum().equals(studentNum)){
                studentList.remove(student);
                isDeleted = true;
                break;
            }
        }
        writeAllStudentInfo(studentList);
        return isDeleted;
    }

    public String getNextStudentNum(boolean doIncrease) {
        int nextStudentNum = 18000001;
        try {
            if (studentNumberInfo.exists()) {
                FileReader fileReader = new FileReader(studentNumberInfo);
                BufferedReader bufReader = new BufferedReader(fileReader);

                String line = "";
                if ((line = bufReader.readLine()) != null) {
                    nextStudentNum = Integer.parseInt(line);
                }
                bufReader.close();
                fileReader.close();
            }
            if (doIncrease) {
                FileWriter fileWriter = new FileWriter(FilePath.STUDENT_NUM_FILE_PATH, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(Integer.toString(nextStudentNum + 1));
                bufferedWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.toString(nextStudentNum);
    }
    public Student selectStudentInfo(String studentNum){
        Student selectedStudent = null;
        Student currentStudent;
        try {
            if(!studentInfo.exists()) return selectedStudent;

            FileReader fileReader = new FileReader(studentInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentStudent = convertStringToStudent(line);
                if (currentStudent.getStudentNum().equals(studentNum)) {
                    selectedStudent = currentStudent;
                    break;
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedStudent;
    }

    public List<Student> selectStudentsInfoByName(String targetStudentName) {
        Student currentStudent;
        List<Student> selectedStudentList = new ArrayList<>();

        try {
            if(!studentInfo.exists()) return selectedStudentList;
            FileReader fileReader = new FileReader(studentInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentStudent = convertStringToStudent(line);
                if (currentStudent.getName().equals(targetStudentName)) {
                    selectedStudentList.add(currentStudent);
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedStudentList;

    }
    private Comparator<Student> numComparator = new Comparator<Student>(){
        @Override
        public int compare(Student s1, Student s2) {
            return s1.getStudentNum().compareTo(s2.getStudentNum());
        }
    };
    private Comparator<Student> nameComparator = new Comparator<Student>(){
        @Override
        public int compare(Student s1, Student s2) {
            return s1.getName().compareTo(s2.getName());
        }
    };
    public List<Student> sortStudentsInfoByStudentNum() {
        List<Student> studentList = getAllStudentsInfo();
        Collections.sort(studentList,numComparator);
        return studentList;
    }

    public List<Student> sortStudentsInfoByName() {
        List<Student> studentList = getAllStudentsInfo();
        Collections.sort(studentList,nameComparator);
        return studentList;
    }


}
