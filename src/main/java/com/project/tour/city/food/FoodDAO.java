package com.project.tour.city.food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.tour.DBUtil;
import com.project.tour.dto.FoodDTO;

public class FoodDAO {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public FoodDAO() {
		conn = DBUtil.open();
	}
	
	public ArrayList<FoodDTO> listFood(String cseq) {
		try {
			String sql = "select\r\n"
					+ "f.seq, f.name, f.address, f.image, fc.category, c.seq as cseq, c.name as city\r\n"
					+ "from tblFood f inner join tblCity c on f.cseq = c.seq \r\n"
					+ "    inner join tblFoodCategory fc on f.fcseq = fc.seq where cseq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cseq);
			rs = pstmt.executeQuery();
			
			ArrayList<FoodDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				FoodDTO dto = new FoodDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setCategory(rs.getString("category"));
				dto.setCseq(rs.getString("cseq"));
				dto.setCity(rs.getString("city"));
				
				list.add(dto);
			}
			return list;
			
			
		} catch (Exception e) {
			System.out.println("FoodDAO.listFood");
			e.printStackTrace();

		}
		return null;
	}

	public FoodDTO findFood(String seq) {
		try {
			String sql = "select\r\n"
					+ "f.seq, f.name, f.address, f.open, f.close, f.image, fc.category\r\n"
					+ "from tblFood f inner join tblFoodCategory fc on f.fcseq = fc.seq where f.seq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			rs = pstmt.executeQuery();
			
			FoodDTO dto = new FoodDTO();
			
			while(rs.next()) {
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setOpen(rs.getString("open"));
				dto.setClose(rs.getString("close"));
				dto.setImage(rs.getString("image"));
				dto.setCategory(rs.getString("category"));
			}
			return dto;
			
		} catch (Exception e) {
			System.out.println("FoodDAO.findFood");
			e.printStackTrace();

		}
		return null;
	}

}
