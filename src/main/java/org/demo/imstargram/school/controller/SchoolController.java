package org.demo.imstargram.school.controller;

import org.demo.imstargram.school.manager.*;
import org.demo.imstargram.school.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SchoolController {
    private LoginManager loginManager;
    private StudentManager studentManager;
    private ProfessorManager professorManager;
    private SubjectManager subjectManager;
    private CourseManager courseManager;

    public SchoolController(){
        loginManager = new LoginManager();
        studentManager = new StudentManager();
        professorManager = new ProfessorManager();
        subjectManager = new SubjectManager();
        courseManager = new CourseManager();

    }

    @Autowired
    private SampleManager sampleManager;

    @GetMapping("/school/login")
    public String goLoginPage() {
        return "school/admin_login";
    }

    @PostMapping("/school/login")
    @ResponseBody
    public String login(Model model, Admin loginAdmin) {

        Admin admin = new Admin();
        admin.setId(loginAdmin.getId());
        admin.setPassword(loginAdmin.getPassword());

        if (loginManager.loginCheck(admin)) {
            model.addAttribute("login_admin", loginAdmin);
            return "login_success";
        } else {
            return "login_fail";
        }
    }

    @GetMapping("/school/app_main")
    public String main(Model model, Admin admin) {
        model.addAttribute("login_admin", admin);
        return "school/app_main";
    }

    @GetMapping("/school/student")
    public String goStudentMenu() {
        return "school/student";
    }
    @GetMapping("/school/student/search")
    public String searchStudentInfo(Model model, SearchCondition studentSearchCondition){

        String sortOption = studentSearchCondition.getSortOption();
        String searchOption = studentSearchCondition.getSearchOption();
        String searchTarget = studentSearchCondition.getSearchTarget();

        List<Student> targetStudentList = new ArrayList<>();
        List<Student> result = new ArrayList<>();

        switch(sortOption){
            case "student_num":
                targetStudentList = studentManager.sortStudentsInfoByStudentNum();
                break;
            case "name":
                targetStudentList = studentManager.sortStudentsInfoByName();
                break;
        }

        switch(searchOption){
            case "all":
                result = targetStudentList;
                break;
            case "student_num":
                for(Student student : targetStudentList){
                    if(student.getStudentNum().equals(searchTarget)){
                        result.add(student);
                    }
                }
                break;
            case "name":
                for(Student student : targetStudentList){
                    if(student.getName().equals(searchTarget)){
                        result.add(student);
                    }
                }
                break;
        }
        String nextStudentNum = studentManager.getNewStudentNum(false);
        model.addAttribute("student_list",result);
        model.addAttribute("next_student_num",nextStudentNum);

        return "school/student_info";
    }


    @GetMapping("/school/student/student_list")
    public String showAllStudentInfo(Model model) {
        List<Student> studentList = studentManager.getAllStudentsInfo();
        String nextStudentNum = studentManager.getNewStudentNum(false);
        model.addAttribute("student_list",studentList);
        model.addAttribute("next_student_num",nextStudentNum);
        return "school/student_info";
    }


    @PostMapping("/school/student/save_student")
    @ResponseBody
    public Map<String, String> saveStudentInfo(Student newStudent){

        ResultCode saveResult = studentManager.saveStudentInfo(newStudent);

        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("result", saveResult.getResult());
        resultMessage.put("msg", saveResult.getMsg());
        return resultMessage;
    }
    @PostMapping("/school/student/modify_student")
    @ResponseBody
    public Map<String,String> modifyStudentInfo(Student targetStudent){

        ResultCode modifyResult = studentManager.modifyStudentInfo(targetStudent);

        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("result", modifyResult.getResult());
        resultMessage.put("msg", modifyResult.getMsg());
        return resultMessage;

    }

    @PostMapping("/school/student/remove_student")
    @ResponseBody
    public Map<String,String> removeStudentInfo(Student targetStudent){

        ResultCode removeResult = studentManager.removeStudentInfo(targetStudent.getStudentNum());

        Map<String,String> resultMessage = new HashMap<>();
        resultMessage.put("result", removeResult.getResult());
        resultMessage.put("msg", removeResult.getMsg());
        return resultMessage;
    }

    @GetMapping("/school/professor")
    public String goProfessorMenu() {
        return "school/professor";
    }

    @GetMapping("/school/professor/search")
    public String searchProfessorInfo(Model model, SearchCondition professorSearchCondition){

        String sortOption = professorSearchCondition.getSortOption();
        String searchOption = professorSearchCondition.getSearchOption();
        String searchTarget = professorSearchCondition.getSearchTarget();

        List<Professor> targetProfessorList = new ArrayList<>();
        List<Professor> result = new ArrayList<>();

        switch(sortOption){
            case "professor_num":
                targetProfessorList = professorManager.sortProfessorsInfoByProfessorNum();
                break;
            case "name":
                targetProfessorList = professorManager.sortProfessorInfoByName();
                break;
        }

        switch(searchOption){
            case "all":
                result = targetProfessorList;
                break;
            case "professor_num":
                for(Professor professor : targetProfessorList){
                    if(professor.getProfessorNum().equals(searchTarget)){
                        result.add(professor);
                    }
                }
                break;
            case "name":
                for(Professor professor : targetProfessorList){
                    if(professor.getName().equals(searchTarget)){
                        result.add(professor);
                    }
                }
                break;
        }
        String nextProfessorNum = professorManager.getNewProfessorNum(false);
        model.addAttribute("professor_list",result);
        model.addAttribute("next_professor_num",nextProfessorNum);

        return "school/professor_info";
    }


    @GetMapping("/school/professor/professor_list")
    public String showAllProfessorInfo(Model model) {
        List<Professor> professorList = professorManager.getAllProfessorsInfo();
        String nextProfessorNum = professorManager.getNewProfessorNum(false);
        model.addAttribute("professor_list",professorList);
        model.addAttribute("next_professor_num",nextProfessorNum);
        return "school/professor_info";
    }


    @PostMapping("/school/professor/save_professor")
    @ResponseBody
    public Map<String, String> saveProfessorInfo(Professor newProfessor){

        ResultCode saveResult = professorManager.saveProfessorInfo(newProfessor);

        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("result", saveResult.getResult());
        resultMessage.put("msg", saveResult.getMsg());
        return resultMessage;
    }
    @PostMapping("/school/professor/modify_professor")
    @ResponseBody
    public Map<String,String> modifyProfessorInfo(Professor targetProfessor){

        ResultCode modifyResult = professorManager.modifyProfessorInfo(targetProfessor);

        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("result", modifyResult.getResult());
        resultMessage.put("msg", modifyResult.getMsg());
        return resultMessage;

    }

    @PostMapping("/school/professor/remove_professor")
    @ResponseBody
    public Map<String,String> removeProfessorInfo(Professor targetProfessor){

        ResultCode removeResult = professorManager.removeProfessorInfo(targetProfessor.getProfessorNum());

        Map<String,String> resultMessage = new HashMap<>();
        resultMessage.put("result", removeResult.getResult());
        resultMessage.put("msg", removeResult.getMsg());
        return resultMessage;
    }


    @GetMapping("/school/subject")
    public String goSubjectMenu() {
        return "school/subject";
    }

    @GetMapping("/school/subject/search")
    public String searchSubjectInfo(Model model, SearchCondition subjectSearchCondition){

        String sortOption = subjectSearchCondition.getSortOption();
        String searchOption = subjectSearchCondition.getSearchOption();
        String searchTarget = subjectSearchCondition.getSearchTarget();

        List<Subject> targetSubjectList = new ArrayList<>();
        List<Subject> result = new ArrayList<>();

        switch(sortOption){
            case "subject_num":
                targetSubjectList = subjectManager.sortSubjectsInfoBySubjectNum();
                break;
            case "name":
                targetSubjectList = subjectManager.sortSubjectsInfoByName();
                break;
        }

        switch(searchOption){
            case "all":
                result = targetSubjectList;
                break;
            case "subject_num":
                for(Subject subject : targetSubjectList){
                    if(subject.getSubjectNum().equals(searchTarget)){
                        result.add(subject);
                    }
                }
                break;
            case "name":
                for(Subject subject : targetSubjectList){
                    if(subject.getName().equals(searchTarget)){
                        result.add(subject);
                    }
                }
                break;
        }
        String nextSubjectNum = subjectManager.getNewSubjectNum(false);
        model.addAttribute("subject_list",result);
        model.addAttribute("next_subject_num",nextSubjectNum);

        return "school/subject_info";
    }


    @GetMapping("/school/subject/subject_list")
    public String showAllSubjectInfo(Model model) {
        List<Subject> subjectList = subjectManager.getAllSubjectsInfo();
        String nextSubjectNum = subjectManager.getNewSubjectNum(false);
        model.addAttribute("subject_list",subjectList);
        model.addAttribute("next_subject_num",nextSubjectNum);
        return "school/subject_info";
    }


    @PostMapping("/school/subject/save_subject")
    @ResponseBody
    public Map<String, String> saveSubjectInfo(Subject newSubject){

        ResultCode saveResult = subjectManager.saveSubjectInfo(newSubject);

        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("result", saveResult.getResult());
        resultMessage.put("msg", saveResult.getMsg());
        return resultMessage;
    }
    @PostMapping("/school/subject/modify_subject")
    @ResponseBody
    public Map<String,String> modifySubjectInfo(Subject targetSubject){

        ResultCode modifyResult = subjectManager.modifySubjectInfo(targetSubject);

        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("result", modifyResult.getResult());
        resultMessage.put("msg", modifyResult.getMsg());
        return resultMessage;

    }

    @PostMapping("/school/subject/remove_subject")
    @ResponseBody
    public Map<String,String> removeSubjectInfo(Subject targetSubject){

        ResultCode removeResult = subjectManager.removeSubjectInfo(targetSubject.getSubjectNum());

        Map<String,String> resultMessage = new HashMap<>();
        resultMessage.put("result", removeResult.getResult());
        resultMessage.put("msg", removeResult.getMsg());
        return resultMessage;
    }

    @GetMapping("/school/course")
    public String goCourseMenu() {
        return "school/course";
    }

    @GetMapping("/school/course/search")
    public String searchCourseInfo(Model model, SearchCondition courseSearchCondition){

        String sortOption = courseSearchCondition.getSortOption();
        String searchOption = courseSearchCondition.getSearchOption();
        String searchTarget = courseSearchCondition.getSearchTarget();

        List<Course> targetCourseList = new ArrayList<>();
        List<Course> result = new ArrayList<>();

        switch(sortOption){
            case "subject_num":
                targetCourseList = courseManager.sortCoursesInfoBySubjectNum();
                break;
            case "subject_name":
                targetCourseList = courseManager.sortCoursesInfoBySubjectName();
                break;
        }

        switch(searchOption){
            case "all":
                result = targetCourseList;
                break;
            case "subject_num":
                for(Course course : targetCourseList){
                    if(course.getCourseNum().equals(searchTarget)){
                        result.add(course);
                    }
                }
                break;
            case "subject_name":
                for(Course course : targetCourseList){
                    if(course.getSubject().getName().equals(searchTarget)){
                        result.add(course);
                    }
                }
                break;
            case "professor_name":
                for(Course course : targetCourseList){
                    if(course.getProfessor().getName().equals(searchTarget)){
                        result.add(course);
                    }
                }
                break;
        }
        String nextCourseNum = courseManager.getNewCourseNum(false);
        List<Professor> professorList = professorManager.getAllProfessorsInfo();
        List<Subject> subjectList = subjectManager.getAllSubjectsInfo();
        model.addAttribute("course_list",result);
        model.addAttribute("next_course_num",nextCourseNum);
        model.addAttribute("subject_list",subjectList);
        model.addAttribute("professor_list",professorList);
        return "school/course_info";
    }


    @GetMapping("/school/course/course_list")
    public String showAllCourseInfo(Model model) {
        List<Course> courseList = courseManager.getAllCoursesInfo();
        String nextCourseNum = courseManager.getNewCourseNum(false);
        model.addAttribute("course_list",courseList);
        model.addAttribute("next_course_num",nextCourseNum);
        return "school/course_info";
    }


    @PostMapping("/school/course/save_course")
    @ResponseBody
    public Map<String, String> saveCourseInfo(Course newCourse){

        ResultCode saveResult = courseManager.saveCourseInfo(newCourse);

        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("result", saveResult.getResult());
        resultMessage.put("msg", saveResult.getMsg());
        return resultMessage;
    }
    @PostMapping("/school/course/modify_course")
    @ResponseBody
    public Map<String,String> modifyCourseInfo(Course targetCourse){

        ResultCode modifyResult = courseManager.modifyCourseInfo(targetCourse);

        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("result", modifyResult.getResult());
        resultMessage.put("msg", modifyResult.getMsg());
        return resultMessage;

    }

    @PostMapping("/school/course/remove_course")
    @ResponseBody
    public Map<String,String> removeCourseInfo(Course targetCourse){

        ResultCode removeResult = courseManager.removeCourseInfo(targetCourse.getCourseNum());

        Map<String,String> resultMessage = new HashMap<>();
        resultMessage.put("result", removeResult.getResult());
        resultMessage.put("msg", removeResult.getMsg());
        return resultMessage;
    }

//    @RequestMapping(value="/school/search_student",produces="application/json;charset=utf-8")
//    public String searchStudentInfoByStudentNum(Model model, @RequestBody Map<String,String> searchInfo){
//        String searchBy = searchInfo.get("searchBy");
//        String searchTarget = searchInfo.get("searchTarget");
//
//        if(searchBy.equals("name")){
//            Student targetStudent = studentManager.selectStudentInfo(searchTarget);
//            model.addAttribute("student_fon")
//        }else{
//            List<Student> targetStudentList = studentManager.selectStudentsInfoByName(searchTarget);
//        }
//
//        return result;
//    }


    @GetMapping("/daoTest")
    public String test() {
        sampleManager.search();
        return "success";
    }

}
