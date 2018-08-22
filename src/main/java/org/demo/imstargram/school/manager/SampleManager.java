package org.demo.imstargram.school.manager;

import org.demo.imstargram.school.dao.SchoolDAO;
import org.demo.imstargram.school.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleManager {
    @Autowired
    SchoolDAO schoolDAO;

    public void search() {
        List<User> users = schoolDAO.selectUsers();

        return;
    }
}
