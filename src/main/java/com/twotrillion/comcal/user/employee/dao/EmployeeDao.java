package com.twotrillion.comcal.user.employee.dao;

import com.twotrillion.comcal.user.employee.vo.DepartmentVo;
import com.twotrillion.comcal.user.employee.vo.EmployeeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public EmployeeVo login_confirm(EmployeeVo employeeVo) {

        System.out.println("[EmployeeDao] login_confirm() called");

        List<EmployeeVo> employeeVos = new ArrayList<EmployeeVo>();

        String sql = "SELECT     e.emp_no emp_no, " +
                                "e.emp_name emp_name, " +
                                "d.dep_type_name dep_type_name, " +
                                "d.dep_type_no dep_type_no, " +
                                "p.pos_type_name pos_type_name, " +
                                "p.pos_type_no pos_type_no, " +
                                "e.emp_email emp_email, " +
                                "e.emp_phone emp_phone," +
                                "e.emp_com_num emp_com_num, " +
                                "e.emp_gender emp_gender," +
                                "e.emp_birth emp_birth, " +
                                "e.emp_birth emp_birth, " +
                                "e.emp_post emp_post, " +
                                "e.emp_superior_emp_no emp_superior_emp_no, " +
                                "e.emp_pw emp_pw " +
                    "FROM tbl_emp e " +
                    "JOIN tbl_dep_type d " +
                        "ON e.emp_dep_no = d.dep_type_no " +
                    "JOIN tbl_pos_type p " +
                        "ON e.emp_pos_no = p.pos_type_no " +
                    "WHERE emp_email = ? ";

        try {

            employeeVos = jdbcTemplate.query(sql, new RowMapper<EmployeeVo>() {

                @Override
                public EmployeeVo mapRow(ResultSet rs, int rowNum) throws SQLException {

                    EmployeeVo employeeVo = new EmployeeVo();

                    employeeVo.setEmp_no(rs.getInt("emp_no"));
                    employeeVo.setEmp_name(rs.getString("emp_name"));
                    employeeVo.getEmp_dep().setDep_type_name(rs.getString("dep_type_name"));
                    employeeVo.getEmp_dep().setDep_type_no(rs.getInt("dep_type_no"));
					employeeVo.getEmp_pos().setPos_type_name(rs.getString("pos_type_name"));
					employeeVo.getEmp_pos().setPos_type_no(rs.getInt("pos_type_no"));
                    employeeVo.setEmp_email(rs.getString("emp_email"));
                    employeeVo.setEmp_phone(rs.getString("emp_phone"));
                    employeeVo.setEmp_com_num(rs.getString("emp_com_num"));
                    employeeVo.setEmp_gender(rs.getString("emp_gender"));
                    employeeVo.setEmp_birth(rs.getString("emp_birth"));
                    employeeVo.setEmp_post(rs.getString("emp_post"));
                    employeeVo.setEmp_superior_emp_no(rs.getInt("emp_superior_emp_no"));
                    employeeVo.setEmp_pw(rs.getString("emp_pw"));

                    return employeeVo;
                }


            }, employeeVo.getEmp_email());

            if (!passwordEncoder.matches(employeeVo.getEmp_pw(), employeeVos.get(0).getEmp_pw())) {
                employeeVos.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeVos.size() > 0 ? employeeVos.get(0) : null ;
    }

    public void create_temp_account(List<EmployeeVo> employeeVos) {
        System.out.println("[EmployeeDao] create_temp_account() called");

        String sql = "INSERT INTO tbl_emp("
                + "emp_name, "
                + "emp_dep_no, "
                + "emp_pos_no, "
                + "emp_email, "
                + "emp_phone, "
                + "emp_com_num, "
                + "emp_gender, "
                + "emp_birth, "
                + "emp_post, "
                + "emp_superior_emp_no, "
                + "emp_pw, "
                + "emp_reg_date, "
                + "emp_mod_date) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";


        try {
            for (EmployeeVo employeeVo : employeeVos) {
                jdbcTemplate.update(sql,
                        employeeVo.getEmp_name(),
                        employeeVo.getEmp_dep().getDep_type_no(),
                        employeeVo.getEmp_pos().getPos_type_no(),
                        employeeVo.getEmp_email(),
                        employeeVo.getEmp_phone(),
                        employeeVo.getEmp_com_num(),
                        employeeVo.getEmp_gender(),
                        employeeVo.getEmp_birth(),
                        employeeVo.getEmp_post(),
                        employeeVo.getEmp_superior_emp_no(),
                        passwordEncoder.encode(employeeVo.getEmp_pw()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String get_dep_name_by_no(Integer no) {
        System.out.println("[EmployeeDao] get_dep_name_by_no() called");

        String sql = "SELECT dep_type_name FROM tbl_dep_type WHERE dep_type_no = ?";

        List<DepartmentVo> departmentVos = new ArrayList<>();
        try {
            departmentVos = jdbcTemplate.query(sql, new RowMapper<DepartmentVo>() {
                @Override
                public DepartmentVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    DepartmentVo departmentVo = new DepartmentVo();
                    departmentVo.setDep_type_name(rs.getString("dep_type_name"));
                    return departmentVo;
                }
            }, no);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return departmentVos.size() > 0 ? departmentVos.get(0).getDep_type_name() : "";
    }
}
