package com.twotrillion.comcal.user.schedule.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScheduleVo {

    private int scd_no;
    private String scd_title;
    private String scd_detail;
    private ScheduleTypeVo scd_type;
    private ScheduleAuthRangeTypeVo scd_auth_range_type;
    private int scd_auth_target_no;
    private int scd_start_year;
    private int scd_start_month;
    private int scd_start_day;
    private int scd_end_year;
    private int scd_end_month;
    private int scd_end_day;

}
