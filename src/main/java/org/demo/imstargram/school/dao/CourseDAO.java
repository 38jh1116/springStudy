package org.demo.imstargram.school.dao;

import org.demo.imstargram.school.FilePath;
import org.demo.imstargram.school.manager.ProfessorManager;
import org.demo.imstargram.school.model.Course;
import org.demo.imstargram.school.model.Professor;
import org.demo.imstargram.school.model.Subject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CourseDAO {

    File courseInfo = new File(FilePath.COURSE_FILE_PATH);
    File courseNumberInfo = new File(FilePath.COURSE_NUM_FILE_PATH);

    private ProfessorDAO professorDAO;
    private SubjectDAO subjectDAO;

    public CourseDAO(){
        professorDAO = new ProfessorDAO();
        subjectDAO = new SubjectDAO();

    }
//    File subjectInfo = new File(FilePath.SUBJECT_FILE_PATH);
//    File professorInfo = new File(FilePath.PROFESSOR_FILE_PATH);

    public List<Course> getAllCoursesInfo(){
        List<Course> courseList = new ArrayList<>();

        try {
            if(!courseInfo.exists()) return courseList;
            FileReader fileReader = new FileReader(courseInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                Course course = convertStringToCourse(line);
                course = addSubjectAndProfessorInfo(course);
                courseList.add(course);
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public void writeAllCourseInfo(List<Course> courses){

        try {
            FileWriter fileWriter = new FileWriter(FilePath.COURSE_FILE_PATH,false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Course course : courses){
                bufferedWriter.write(course.toString());
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Course convertStringToCourse(String line) {
        String[] courseInfo = line.split("/");
        Course course = new Course();
        course.setCourseNum(courseInfo[0]);
        course.setSubjectNum(courseInfo[1]);
        course.setClassNum(courseInfo[2]);
        course.setProfessorNum(courseInfo[3]);
        course.setSemester(courseInfo[4]);
        course.setTime(courseInfo[5]);
        return course;
    }

    public boolean checkDupleCourse(Course targetCourse,String exceptCourse){

        Course currentCourse;

        boolean isExist = false;

        if(!courseInfo.exists()) return isExist;
        try {
            FileReader fileReader = new FileReader(courseInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentCourse = convertStringToCourse(line);
                if (currentCourse.getSubject().getSubjectNum().equals(targetCourse.getSubject().getSubjectNum())
                        &&currentCourse.getClassNum().equals(targetCourse.getClassNum())) {
                    if(!currentCourse.getCourseNum().equals(exceptCourse)){
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
    public boolean insertCourseInfo(Course newCourse){
        String newCourseInfo = newCourse.toString();
        try {
            FileWriter fileWriter = new FileWriter(FilePath.COURSE_FILE_PATH,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newCourseInfo);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateCourseInfo(Course targetCourse) {
        boolean isUpdated = false;
        List<Course> courseList = getAllCoursesInfo();
        for(Course course : courseList){
            if(course.getCourseNum().equals(targetCourse.getCourseNum())){
                course.setSubjectNum(targetCourse.getSubject().getSubjectNum());
                course.setClassNum(targetCourse.getClassNum());
                course.setProfessorNum(targetCourse.getProfessor().getProfessorNum());
                course.setSemester(targetCourse.getSemester());
                course.setTime(targetCourse.getTime());
                isUpdated = true;
                break;
            }
        }
        writeAllCourseInfo(courseList);
        return isUpdated;
    }

    public boolean deleteCourseInfo(String courseNum) {
        boolean isDeleted = false;
        List<Course> courseList = getAllCoursesInfo();
        for(Course course : courseList){
            if(course.getCourseNum().equals(courseNum)){
                courseList.remove(course);
                isDeleted = true;
                break;
            }
        }
        writeAllCourseInfo(courseList);
        return isDeleted;
    }

    public String getNextCourseNum(boolean doIncrease) {
        int nextCourseNum = 77000001;
        try {
            if(courseNumberInfo.exists()){
                FileReader fileReader = new FileReader(courseNumberInfo);
                BufferedReader bufReader = new BufferedReader(fileReader);

                String line = "";
                if((line = bufReader.readLine()) != null){
                    nextCourseNum = Integer.parseInt(line);
                }
                bufReader.close();
                fileReader.close();
            }
            if(doIncrease){
                FileWriter fileWriter = new FileWriter(FilePath.COURSE_NUM_FILE_PATH,false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(Integer.toString(nextCourseNum + 1));
                bufferedWriter.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.toString(nextCourseNum);
    }

    public Course selectCourseInfo(String courseNum) {
        Course selectedCourse = null;
        Course currentCourse;

        try {
            if(!courseInfo.exists()) return selectedCourse;

            FileReader fileReader = new FileReader(courseInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentCourse = convertStringToCourse(line);
                if (currentCourse.getCourseNum().equals(courseNum)) {
                    selectedCourse = addSubjectAndProfessorInfo(currentCourse);
                    break;
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedCourse;
    }

    public List<Course> selectCoursesInfoBySubjectNum(String targetSubjectNum) {
        Course currentCourse;
        List<Course> selectedCourseList = new ArrayList<>();

        try {
            if(!courseInfo.exists()) return selectedCourseList;
            FileReader fileReader = new FileReader(courseInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentCourse = convertStringToCourse(line);
                if (currentCourse.getSubject().getSubjectNum().equals(targetSubjectNum)) {
                    currentCourse = addSubjectAndProfessorInfo(currentCourse);
                    selectedCourseList.add(currentCourse);
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedCourseList;

    }


    public List<Course> selectCoursesInfoBySubjectName(String targetSubjectName) {
        Course currentCourse;
        List<Course> selectedCourseList = new ArrayList<>();

        try {
            if(!courseInfo.exists()) return selectedCourseList;
            FileReader fileReader = new FileReader(courseInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentCourse = convertStringToCourse(line);
                if (currentCourse.getSubject().getName().equals(targetSubjectName)) {
                    currentCourse = addSubjectAndProfessorInfo(currentCourse);
                    selectedCourseList.add(currentCourse);
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedCourseList;

    }

    public List<Course> selectCoursesInfoByProfessorName(String targetProfessorName) {

        ProfessorManager professorManager  = new ProfessorManager();
        String targetProfessorNum = professorManager.getProfessorNum(targetProfessorName);
        return selectCoursesInfoByProfessorNum(targetProfessorNum);
    }

    private Comparator<Course> numComparator = new Comparator<Course>(){
        @Override
        public int compare(Course c1, Course c2) {
            return c1.getSubject().getSubjectNum().compareTo(c2.getSubject().getSubjectNum());
        }
    };

    private Comparator<Course> nameComparator = new Comparator<Course>(){
        @Override
        public int compare(Course c1, Course c2) {
            return c1.getSubject().getName().compareTo(c2.getSubject().getName());
        }
    };


    public List<Course> sortCoursesInfoBySubjectNum() {
        List<Course> courseList = getAllCoursesInfo();
        Collections.sort(courseList,numComparator);
        return courseList;
    }

    public List<Course> sortCoursesInfoBySubjectName() {
        List<Course> courseList = getAllCoursesInfo();
        Collections.sort(courseList,nameComparator);
        return courseList;
    }

    public boolean findCourse(String courseNum) {
        Course currentCourse;
        boolean isExist = false;

        if(!courseInfo.exists()) return isExist;
        try {
            FileReader fileReader = new FileReader(courseInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentCourse = convertStringToCourse(line);
                if (currentCourse.getCourseNum().equals(courseNum)) {
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
    public boolean findSubject(String subjectNum){
        return subjectDAO.findSubject(subjectNum);
    }
    public boolean findProfessor(String professorNum){
        return professorDAO.findProfessor(professorNum);
    }

    private Course addSubjectAndProfessorInfo(Course course){

        Subject subject = subjectDAO.selectSubjectInfo(course.getSubject().getSubjectNum());
        Professor professor = professorDAO.selectProfessorInfo(course.getProfessor().getProfessorNum());

        course.setSubject(subject);
        course.setProfessor(professor);
        return course;
    }

    public List<Course> selectCoursesInfoByProfessorNum(String targetProfessorNum) {

        List<Course> selectedCourseList = new ArrayList<>();
        Course currentCourse;

        if("".equals(targetProfessorNum)) return selectedCourseList;
        if(!courseInfo.exists()) return selectedCourseList;

        try {
            FileReader fileReader = new FileReader(courseInfo);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufReader.readLine()) != null) {
                currentCourse = convertStringToCourse(line);
                if (currentCourse.getProfessor().getProfessorNum().equals(targetProfessorNum)) {
                    currentCourse = addSubjectAndProfessorInfo(currentCourse);
                    selectedCourseList.add(currentCourse);
                }
            }
            bufReader.close();
            fileReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return selectedCourseList;
    }
}

