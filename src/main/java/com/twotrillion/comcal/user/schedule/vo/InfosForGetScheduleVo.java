package com.twotrillion.comcal.user.schedule.vo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfosForGetScheduleVo {
    private String date;
    private String start_date;
    private String end_date;
    private int emp_no;
    private int dep_no;
    private int[] pjt_nos;
    private boolean private_schedule;
    private boolean project_schedule;
    private boolean department_schedule;
    private boolean company_schedule;
}
