package time_table;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class ScheduleService {
	
	ScheduleDAO dao = new ScheduleDAO();
	
	public Subject loadSubject(String dbTableName) {
		Subject subject = new Subject();
		
		subject = dao.loadSubject(dbTableName);
		
		return subject;
	}
	
	public ArrayList<Subject> loadSubjectList(String dbTableName) {
		ArrayList<Subject> list = new ArrayList<>();
		
		dao.loadSubjectList(dbTableName);
		
		return list;
	}
	
//// TIME자료형을 PERIOD 구간용 INT로 계산
	public int calcPeriod(Date startTime, Date endTime) {
		
		int startHour = startTime.getHours();	// 시작시간(Hour)
		int endHour = endTime.getHours();		// 종료시간(Hour)
		
		switch(startHour) {
		case 9: return 0;	// 1교시
		case 10: return 1;
		case 11: return 2;
		case 12: return 3;
		case 13: return 4;
		case 14: return 5;
		case 15: return 6;
		case 16: return 7;
		case 17: return 8;
		case 18: return 9;	// 10교시
		default:
			return 999;
		}
	}
	

}
