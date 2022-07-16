package com.project.tour.city.food;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.tour.city.CityDAO;
import com.project.tour.dto.CityDTO;
import com.project.tour.dto.FoodDTO;

@WebServlet("/city/food.do")
public class FoodList extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String cseq = req.getParameter("cseq");
		
		int page = 1;
		
		if (req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		
		CityDAO cdao = new CityDAO();
		CityDTO cdto = cdao.findCity(cseq);
		
		FoodDAO dao = new FoodDAO();
		ArrayList<FoodDTO> list = dao.listFood(cseq, page);
		
		Pagination pagination = new Pagination(page, dao.getTotalCount(cseq), 10, 10);
		
		req.setAttribute("pagination", pagination);
		req.setAttribute("list", list);
		req.setAttribute("cdto", cdto);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/city/food/foodlist.jsp");
		dispatcher.forward(req, resp);

	}

}
