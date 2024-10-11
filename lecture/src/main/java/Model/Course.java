
package Model;

public class Course {
    private int courseId;
    private String courseName;
    private String departmentName;
    private String classification;
    private String semester;
    private int credit;

    // 생성자
    public Course(int courseId, String courseName, String departmentName, String classification, String semester, int credit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.departmentName = departmentName;
        this.classification = classification;
        this.semester = semester;	
        this.credit = credit;
    }

    // Getter 및 Setter
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
