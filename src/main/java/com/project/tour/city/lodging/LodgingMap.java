package com.project.tour.city.lodging;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.tour.dto.FoodDTO;
import com.project.tour.dto.LodgingDTO;

@WebServlet("/city/lodging/view/map.do")
public class LodgingMap extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String seq = req.getParameter("seq");
		
		LodgingDAO dao = new LodgingDAO();
		LodgingDTO dto = dao.get(seq);
		
		req.setAttribute("dto", dto);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/city/lodging/lodgingmap.jsp");
		dispatcher.forward(req, resp);

	}

}

