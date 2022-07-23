package com.project.tour.plan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.tour.dto.CityDTO;
import com.project.tour.dto.PlanDTO;

@WebServlet("/plan.do")
public class PlanList extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String column = req.getParameter("column");
		String word = req.getParameter("word");
		String isSearch = "n";
		
		//검색일 경우만 isSearch가 y
		if ((column == null || word == null)
				|| (column == "" || word == "")) {
			isSearch = "n";
		} else {
			isSearch = "y";
		}
		
		PlanDAO dao = new PlanDAO();
		
		//페이징
		int nowPage = 0; //현재 페이지 번호(=page)
		int begin = 0; //SQL조건 -시작값 ex) 1
		int end = 0; //SQL조건 -끝값 ex) 10
		int pageSize = 9; //한페이지 당 출력할 게시물 수
		
		int totalCount = 0; //총 게시물 수 
		int totalPage = 0; //총 페이지 수
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("column", column);
		map.put("word", word);
		map.put("isSearch", isSearch);
		
		//페이지라는 데이터를 넘길 것이므로 
		String page = req.getParameter("page");
		
		if (page == null || page == "") nowPage = 1; //페이지를 안넘기면 무조건 1페이지
		else nowPage = Integer.parseInt(page); 
		
		begin = ((nowPage -1) * pageSize) + 1;
		end = begin + pageSize - 1;
		
		map.put("begin", begin + ""); 
		map.put("end", end + "");
		
		//총 페이지 수 구하기
		totalCount = dao.getTotalCount(map);
		totalPage = (int)Math.ceil((double)totalCount/ pageSize);
		
		String pagebar = "";
		
		int blockSize = 10; //한번에 보여질 페이지 개수
		int n = 0;			//페이지 번호
		int loop = 0;		//루프변수
		
		pagebar = "";
		
		loop = 1;
		n = ((nowPage - 1) / blockSize) * blockSize + 1;
		
		
		ArrayList<PlanDTO> list = null;
		
		pagebar += "<ul class=\"pagination\">";
		
		if (isSearch.equals("y")) { // 검색인 경우
			
			if (n == 1) {
				
				pagebar += String.format("<li class=\"page-item \">\r\n"
						+ "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Previous\">\r\n"
						+ "		        <span aria-hidden=\"true\">&laquo;</span>\r\n"
						+ "		      </a>\r\n"
						+ "		    </li>");
					
				} else {
				
				pagebar += String.format("<<li class=\"page-item\">\r\n"
						+ "		      <a class=\"page-link\" href=\"/planitshare/plan.do?page=%d&column=%s&word=%s\" aria-label=\"Previous\">\r\n"
						+ "		        <span aria-hidden=\"true\">&laquo;</span>\r\n"
						+ "		      </a>\r\n"
						+ "		    </li> ", n -1, column, word);
				
				}
			
			while (!(loop > blockSize || n > totalPage )) {
				
					if (n == nowPage) {
						pagebar += String.format("<li class=\"page-item active\"><a class=\"page-link\" href=\"#!\">%d</a></li> ", n);
						
					} else {
						pagebar += String.format("<li class=\"page-item\"><a class=\"page-link\" href=\"/planitshare/plan.do?page=%d&column=%s&word=%s\">%d</a></li> ", n, column, word, n);
						
					}
					
					loop++;
					n++;
				
				}
			
				if (n > totalPage) {
					pagebar += String.format("<li class=\"page-item\">\r\n"
							+ "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Next\">\r\n"
							+ "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
							+ "		      </a>\r\n"
							+ "		    </li>");
				
				} else {
					
					pagebar += String.format("<li class=\"page-item\">\r\n"
							+ "		      <a class=\"page-link\" href=\"/planitshare/plan.do?page=%d&column=%s&word=%s\" aria-label=\"Next\">\r\n"
							+ "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
							+ "		      </a>\r\n"
							+ "		    </li>", n, column, word);
				}
				
				pagebar += "</ul>";
			
			list = dao.planSearch(map);

		} else { // 검색이 아닌 경우
			
			
			if (n == 1) {
				
				pagebar += String.format("<li class=\"page-item \">\r\n"
						+ "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Previous\">\r\n"
						+ "		        <span aria-hidden=\"true\">&laquo;</span>\r\n"
						+ "		      </a>\r\n"
						+ "		    </li>");
					
				} else {
				
				pagebar += String.format("<<li class=\"page-item\">\r\n"
						+ "		      <a class=\"page-link\" href=\"/planitshare/plan.do?page=%d\" aria-label=\"Previous\">\r\n"
						+ "		        <span aria-hidden=\"true\">&laquo;</span>\r\n"
						+ "		      </a>\r\n"
						+ "		    </li> ", n -1);
				
				}
			
			while (!(loop > blockSize || n > totalPage )) {
				
					if (n == nowPage) {
						pagebar += String.format("<li class=\"page-item active\"><a class=\"page-link\" href=\"#!\">%d</a></li> ", n);
						
					} else {
						pagebar += String.format("<li class=\"page-item\"><a class=\"page-link\" href=\"/planitshare/plan.do?page=%d\">%d</a></li> ", n, n);
						
					}
					
					loop++;
					n++;
				
				}
			
				if (n > totalPage) {
					pagebar += String.format("<li class=\"page-item\">\r\n"
							+ "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Next\">\r\n"
							+ "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
							+ "		      </a>\r\n"
							+ "		    </li>");
				
				} else {
					
					pagebar += String.format("<li class=\"page-item\">\r\n"
							+ "		      <a class=\"page-link\" href=\"/planitshare/plan.do?page=%d\" aria-label=\"Next\">\r\n"
							+ "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
							+ "		      </a>\r\n"
							+ "		    </li>", n);
				}
				
				pagebar += "</ul>";
			
			list = dao.getlist(map);
		}
		
		
		for (PlanDTO dto : list) {
			if (dto.getTitle().length() >= 20) {
				dto.setTitle(dto.getTitle().substring(0, 20) + "...");
			}
		}
		
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("nowPage", nowPage);
		req.setAttribute("pagebar", pagebar);
		req.setAttribute("map", map);
		req.setAttribute("list", list);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/plan/planlist.jsp");
		dispatcher.forward(req, resp);

	}

}

