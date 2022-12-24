package com.twotrillion.comcal.user.employee.controller;

import com.twotrillion.comcal.user.employee.service.EmployeeService;
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
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/login_confirm" , method = RequestMethod.POST)
    @ResponseBody
    public Object login_confirm(@RequestBody Map<String, String> msg , HttpSession session) {
        System.out.println("[EmployeeController] login_confirm() called");

        EmployeeVo employeeVo = new EmployeeVo();
        employeeVo.setEmp_email(msg.get("emp_email"));
        employeeVo.setEmp_pw(msg.get("emp_pw"));

        Map<String, String> map = employeeService.login_confirm(employeeVo, session);

        return map;
    }

    @RequestMapping(value = "/create_temp_account" , method = RequestMethod.POST)
    @ResponseBody
    public Object create_temp_account() {
        System.out.println("[EmployeeController] create_temp_account() called");

        Map<String, String> map = new HashMap<String, String>();
        employeeService.create_temp_account();
        map.put("result","success");

        return map;
    }

}