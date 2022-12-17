package com.twotrillion.comcal.user.project.vo;

import com.twotrillion.comcal.user.employee.vo.DepartmentVo;
import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectVo {
    private int pjt_no;
    private String pjt_title;
    private DepartmentVo pjt_dep_no;
    private EmployeeVo pjt_manager_emp;
    private int pjt_start_year;
    private int pjt_start_month;
    private int pjt_start_day;
    private int pjt_end_year;
    private int pjt_end_month;
    private int pjt_end_day;

}
