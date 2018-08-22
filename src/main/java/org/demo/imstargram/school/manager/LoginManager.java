package org.demo.imstargram.school.manager;

import org.demo.imstargram.school.dao.AdminDAO;
import org.demo.imstargram.school.model.Admin;

public class LoginManager {

    AdminDAO adminDAO = new AdminDAO();

    public boolean loginCheck(Admin admin) {

        String input_id = admin.getId();
        String input_pw = admin.getPassword();
        String user_pw = adminDAO.login(input_id);
        if(user_pw.equals(input_pw)) return true;
        else return false;
    }
}
