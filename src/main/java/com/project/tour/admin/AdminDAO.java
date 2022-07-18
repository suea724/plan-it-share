package com.project.tour.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.tour.DBUtil;
import com.project.tour.dto.AdminFoodDTO;
import com.project.tour.dto.AdminLodgingDTO;
import com.project.tour.dto.AdminTourDTO;

public class AdminDAO {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;

	public ArrayList<AdminLodgingDTO> listLodging(int page) {
		
		try {
			conn = DBUtil.open();
			
			int begin = (page - 1) * 10 + 1; 
			int end = page * 10;
			
			String sql = "select * from (select rownum as rnum,  l.seq, c.name as city, lc.category, l.name, l.address, l.image, l.checkin, l.checkout  from tblLodging l inner join tblCity c on c.seq = l.cseq inner join tblLodgingCategory lc on l.lcseq = lc.seq) where rnum between ? and ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, begin);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			ArrayList<AdminLodgingDTO> list = new ArrayList<AdminLodgingDTO>();
			
			while(rs.next()) {
				
				AdminLodgingDTO dto = new AdminLodgingDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setCity(rs.getString("city"));
				dto.setCategory(rs.getString("category"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				
				if (rs.getString("image") != null) {
					dto.setHasImage("y");
				} else {
					dto.setHasImage("n");
				}
				dto.setCheckIn(rs.getString("checkin"));
				dto.setCheckOut(rs.getString("checkout"));
				
				list.add(dto);
			}
			return list;
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.listLodging");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		
		return null;
	}

	public ArrayList<AdminTourDTO> listTour(int page) {
		try {
			
			conn = DBUtil.open();
			
			int begin = (page - 1) * 10 + 1; 
			int end = page * 10;
			
			String sql = "select * from (select rownum as rnum, t.seq, c.name as city, tc.category, t.placename, t.address, t.image, t.open, t.close  from tblTour t inner join tblCity c on c.seq = t.cseq inner join tblTourCategory tc on t.tcseq = tc.seq) where rnum between ? and ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, begin);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			ArrayList<AdminTourDTO> list = new ArrayList<AdminTourDTO>();
			
			while(rs.next()) {
				
				AdminTourDTO dto = new AdminTourDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setCity(rs.getString("city"));
				dto.setCategory(rs.getString("category"));
				dto.setPlacename(rs.getString("placename"));
				dto.setAddress(rs.getString("address"));
				
				if (rs.getString("image") != null) {
					dto.setHasImage("y");
				} else {
					dto.setHasImage("n");
				}
				dto.setOpen(rs.getString("open"));
				dto.setClose(rs.getString("close"));
				
				list.add(dto);
			}
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.listTour");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
		
		return null;
	}

	public ArrayList<AdminFoodDTO> listFood(int page) {
		try {
			
			conn = DBUtil.open();
			
			int begin = (page - 1) * 10 + 1; 
			int end = page * 10;
			
			String sql = "select * from (select rownum as rnum, f.seq, c.name as city, fc.category, f.name, f.address, f.image, f.open, f.close  from tblFood f inner join tblCity c on c.seq = f.cseq inner join tblFoodCategory fc on f.fcseq = fc.seq) where rnum between ? and ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, begin);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			ArrayList<AdminFoodDTO> list = new ArrayList<AdminFoodDTO>();
			
			while(rs.next()) {
				
				AdminFoodDTO dto = new AdminFoodDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setCity(rs.getString("city"));
				dto.setCategory(rs.getString("category"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				
				if (rs.getString("image") != null) {
					dto.setHasImage("y");
				} else {
					dto.setHasImage("n");
				}
				dto.setOpen(rs.getString("open"));
				dto.setClose(rs.getString("close"));
				
				list.add(dto);
			}
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.listFood");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
		
		return null;
	}

	public int getTotalLodgingCnt() {
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "select count(*) as cnt from tblLodging";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));
			}
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getTotalLodgingCnt");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		
		return 0;
	}

	public int getTotalTourCnt() {
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "select count(*) as cnt from tblTour";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getTotalTourCnt");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
	}

	public int getTotalFoodCnt() {
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "select count(*) as cnt from tblFood";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getTotalFoodCnt");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}

		return 0;
	}

}
