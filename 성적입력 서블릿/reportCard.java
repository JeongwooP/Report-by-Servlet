package exam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shop.Goods;

@WebServlet("/reportCard")
public class reportCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//get으로 받으면 세션삭제
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null) return;
		
		ArrayList<RecordOfGrade> rogList = (ArrayList<RecordOfGrade>)session.getAttribute("rogList");
		if(rogList == null) return;
		
		session.removeAttribute("rogList");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html><body><script type='text/javascript'>alert('세션이 삭제되었습니다. 입력창으로 돌아갑니다.');");
		out.println("(function(){location.href='exam/gradeInput.html'})();</script>");
		out.println("</body></html>");
		//response.sendRedirect("exam/gradeInput.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		int num = Integer.valueOf(request.getParameter("num"));
		String name = request.getParameter("name");
		int kor = Integer.valueOf(request.getParameter("kor"));
		int eng = Integer.valueOf(request.getParameter("eng"));
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		ArrayList<RecordOfGrade> rogList = (ArrayList<RecordOfGrade>)session.getAttribute("rogList");
		if(rogList == null)
			rogList = new ArrayList<>();
		
		//본래 response.sendRedirect("exam/gradeInput.html"); 로 인해 발생하는 오류를 예방하기 위해
		//flag를 통해 코딩했으나 location.href를 사용하여 불필요한 패턴이 되어버렸습니다...ㅎㅎ
		boolean flag = false;  
		for (RecordOfGrade i : rogList) {
			if(i.getNum() == num){
				flag = true;
				break;
			}
		}
		
		if(flag){
			out.println("<html><body><script type='text/javascript'>alert('중복된 번호입니다. 다시 입력하세요');");
			//out.println("history.back();</script>"); > exam/gradeInput.html에서 입력했던 값이 유지됨
			out.println("(function(){location.href='exam/gradeInput.html'})();</script>");
			out.println("</body></html>");
			//response.sendRedirect("exam/gradeInput.html"); > js로 알랏창 띄우는 것이 불가능함.
		}
		else{
			rogList.add(new RecordOfGrade(num, name, kor, eng));
			session.setAttribute("rogList", rogList);
			
			out.println("<html><body>");
			out.println("<h2>학생들 성적표</h2>");
			out.println("<br><table width='40%' border='1'>");
			out.println("<tr><th>번호</th><th>이름</th><th>국어</th><th>영어</th><th>총점</th></tr>");
			for (int i = 0; i < rogList.size(); i++) {
				RecordOfGrade rogGrade = rogList.get(i);
				out.println("<tr><td>" + rogGrade.getNum() + "</td>");
				out.println("<td>" + rogGrade.getName() + "</td>");
				out.println("<td>" + rogGrade.getKor() + "</td>");
				out.println("<td>" + rogGrade.getEng() + "</td>");
				out.println("<td>" + (rogGrade.getKor() + rogGrade.getEng()) + "</td></tr>");
			}
			out.println("</table>");
			out.println("<br>인원수 : " + rogList.size() + "<p/>");
			out.println("<br><a href = 'exam/gradeInput.html'>새로 입력</a>");
			out.println("&nbsp;<a href = 'reportCard'>세션삭제</a>"); // 세션 삭제 후 다시 불러오는 기능 추가 필요
			out.println("</body></html>");
			out.close();
		}
	}

}
