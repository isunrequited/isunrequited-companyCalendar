package com.twotrillion.comcal.user.calendar.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CalendarDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
}
