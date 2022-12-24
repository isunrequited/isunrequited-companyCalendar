package com.twotrillion.comcal.user.attendance.dao;

import com.twotrillion.comcal.user.attendance.vo.AttendanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int go_work(AttendanceVo attendanceVo) {
        System.out.println("[AttendanceVo] go_work() CALLED!!");

        String sql = "INSERT INTO tbl_attendance_record("
                + "emp_no, "
                + "atd_year, "
                + "atd_month, "
                + "atd_day, "
                + "atd_start_time, "
                + "atd_type_no,"
                + "atd_rcd_detail, "
                + "atd_rcd_reg_date,"
                + "atd_rcd_mod_date) "
                + "VALUES(?,?,?,?,?,?,?,NOW(),NOW())";
        int result = 0;

        try {
            result = jdbcTemplate.update(sql,
                    attendanceVo.getEmp_info().getEmp_no(),
                    attendanceVo.getAtd_year(),
                    attendanceVo.getAtd_month(),
                    attendanceVo.getAtd_day(),
                    attendanceVo.getAtd_start_time(),
                    attendanceVo.getAtd_type().getAtd_type_no(),
                    attendanceVo.getAtd_rcd_detail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
