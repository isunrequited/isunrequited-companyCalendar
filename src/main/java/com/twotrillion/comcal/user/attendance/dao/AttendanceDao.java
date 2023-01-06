package com.twotrillion.comcal.user.attendance.dao;

import com.twotrillion.comcal.user.attendance.vo.AttendanceVo;
import com.twotrillion.comcal.user.common.paging.Criteria;
import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int go_work(AttendanceVo attendanceVo) {
        System.out.println("[AttendanceDao] go_work() CALLED!!");

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

    public int go_work_status_change(AttendanceVo attendanceVo) {
        System.out.println("[AttendanceDao] go_work_status_change() CALLED!!");

        String sql = "UPDATE tbl_emp " +
                "SET emp_status_no = 1 " +
                "WHERE emp_no = ? ";

        int result = 0;

        try {
            result = jdbcTemplate.update(sql, attendanceVo.getEmp_info().getEmp_no());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int go_home(AttendanceVo attendanceVo) {
        System.out.println("[AttendanceDao] go_home() CALLED!!");

        String sql = "UPDATE tbl_attendance_record " +
                        "SET atd_end_time = ?, " +
                            "atd_type_no = ?, " +
                            "atd_rcd_mod_date = NOW() " +
                         "WHERE atd_rcd_no = " +
                                "(SELECT atd_rcd_no FROM " +
                                    "(SELECT atd_rcd_no FROM tbl_attendance_record " +
                                    "WHERE emp_no = ? ORDER BY atd_rcd_no DESC LIMIT 1) ar) ";
        int result = 0;

        try {
            result = jdbcTemplate.update(sql,
                    attendanceVo.getAtd_end_time(),
                    attendanceVo.getAtd_type().getAtd_type_no(),
                    attendanceVo.getEmp_info().getEmp_no());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int go_home_status_change(AttendanceVo attendanceVo) {
        System.out.println("[AttendanceDao] go_home() CALLED!!");

        String sql = "UPDATE tbl_emp " +
                "SET emp_status_no = 0 " +
                "WHERE emp_no = ? ";

        int result = 0;

        try {
            result = jdbcTemplate.update(sql, attendanceVo.getEmp_info().getEmp_no());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<AttendanceVo> get_attendance_list(Criteria criteria, int emp_no) {
        System.out.println("[AttendanceDao] get_attendance_list() CALLED!!");

        String sql = "SELECT "
                    + "atdr.atd_rcd_no atd_rcd_no, "
                    + "atdr.atd_year atd_year, "
                    + "atdr.atd_month atd_month, "
                    + "atdr.atd_day atd_day, "
                    + "atdr.atd_start_time atd_start_time, "
                    + "atdr.atd_end_time atd_end_time, "
                    + "atdt.atd_type_name atd_type_name, "
                    + "atdr.atd_rcd_detail atd_rcd_detail "
                + "FROM tbl_attendance_record atdr "
                + "JOIN tbl_attendance_type atdt "
                    + "ON atdr.atd_type_no = atdt.atd_type_no "
                + "WHERE atdr.emp_no = ? "
                + "ORDER BY atd_rcd_no DESC LIMIT ?, ?";

        List<AttendanceVo> attendanceVos = null;

        try {

            attendanceVos = jdbcTemplate.query(sql, new RowMapper<AttendanceVo>() {

                @Override
                public AttendanceVo mapRow(ResultSet rs, int rowNum) throws SQLException {

                    AttendanceVo attendanceVo = new AttendanceVo();
                    attendanceVo.setAtd_rcd_no(rs.getInt("atd_rcd_no"));
                    attendanceVo.setAtd_year(rs.getInt("atd_year"));
                    attendanceVo.setAtd_month(rs.getInt("atd_month"));
                    attendanceVo.setAtd_day(rs.getInt("atd_day"));
                    attendanceVo.setAtd_start_time(rs.getString("atd_start_time"));
                    attendanceVo.setAtd_end_time(rs.getString("atd_end_time"));
                    attendanceVo.getAtd_type().setAtd_type_name(rs.getString("atd_type_name"));
                    attendanceVo.setAtd_rcd_detail(rs.getString("atd_rcd_detail"));

                    return attendanceVo;
                }

            }, emp_no, criteria.getSkip(), criteria.getAmount());



        } catch (Exception e) {
            e.printStackTrace();
        }

        return attendanceVos;
    }



    public int get_total_attendance_list_cnt() {
        System.out.println("[AttendanceDao] get_total_attendance_list_cnt() CALLED!!");

        String sql = "select COUNT(*) FROM tbl_attendance_record";
        int totalCnt = -1;

        try {
            totalCnt = jdbcTemplate.queryForObject(sql, Integer.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalCnt;
    }


    public int attendance_check(int emp_no) {
        System.out.println("[AttendanceDao] attendance_check() CALLED!!");

        String sql = "select emp_status_no FROM tbl_emp WHERE emp_no = ?";
        List<EmployeeVo> employeeVos = new ArrayList<>();
        try {
            employeeVos = jdbcTemplate.query(sql, new RowMapper<EmployeeVo>() {

                @Override
                public EmployeeVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    EmployeeVo employeeVo = new EmployeeVo();
                    employeeVo.getEmp_status().setEmp_status_no(rs.getInt("emp_status_no"));

                    return employeeVo;
                }
            }, emp_no);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeVos.size() > 0 ? employeeVos.get(0).getEmp_status().getEmp_status_no() : -1;
    }
}
