package com.twotrillion.comcal.user.schedule.dao;

import com.twotrillion.comcal.user.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDao {

    @Autowired
    ScheduleService scheduleService;
}
