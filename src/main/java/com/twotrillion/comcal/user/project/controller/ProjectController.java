package com.twotrillion.comcal.user.project.controller;

import com.twotrillion.comcal.user.project.service.ProjectService;
import com.twotrillion.comcal.user.project.vo.ProjectVo;
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

    @RequestMapping(value = "/create_project_confirm", method = RequestMethod.POST)
    @ResponseBody
    public Object create_project_confirm(@RequestBody ProjectVo projectVo, HttpSession session) {
        System.out.println("[ProjectController] create_project_confirm() CALLED!!");


        Map<String, Object> map = projectService.create_project_confirm(session, projectVo);
        System.out.println("projectVo" + projectVo);
        return map;

    }

    @RequestMapping(value = "/get_project_list", method = RequestMethod.POST)
    @ResponseBody
    public Object get_project_list(HttpSession session) {
        System.out.println("[ProjectController] get_project_list() CALLED!!");

        Map<String, Object> map = projectService.get_project_list(session);
        return map;

    }

    @RequestMapping(value = "/get_project_detail", method = RequestMethod.POST)
    @ResponseBody
    public Object get_project_detail(HttpSession session, @RequestBody Map<String, Integer> msgMap) {
        System.out.println("[ProjectController] get_project_detail_modal() CALLED!!");

        Map<String, Object> map = projectService.get_project_detail(session, msgMap.get("pjt_no"));
        return map;

    }

}
