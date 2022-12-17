package com.twotrillion.comcal.user.employee.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
}
