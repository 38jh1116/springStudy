package org.demo.imstargram.school.dao;

import org.demo.imstargram.school.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolDAO {
    List<User> selectUsers();
}
