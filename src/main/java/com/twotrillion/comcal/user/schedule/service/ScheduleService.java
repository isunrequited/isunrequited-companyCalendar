package com.twotrillion.comcal.user.schedule.service;

import com.twotrillion.comcal.user.schedule.dao.ScheduleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    ScheduleDao scheduleDao;
}
