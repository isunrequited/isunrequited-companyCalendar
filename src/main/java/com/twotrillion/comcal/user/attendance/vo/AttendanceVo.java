package com.twotrillion.comcal.user.attendance.vo;

import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AttendanceVo {
    private int atd_rcd_no;
    private EmployeeVo emp_info;
    private int atd_year;
    private int atd_month;
    private int atd_day;
    private String atd_start_time;
    private String atd_end_time;
    private AttendanceTypeVo atd_type;
    private String atd_rcd_detail;

    public AttendanceVo() {
        this.setEmp_info(new EmployeeVo());
        this.setAtd_type(new AttendanceTypeVo());
    }
}
