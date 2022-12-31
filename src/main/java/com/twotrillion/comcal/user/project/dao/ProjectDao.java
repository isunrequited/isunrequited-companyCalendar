package com.twotrillion.comcal.user.project.dao;

import com.twotrillion.comcal.user.employee.vo.DepartmentVo;
import com.twotrillion.comcal.user.project.vo.ProjectVo;
import com.twotrillion.comcal.user.schedule.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ProjectVo> get_projects_by_emp_no(int emp_no) {
        System.out.println("[ProjectDao] get_projects_by_emp_no() CALLED!!");

        String sql = "SELECT p.pjt_no pjt_no, " +
                            "p.pjt_title pjt_title, " +
                            "p.pjt_dep_no pjt_dep_no, " +
                            "p.pjt_manager_emp_no pjt_manager_emp_no, " +
                            "p.pjt_start_year pjt_start_year, " +
                            "p.pjt_start_month pjt_start_month, " +
                            "p.pjt_start_day pjt_start_day, " +
                            "p.pjt_end_year pjt_end_year, " +
                            "p.pjt_end_month pjt_end_month, " +
                            "p.pjt_end_day pjt_end_day " +
                    "FROM tbl_emp_pjt ep " +
                    "JOIN tbl_pjt p " +
                        "ON ep.pjt_no = p.pjt_no " +
                    "WHERE ep.emp_no = ?";

        List<ProjectVo> projectVos = new ArrayList<>();
        try {
            projectVos = jdbcTemplate.query(sql, new RowMapper<ProjectVo>() {
                @Override
                public ProjectVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ProjectVo projectVo = new ProjectVo();
                    projectVo.setPjt_no(rs.getInt("pjt_no"));
                    projectVo.setPjt_title(rs.getString("pjt_title"));
                    projectVo.getPjt_dep().setDep_type_no(rs.getInt("pjt_dep_no"));
                    projectVo.getPjt_manager_emp().setEmp_no(rs.getInt("pjt_manager_emp_no"));
                    projectVo.setPjt_start_year(rs.getInt("pjt_start_year"));
                    projectVo.setPjt_start_month(rs.getInt("pjt_start_month"));
                    projectVo.setPjt_start_day(rs.getInt("pjt_start_day"));
                    projectVo.setPjt_end_year(rs.getInt("pjt_end_year"));
                    projectVo.setPjt_end_month(rs.getInt("pjt_end_month"));
                    projectVo.setPjt_end_day(rs.getInt("pjt_end_day"));

                    return projectVo;
                }
            }, emp_no);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectVos;
    }

    public List<ProjectVo> get_projects_title_by_emp_no(int emp_no) {
        System.out.println("[ProjectDao] get_projects_by_emp_no() CALLED!!");

        String sql = "SELECT p.pjt_no pjt_no, " +
                            "p.pjt_title pjt_title " +
                    "FROM tbl_emp_pjt ep " +
                    "JOIN tbl_pjt p " +
                         "ON ep.pjt_no = p.pjt_no " +
                    "WHERE ep.emp_no = ?";

        List<ProjectVo> projectVos = new ArrayList<>();
        try {
            projectVos = jdbcTemplate.query(sql, new RowMapper<ProjectVo>() {
                @Override
                public ProjectVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ProjectVo projectVo = new ProjectVo();
                    projectVo.setPjt_no(rs.getInt("pjt_no"));
                    projectVo.setPjt_title(rs.getString("pjt_title"));

                    return projectVo;
                }
            }, emp_no);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectVos;
    }

    public String get_pjt_name_by_no(Integer no) {
        System.out.println("[EmployeeDao] get_pjt_name_by_no() called");

        String sql = "SELECT pjt_title FROM tbl_pjt WHERE pjt_no = ?";

        List<ProjectVo> projectVos = new ArrayList<>();

        try {
            projectVos = jdbcTemplate.query(sql, new RowMapper<ProjectVo>() {
                @Override
                public ProjectVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ProjectVo projectVo = new ProjectVo();
                    projectVo.setPjt_title(rs.getString("pjt_title"));

                    return projectVo;
                }
            }, no);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(projectVos);


        return projectVos.size() > 0 ? projectVos.get(0).getPjt_title() : "";
    }
}
