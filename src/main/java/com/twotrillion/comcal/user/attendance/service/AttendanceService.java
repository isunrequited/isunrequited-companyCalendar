package com.twotrillion.comcal.user.attendance.service;

import com.twotrillion.comcal.user.attendance.dao.AttendanceDao;
import com.twotrillion.comcal.user.attendance.vo.AttendanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AttendanceService {

    @Autowired
    AttendanceDao attendanceDao;

    public Map<String, String> go_work(AttendanceVo attendanceVo) {
        System.out.println("[AttendanceService] go_work() CALLED!!");

        Map<String, String> map = new HashMap<>();

        int hour = Integer.parseInt(attendanceVo.getAtd_start_time().split(":")[0]);

        if (hour < 9) {
            attendanceVo.getAtd_type().setAtd_type_no(0);
        } else {
            attendanceVo.getAtd_type().setAtd_type_no(1);
        }

        int result = attendanceDao.go_work(attendanceVo);

        if (result > 0) {
            System.out.println("ATTENDANCE SUCCESS");
            map.put("result", "success");
        }
        else{
            System.out.println("ATTENDANCE FAIL!!");
            map.put("result", "fail");
        }

        return map;
    }
}
