package com.project.tour.city;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.tour.dto.CityDTO;
import com.project.tour.dto.DistrinctDTO;


@WebServlet("/city.do")
public class CityMainPage extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		String keyword = req.getParameter("keyword");
		
		CityDAO dao = new CityDAO();
		
		ArrayList<CityDTO> clist = dao.getAllCity();
		ArrayList<CityDTO> rlist = dao.findRecommendCity();
		ArrayList<DistrinctDTO> dlist = dao.findSubCity();
		
		req.setAttribute("clist", clist);
		req.setAttribute("rlist", rlist);
		req.setAttribute("dlist", dlist);
		
		if (keyword == null) { // 여행지 메인 페이지일 경우
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/city/city.jsp");
			dispatcher.forward(req, resp);
			
		} else { // 검색어가 입력될 경우
			
			String searchCseq = dao.getCitySeq(keyword);
			
			if (searchCseq == null) { // 검색 결과가 없으면
				req.setAttribute("searchError", "y");
				
				RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/city/city.jsp");
				dispatcher.forward(req, resp);
				
			} else { // 검색 결과가 있으면
				resp.sendRedirect("/planitshare/citydetail.do?cseq=" + searchCseq); // 상세 페이지로 이동
			}
		}

	}


}

