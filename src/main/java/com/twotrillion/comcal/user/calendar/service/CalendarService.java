package com.twotrillion.comcal.user.calendar.service;

import com.twotrillion.comcal.user.calendar.dao.CalendarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

    @Autowired
    CalendarDao calendarDao;
}
