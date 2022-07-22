package com.project.tour.user.mypage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.tour.dto.MyPageDTO;
import com.project.tour.dto.UserDTO;

@WebServlet("/mypage/myplan.do")
public class MyPlan extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		UserDTO dto = (UserDTO) session.getAttribute("auth");
		String id = dto.getId();
		
		HashMap<String, String> map = new HashMap<String, String>();

		MyPageDAO dao = new MyPageDAO();

		// 페이징
		int currentPage = 0;
		int begin = 0;
		int end = 0;
		int pageCnt = 10;

		int totalCnt = 0;
		int totalPage = 0;

		String page = req.getParameter("page");

		if (page == null || page == "")
			currentPage = 1;
		else
			currentPage = Integer.parseInt(page);

		begin = ((currentPage - 1) * pageCnt) + 1;
		end = begin + pageCnt - 1;

		map.put("begin", begin + "");
		map.put("end", end + "");
		
		
		// 내 일정
		ArrayList<MyPageDTO> mpList = dao.mpList(id, map);
		req.setAttribute("mpList", mpList);
		
		// 내 일정 총 갯수
		totalCnt = dao.getmpCnt(id);
		totalPage = (int) Math.ceil((double) totalCnt / pageCnt);
		
		String pagebar = "";

		int pageSize = 10; // 보이는 페이지 개수
		int n = 0; // 페이지 번호
		int loop = 0;

		pagebar = "";

		loop = 1;
		n = ((currentPage - 1) / pageSize) * pageSize + 1;

		pagebar += "<ul class=\"pagination\">";

		if (n == 1) {
			pagebar += String.format(" <li class=\"page-item\">\r\n"
					+ "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Previous\">\r\n"
					+ "		        <span aria-hidden=\"true\">&laquo;</span>\r\n" + "		      </a>\r\n"
					+ "		    </li> ");
		} else {
			pagebar += String.format(" <li class=\"page-item\">\r\n"
					+ "		      <a class=\"page-link\" href=\"/planitshare/mypage/myplan.do?page=%d\" aria-label=\"Previous\">\r\n"
					+ "		        <span aria-hidden=\"true\">&laquo;</span>\r\n" + "		      </a>\r\n"
					+ "		    </li> ", n - 1);
		}

		while (!(loop > pageSize || n > totalPage)) {

			if (n == currentPage) {
				pagebar += String.format(
						" <li class=\"page-item active\"><a class=\"page-link\" href=\"#!\">%d</a></li> ", n);
			} else {
				pagebar += String.format(
						" <li class=\"page-item\"><a class=\"page-link\" href=\"/planitshare/mypage/myplan.do?page=%d\">%d</a></li> ",
						n, n);
			}

			loop++;
			n++;
		}

		if (n > totalPage) {
			pagebar += String.format(" <li class=\"page-item\">\r\n"
					+ "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Next\">\r\n"
					+ "		        <span aria-hidden=\"true\">&raquo;</span>\r\n" + "		      </a>\r\n"
					+ "		    </li> ");
		} else {
			pagebar += String.format(" <li class=\"page-item\">\r\n"
					+ "		      <a class=\"page-link\" href=\"/planitshare/mypage/myplan.do?=%d\" aria-label=\"Next\">\r\n"
					+ "		        <span aria-hidden=\"true\">&raquo;</span>\r\n" + "		      </a>\r\n"
					+ "		    </li> ", n);
		}

		pagebar += "</ul>";

		req.setAttribute("list", mpList);

		req.setAttribute("pagebar", pagebar);
		
		
		
		

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/user/mypage/myplan.jsp");

		dispatcher.forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
