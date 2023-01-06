package com.twotrillion.comcal.user.employee.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class EmployeeVo {
    private int emp_no;
    private String emp_name;
    private DepartmentVo emp_dep;
    private PositionVo emp_pos;
    private EmpStatus emp_status;
    private String emp_email;
    private String emp_phone;
    private String emp_com_num;
    private String emp_gender;
    private String emp_birth;
    private String emp_post;
    private int emp_superior_emp_no;
    private String emp_pw;

    public EmployeeVo() {
        this.setEmp_dep(new DepartmentVo());
        this.setEmp_pos(new PositionVo());
        this.setEmp_status(new EmpStatus());
    }
}
