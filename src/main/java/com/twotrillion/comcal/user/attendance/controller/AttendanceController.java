package com.twotrillion.comcal.user.attendance.controller;

import com.twotrillion.comcal.user.attendance.service.AttendanceService;

import com.twotrillion.comcal.user.attendance.vo.AttendanceVo;
import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @RequestMapping(value = "/go_work",method = RequestMethod.POST)
    @ResponseBody
    public Object go_work(@RequestBody AttendanceVo attendanceVo, HttpSession session) {
        System.out.println("[AttendanceController] go_work() called");

        Map<String, String> map =  attendanceService.go_work(attendanceVo, session);

        return map;
    }

    @RequestMapping(value = "/go_home",method = RequestMethod.POST)
    @ResponseBody
    public Object go_home(@RequestBody AttendanceVo attendanceVo, HttpSession session) {
        System.out.println("[AttendanceController] go_home() called");

        Map<String, String> map =  attendanceService.go_home(attendanceVo, session);

        return map;
    }

    @RequestMapping(value = "get_attendance_list", method = RequestMethod.POST)
    @ResponseBody
    public Object get_attendance_list(@RequestBody Map<String, String> msgMap, HttpSession session) {
        System.out.println("[AttendanceController] attendanceList() called");
        Map<String, Object> map = attendanceService.get_attendance_list(msgMap, session);

        return map;
    }

    @RequestMapping(value = "attendance_check", method = RequestMethod.POST)
    @ResponseBody
    public Object attendance_check(HttpSession session) {
        System.out.println("[AttendanceController] attendance_check() called");
        Map<String, Object> map = attendanceService.attendance_check(session);

        return map;
    }
}
