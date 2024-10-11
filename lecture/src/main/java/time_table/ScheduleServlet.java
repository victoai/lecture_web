package time_table;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/schedule")
public class ScheduleServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//// 스케쥴 표를 그리기 위한 데이터(MODEL) 불러오기 (Service > DAO)
		ScheduleService service = new ScheduleService();
		
		//스케쥴 결과용 LIST 선언
		String enrollDbName = "ENROLL";
		String cartDbName = "CART";
		String wishDbName = "WISH";
		ArrayList<Subject> list_enroll = new ArrayList<>();		// 등록과목
		ArrayList<Subject> list_cart = new ArrayList<>();		// 선택과목
		ArrayList<Subject> list_wish = new ArrayList<>();		// 희망과목
		
		list_enroll = service.loadSubjectList(enrollDbName);
		list_cart = service.loadSubjectList(cartDbName);
		list_wish = service.loadSubjectList(wishDbName);
		
		
		
		
//// 스케쥴 표 렌더링을 위한 데이터 저장
//// 렌더용 2차원배열 저장
		//JSP에서 FOR문을 통한 요일별 1~10교시 출력 준비?
		
		// A안
		int totalWeeks = 6;
		int totalPeriods = 10;
		Subject subjects[][] = new Subject[totalPeriods][totalWeeks];
		
		
		for(int i=0; i<totalWeeks; i++) {		//요일 (0:MON, 1:TUE, 2:WED, 3:THU, 4:FRI, 5:SAT)
			for(int j=0; j<totalPeriods; j++) {		//교시 (EX. 1교시, 2교시, ... 10교시)
				
				if(subjects[j][i] == null)
					continue;
			}
		}
		
		// B안
		for(Subject subject : list_enroll) {
			
		}
		
		
		//메모리 저장
		req.setAttribute("timeTable", subjects);
		


//// JSP 호출
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/timetable/schedule.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	public void test() {
	
		ScheduleService service = new ScheduleService();
		
		
	}

}
