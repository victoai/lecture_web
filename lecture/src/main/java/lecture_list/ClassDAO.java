// src/model/ClassDAO.java
package lecture_list;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DBConnection;
import Model.Classes;

public class ClassDAO {

	// 모든 강의 조회 메소드
	public List<Classes> getAllClasses(int studentId) {
		List<Classes> classList = new ArrayList<>();
		String sql = "SELECT cl.class_id, cl.course_id, p.name AS professor_name, cl.room_no, cl.capacity, "
				+ "cl.enrolled, cl.day_of_week, cl.start_time, cl.end_time, "
				+ "c.course_name, c.department_id, d.department_name, c.classification, c.semester AS course_semester, c.credit, "
				+ "CASE WHEN ch.is_retake IS NOT NULL THEN 1 ELSE 0 END AS is_retake " + "FROM Class cl "
				+ "JOIN Professor p ON cl.professor_id = p.professor_id "
				+ "JOIN Course c ON cl.course_id = c.course_id "
				+ "JOIN Department d ON c.department_id = d.department_id "
				+ "LEFT JOIN Course_History ch ON cl.course_id = ch.course_id AND ch.student_id = ? "
				+ "ORDER BY cl.class_id";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					int classId = rs.getInt("class_id");
					int courseId = rs.getInt("course_id");
					String courseName = rs.getString("course_name");
					int departmentId = rs.getInt("department_id");
					String departmentName = rs.getString("department_name");
					String classification = rs.getString("classification");
					String courseSemester = rs.getString("course_semester");
					int credit = rs.getInt("credit");
					String professorName = rs.getString("professor_name");
					String roomNo = rs.getString("room_no");
					int capacity = rs.getInt("capacity");
					int enrolled = rs.getInt("enrolled");
					String dayOfWeek = rs.getString("day_of_week");
					String startTime = rs.getString("start_time");
					String endTime = rs.getString("end_time");
					boolean isRetake = rs.getInt("is_retake") == 1;

					Classes classEntity = new Classes();
					classEntity.setClassId(classId);
					classEntity.setCourseId(courseId);
					classEntity.setCourseName(courseName);
					classEntity.setDepartmentId(departmentId);
					classEntity.setDepartmentName(departmentName);
					classEntity.setClassification(classification);
					classEntity.setCourseSemester(courseSemester);
					classEntity.setCredit(credit);
					classEntity.setProfessorName(professorName);
					classEntity.setRoomNo(roomNo);
					classEntity.setCapacity(capacity);
					classEntity.setEnrolled(enrolled);
					classEntity.setDayOfWeek(dayOfWeek);
					classEntity.setStartTime(startTime);
					classEntity.setEndTime(endTime);
					classEntity.setIsRetake(isRetake);

					classList.add(classEntity);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return classList;
	}

	// 클래스 ID로 특정 강의 조회 메소드
	public Classes getClassById(int classId) {
		String sql = "SELECT cl.class_id, cl.course_id, p.name AS professor_name, cl.room_no, cl.capacity, "
				+ "cl.enrolled, cl.day_of_week, cl.start_time, cl.end_time, "
				+ "c.course_name, c.department_id, d.department_name, c.classification, c.semester AS course_semester, c.credit, "
				+ "CASE WHEN ch.is_retake IS NOT NULL THEN 1 ELSE 0 END AS is_retake " + "FROM Class cl "
				+ "JOIN Professor p ON cl.professor_id = p.professor_id "
				+ "JOIN Course c ON cl.course_id = c.course_id "
				+ "JOIN Department d ON c.department_id = d.department_id "
				+ "LEFT JOIN Course_History ch ON cl.course_id = ch.course_id AND ch.student_id = ? "
				+ "WHERE cl.class_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Assuming student_id is not needed for this method, or you might need to pass
			// it
			// If not needed, remove the student_id from the query
			pstmt.setInt(1, 0); // Placeholder if student_id is not required
			pstmt.setInt(2, classId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					int courseId = rs.getInt("course_id");
					String courseName = rs.getString("course_name");
					int departmentId = rs.getInt("department_id");
					String departmentName = rs.getString("department_name");
					String classification = rs.getString("classification");
					String courseSemester = rs.getString("course_semester");
					int credit = rs.getInt("credit");
					String professorName = rs.getString("professor_name");
					String roomNo = rs.getString("room_no");
					int capacity = rs.getInt("capacity");
					int enrolled = rs.getInt("enrolled");
					String dayOfWeek = rs.getString("day_of_week");
					String startTime = rs.getString("start_time");
					String endTime = rs.getString("end_time");
					boolean isRetake = rs.getInt("is_retake") == 1;

					Classes classEntity = new Classes();
					classEntity.setClassId(classId);
					classEntity.setCourseId(courseId);
					classEntity.setCourseName(courseName);
					classEntity.setDepartmentId(departmentId);
					classEntity.setDepartmentName(departmentName);
					classEntity.setClassification(classification);
					classEntity.setCourseSemester(courseSemester);
					classEntity.setCredit(credit);
					classEntity.setProfessorName(professorName);
					classEntity.setRoomNo(roomNo);
					classEntity.setCapacity(capacity);
					classEntity.setEnrolled(enrolled);
					classEntity.setDayOfWeek(dayOfWeek);
					classEntity.setStartTime(startTime);
					classEntity.setEndTime(endTime);
					classEntity.setIsRetake(isRetake);

					return classEntity;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// enrolled 값 증가 메소드
	public boolean incrementEnrolled(int classId) {
		String sql = "UPDATE Class SET enrolled = enrolled + 1 WHERE class_id = ? AND enrolled < capacity";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, classId);
			int updatedRows = pstmt.executeUpdate();
			return updatedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// enrolled 값 감소 메소드
	public boolean decrementEnrolled(int classId) {
		String sql = "UPDATE Class SET enrolled = enrolled - 1 WHERE class_id = ? AND enrolled > 0";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, classId);
			int updatedRows = pstmt.executeUpdate();
			return updatedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
