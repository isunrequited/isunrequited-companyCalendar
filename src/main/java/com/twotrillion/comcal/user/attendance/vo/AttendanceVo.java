package com.twotrillion.comcal.user.attendance.vo;

import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendanceVo {
    private int atd_rcd_no;
    private EmployeeVo emp_no;
    private int atd_year;
    private int atd_monty;
    private int atd_day;
    private String atd_start_time;
    private String atd_end_time;
    private AttendanceTypeVo atd_type;
    private String atd_rcd_detail;

}
