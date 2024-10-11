// src/controller/CourseController.java
package lecture_list;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import Model.Classes;
import Model.Course;
import Model.Department;

@WebServlet("/courses")
public class CourseController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;
    private ClassDAO classDAO;
    private EnrollmentDAO enrollmentDAO;
    private DepartmentDAO departmentDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
        classDAO = new ClassDAO();
        enrollmentDAO = new EnrollmentDAO();
        departmentDAO = new DepartmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 세션에서 student_id 가져오기 (로그인 구현 필요)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student_id") == null) {
            // 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int studentId = (int) session.getAttribute("student_id");

        // 모든 강의 목록 조회
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("allCourses", courses);

        // 학생이 이미 신청한 강의의 course_id와 class_id 목록 조회
        Map<Integer, Integer> enrolledCourses = enrollmentDAO.getEnrolledCourses(studentId);
        request.setAttribute("enrolledCourses", enrolledCourses);

        // 모든 클래스 조회
        List<Classes> allClasses = classDAO.getAllClasses(studentId);
        request.setAttribute("allClasses", allClasses);
        
        // 모든 학과 조회 및 맵 생성
        List<Department> departments = departmentDAO.getAllDepartments();
        Map<Integer, String> departmentsMap = new HashMap<>();
        for (Department dept : departments) {
            departmentsMap.put(dept.getDepartmentId(), dept.getDepartmentName());
        }
        request.setAttribute("departmentsMap", departmentsMap);

        request.getRequestDispatcher("/WEB-INF/views/search_lecture/courseList.jsp").forward(request, response);
    }
}
