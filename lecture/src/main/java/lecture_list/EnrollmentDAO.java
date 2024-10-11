// src/Model/EnrollmentDAO.java
package lecture_list;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import DBConnection.DBConnection;
import Model.Classes;

public class EnrollmentDAO {

    // 학생이 이미 신청한 강의 목록 조회
    public Map<Integer, Integer> getEnrolledCourses(int studentId) {
        Map<Integer, Integer> enrolledCourses = new HashMap<>();
        String sql = "SELECT course_id, class_id FROM Enrollment WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int courseId = rs.getInt("course_id");
                    int classId = rs.getInt("class_id");
                    enrolledCourses.put(courseId, classId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
        }

        return enrolledCourses;
    }

    // 학생이 강의를 신청하는 메소드
    // 반환 값:
    // 0: 성공
    // 1: 이미 신청한 강좌
    // 2: 마감된 강좌
    // -1: 기타 오류
    public int enrollCourse(int studentId, int courseId, int classId) {
        String checkEnrollmentSQL = "SELECT COUNT(*) AS cnt FROM Enrollment WHERE student_id = ? AND course_id = ?";
        String checkCapacitySQL = "SELECT capacity, enrolled FROM Class WHERE class_id = ?";
        String insertEnrollmentSQL = "INSERT INTO Enrollment (student_id, course_id, class_id) VALUES (?, ?, ?)";
        String updateEnrolledSQL = "UPDATE Class SET enrolled = enrolled + 1 WHERE class_id = ?";

        Connection conn = null;
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtCheckCapacity = null;
        PreparedStatement pstmtInsert = null;
        PreparedStatement pstmtUpdateEnrolled = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 1. 이미 신청한 강좌인지 확인
            pstmtCheck = conn.prepareStatement(checkEnrollmentSQL);
            pstmtCheck.setInt(1, studentId);
            pstmtCheck.setInt(2, courseId);
            rs = pstmtCheck.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("cnt");
                if (count > 0) {
                    conn.rollback();
                    return 1; // 이미 신청한 강좌
                }
            }

            // 2. 강좌 정원이 마감되지 않았는지 확인
            pstmtCheckCapacity = conn.prepareStatement(checkCapacitySQL);
            pstmtCheckCapacity.setInt(1, classId);
            rs = pstmtCheckCapacity.executeQuery();
            if (rs.next()) {
                int capacity = rs.getInt("capacity");
                int enrolled = rs.getInt("enrolled");
                if (enrolled >= capacity) {
                    conn.rollback();
                    return 2; // 마감된 강좌
                }
            } else {
                conn.rollback();
                return -1; // 강좌 정보 없음
            }

            // 3. Enrollment 테이블에 삽입
            pstmtInsert = conn.prepareStatement(insertEnrollmentSQL);
            pstmtInsert.setInt(1, studentId);
            pstmtInsert.setInt(2, courseId);
            pstmtInsert.setInt(3, classId);
            int insertedRows = pstmtInsert.executeUpdate();
            if (insertedRows == 0) {
                conn.rollback();
                return -1; // 삽입 실패
            }

            // 4. Class 테이블의 enrolled 값 증가
            pstmtUpdateEnrolled = conn.prepareStatement(updateEnrolledSQL);
            pstmtUpdateEnrolled.setInt(1, classId);
            int updatedRows = pstmtUpdateEnrolled.executeUpdate();
            if (updatedRows == 0) {
                conn.rollback();
                return -1; // 업데이트 실패
            }

            conn.commit();
            return 0; // 성공

        } catch (SQLException e) {
            e.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
            }
            return -1; // 기타 오류
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmtCheck != null)
                    pstmtCheck.close();
                if (pstmtCheckCapacity != null)
                    pstmtCheckCapacity.close();
                if (pstmtInsert != null)
                    pstmtInsert.close();
                if (pstmtUpdateEnrolled != null)
                    pstmtUpdateEnrolled.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
            }
        }
    }

    // 학생이 강의를 취소하는 메소드
    // 반환 값:
    // 0: 취소 성공
    // 1: 신청하지 않은 강좌
    // -1: 취소 실패 (기타 오류)
    public int unenrollCourse(int studentId, int courseId, int classId) {
        String checkEnrollmentSQL = "SELECT COUNT(*) AS cnt FROM Enrollment WHERE student_id = ? AND course_id = ?";
        String deleteEnrollmentSQL = "DELETE FROM Enrollment WHERE student_id = ? AND course_id = ?";
        String updateEnrolledSQL = "UPDATE Class SET enrolled = enrolled - 1 WHERE class_id = ? AND enrolled > 0";

        Connection conn = null;
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtDelete = null;
        PreparedStatement pstmtUpdate = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 1. 신청한 강좌인지 확인
            pstmtCheck = conn.prepareStatement(checkEnrollmentSQL);
            pstmtCheck.setInt(1, studentId);
            pstmtCheck.setInt(2, courseId);
            rs = pstmtCheck.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("cnt");
                if (count == 0) {
                    conn.rollback();
                    return 1; // 신청하지 않은 강좌
                }
            }

            // 2. Enrollment 테이블에서 삭제
            pstmtDelete = conn.prepareStatement(deleteEnrollmentSQL);
            pstmtDelete.setInt(1, studentId);
            pstmtDelete.setInt(2, courseId);
            int deletedRows = pstmtDelete.executeUpdate();
            if (deletedRows == 0) {
                conn.rollback();
                return -1; // 삭제 실패
            }

            // 3. Class 테이블의 enrolled 값 감소
            pstmtUpdate = conn.prepareStatement(updateEnrolledSQL);
            pstmtUpdate.setInt(1, classId);
            int updatedRows = pstmtUpdate.executeUpdate();
            if (updatedRows == 0) {
                conn.rollback();
                return -1; // 감소 실패
            }

            conn.commit();
            return 0; // 취소 성공

        } catch (SQLException e) {
            e.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
            }
            return -1; // 기타 오류
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmtCheck != null)
                    pstmtCheck.close();
                if (pstmtDelete != null)
                    pstmtDelete.close();
                if (pstmtUpdate != null)
                    pstmtUpdate.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
            }
        }
    }

    // 클래스 정보 조회 메소드
    public Classes getClassById(int classId) {
        String sql = "SELECT c.class_id, c.course_id, co.course_name, co.department_id, d.department_name, " +
                     "co.classification, co.semester AS course_semester, co.credit, co.professor_name, " +
                     "c.room_no, c.day_of_week, c.start_time, c.end_time, c.capacity, c.enrolled, co.is_retake " +
                     "FROM Class c " +
                     "JOIN Course co ON c.course_id = co.course_id " +
                     "JOIN Department d ON co.department_id = d.department_id " +
                     "WHERE c.class_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, classId);
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
                    String dayOfWeek = rs.getString("day_of_week");
                    String startTime = rs.getString("start_time");
                    String endTime = rs.getString("end_time");
                    int capacity = rs.getInt("capacity");
                    int enrolled = rs.getInt("enrolled");
                    boolean isRetake = rs.getBoolean("is_retake");

                    return new Classes(classId, courseId, courseName, departmentId, departmentName,
                                      classification, courseSemester, credit, professorName,
                                      roomNo, dayOfWeek, startTime, endTime,
                                      capacity, enrolled, isRetake);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 실제 프로젝트에서는 로깅 프레임워크 사용 권장
        }

        return null;
    }

    // 기타 필요한 메소드...
}
