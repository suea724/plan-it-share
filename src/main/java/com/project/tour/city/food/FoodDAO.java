package com.project.tour.city.food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.tour.DBUtil;
import com.project.tour.dto.FoodDTO;
import com.project.tour.dto.FoodReviewDTO;

public class FoodDAO {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public FoodDAO() {
		conn = DBUtil.open();
	}
	
	/**
	 * 
	 * 음식점 리스트를 출력하는 메서드
	 * 
	 * @author 안수아
	 * @param cseq
	 * @param page
	 * @return ArrayList<FoodDTO>
	 */
	public ArrayList<FoodDTO> listFood(String cseq, int page) {
		
		conn = DBUtil.open();
		
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
				dto.setReviewAvg(String.format("%.1f" ,rs.getDouble("reviewavg")));
				
				
				list.add(dto);
			}
			return list;
			
			
		} catch (Exception e) {
			System.out.println("FoodDAO.listFood");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return null;
	}

	/**
	 * 특정 음식점에 대한 정보를 출력하는 메서드
	 * 
	 * @author 안수아
	 * @param seq
	 * @return FoodDTO
	 */
	public FoodDTO findFood(String seq) {
		
		conn = DBUtil.open();
		
		try {
			String sql = "select f.seq, f.name, f.address, f.open, f.close, f.image, fc.category, (select count(*) from tblLikeFood lf where lf.fseq = f.seq) as likecnt, (select count(*) from tblFoodReview fr where fr.fseq = f.seq) as reviewcnt, (select avg(star) from tblFoodReview fr where fr.fseq = f.seq) as reviewavg\r\n"
					+ "from tblFood f inner join tblCity c on f.cseq = c.seq\r\n"
					+ "inner join tblFoodCategory fc on f.fcseq = fc.seq where f.seq = ?";
			
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
				dto.setReviewCnt(rs.getString("reviewcnt"));
				dto.setReviewAvg(String.format("%.1f" ,rs.getDouble("reviewavg")));
				dto.setLikeCnt(rs.getString("likecnt"));
			}
			return dto;
			
		} catch (Exception e) {
			System.out.println("FoodDAO.findFood");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return null;
	}

	/**
	 * 전체 음식점 개수를 출력하는 메서드
	 * 
	 * @author 안수아
	 * @param cseq
	 * @return int
	 */
	public int getTotalCount(String cseq) {
		
		conn = DBUtil.open();
		
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

		} finally {
			DBUtil.close();
		}
		
		return 0;
	}

	/**
	 * 특정 음식점에 대한 모든 리뷰를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @param seq
	 * @return ArrayList<FoodReviewDTO>
	 */
	public ArrayList<FoodReviewDTO> findReviews(String seq) {
		
		conn = DBUtil.open();
		
		try {
			String sql = "select * from tblFoodReview where fseq = ? order by seq desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			rs = pstmt.executeQuery();
			
			ArrayList<FoodReviewDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				FoodReviewDTO dto = new FoodReviewDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setStar(rs.getString("star"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setImage(rs.getString("image"));
				dto.setId(rs.getString("id"));
				dto.setFseq(rs.getString("fseq"));
				dto.setContent(rs.getString("content"));
				
				list.add(dto);
			}
			return list;
			
		} catch (Exception e) {
			System.out.println("FoodDAO.findReviews");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return null;
	}

	/**
	 * 특정 음식점에 리뷰를 추가하는 메서드
	 * 
	 * @author 안수아
	 * @param dto
	 * @return int
	 */
	public int addReview(FoodReviewDTO dto) {
		
		conn = DBUtil.open();
		
		try {
			
			if (dto.getImage() != null) {
				String sql = "insert into tblFoodReview values(seqFoodReview.nextVal, ?, ?, default, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getContent());
				pstmt.setString(2, dto.getStar());
				pstmt.setString(3, dto.getId());
				pstmt.setString(4, dto.getFseq());
				pstmt.setString(5, dto.getImage());
				
			} else {
				String sql = "insert into tblFoodReview values(seqFoodReview.nextVal, ?, ?, default, ?, ?, null)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getContent());
				pstmt.setString(2, dto.getStar());
				pstmt.setString(3, dto.getId());
				pstmt.setString(4, dto.getFseq());
			}
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("FoodDAO.addReview");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
	}

	/**
	 * 가장 최근에 작성된 리뷰를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @return FoodReviewDTO
	 */
	public FoodReviewDTO getLatestReivew() {
		
		conn = DBUtil.open();
		
		try {
			String sql = "select * from tblFoodReview where seq = (select max(seq) from tblFoodReview)";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			FoodReviewDTO dto = new FoodReviewDTO();
			
			if (rs.next()) {
				dto.setContent(rs.getString("content"));
				dto.setStar(rs.getString("star"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setId(rs.getString("id"));
				dto.setImage(rs.getString("image"));
			}
			return dto;
			
			
		} catch (Exception e) {
			System.out.println("FoodDAO.getLatestReivew");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
		
		return null;
	}

	/**
	 * 음식점명으로 검색한 결과 리스트를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @param cseq
	 * @param keyword
	 * @param page
	 * @return ArrayList<FoodDTO>
	 */
	public ArrayList<FoodDTO> searchByName(String cseq, String keyword, int page) {
		
		conn = DBUtil.open();
		
		try {
			
			int begin = (page - 1) * 10 + 1; 
			int end = page * 10;
			
			String sql = String.format("select * from (select rownum as rnum,a.* from\r\n"
					+ "(select\r\n"
					+ "f.seq, f.name, f.address, f.open, f.close, f.image, f.cseq, fc.category, (select count(*) from tblLikeFood lf where lf.fseq = f.seq) as likecnt, (select count(*) from tblFoodReview fr where fr.fseq = f.seq) as reviewcnt, (select avg(star) from tblFoodReview fr where fr.fseq = f.seq) as reviewavg\r\n"
					+ "from tblFood f inner join tblCity c on f.cseq = c.seq\r\n"
					+ "inner join tblFoodCategory fc on f.fcseq = fc.seq where c.seq = ? and f.name like '%%%s%%' order by likecnt desc) a) where rnum between ? and ?", keyword);
			
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
				dto.setReviewAvg(String.format("%.1f" ,rs.getDouble("reviewavg")));
				
				
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("FoodDAO.searchByName");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		
		return null;
	}
	
	/**
	 * 카테고리로 검색한 결과 리스트를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @param cseq
	 * @param keyword
	 * @param page
	 * @return ArrayList<FoodDTO>
	 */
	public ArrayList<FoodDTO> searchByCategory(String cseq, String keyword, int page) {
		
		conn = DBUtil.open();
		
			try {
			
			int begin = (page - 1) * 10 + 1; 
			int end = page * 10;
			
			String sql = String.format("select * from (select rownum as rnum,a.* from\r\n"
					+ "(select\r\n"
					+ "f.seq, f.name, f.address, f.open, f.close, f.image, f.cseq, fc.category, (select count(*) from tblLikeFood lf where lf.fseq = f.seq) as likecnt, (select count(*) from tblFoodReview fr where fr.fseq = f.seq) as reviewcnt, (select avg(star) from tblFoodReview fr where fr.fseq = f.seq) as reviewavg\r\n"
					+ "from tblFood f inner join tblCity c on f.cseq = c.seq\r\n"
					+ "        inner join tblFoodCategory fc on f.fcseq = fc.seq where c.seq = ? and fc.category like '%%%s%%' order by likecnt desc) a) where rnum between ? and ?", keyword);
			
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
				dto.setReviewAvg(String.format("%.1f" ,rs.getDouble("reviewavg")));
				
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("FoodDAO.searchByCategory");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		
		return null;
	}

	/**
	 * 음식점명으로 검색한 결과의 개수를 반환하는 메서드
	 * 
	 * @author 안수아
	 * @param cseq
	 * @param keyword
	 * @return int
	 */
	public int getSearchByNameCount(String cseq, String keyword) {
		
		conn = DBUtil.open();

		try {
			String sql = "select count(*) as cnt from (select rownum as rnum,a.* from\r\n"
					+ "(select\r\n"
					+ "f.seq, f.name, f.address, f.open, f.close, f.image, f.cseq, fc.category, (select count(*) from tblLikeFood lf where lf.fseq = f.seq) as likecnt, (select count(*) from tblFoodReview fr where fr.fseq = f.seq) as reviewcnt, (select avg(star) from tblFoodReview fr where fr.fseq = f.seq) as reviewavg\r\n"
					+ "from tblFood f inner join tblCity c on f.cseq = c.seq\r\n"
					+ "        inner join tblFoodCategory fc on f.fcseq = fc.seq where c.seq = ? and f.name like '%%?%%' order by likecnt desc) a)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cseq);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));	
			}
			
		} catch (Exception e) {
			System.out.println("FoodDAO.getSearchByNameCount");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		
		return 0;
		
	}
	
	/**
	 * 카테고리로 검색한 결과의 개수를 반환하는 메서드
	 * 
	 * @author 안수아
	 * @param cseq
	 * @param keyword
	 * @return int
	 */
	public int getSearchByCategoryCount(String cseq, String keyword) {
		
		conn = DBUtil.open();

		try {
			String sql = "select count(*) as cnt from (select rownum as rnum,a.* from\r\n"
					+ "(select\r\n"
					+ "f.seq, f.name, f.address, f.open, f.close, f.image, f.cseq, fc.category, (select count(*) from tblLikeFood lf where lf.fseq = f.seq) as likecnt, (select count(*) from tblFoodReview fr where fr.fseq = f.seq) as reviewcnt, (select avg(star) from tblFoodReview fr where fr.fseq = f.seq) as reviewavg\r\n"
					+ "from tblFood f inner join tblCity c on f.cseq = c.seq\r\n"
					+ "        inner join tblFoodCategory fc on f.fcseq = fc.seq where c.seq = ? and fc.category like '%%?%%' order by likecnt desc) a)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cseq);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));	
			}
			
		} catch (Exception e) {
			System.out.println("FoodDAO.getSearchByNameCount");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		
		return 0;
		
	}
	
}
