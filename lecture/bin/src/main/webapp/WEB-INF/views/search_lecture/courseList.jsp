<!-- WebContent/WEB-INF/views/search_lecture/courseList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="Model.Course"%>
<%@ page import="Model.Classes"%>
<%@ page import="Model.Department"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 목록</title>
<style>
/* 스타일 예시 */
.logout {
	text-align: right;
	margin-bottom: 20px;
}

.message {
	text-align: center;
	margin-bottom: 20px;
}

.message.error {
	color: red;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 40px;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: center;
}

th {
	background-color: #f2f2f2;
}

.btn-disabled {
	background-color: #cccccc;
	border: none;
	color: #666666;
	padding: 5px 10px;
	cursor: not-allowed;
}

.btn-enroll, .btn-delete {
	padding: 5px 10px;
	cursor: pointer;
}

.btn-delete {
	background-color: #ff4d4d;
	color: white;
	border: none;
}
</style>
<script>
    // 페이지 로드 시 메시지에 따라 알림 메시지 표시
    <% 
        String message = (String) session.getAttribute("message");
        if (message != null) { 
    %>
        window.onload = function() {
            switch ("<%= message %>") {
                case "success":
                    alert('강의 신청이 완료되었습니다.');
                    break;
                case "already_enrolled":
                    alert('이미 신청한 강좌입니다.');
                    break;
                case "class_full":
                    alert('해당 강좌는 마감되었습니다.');
                    break;
                case "fail":
                    alert('강의 신청에 실패했습니다. 다시 시도해주세요.');
                    break;
                case "unenroll_success":
                    alert('강의 신청이 취소되었습니다.');
                    break;
                case "unenroll_fail":
                    alert('강의 취소에 실패했습니다. 다시 시도해주세요.');
                    break;
                default:
                    break;
            }
        }
    <%
            session.removeAttribute("message"); // 메시지 제거
        }
    %>
    
    // 이미 신청한 강좌의 경우, 버튼 클릭 시 알림 메시지 표시
    function showAlreadyEnrolledAlert() {
        alert('이미 신청한 강좌입니다.');
    }
