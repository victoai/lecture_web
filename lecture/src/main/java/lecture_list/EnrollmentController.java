// src/controller/EnrollmentController.java
package lecture_list;

import lecture_list.EnrollmentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/enroll")
public class EnrollmentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EnrollmentDAO enrollmentDAO;

    @Override
    public void init() throws ServletException {
        enrollmentDAO = new EnrollmentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 세션에서 student_id 가져오기
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student_id") == null) {
            // 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int studentId = (int) session.getAttribute("student_id");
        String courseIdStr = request.getParameter("courseId");
        String classIdStr = request.getParameter("classId");

        // 파라미터 값 로깅 (개발 중에만 사용, 배포 시 제거)
        System.out.println("Enrollment Attempt:");
        System.out.println("Student ID: " + studentId);
        System.out.println("Course ID: " + courseIdStr);
        System.out.println("Class ID: " + classIdStr);

        if (courseIdStr == null || classIdStr == null || courseIdStr.isEmpty() || classIdStr.isEmpty()) {
            session.setAttribute("message", "fail");
            response.sendRedirect(request.getContextPath() + "/courses");
            return;
        }

        int courseId;
        int classId;
        try {
            courseId = Integer.parseInt(courseIdStr);
            classId = Integer.parseInt(classIdStr);
        } catch (NumberFormatException e) {
            e.printStackTrace(); // 로깅 프레임워크 사용 권장
            session.setAttribute("message", "fail");
            response.sendRedirect(request.getContextPath() + "/courses");
            return;
        }

        int result = enrollmentDAO.enrollCourse(studentId, courseId, classId);
        switch (result) {
            case 0:
                session.setAttribute("message", "success");
                break;
            case 1:
                session.setAttribute("message", "already_enrolled");
                break;
            case 2:
                session.setAttribute("message", "class_full");
                break;
            default:
                session.setAttribute("message", "fail");
                break;
        }
        response.sendRedirect(request.getContextPath() + "/courses");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
