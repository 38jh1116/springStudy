package org.demo.imstargram.school.controller;

import org.demo.imstargram.school.manager.LoginManager;
import org.demo.imstargram.school.manager.SampleManager;
import org.demo.imstargram.school.manager.StudentManager;
import org.demo.imstargram.school.model.Admin;
import org.demo.imstargram.school.model.ResultCode;
import org.demo.imstargram.school.model.Student;
import org.demo.imstargram.school.model.StudentSearchCondition;
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

    public SchoolController(){
        loginManager = new LoginManager();
        studentManager = new StudentManager();
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
    public String searchStudentInfo(Model model, StudentSearchCondition studentSearchCondition){

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
