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
	
	public ArrayList<FoodDTO> listFood(String cseq, int page) {
		
		int begin = (page - 1) * 10 + 1; 
		int end = page * 10;
		
		try {
			String sql = "select * from (select rownum as rnum,a.* from\r\n"
					+ "(select\r\n"
					+ "f.seq, f.name, f.address, f.open, f.close, f.image, f.cseq, fc.category, (select count(*) from tblLikeFood lf where lf.fseq = f.seq) as likecnt, (select count(*) from tblFoodReview fr where fr.fseq = f.seq) as reviewcnt, (select avg(star) from tblFoodReview fr where fr.fseq = f.seq) as reviewavg\r\n"
					+ "from tblFood f inner join tblCity c on f.cseq = c.seq\r\n"
					+ "        inner join tblFoodCategory fc on f.fcseq = fc.seq where c.seq = ? order by likecnt desc) a) where rnum between ? and ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cseq);
			pstmt.setInt(2, begin);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();
			
			ArrayList<FoodDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				FoodDTO dto = new FoodDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setImage(rs.getString("image"));
				dto.setCategory(rs.getString("category"));
				dto.setCseq(rs.getString("cseq"));
				dto.setLikeCnt(rs.getString("likecnt"));
				dto.setReviewCnt(rs.getString("reviewcnt"));
				dto.setReviewAvg(rs.getString("reviewavg"));
				
				
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

	public int getTotalCount(String cseq) {
		
		try {
			String sql = "select count(*) as cnt from tblFood where cseq = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cseq);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));	
			}
			
		} catch (Exception e) {
			System.out.println("FoodDAO.getTotalCount");
			e.printStackTrace();

		}
		
		return 0;
	}

}
