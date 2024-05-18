package org.example;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.ConnectionEvent;

import com.microsoft.sqlserver.jdbc.SQLServerResource_ja;

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
            StringBuilder output = new StringBuilder();
            try (PreparedStatement stmt = conn.prepareStatement("SELECT name FROM main.employees WHERE emp_id = ?")) {
                stmt.setLong(1, empId);
                try (ResultSet results = stmt.executeQuery()) {
                    while (results.next()) {
                        output.append("For employee: " + results.getString("name") + "\n");
                    }
                }
            }
            String tenureFunc = "SELECT main.TENURE(?) as tenure;";
            try (PreparedStatement stmt = conn.prepareStatement(tenureFunc)) {
                stmt.setLong(1, empId);
                try (ResultSet results = stmt.executeQuery()) {
                    while (results.next()) {
                        int months = results.getInt("tenure");
                        if (months == 1) {
                            output.append(months + " month as head of ");
                        } else {
                            output.append(months + " months as head of ");
                        }
                    }
                }
            }
            try (PreparedStatement stmt = conn.prepareStatement("SELECT d.name as name FROM main.departments d JOIN main.dept_hods dh ON d.dept_id = dh.dept_id WHERE dh.hod = ?")) {
                stmt.setLong(1, empId);
                try (ResultSet results = stmt.executeQuery()) {
                    while(results.next()) {
                        output.append(results.getString("name"));
                    }
                }
            }
            return output.toString();
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

    public List<CountryDTO> getAllCountries() {
        var countries = new ArrayList<CountryDTO>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT country_id, name FROM main.countries;")) {
                try (ResultSet results = stmt.executeQuery()) {
                    while (results.next()) {
                        countries.add(new CountryDTO(results.getLong("country_id"), results.getString("name")));
                    }
                }
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return countries;
    }

    public List<ProvinceDTO> getProvincesFor(Long countryId) {
        var provinces = new ArrayList<ProvinceDTO>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            try (PreparedStatement stmt = conn
                    .prepareStatement("SELECT province_id, name_en FROM main.provinces WHERE country_id = ?")) {
                stmt.setLong(1, countryId);
                try (ResultSet results = stmt.executeQuery()) {
                    while (results.next()) {
                        provinces.add(new ProvinceDTO(results.getLong("province_id"), results.getString("name_en")));
                    }
                }
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return provinces;
    }

    public List<DistrictDTO> getDistrictsFor(Long provinceId) {
        var districts = new ArrayList<DistrictDTO>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            try (PreparedStatement stmt = conn
                    .prepareStatement("SELECT district_id, name_en FROM main.districts WHERE province_id = ?")) {
                stmt.setLong(1, provinceId);
                try (ResultSet results = stmt.executeQuery()) {
                    while (results.next()) {
                        districts.add(new DistrictDTO(results.getLong("district_id"), results.getString("name_en")));
                    }
                }
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return districts;
    }

    public List<CityDTO> getCitiesFor(Long districtId) {
        var cities = new ArrayList<CityDTO>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            try (PreparedStatement stmt = conn
                    .prepareStatement("SELECT city_id, name_en FROM main.cities WHERE district_id = ?")) {
                stmt.setLong(1, districtId);
                try (ResultSet results = stmt.executeQuery()) {
                    while (results.next()) {
                        cities.add(new CityDTO(results.getLong("city_id"), results.getString("name_en")));
                    }
                }
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return cities;
    }
    
    public List<HodDTO> getHods() {
        var hods = new ArrayList<HodDTO>();
        try(Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "SELECT e.emp_id as emp_id, e.name as name FROM main.employees e JOIN main.dept_hods dh ON e.emp_id = dh.hod;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet results = stmt.executeQuery()) {
                    while(results.next()) {
                        hods.add(new HodDTO(results.getLong("emp_id"), results.getString("name")));
                    }
                }
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return hods;
    }
}
