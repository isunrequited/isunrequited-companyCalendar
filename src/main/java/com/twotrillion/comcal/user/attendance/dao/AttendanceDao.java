package com.twotrillion.comcal.user.attendance.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
}