</script>
</head>
<body>
	<div class="logout">
		<a href="<%=request.getContextPath()%>/logout">로그아웃</a>
	</div>

	<h1 style="text-align: center;">강의 목록</h1>

	<!-- 전체 강의 목록 -->
	<table>
		<thead>
			<tr>
				<th>신청</th>
				<th>Course ID</th>
				<th>강의명</th>
				<th>학과 이름</th> <!-- 헤더 수정 -->
				<th>분류</th>
				<th>학기</th>
				<th>학점</th>
				<th>교수명</th>
				<th>강의실</th>
				<th>시간</th>
				<th>정원</th>
				<th>신청 인원</th>
				<th>재수강 여부</th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Classes> allClasses = (List<Classes>) request.getAttribute("allClasses");
			Map<Integer, Integer> enrolledCourses = (Map<Integer, Integer>) request.getAttribute("enrolledCourses");
			Map<Integer, String> departmentsMap = (Map<Integer, String>) request.getAttribute("departmentsMap");

			if (allClasses != null && !allClasses.isEmpty()) {
				for (Classes classEntity : allClasses) {
					int courseId = classEntity.getCourseId();
					boolean isEnrolled = enrolledCourses.containsKey(courseId);
					boolean canEnroll = classEntity.getEnrolled() < classEntity.getCapacity();
			%>
			<tr>
				<td>
					<%
					if (isEnrolled) {
						// 이미 신청한 강좌인 경우, 버튼 클릭 시 알림 메시지 표시
					%>
					<button type="button" class="btn-enroll"
						onclick="showAlreadyEnrolledAlert();">신청</button>
					<%
					} else {
						if (canEnroll) {
					%>
					<form action="<%=request.getContextPath()%>/enroll" method="post">
						<input type="hidden" name="courseId" value="<%=courseId%>">
						<input type="hidden" name="classId"
							value="<%=classEntity.getClassId()%>">
						<button type="submit" class="btn-enroll">신청</button>
					</form>
					<%
						} else {
					%>
					<button type="button" class="btn-disabled">마감</button>
					<%
						}
					}
					%>
				</td>
				<td><%=courseId%></td>
				<td><%=classEntity.getCourseName()%></td>
				<td><%=departmentsMap.get(classEntity.getDepartmentId()) %></td> <!-- 학과 이름 표시 -->
				<td><%=classEntity.getClassification()%></td>
				<td><%=classEntity.getCourseSemester()%></td>
				<td><%=classEntity.getCredit()%></td>
				<td><%=classEntity.getProfessorName()%> 교수</td>
				<td><%=classEntity.getRoomNo()%></td>
				<td><%=classEntity.getDayOfWeek()%> <%=classEntity.getStartTime()%>
					~ <%=classEntity.getEndTime()%></td>
				<td><%=classEntity.getCapacity()%></td>
				<td><%=classEntity.getEnrolled()%></td>
				<td><%=classEntity.getIsRetake() ? "예" : "아니오"%></td>
			</tr>
			<%
				}
			} else {
			%>
			<tr>
				<td colspan="13">조회된 강의가 없습니다.</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<!-- 신청된 강의를 별도로 표시 -->
	<h2 style="text-align: center; margin-top: 40px;">신청된 강의</h2>
	<table>
		<thead>
			<tr>
				<th>삭제</th>
				<th>Course ID</th>
				<th>강의명</th>
				<th>학과 이름</th> <!-- 헤더 수정 -->
				<th>분류</th>
				<th>학기</th>
				<th>학점</th>
				<th>교수명</th>
				<th>강의실</th>
				<th>시간</th>
				<th>재수강 여부</th>
				<th>정원</th>
				<th>신청 인원</th>
			</tr>
		</thead>
		<tbody>
			<%
			if (enrolledCourses != null && !enrolledCourses.isEmpty()) {
				for (Map.Entry<Integer, Integer> entry : enrolledCourses.entrySet()) {
					int courseId = entry.getKey();
					int classId = entry.getValue();
					Classes enrolledClass = null;

					// enrolledCourses는 courseId와 classId의 맵
					for (Classes classEntity : allClasses) {
						if (classEntity.getCourseId() == courseId && classEntity.getClassId() == classId) {
							enrolledClass = classEntity;
							break;
						}
					}

					if (enrolledClass != null) {
			%>
			<tr>
				<td>
					<form action="<%=request.getContextPath()%>/unenroll" method="post"
						onsubmit="return confirm('정말로 신청을 취소하시겠습니까?');">
						<input type="hidden" name="courseId" value="<%=courseId%>">
						<input type="hidden" name="classId"
							value="<%=enrolledClass.getClassId()%>">
						<button type="submit" class="btn-delete">삭제</button>
					</form>
				</td>
				<td><%=enrolledClass.getCourseId()%></td>
				<td><%=enrolledClass.getCourseName()%></td>
				<td><%=departmentsMap.get(enrolledClass.getDepartmentId()) %></td> <!-- 학과 이름 표시 -->
				<td><%=enrolledClass.getClassification()%></td>
				<td><%=enrolledClass.getCourseSemester()%></td>
				<td><%=enrolledClass.getCredit()%></td>
				<td><%=enrolledClass.getProfessorName()%> 교수</td>
				<td><%=enrolledClass.getRoomNo()%></td>
				<td><%=enrolledClass.getDayOfWeek()%> <%=enrolledClass.getStartTime()%>
					~ <%=enrolledClass.getEndTime()%></td>
				<td><%=enrolledClass.getIsRetake() ? "예" : "아니오"%></td>
				<td><%=enrolledClass.getCapacity()%></td>
				<td><%=enrolledClass.getEnrolled()%></td>
			</tr>
			<%
					}
				}
			} else {
			%>
			<tr>
				<td colspan="13">신청된 강의가 없습니다.</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</body>
</html>
