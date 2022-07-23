package com.project.tour.city.lodging;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.tour.city.CityDAO;
import com.project.tour.city.food.FoodDAO;
import com.project.tour.city.food.Pagination;
import com.project.tour.dto.CityDTO;
import com.project.tour.dto.FoodDTO;
import com.project.tour.dto.LodgingDTO;

@WebServlet("/city/lodging.do")
public class LodgingList extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

      
	   String cseq = req.getParameter("cseq");
		String distinct = req.getParameter("distinct");
		String keyword = req.getParameter("keyword");
		
		int page = 1;
		
		if (req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		
		LodgingDAO ldao = new LodgingDAO();
		CityDAO cdao = new CityDAO();
		
		CityDTO dto = cdao.findCity(cseq);

		Pagination pagination = null;
		ArrayList<LodgingDTO> list = null;
		
		if (distinct == null || distinct.equals("") || keyword == null || keyword.equals("")) { // 검색어가 없을 경우 > 전체 리스트 출력
			
			 list = ldao.listLodging(cseq, page);
			 pagination = new Pagination(page, ldao.getTotalCount(cseq), 10, 10);
			
		} else { // 검색어가 있을 경우 > 검색 결과 리스트 출력
			
			if (distinct.equals("name")) { // 음식점명으로 검색하는 경우
				
				list = ldao.searchByName(cseq, keyword, page);
				pagination = new Pagination(page, ldao.getSearchByNameCount(cseq, keyword), 10, 10);
				req.setAttribute("distinct", "name");
				req.setAttribute("keyword", keyword);
				
			} else { // 카테고리로 검색하는 경우
				list = ldao.searchByCategory(cseq, keyword, page);
				pagination = new Pagination(page, ldao.getSearchByCategoryCount(cseq, keyword), 10, 10);
				req.setAttribute("distinct", "category");
				req.setAttribute("keyword", keyword);
			}
		}
		
		req.setAttribute("cseq", cseq);
		req.setAttribute("pagination", pagination);
		req.setAttribute("list", list);
		req.setAttribute("dto", dto);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/city/lodging/lodginglist.jsp");
		dispatcher.forward(req, resp);

   }

}
