// src/model/Classes.java
package Model;

public class Classes {
	public Classes() {
	}

	public Classes(Integer classId, int courseId, String courseName, int departmentId, String departmentName,
			String classification, String courseSemester, int credit, String professorName, String roomNo,
			String dayOfWeek, String startTime, String endTime, Integer capacity, Integer enrolled, boolean isRetake) {
		super();
		this.classId = classId;
		this.courseId = courseId;
		this.courseName = courseName;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.classification = classification;
		this.courseSemester = courseSemester;
		this.credit = credit;
		this.professorName = professorName;
		this.roomNo = roomNo;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
		this.enrolled = enrolled;
		this.isRetake = isRetake;
	}

	private Integer classId;
	private int courseId;
	private String courseName; // 추가
	private int departmentId; // 추가
	private String departmentName;
	private String classification; // 추가
	private String courseSemester; // 추가
	private int credit; // 추가
	private String professorName;
	private String semester;
	private String roomNo;
	private Integer capacity;
	private Integer enrolled = 0;
	private String dayOfWeek;
	private String startTime;
	private String endTime;
	private boolean isRetake;

	// Getters and Setters

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() { // 추가
		return courseName;
	}

	public void setCourseName(String courseName) { // 추가
		this.courseName = courseName;
	}

	public int getDepartmentId() { // 추가
		return departmentId;
	}

	public void setDepartmentId(int departmentId) { // 추가
		this.departmentId = departmentId;
	}

	public String getClassification() { // 추가
		return classification;
	}

	public void setClassification(String classification) { // 추가
		this.classification = classification;
	}

	public String getCourseSemester() { // 추가
		return courseSemester;
	}

	public void setCourseSemester(String courseSemester) { // 추가
		this.courseSemester = courseSemester;
	}

	public int getCredit() { // 추가
		return credit;
	}

	public void setCredit(int credit) { // 추가
		this.credit = credit;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(Integer enrolled) {
		this.enrolled = enrolled;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean getIsRetake() {
		return isRetake;
	}

	public void setIsRetake(boolean isRetake) {
		this.isRetake = isRetake;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
