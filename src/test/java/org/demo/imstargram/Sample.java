package org.demo.imstargram;

import com.google.gson.Gson;
import org.demo.imstargram.school.model.Student;
import org.junit.Test;

public class Sample {
    @Test
    public void main() {
        Gson gson = new Gson();

        Student s = new Student();
        s.setStudentNum("aaaa");
        s.setEmail("bbbb");
        s.setPhoneNum("cccc");
        System.out.println(gson.toJson(s));
    }

    @Test
    public void test2() {
        String a = "{\"studentNum\":\"aaaa\",\"phoneNum\":\"cccc\",\"email\":\"bbbb\"}";

        Gson gson = new Gson();
        Student s = gson.fromJson(a, Student.class);
        System.out.println(s);
    }
}
