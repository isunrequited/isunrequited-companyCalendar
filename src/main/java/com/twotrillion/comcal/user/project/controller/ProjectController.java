package com.twotrillion.comcal.user.project.controller;

import com.twotrillion.comcal.user.project.service.ProjectService;
import com.twotrillion.comcal.user.schedule.vo.InfosForGetScheduleVo;
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
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/get_projects_title_by_emp_no", method = RequestMethod.POST)
    @ResponseBody
    public Object get_projects_title_by_emp_no(HttpSession session) {
        System.out.println("[ProjectController] get_projects_title_by_emp_no() CALLED!!");
        Map<String, Object> map = projectService.get_projects_title_by_emp_no(session);

        return map;
    }

}
