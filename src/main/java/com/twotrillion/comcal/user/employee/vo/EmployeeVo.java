package com.twotrillion.comcal.user.employee.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeVo {
    private int emp_no;
    private String emp_name;
    private DepartmentVo emp_dep;
    private PositionVo emp_pos;
    private String emp_email;
    private String emp_phone;
    private String emp_com_num;
    private char emp_gender;
    private String emp_birth;
    private String emp_post;
    private EmployeeVo emp_superior_emp;
    private String emp_pw;
}
