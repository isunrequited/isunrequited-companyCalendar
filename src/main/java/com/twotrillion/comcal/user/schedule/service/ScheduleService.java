package com.twotrillion.comcal.user.schedule.service;

import com.twotrillion.comcal.user.employee.dao.EmployeeDao;
import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import com.twotrillion.comcal.user.project.dao.ProjectDao;
import com.twotrillion.comcal.user.project.vo.ProjectVo;
import com.twotrillion.comcal.user.schedule.dao.ScheduleDao;
import com.twotrillion.comcal.user.schedule.vo.InfosForGetScheduleVo;
import com.twotrillion.comcal.user.schedule.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleService {

    @Autowired
    ScheduleDao scheduleDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    EmployeeDao employeeDao;

    public Map<String, String> create_schedule(ScheduleVo scheduleVo, HttpSession session) {
        System.out.println("[ScheduleService] create_schedule() CALLED!!");
        Map<String, String> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            scheduleVo.getScd_writer_emp_info().setEmp_no(logged_in_employee_vo.getEmp_no());

            int scd_type_no = scheduleVo.getScd_type().getScd_type_no();

            if (scd_type_no <= 3) {
                scheduleVo.getScd_auth_range_type().setScd_auth_range_type_no(scd_type_no);
            } else if (scd_type_no == 4) {
                scheduleVo.getScd_auth_range_type().setScd_auth_range_type_no(3);
            } else if (scd_type_no == 5) {
                scheduleVo.getScd_auth_range_type().setScd_auth_range_type_no(0);
            } else {
                scheduleVo.getScd_auth_range_type().setScd_auth_range_type_no(0);
            }

            int result = scheduleDao.create_schedule(scheduleVo);

            if (result <= 0) {
                map.put("result", "fail");
            } else {
                map.put("result", "success");
            }
        }

        return map;
    }


    public Map<String, Object> get_schedule_by_no(int scd_no, HttpSession session) {
        System.out.println("[ScheduleService] get_schedule_by_no() CALLED!!");
        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            ScheduleVo scheduleVo = scheduleDao.get_schedule_by_no(scd_no);
            if (scheduleVo != null) {
                String scd_target_name = "";
                switch (scheduleVo.getScd_auth_range_type().getScd_auth_range_type_no()) {
                    case 0:
                        scd_target_name = "개인";
                        break;
                    case 1:
                        scd_target_name = projectDao.get_pjt_name_by_no(scheduleVo.getScd_auth_target_no());
                        break;
                    case 2:
                        scd_target_name = employeeDao.get_dep_name_by_no(scheduleVo.getScd_auth_target_no());
                        break;
                    case 3:
                        scd_target_name = "회사";
                        break;
                    default:
                        scd_target_name = "기타";
                }
                map.put("result", "success");
                map.put("scheduleVo", scheduleVo);
                map.put("scd_target_name", scd_target_name);
            } else {
                map.put("result", "fail");
            }
        }

        return map;
    }

    public Map<String, Object> schedule_auth_confirm(Integer scd_no, HttpSession session) {
        System.out.println("[ScheduleService] schedule_auth_confirm() CALLED!!");
        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            int schedule_writer_emp_no = scheduleDao.schedule_auth_confirm(scd_no);
            if (schedule_writer_emp_no == logged_in_employee_vo.getEmp_no()) {
                map.put("result", "success");
            } else {
                map.put("result", "fail");
            }
        }

        return map;
    }

    public Map<String, String> modify_schedule(ScheduleVo scheduleVo, HttpSession session) {
        System.out.println("[ScheduleService] modify_schedule() CALLED!!");
        Map<String, String> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            int result = scheduleDao.modify_schedule(scheduleVo);
            if (result > 0 ) {
                map.put("result", "success");
            } else {
                map.put("result", "fail");
            }
        }

        return map;
    }

    public Map<String, String> delete_schedule(Integer scd_no, HttpSession session) {
        System.out.println("[ScheduleService] delete_schedule() CALLED!!");
        Map<String, String> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            int result = scheduleDao.delete_schedule(scd_no);
            if (result > 0 ) {
                map.put("result", "success");
            } else {
                map.put("result", "fail");
            }
        }

        return map;
    }

    public Map<String, Object> get_schedule_list_by_date(InfosForGetScheduleVo infosForGetScheduleVo, HttpSession session) {
        System.out.println("[ScheduleService] get_schedule_list_by_date() CALLED!!");
        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            if (infosForGetScheduleVo.isPrivate_schedule() || infosForGetScheduleVo.isProject_schedule() || infosForGetScheduleVo.isDepartment_schedule() || infosForGetScheduleVo.isCompany_schedule()) {
                infosForGetScheduleVo.setEmp_no(logged_in_employee_vo.getEmp_no());
                infosForGetScheduleVo.setDep_no(logged_in_employee_vo.getEmp_dep().getDep_type_no());

                List<ProjectVo> projectVos = projectDao.get_projects_by_emp_no(logged_in_employee_vo.getEmp_no());
                int[] pjt_nos = new int[projectVos.size()];
                for (int i = 0; i < projectVos.size(); i++) {
                    pjt_nos[i] = projectVos.get(i).getPjt_no();
                }
                infosForGetScheduleVo.setPjt_nos(pjt_nos);
                System.out.println("infosForGetScheduleVo = " + infosForGetScheduleVo);

                List<ScheduleVo> scheduleVos = scheduleDao.get_schedule_list_by_date(infosForGetScheduleVo);
                System.out.println("scheduleVos = " + scheduleVos);

                if (scheduleVos != null ) {
                    map.put("result", "success");
                    map.put("scheduleVos", scheduleVos);
                } else {
                    map.put("result", "fail");
                }
            } else {
                map.put("result", "success");
            }
        }

        return map;
    }

    public Map<String, Object> get_schedule_list(InfosForGetScheduleVo infosForGetScheduleVo, HttpSession session) {
        System.out.println("[ScheduleService] get_schedule_list() CALLED!!");
        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            infosForGetScheduleVo.setEmp_no(logged_in_employee_vo.getEmp_no());
            infosForGetScheduleVo.setDep_no(logged_in_employee_vo.getEmp_dep().getDep_type_no());

            List<ProjectVo> projectVos = projectDao.get_projects_by_emp_no(logged_in_employee_vo.getEmp_no());
            int[] pjt_nos = new int[projectVos.size()];
            for (int i = 0; i < projectVos.size(); i++) {
                pjt_nos[i] = projectVos.get(i).getPjt_no();
            }
            infosForGetScheduleVo.setPjt_nos(pjt_nos);


            List<ScheduleVo> scheduleVos = scheduleDao.get_schedule_list(infosForGetScheduleVo);

            if (scheduleVos == null) {
                map.put("result", "fail");
            } else {
                map.put("result", "success");
                map.put("scheduleVos", scheduleVos);
            }
        }

        return map;

    }
}
