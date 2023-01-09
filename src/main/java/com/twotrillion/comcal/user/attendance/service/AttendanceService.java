package com.twotrillion.comcal.user.attendance.service;

import com.twotrillion.comcal.user.attendance.dao.AttendanceDao;
import com.twotrillion.comcal.user.attendance.vo.AttendanceVo;
import com.twotrillion.comcal.user.common.paging.Criteria;
import com.twotrillion.comcal.user.common.paging.PageMakerVo;
import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {

    @Autowired
    AttendanceDao attendanceDao;

    public Map<String, String> go_work(AttendanceVo attendanceVo, HttpSession session) {
        System.out.println("[AttendanceService] go_work() CALLED!!");

        Map<String, String> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "session-fail");
        } else {
            int hour = Integer.parseInt(attendanceVo.getAtd_start_time().split(":")[0]);

            if (hour < 9) {
                attendanceVo.getAtd_type().setAtd_type_no(0);
            } else {
                attendanceVo.getAtd_type().setAtd_type_no(1);
            }
            attendanceVo.getEmp_info().setEmp_no(logged_in_employee_vo.getEmp_no());
            int result = attendanceDao.go_work(attendanceVo);
            attendanceDao.go_work_status_change(attendanceVo);

            if (result > 0) {
                map.put("result", "success");
            }
            else{
                map.put("result", "fail");
            }
        }

        return map;
    }

    public Map<String, String> go_home(AttendanceVo attendanceVo, HttpSession session) {
        System.out.println("[AttendanceService] go_home() CALLED!!");

        Map<String, String> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "session-fail");
        } else {
            int hour = Integer.parseInt(attendanceVo.getAtd_end_time().split(":")[0]);
            boolean late_for_work = attendanceDao.late_for_work_check(logged_in_employee_vo.getEmp_no());
            if (hour < 15) {
                attendanceVo.getAtd_type().setAtd_type_no(3);
            } else if (!late_for_work) {
                attendanceVo.getAtd_type().setAtd_type_no(0);
            } else {
                attendanceVo.getAtd_type().setAtd_type_no(1);
            }

            attendanceVo.getEmp_info().setEmp_no(logged_in_employee_vo.getEmp_no());

            int result = attendanceDao.go_home(attendanceVo);
            attendanceDao.go_home_status_change(attendanceVo);

            if (result > 0) {
                map.put("result", "success");
            }
            else{
                map.put("result", "fail");
            }
        }

        return map;
    }

    public Map<String, Object> get_attendance_list(Map<String, String> msgMap, HttpSession session) {
        System.out.println("[AttendanceService] AttendanceService() CALLED!!");
        Map<String, Object> map = new HashMap<>();
        Criteria criteria = new Criteria(Integer.parseInt(msgMap.get("pageNum")), Integer.parseInt(msgMap.get("amount")));

        List<AttendanceVo> attendanceVos = new ArrayList<>();
        int totalListCnt = -1;

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "session-fail");
        } else {
            attendanceVos = attendanceDao.get_attendance_list(criteria, logged_in_employee_vo.getEmp_no());
            totalListCnt = attendanceDao.get_total_attendance_list_cnt();

            PageMakerVo pageMakerVo = new PageMakerVo(criteria, totalListCnt);

            if(attendanceVos != null && totalListCnt >= 0) {
                map.put("attendanceVos", attendanceVos);
                map.put("pageMakerVo", pageMakerVo);
            }
            map.put("result", "success");
        }
        return map;
    }


    public Map<String, Object> attendance_check(HttpSession session) {
        System.out.println("[AttendanceService] go_home() CALLED!!");

        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "session-fail");
        } else {
            int att_status = attendanceDao.attendance_check(logged_in_employee_vo.getEmp_no());
            if (att_status == -1) {
                map.put("result", "fail");
            } else {
                map.put("result", "success");
                map.put("att_status", att_status);
            }
        }

        return map;
    }
}
