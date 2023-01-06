package com.twotrillion.comcal.user.employee.service;

import com.twotrillion.comcal.user.employee.dao.EmployeeDao;
import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import com.twotrillion.comcal.user.schedule.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    public Map<String, String> login_confirm(EmployeeVo employeeVo, HttpSession session) {
        System.out.println("[EmployeeService] login_confirm() called");

        EmployeeVo logged_in_employee_vo = employeeDao.login_confirm(employeeVo);

        Map<String, String> map = new HashMap<>();

        if (logged_in_employee_vo == null) {
            System.out.println("LOG-IN FAIL!!");
            map.put("result", "fail");
        } else {
            System.out.println("LOG-IN SUCCESS!!");
            System.out.println("logged_in_employee_vo = " + logged_in_employee_vo);
            session.setAttribute("logged_in_employee_vo", logged_in_employee_vo);
            session.setMaxInactiveInterval(60 * 30);
            map.put("result","success");
        }

        return map;
    }

    public void create_temp_account() {
        List<EmployeeVo> employeeVos = new ArrayList<EmployeeVo>();

        EmployeeVo employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("김사장"); //부서 직급?
        employeeVo.getEmp_dep().setDep_type_no(0);
        employeeVo.getEmp_pos().setPos_type_no(0);
        employeeVo.setEmp_email("kimsajang@tt.com");
        employeeVo.setEmp_phone("010-1234-1234");
        employeeVo.setEmp_com_num("7701");
        employeeVo.setEmp_gender("M");
        employeeVo.setEmp_birth("1970년 1월 1일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(0);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);


        // <인사부>
        employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("이부장");
        employeeVo.getEmp_dep().setDep_type_no(0);
        employeeVo.getEmp_pos().setPos_type_no(1);
        employeeVo.setEmp_email("leebujang@tt.com");
        employeeVo.setEmp_phone("010-1122-3344");
        employeeVo.setEmp_com_num("7702");
        employeeVo.setEmp_gender("M");
        employeeVo.setEmp_birth("1978년 5월 30일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(0);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);

        employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("강대리");
        employeeVo.getEmp_dep().setDep_type_no(0);
        employeeVo.getEmp_pos().setPos_type_no(2);
        employeeVo.setEmp_email("kangdaeri@tt.com");
        employeeVo.setEmp_phone("010-7029-1052");
        employeeVo.setEmp_com_num("7703");
        employeeVo.setEmp_gender("W");
        employeeVo.setEmp_birth("1988년 7월 21일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(1);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);

        employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("오사원");
        employeeVo.getEmp_dep().setDep_type_no(0);
        employeeVo.getEmp_pos().setPos_type_no(2);
        employeeVo.setEmp_email("5sawon@tt.com");
        employeeVo.setEmp_phone("010-1526-4346");
        employeeVo.setEmp_com_num("7704");
        employeeVo.setEmp_gender("M");
        employeeVo.setEmp_birth("1994년 2월 12일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(1);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);

        // <개발부>
        employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("고부장");
        employeeVo.getEmp_dep().setDep_type_no(1);
        employeeVo.getEmp_pos().setPos_type_no(1);
        employeeVo.setEmp_email("gobujang@tt.com");
        employeeVo.setEmp_phone("010-1122-3344");
        employeeVo.setEmp_com_num("7711");
        employeeVo.setEmp_gender("M");
        employeeVo.setEmp_birth("1980년 10월 10일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(0);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);

        employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("황대리");
        employeeVo.getEmp_dep().setDep_type_no(1);
        employeeVo.getEmp_pos().setPos_type_no(2);
        employeeVo.setEmp_email("hwangdaeri@tt.com");
        employeeVo.setEmp_phone("010-3247-8872");
        employeeVo.setEmp_com_num("7712");
        employeeVo.setEmp_gender("M");
        employeeVo.setEmp_birth("1990년 12월 1일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(4);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);

        employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("최사원");
        employeeVo.getEmp_dep().setDep_type_no(1);
        employeeVo.getEmp_pos().setPos_type_no(2);
        employeeVo.setEmp_email("choisawon@tt.com");
        employeeVo.setEmp_phone("010-1612-7754");
        employeeVo.setEmp_com_num("7704");
        employeeVo.setEmp_gender("M");
        employeeVo.setEmp_birth("1992년 7월 21일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(4);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);

        // <영업부>
        employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("장부장");
        employeeVo.getEmp_dep().setDep_type_no(2);
        employeeVo.getEmp_pos().setPos_type_no(1);
        employeeVo.setEmp_email("jangbujang@tt.com");
        employeeVo.setEmp_phone("010-7754-3523");
        employeeVo.setEmp_com_num("7721");
        employeeVo.setEmp_gender("M");
        employeeVo.setEmp_birth("1975년 5월 3일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(0);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);

        employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("마대리");
        employeeVo.getEmp_dep().setDep_type_no(2);
        employeeVo.getEmp_pos().setPos_type_no(2);
        employeeVo.setEmp_email("mabujang@tt.com");
        employeeVo.setEmp_phone("010-5152-3324");
        employeeVo.setEmp_com_num("7721");
        employeeVo.setEmp_gender("M");
        employeeVo.setEmp_birth("1983년 11월 11일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(7);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);

        employeeVo = new EmployeeVo();
        employeeVo.setEmp_name("박사원");
        employeeVo.getEmp_dep().setDep_type_no(2);
        employeeVo.getEmp_pos().setPos_type_no(2);
        employeeVo.setEmp_email("parksawon@tt.com");
        employeeVo.setEmp_phone("010-8763-7784");
        employeeVo.setEmp_com_num("7722");
        employeeVo.setEmp_gender("M");
        employeeVo.setEmp_birth("1995년 9월 19일");
        employeeVo.setEmp_post("대전광역시 0구 00동 00아파트");
        employeeVo.setEmp_superior_emp_no(7);
        employeeVo.setEmp_pw("1111");
        employeeVos.add(employeeVo);

        employeeDao.create_temp_account(employeeVos);

    }

    public Map<String, Object> get_logged_in_emp_info(HttpSession session) {
        System.out.println("[EmployeeService] get_logged_in_emp_info() CALLED!!");
        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            map.put("result", "success");
            map.put("emp_no", logged_in_employee_vo.getEmp_no());
            map.put("emp_name", logged_in_employee_vo.getEmp_name());
            map.put("dep_no", logged_in_employee_vo.getEmp_dep().getDep_type_no());
            map.put("dep_name", logged_in_employee_vo.getEmp_dep().getDep_type_name());
        }
        return map;
    }

    public Map<String, Object> get_dep_member_by_dep_no(Map<String, Integer> msg, HttpSession session) {
        System.out.println("[EmployeeService] get_dep_member_by_dep_no() CALLED!!");

        Map<String, Object> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {

            List<EmployeeVo> employeeVos =  employeeDao.get_dep_member_by_dep_no(msg.get("dep_no"));
            map.put("result", "success");
            map.put("employeeVos", employeeVos);
        }

        return map;
    }

    public Map<String, String> logout_confirm(HttpSession session) {
        System.out.println("[EmployeeService] logout_confirm() CALLED!!");

        Map<String, String> map = new HashMap<>();

        EmployeeVo logged_in_employee_vo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
        if (logged_in_employee_vo == null) {
            map.put("result", "fail");
        } else {
            session.invalidate();
            map.put("result", "success");
        }

        return map;
    }
}
