package org.example;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbService {
    private static final String URL = "jdbc:sqlserver://localhost;database=empdb;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "Ranmal@ousl1433";

    public void callRegisterProc(EmployeeDTO employeeDTO) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String procedure = "{call main.register(?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(procedure);
            stmt.setString("name", employeeDTO.name());
            stmt.setString("role", employeeDTO.role());
            stmt.setLong("department", employeeDTO.dept());
            stmt.setString("house_num", employeeDTO.houseNum());
            stmt.setString("street", employeeDTO.street());
            stmt.setLong("city_id", employeeDTO.cityId());
            stmt.setString("email", employeeDTO.email());
            stmt.setLong("telephone", employeeDTO.tel());
            stmt.execute();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    public String calculateTenure(Long empId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String result = "";
            String tenureFunc = "SELECT main.TENURE(?) as tenure;";
            try (PreparedStatement stmt = conn.prepareStatement(tenureFunc)) {
                stmt.setLong(1, empId);
                try (ResultSet results = stmt.executeQuery()) {
                    while (results.next()) {
                        result += results.getInt("tenure");
                    }
                }
            }
            return result.equals("1") ? result + " month" : result + " months";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "-1";
        }
    }

    public List<DepartmentDTO> getAllDepartments() {
        var departments = new ArrayList<DepartmentDTO>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT dept_id, name FROM main.departments;")) {
                try (ResultSet results = stmt.executeQuery()) {
                    while (results.next()) {
                        departments.add(new DepartmentDTO(results.getLong("dept_id"), results.getString("name")));
                    }
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return departments;
    }
}
