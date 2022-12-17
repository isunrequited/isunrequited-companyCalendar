package com.twotrillion.comcal.user.attendance.service;

import com.twotrillion.comcal.user.attendance.dao.AttendanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    @Autowired
    AttendanceDao attendanceDao;
}
