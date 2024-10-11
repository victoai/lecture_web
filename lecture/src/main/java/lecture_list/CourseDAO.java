// src/model/CourseDAO.java
package lecture_list;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DBConnection;
import Model.Course;

public class CourseDAO {

    // 모든 강좌 조회 메소드
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT c.course_id, c.course_name, d.department_name, c.classification, c.semester, c.credit "
                   + "FROM Course c "
                   + "JOIN Department d ON c.department_id = d.department_id "
                   + "ORDER BY c.course_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                String courseName = rs.getString("course_name");
                String departmentName = rs.getString("department_name");
                String classification = rs.getString("classification");
                String semester = rs.getString("semester");
                int credit = rs.getInt("credit");

                Course course = new Course(courseId, courseName, departmentName, classification, semester, credit);
                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList;
    }

    // 특정 강좌 조회 메소드 
    public Course getCourseById(int courseId) {
        String sql = "SELECT c.course_id, c.course_name, d.department_name, c.classification, c.semester, c.credit "
                   + "FROM Course c "
                   + "JOIN Department d ON c.department_id = d.department_id "
                   + "WHERE c.course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String courseName = rs.getString("course_name");
                    String departmentName = rs.getString("department_name");
                    String classification = rs.getString("classification");
                    String semester = rs.getString("semester");
                    int credit = rs.getInt("credit");

                    return new Course(courseId, courseName, departmentName, classification, semester, credit);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
