// src/Model/DepartmentDAO.java
package lecture_list;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DBConnection.DBConnection;
import Model.Department;

public class DepartmentDAO {
    
    // 모든 학과를 조회하는 메소드
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT department_id, department_name FROM Department"; // 실제 테이블명과 컬럼명으로 수정
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             
            while (rs.next()) {
                int id = rs.getInt("department_id");
                String name = rs.getString("department_name");
                departments.add(new Department(id, name));
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
        }
        
        return departments;
    }
    
    // 학과 ID로 학과 이름을 조회하는 메소드
    public String getDepartmentNameById(int departmentId) {
        String departmentName = "";
        String sql = "SELECT department_name FROM Department WHERE department_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, departmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    departmentName = rs.getString("department_name");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
        }
        
        return departmentName;
    }
}
