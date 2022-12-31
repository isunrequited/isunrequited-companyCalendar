package com.twotrillion.comcal.user.schedule.vo;

import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ScheduleVo {

    private int scd_no;
    private String scd_title;
    private String scd_detail;
    private EmployeeVo scd_writer_emp_info;
    private ScheduleTypeVo scd_type;
    private ScheduleAuthRangeTypeVo scd_auth_range_type;
    private int scd_auth_target_no;
    private int scd_start_year;
    private int scd_start_month;
    private int scd_start_day;
    private int scd_end_year;
    private int scd_end_month;
    private int scd_end_day;

    public ScheduleVo() {
        this.setScd_writer_emp_info(new EmployeeVo());
        this.setScd_type(new ScheduleTypeVo());
        this.setScd_auth_range_type(new ScheduleAuthRangeTypeVo());
    }

}
