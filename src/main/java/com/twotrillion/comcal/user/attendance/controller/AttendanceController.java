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

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");

        attendanceVo.getEmp_info().setEmp_no(logged_in_employee_vo.getEmp_no());

        Map<String, String> map =  attendanceService.go_work(attendanceVo);

        return map;
    }

}
