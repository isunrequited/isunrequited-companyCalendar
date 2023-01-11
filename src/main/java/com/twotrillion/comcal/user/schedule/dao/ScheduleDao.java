package com.twotrillion.comcal.user.schedule.dao;

import com.twotrillion.comcal.user.schedule.vo.InfosForGetScheduleVo;
import com.twotrillion.comcal.user.schedule.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ScheduleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int create_schedule(ScheduleVo scheduleVo) {
        System.out.println("[ScheduleDao] create_schedule() CALLED!!");
        int result = -1;

        String sql = "INSERT INTO tbl_scd (scd_title, " +
                                            "scd_detail, " +
                                            "scd_type_no, " +
                                            "scd_writer_emp_no, " +
                                            "scd_auth_range_type_no, " +
                                            "scd_auth_target_no, " +
                                            "scd_start_year, " +
                                            "scd_start_month, " +
                                            "scd_start_day, " +
                                            "scd_end_year, " +
                                            "scd_end_month, " +
                                            "scd_end_day, " +
                                            "scd_reg_date, " +
                                            "scd_mod_date) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

        try {
            result = jdbcTemplate.update(sql, scheduleVo.getScd_title(),
                                                scheduleVo.getScd_detail(),
                                                scheduleVo.getScd_type().getScd_type_no(),
                                                scheduleVo.getScd_writer_emp_info().getEmp_no(),
                                                scheduleVo.getScd_auth_range_type().getScd_auth_range_type_no(),
                                                scheduleVo.getScd_auth_target_no(),
                                                scheduleVo.getScd_start_year(),
                                                scheduleVo.getScd_start_month(),
                                                scheduleVo.getScd_start_day(),
                                                scheduleVo.getScd_end_year(),
                                                scheduleVo.getScd_end_month(),
                                                scheduleVo.getScd_end_day());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public ScheduleVo get_schedule_by_no(int scd_no) {
        System.out.println("[ScheduleDao] get_schedule_by_no() CALLED!!");
        String sql = "SELECT s.scd_no scd_no, " +
                            "e.emp_name scd_writer_emp_name, " +
                            "t.scd_type_name scd_type_name, " +
                            "s.scd_start_year scd_start_year, " +
                            "s.scd_start_month scd_start_month, " +
                            "s.scd_start_day scd_start_day, " +
                            "s.scd_end_year scd_end_year, " +
                            "s.scd_end_month scd_end_month, " +
                            "s.scd_end_day scd_end_day, " +
                            "s.scd_title scd_title, " +
                            "s.scd_detail scd_detail, " +
                            "s.scd_auth_range_type_no scd_auth_range_type_no, " +
                            "s.scd_auth_target_no scd_auth_target_no " +
                    "FROM tbl_scd s " +
                    "JOIN tbl_emp e " +
                        "ON s.scd_writer_emp_no = e.emp_no " +
                    "JOIN tbl_scd_type t " +
                        "ON s.scd_type_no = t.scd_type_no " +
                    "WHERE s.scd_no = ?";

        List<ScheduleVo> scheduleVos = new ArrayList<>();
        try {
            scheduleVos = jdbcTemplate.query(sql, new RowMapper<ScheduleVo>() {
                @Override
                public ScheduleVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ScheduleVo scheduleVo = new ScheduleVo();
                    scheduleVo.setScd_no(rs.getInt("scd_no"));
                    scheduleVo.setScd_title(rs.getString("scd_title"));
                    scheduleVo.getScd_writer_emp_info().setEmp_name(rs.getString("scd_writer_emp_name"));
                    scheduleVo.getScd_type().setScd_type_name(rs.getString("scd_type_name"));
                    scheduleVo.getScd_auth_range_type().setScd_auth_range_type_no(rs.getInt("scd_auth_range_type_no"));
                    scheduleVo.setScd_auth_target_no(rs.getInt("scd_auth_target_no"));
                    scheduleVo.setScd_detail(rs.getString("scd_detail"));
                    scheduleVo.setScd_start_year(rs.getInt("scd_start_year"));
                    scheduleVo.setScd_start_month(rs.getInt("scd_start_month"));
                    scheduleVo.setScd_start_day(rs.getInt("scd_start_day"));
                    scheduleVo.setScd_end_year(rs.getInt("scd_end_year"));
                    scheduleVo.setScd_end_month(rs.getInt("scd_end_month"));
                    scheduleVo.setScd_end_day(rs.getInt("scd_end_day"));

                    return scheduleVo;
                }
            }, scd_no);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return scheduleVos.size() > 0 ? scheduleVos.get(0) : null;
    }

    public int schedule_auth_confirm(Integer scd_no) {
        System.out.println("[ScheduleDao] schedule_auth_confirm() CALLED!!");
        int scd_writer_emp_no = -1;

        String sql = "SELECT scd_writer_emp_no FROM tbl_scd WHERE scd_no = ?";

        try {
            scd_writer_emp_no = jdbcTemplate.query(sql, new RowMapper<ScheduleVo>() {
                @Override
                public ScheduleVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ScheduleVo scheduleVo = new ScheduleVo();
                    scheduleVo.getScd_writer_emp_info().setEmp_no(rs.getInt("scd_writer_emp_no"));

                    return scheduleVo;
                }
            }, scd_no).get(0).getScd_writer_emp_info().getEmp_no();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scd_writer_emp_no;
    }

    public int modify_schedule(ScheduleVo scheduleVo) {
        System.out.println("[ScheduleDao] modify_schedule() CALLED!!");
        int result = -1;

        String sql = "UPDATE tbl_scd " +
                    "SET scd_title = ?, " +
                        "scd_detail = ?, " +
                        "scd_start_year = ?, " +
                        "scd_start_month = ?, " +
                        "scd_start_day = ?, " +
                        "scd_end_year = ?, " +
                        "scd_end_month = ?, " +
                        "scd_end_day = ?, " +
                        "scd_mod_date = NOW() " +
                    "WHERE scd_no = ?";

        try {
            result = jdbcTemplate.update(sql, scheduleVo.getScd_title(),
                                                scheduleVo.getScd_detail(),
                                                scheduleVo.getScd_start_year(),
                                                scheduleVo.getScd_start_month(),
                                                scheduleVo.getScd_start_day(),
                                                scheduleVo.getScd_end_year(),
                                                scheduleVo.getScd_end_month(),
                                                scheduleVo.getScd_end_day(),
                                                scheduleVo.getScd_no());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int delete_schedule(Integer scd_no) {
        System.out.println("[ScheduleDao] delete_schedule() CALLED!!");
        int result = -1;

        String sql = "DELETE FROM tbl_scd WHERE scd_no = ?";

        try {
            result = jdbcTemplate.update(sql, scd_no);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<ScheduleVo> get_schedule_list_by_date(InfosForGetScheduleVo infosForGetScheduleVo) {
        System.out.println("[ScheduleDao] delete_schedule() CALLED!!");
        List<ScheduleVo> scheduleVos = new ArrayList<>();

        String sql = "SELECT * FROM tbl_scd " +
                    "WHERE ( DATE(?) BETWEEN DATE(CONCAT(scd_start_year, '-', scd_start_month, '-', scd_start_day)) AND DATE(CONCAT(scd_end_year, '-', scd_end_month, '-', scd_end_day)) + 1) AND ( ";

        if (infosForGetScheduleVo.isPrivate_schedule()) {
            sql += "(scd_auth_range_type_no = 0 " +
                    "AND scd_auth_target_no = " + Integer.toString(infosForGetScheduleVo.getEmp_no()) + ") OR ";
        }

        if (infosForGetScheduleVo.isProject_schedule()) {
            sql += "(scd_auth_range_type_no = 1 " +
                    "AND scd_auth_target_no IN (" + Arrays.stream(infosForGetScheduleVo.getPjt_nos()).mapToObj(String::valueOf).collect(Collectors.joining(", ")) + ")) OR ";
        }

        if (infosForGetScheduleVo.isDepartment_schedule()) {
            sql += "(scd_auth_range_type_no = 2 " +
                    "AND scd_auth_target_no = " + Integer.toString(infosForGetScheduleVo.getDep_no()) + ") OR ";
        }

        if (infosForGetScheduleVo.isCompany_schedule()) {
            sql += "(scd_auth_range_type_no = 3 " +
                    "AND scd_auth_target_no = 0) OR ";
        }
        sql += "1 = 0)";

        System.out.println("sql = " + sql);
        try {
            scheduleVos = jdbcTemplate.query(sql, new RowMapper<ScheduleVo>() {
                @Override
                public ScheduleVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ScheduleVo scheduleVo = new ScheduleVo();
                    scheduleVo.setScd_no(rs.getInt("scd_no"));
                    scheduleVo.setScd_title(rs.getString("scd_title"));
                    scheduleVo.getScd_auth_range_type().setScd_auth_range_type_no(rs.getInt("scd_auth_range_type_no"));
                    scheduleVo.setScd_start_year(rs.getInt("scd_start_year"));
                    scheduleVo.setScd_start_month(rs.getInt("scd_start_month"));
                    scheduleVo.setScd_start_day(rs.getInt("scd_start_day"));
                    scheduleVo.setScd_end_year(rs.getInt("scd_end_year"));
                    scheduleVo.setScd_end_month(rs.getInt("scd_end_month"));
                    scheduleVo.setScd_end_day(rs.getInt("scd_end_day"));

                    return scheduleVo;
                }
            }, infosForGetScheduleVo.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scheduleVos;
    }

    public List<ScheduleVo> get_schedule_list(InfosForGetScheduleVo infosForGetScheduleVo) {
        System.out.println("[ScheduleDao] get_schedule_list() CALLED!!");
        List<ScheduleVo> scheduleVos = new ArrayList<>();

        String sql = "SELECT * FROM tbl_scd " +
                "WHERE scd_writer_emp_no = ? " +
                "AND ((DATE(CONCAT(scd_start_year, '-', scd_start_month, '-', scd_start_day)) BETWEEN DATE(?) AND DATE(?)) " +
                "OR (DATE(CONCAT(scd_end_year, '-', scd_end_month, '-', scd_end_day)) BETWEEN DATE(?) AND DATE(?))) AND ( ";

        if (infosForGetScheduleVo.isPrivate_schedule()) {
            sql += "(scd_auth_range_type_no = 0 " +
                    "AND scd_auth_target_no = " + Integer.toString(infosForGetScheduleVo.getEmp_no()) + ") OR ";
        }

        if (infosForGetScheduleVo.isProject_schedule()) {
            sql += "(scd_auth_range_type_no = 1 " +
                    "AND scd_auth_target_no IN (" + Arrays.stream(infosForGetScheduleVo.getPjt_nos()).mapToObj(String::valueOf).collect(Collectors.joining(", ")) + "))   OR ";
        }

        if (infosForGetScheduleVo.isDepartment_schedule()) {
            sql += "(scd_auth_range_type_no = 2 " +
                    "AND scd_auth_target_no = " + Integer.toString(infosForGetScheduleVo.getDep_no()) + ") OR ";
        }

        if (infosForGetScheduleVo.isCompany_schedule()) {
            sql += "(scd_auth_range_type_no = 3 " +
                    "AND scd_auth_target_no = 0) OR ";
        }
        sql += "1 = 0)";

        try {
            scheduleVos = jdbcTemplate.query(sql, new RowMapper<ScheduleVo>() {
                @Override
                public ScheduleVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ScheduleVo scheduleVo = new ScheduleVo();
                    scheduleVo.setScd_no(rs.getInt("scd_no"));
                    scheduleVo.setScd_title(rs.getString("scd_title"));
                    scheduleVo.getScd_auth_range_type().setScd_auth_range_type_no(rs.getInt("scd_auth_range_type_no"));
                    scheduleVo.setScd_start_year(rs.getInt("scd_start_year"));
                    scheduleVo.setScd_start_month(rs.getInt("scd_start_month"));
                    scheduleVo.setScd_start_day(rs.getInt("scd_start_day"));
                    scheduleVo.setScd_end_year(rs.getInt("scd_end_year"));
                    scheduleVo.setScd_end_month(rs.getInt("scd_end_month"));
                    scheduleVo.setScd_end_day(rs.getInt("scd_end_day"));

                    return scheduleVo;
                }
            }, infosForGetScheduleVo.getEmp_no(),
                    infosForGetScheduleVo.getStart_date(),
                    infosForGetScheduleVo.getEnd_date(),
                    infosForGetScheduleVo.getStart_date(),
                    infosForGetScheduleVo.getEnd_date());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scheduleVos;
    }
}
