package com.twotrillion.comcal.user.project.service;

import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import com.twotrillion.comcal.user.project.dao.ProjectDao;
import com.twotrillion.comcal.user.project.vo.ProjectVo;
import com.twotrillion.comcal.user.schedule.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    ProjectDao projectDao;

    public Map<String, Object> get_projects_title_by_emp_no(HttpSession session) {
        System.out.println("[ProjectService] get_projects_title_by_emp_no() CALLED!!");
        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            List<ProjectVo> projectVos = projectDao.get_projects_title_by_emp_no(logged_in_employee_vo.getEmp_no());
            if (projectVos != null) {
                map.put("result", "success");
                map.put("projectVos", projectVos);
            } else {
                map.put("result", "fail");
            }
        }

        return map;

    }

    public Map<String, Object> create_project_confirm(HttpSession session, ProjectVo projectVo) {
        System.out.println("[ProjectService] create_project_confirm() CALLED!!");
        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            int pjt_no = projectDao.create_project_confirm(projectVo);
            int result = projectDao.create_emp_project_confirm(pjt_no, projectVo);

            if (result > 0) {
                map.put("result", "success");
            } else {
                map.put("result", "fail");
            }
        }

        return map;
    }

    public Map<String, Object> get_project_list(HttpSession session) {
        System.out.println("[ProjectService] get_project_list() CALLED!!");
        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            List<ProjectVo> projectVos = projectDao.get_projects_by_emp_no(logged_in_employee_vo.getEmp_no());

            if (projectVos != null) {
                map.put("result", "success");
                map.put("projectVos", projectVos);
            } else {
                map.put("result", "fail");
            }
        }

        return map;
    }

}
