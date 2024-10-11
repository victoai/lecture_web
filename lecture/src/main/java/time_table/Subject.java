package time_table;

// 스케쥴표를 그리기용 과목정보
public class Subject {
	
	String classfication;		// 분류
	String courseName;			// 과정이름
	String roomNumber;			// 강의실
	String proffessorName;		// 교수이름
	
	public int day;				// 0 -> MONDAY
	public int period;			// 0 -> 1교시
	
	
	
	public int setDayInteger(String dayOfWeek) {
		switch(dayOfWeek) {
		case "MON":
			return 0;
		case "TUE":
			return 1;
		case "WED":
			return 2;
		case "THU":
			return 3;
		case "FRI":
			return 4;
		case "SAT":
			return 5;
		case "SUN":
			return 6;
		default:
			return 999;
		}
	}
	
	
	public Subject() {
		
	}
	
	public Subject(String classfication, String courseName, String roomNumber, String proffessorName) {
		this.classfication = classfication;
		this.courseName = courseName;
		this.roomNumber = roomNumber;
		this.proffessorName = proffessorName;
	}

	public String getClassfication() {
		return classfication;
	}

	public void setClassfication(String classfication) {
		this.classfication = classfication;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getProffessorName() {
		return proffessorName;
	}

	public void setProffessorName(String proffessorName) {
		this.proffessorName = proffessorName;
	}
	
}
