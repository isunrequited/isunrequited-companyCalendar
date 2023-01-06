package com.twotrillion.comcal.user.project.dao;

import com.twotrillion.comcal.user.employee.vo.DepartmentVo;
import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import com.twotrillion.comcal.user.project.vo.ProjectVo;
import com.twotrillion.comcal.user.schedule.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
                "d.dep_type_no dep_type_no, " +
                "d.dep_type_name dep_type_name, " +
                "e.emp_no emp_no, " +
                "e.emp_name emp_name, " +
                "p.pjt_start_year pjt_start_year, " +
                "p.pjt_start_month pjt_start_month, " +
                "p.pjt_start_day pjt_start_day, " +
                "p.pjt_end_year pjt_end_year, " +
                "p.pjt_end_month pjt_end_month, " +
                "p.pjt_end_day pjt_end_day " +
                "FROM tbl_emp_pjt ep " +
                "JOIN tbl_pjt p " +
                "ON ep.pjt_no = p.pjt_no " +
                "JOIN tbl_emp e " +
                "ON p.pjt_manager_emp_no = e.emp_no " +
                "JOIN tbl_dep_type d " +
                "ON p.pjt_dep_no = d.dep_type_no " +
                "WHERE ep.emp_no = ?";

        List<ProjectVo> projectVos = new ArrayList<>();
        try {
            projectVos = jdbcTemplate.query(sql, new RowMapper<ProjectVo>() {
                @Override
                public ProjectVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ProjectVo projectVo = new ProjectVo();
                    projectVo.setPjt_no(rs.getInt("pjt_no"));
                    projectVo.setPjt_title(rs.getString("pjt_title"));
                    projectVo.getPjt_dep().setDep_type_no(rs.getInt("dep_type_no"));
                    projectVo.getPjt_dep().setDep_type_name(rs.getString("dep_type_name"));
                    projectVo.getPjt_manager_emp().setEmp_no(rs.getInt("emp_no"));
                    projectVo.getPjt_manager_emp().setEmp_name(rs.getString("emp_name"));
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

    public int create_project_confirm(ProjectVo projectVo) {
        System.out.println("[EmployeeDao] create_project_confirm() called");

        String sql = "INSERT INTO tbl_pjt( "
                + "pjt_title, "
                + "pjt_dep_no, "
                + "pjt_manager_emp_no, "
                + "pjt_start_year, "
                + "pjt_start_month, "
                + "pjt_start_day, "
                + "pjt_end_year, "
                + "pjt_end_month, "
                + "pjt_end_day, "
                + "pjt_reg_date, "
                + "pjt_mod_date) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";



        int pjt_no = -1;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, projectVo.getPjt_title());
                    pstmt.setInt(2, projectVo.getPjt_dep().getDep_type_no());
                    pstmt.setInt(3, projectVo.getPjt_manager_emp().getEmp_no());
                    pstmt.setInt(4, projectVo.getPjt_start_year());
                    pstmt.setInt(5, projectVo.getPjt_start_month());
                    pstmt.setInt(6, projectVo.getPjt_start_day());
                    pstmt.setInt(7, projectVo.getPjt_end_year());
                    pstmt.setInt(8, projectVo.getPjt_end_month());
                    pstmt.setInt(9, projectVo.getPjt_end_day());


                    return pstmt;
                }
            }, keyHolder);

            pjt_no = keyHolder.getKey().intValue();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return pjt_no;
    }

    public int create_emp_project_confirm(int pjt_no, ProjectVo projectVo) {
        System.out.println("[EmployeeDao] create_emp_project_confirm() called");
        String sql = "INSERT INTO tbl_emp_pjt(emp_no, pjt_no, emp_pjt_reg_date, emp_pjt_mod_date) " +
                "VALUE (?, ?, NOW(), NOW())";

        int result = -1;

        try {
            for (int emp_no : projectVo.getPjt_member()) {
                result = jdbcTemplate.update(sql, emp_no, pjt_no);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public ProjectVo get_project_detail(int pjt_no) {
        System.out.println("[EmployeeDao] get_project_detail() called");

        String sql = "SELECT p.pjt_no pjt_no, " +
                "p.pjt_title pjt_title, " +
                "d.dep_type_no dep_type_no, " +
                "d.dep_type_name dep_type_name, " +
                "e.emp_no emp_no, " +
                "e.emp_name emp_name, " +
                "p.pjt_start_year pjt_start_year, " +
                "p.pjt_start_month pjt_start_month, " +
                "p.pjt_start_day pjt_start_day, " +
                "p.pjt_end_year pjt_end_year, " +
                "p.pjt_end_month pjt_end_month, " +
                "p.pjt_end_day pjt_end_day " +
                "FROM tbl_pjt p " +
                "JOIN tbl_emp e " +
                "ON p.pjt_manager_emp_no = e.emp_no " +
                "JOIN tbl_dep_type d " +
                "ON p.pjt_dep_no = d.dep_type_no " +
                "WHERE p.pjt_no = ?";

        List<ProjectVo> projectVos = new ArrayList<ProjectVo>();

        try {

            projectVos = jdbcTemplate.query(sql, new RowMapper<ProjectVo>() {

                @Override
                public ProjectVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ProjectVo projectVo = new ProjectVo();
                    projectVo.setPjt_no(rs.getInt("pjt_no"));
                    projectVo.setPjt_title(rs.getString("pjt_title"));
                    projectVo.getPjt_dep().setDep_type_no(rs.getInt("dep_type_no"));
                    projectVo.getPjt_dep().setDep_type_name(rs.getString("dep_type_name"));
                    projectVo.getPjt_manager_emp().setEmp_no(rs.getInt("emp_no"));
                    projectVo.getPjt_manager_emp().setEmp_name(rs.getString("emp_name"));
                    projectVo.setPjt_start_year(rs.getInt("pjt_start_year"));
                    projectVo.setPjt_start_month(rs.getInt("pjt_start_month"));
                    projectVo.setPjt_start_day(rs.getInt("pjt_start_day"));
                    projectVo.setPjt_end_year(rs.getInt("pjt_end_year"));
                    projectVo.setPjt_end_month(rs.getInt("pjt_end_month"));
                    projectVo.setPjt_end_day(rs.getInt("pjt_end_day"));

                    return projectVo;
                }

            }, pjt_no);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectVos.size() > 0 ? projectVos.get(0) : null;
    }

    public List<EmployeeVo> get_project_members(int pjt_no) {
        System.out.println("[EmployeeDao] get_project_members() called");

        String sql = "SELECT e.emp_no emp_no, " +
                "e.emp_name emp_name " +
                "FROM tbl_emp_pjt ep " +
                "JOIN tbl_emp e " +
                "ON ep.emp_no = e.emp_no " +
                "WHERE ep.pjt_no = ?";

        List<EmployeeVo> employeeVos = new ArrayList<EmployeeVo>();

        try {
            employeeVos = jdbcTemplate.query(sql, new RowMapper<EmployeeVo>() {

                @Override
                public EmployeeVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    EmployeeVo employeeVo = new EmployeeVo();

                    employeeVo.setEmp_no(rs.getInt("emp_no"));
                    employeeVo.setEmp_name(rs.getString("emp_name"));

                    System.out.println(employeeVo.getEmp_no());

                    return employeeVo;
                }

            }, pjt_no);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return employeeVos;
    }
}
