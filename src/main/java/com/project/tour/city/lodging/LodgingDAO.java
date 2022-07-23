package com.project.tour.city.lodging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.project.tour.DBUtil;
import com.project.tour.dto.FoodDTO;
import com.project.tour.dto.FoodReviewDTO;
import com.project.tour.dto.LodgingDTO;
import com.project.tour.dto.LodgingLikeDTO;
import com.project.tour.dto.LodgingReviewDTO;


public class LodgingDAO {
	
	
	Connection conn = null;
	PreparedStatement pstat = null;
	Statement stat = null;
	ResultSet rs = null;
	
	public LodgingDAO() {
		conn = DBUtil.open();
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~서영 DAO 시작~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * 
	 * 지역선택 후 숙소를 선택하면 해당 숙소의 list를 가져오는 메소드
	 * 
	 * 
	 * @author 백서영
	 * @param cseq
	 * @return ArrayList<LodgingDTO>
	 */
	public ArrayList<LodgingDTO> lodginglist(String cseq) {
		try {
			
			String sql = "select * from vwLodging where rownum <= 10";
			
			stat = conn.createStatement();
			
			rs = stat.executeQuery(sql);
			
			ArrayList<LodgingDTO> list = new ArrayList<LodgingDTO>();
			
			while (rs.next()) {
				LodgingDTO dto = new LodgingDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setImage(rs.getString("image"));
				dto.setCheckin(rs.getString("checkin"));
				dto.setCheckout(rs.getString("checkout"));
				dto.setCategory(rs.getString("category"));
				dto.setLikecnt(rs.getString("likecnt"));
				dto.setReviewavg(rs.getString("reviewavg"));
				dto.setReviewcnt(rs.getString("reviewcnt"));
				list.add(dto);
				
				
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.lodginglist");
			e.printStackTrace();

		}
		return null;
	}

	
	/**
	 * 
	 * 
	 * 숙소를 선택하면 해당숙소의 정보를 담은 dto를 넘겨주는 메소드
	 * 
	 * @author 백서영
	 * @param seq
	 * @return LodgingDTO
	 */
	public LodgingDTO get(String seq) {
		try {
			String sql = "select * from vwLodging where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				LodgingDTO dto = new LodgingDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setImage(rs.getString("image"));
				dto.setCheckin(rs.getString("checkin"));
				dto.setCheckout(rs.getString("checkout"));
				dto.setCategory(rs.getString("category"));
				dto.setLikecnt(rs.getString("likecnt"));
				dto.setReviewavg(String.format("%.1f" ,rs.getDouble("reviewavg")));
				dto.setReviewcnt(rs.getString("reviewcnt"));

				return dto;
			}
			
			
			
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.get");
			e.printStackTrace();

		}
		return null;
	}


	/**
	 * 
	 * 
	 * 숙소번호에 해당하는 리뷰의 목록을 출력하는 메소드
	 * 
	 * @author 백서영
	 * @param seq
	 * @return ArrayList<LodgingReviewDTO>
	 */
	public ArrayList<LodgingReviewDTO> review(String seq) {
		try {
			
			
			String sql = " SELECT r.*, ROWNUM "
						+ " FROM ( "
						+ "    SELECT * "
						+ "    FROM tblLodgingReview "
						+ "    WHERE lseq = ? "
						+ "     ORDER BY seq DESC "
						+ "    ) r "
						+ "WHERE ROWNUM <= 10 ";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
			ArrayList<LodgingReviewDTO> list = new ArrayList<LodgingReviewDTO>();
			
			while (rs.next()) {
				LodgingReviewDTO dto = new LodgingReviewDTO();
				dto.setSeq(rs.getString("seq"));
				dto.setLseq(rs.getString("lseq"));
				dto.setContent(rs.getString("content"));
				dto.setImage(rs.getString("image"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setId(rs.getString("id"));
				dto.setStar(rs.getString("star"));
				
				list.add(dto);
				
			}

			return list;
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.review");
			e.printStackTrace();

		}
		return null;
	}
	
	
	/**
	 * 
	 * 
	 * 숙소번호에 해당하는 리뷰의 더보기 목록을 가져오는 메소드
	 * 
	 * @author 백서영
	 * @param seq
	 * @return ArrayList<LodgingReviewDTO>
	 */
	public ArrayList<LodgingReviewDTO> reviewMore(String lseq, String first, String last) {
		try {
			
			
			String sql = "SELECT * "
					   + "FROM ( "
					   + "    SELECT r.* "
					   + "          , ROW_NUMBER() OVER (ORDER BY seq DESC) no "
					   + "    FROM tblLodgingReview r "
					   + "    WHERE lseq = ? "
					   + "      "
					   + "    )  "
					   + "WHERE no BETWEEN ? AND ? ";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, lseq);
			pstat.setString(2, first);
			pstat.setString(3, last);
			
			rs = pstat.executeQuery();
			
			ArrayList<LodgingReviewDTO> list = new ArrayList<LodgingReviewDTO>();
			
			while (rs.next()) {
				LodgingReviewDTO dto = new LodgingReviewDTO();
				dto.setSeq(rs.getString("seq"));
				dto.setLseq(rs.getString("lseq"));
				dto.setContent(rs.getString("content"));
				dto.setImage(rs.getString("image"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setId(rs.getString("id"));
				dto.setStar(rs.getString("star"));
				
				list.add(dto);
				
			}

			return list;
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.review");
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 
	 * 리뷰를 작성한내용을 디비에 insert하는 메소드
	 * 
	 * @author 백서영
	 * @param dto
	 * @return int
	 */
	public int addComment(LodgingReviewDTO dto) {
		
		try {
			
			String sql = "insert into tblLodgingReview(seq, content, star, regdate, id, lseq, image) values(seqLodgingReview.nextVal, ?, ?, sysdate, ?, ?, ?)";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getContent());
			pstat.setString(2, dto.getStar());
			pstat.setString(3, dto.getId());
			pstat.setString(4, dto.getLseq());
			pstat.setString(5, dto.getImage());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.addComment");
			e.printStackTrace();

		}
		return 0;
	}


	/**
	 * 
	 * 리뷰를 삭제하는 메소드
	 * 
	 * @author 백서영
	 * @param seq
	 * @return int
	 */
	public int delComment(String seq) {
		try {
			
			String sql = "delete from tblLodgingReview where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);

			return pstat.executeUpdate();

			
		} catch (Exception e) {
			System.out.println("LodgingDAO.delComment");
			e.printStackTrace();

		}
		return 0;
	}


	/**
	 * 
	 * 
	 * 검색에 따른 숙소의 목록을 출력하는 메소드 
	 * 
	 * @author 백서영
	 * @param map
	 * @return ArrayList<LodgingDTO>
	 */
	public ArrayList<LodgingDTO> searchlist(HashMap<String, String> map) {
		try {
			

			String where = "";
			
			if (map.get("isSearch").equals("y")) {
				where = " where " + map.get("column")  + " like " + "\'%" + map.get("word") + "%\'";

			}
			
			
			String sql = "select * from vwLodging" + where;
			
			
			stat = conn.createStatement();

			rs = stat.executeQuery(sql);
			
			ArrayList<LodgingDTO> list = new ArrayList<LodgingDTO>();
			
			while (rs.next()) {
				LodgingDTO dto = new LodgingDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setLcseq(rs.getString("lcseq"));
				dto.setCseq(rs.getString("cseq"));
				dto.setImage(rs.getString("image"));
				dto.setCheckin(rs.getString("checkin"));
				dto.setCheckout(rs.getString("checkout"));
				dto.setCity(rs.getString("city"));
				dto.setCategory(rs.getString("category"));
				dto.setLikecnt(rs.getString("likecnt"));
				dto.setReviewavg(rs.getString("reviewavg"));
				dto.setReviewcnt(rs.getString("reviewcnt"));
				
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.searchlist");
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 
	 * 찜하기를 눌렀을경우 찜 목록에 추가 되고 한번더 눌렀을시 취소되는 메소드
	 * 
	 * @author 백서영
	 * @param dto
	 * @return int
	 */
	public int lodginglike(LodgingLikeDTO dto) {
		try {
			
			String sql = "select count(*) as cnt from tblLikeLodging where lseq = ? and id = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getLseq());
			pstat.setString(2, dto.getId());
			
			rs = pstat.executeQuery();
			
			
			int result = 0;
			
			if (rs.next()) {
				result = rs.getInt("cnt");
				
			}
			

			int state = 0;
			
			if (result == 1) {
				//참여 했을경우
				state = 2; 
				
			} else {
				//참여 안했을경우
				state = 1;
			}
			
			System.out.println("state:" + state);
			
			if (state == 1) {
				//처음 참여하는 경우 insert
				sql = "insert into tblLikeLodging (seq, id, lseq) values (seqLikeLodging.nextVal, ?, ?)";
				
				pstat = conn.prepareStatement(sql);
				
				pstat.setString(1, dto.getId());
				pstat.setString(2, dto.getLseq());
				
				return pstat.executeUpdate();
				
			} else if (state == 2) {
				//참여를 했는데 또 누르면 취소
				sql = "delete from tblLikeLodging where id = ? and lseq = ?";
				
				pstat = conn.prepareStatement(sql);
				
				pstat.setString(1, dto.getId());
				pstat.setString(2, dto.getLseq());
				
				return pstat.executeUpdate();
				
				
			}
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.lodginglike");
			e.printStackTrace();

		}
		return 0;
	}

	/**
	 * 
	 * 숙소에 찜한 횟수를 출력하기 위한 메소드
	 * 
	 * @author 백서영
	 * @param seq
	 * @return int
	 */
	public int getlodginglike(String seq) {
		try {
			String sql = "select likecnt from vwLodging where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.getlodginglike");
			e.printStackTrace();

		}
		return 0;
	}


	/**
	 * 
	 * 페이징처리를 하기위해 총 게시글 수를 얻어내기위한 메소드
	 * 
	 * @author 백서영
	 * @param map
	 * @return int
	 */
	public int getTotalCount(HashMap<String, String> map) {
		try {
			
			String where = "";
			
			if (map.get("isSearch").equals("y")) {
				where = String.format("where %s like '%%%s%%'"
										, map.get("column")
										, map.get("word"));
			}
			
			String sql = "select count(*) as cnt from vwLodging " + where;
			
			stat = conn.createStatement();
			
			rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				return rs.getInt("cnt");
				
			}
			
			
			
			
		} catch (Exception e) {
			System.out.println("BoardDAO.getTotalCount");
			e.printStackTrace();
		}
		
		
		return 0;
	}

	/**
	 * 
	 * 리뷰를 수정했을경우 업데이트 되는 메소드
	 * 
	 * @author 백서영
	 * @param dto
	 * @return int
	 */
	public int editComment(LodgingReviewDTO dto) {
		try {
			String sql = "update tblLodgingReview set content = ?, star = ?, regdate = sysdate where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getContent());
			pstat.setString(2, dto.getStar());
			pstat.setString(3, dto.getSeq());
			
			return pstat.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.editComment");
			e.printStackTrace();

		}
		return 0;
	}


	public int findLike(String seq, String id) {
		try {
			
			conn = DBUtil.open();
			
			String sql = "select count(*) as cnt from tblLikeLodging where lseq = ? and id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			pstat.setString(2, id);
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt")); 
			}
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.findLike");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
	}

	public int deleteLike(String seq, String id) {
		try {
			conn = DBUtil.open();
			String sql = "delete from tblLikeLodging where lseq = ? and id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			pstat.setString(2, id);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.deleteLike");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
	}
	
	public int insertLike(String seq, String id) {
		try {
			conn = DBUtil.open();
			
			String sql = "insert into tblLikeLodging values (seqLikeLodging.nextVal, ?, ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setString(2, seq);
			
			return pstat.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("LodgingDAO.deleteLike");
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
		
		try {
			String sql = "select * from tblFoodReview where fseq = ? order by seq desc";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
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

		} 
		return null;
	}
	
	
	public ArrayList<LodgingDTO> listLodging(String cseq, int page) {
		int begin = (page - 1) * 10 + 1; 
		int end = page * 10;
		
		try {
			String sql = "select * from (select rownum as rnum,a.* from\r\n"
					+ "(select\r\n"
					+ "l.seq, l.name, l.address, l.checkin, l.checkout, l.image, l.cseq, lc.category, (select count(*) from tblLikeLodging ll where ll.lseq = l.seq) as likecnt, (select count(*) from tblLodgingReview lr where lr.lseq = l.seq) as reviewcnt, (select avg(star) from tblLodgingReview lr where lr.lseq = l.seq) as reviewavg\r\n"
					+ "from tblLodging l inner join tblCity c on l.cseq = c.seq\r\n"
					+ "        inner join tblLodgingCategory lc on l.lcseq = lc.seq where c.seq = ? order by likecnt desc) a) where rnum between ? and ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cseq);
			pstat.setInt(2, begin);
			pstat.setInt(3, end);
			
			rs = pstat.executeQuery();
			
			ArrayList<LodgingDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				
				LodgingDTO dto = new LodgingDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setImage(rs.getString("image"));
				dto.setCategory(rs.getString("category"));
				dto.setCseq(rs.getString("cseq"));
				dto.setLikecnt(rs.getString("likecnt"));
				dto.setReviewcnt(rs.getString("reviewcnt"));
				dto.setReviewavg(String.format("%.1f" ,rs.getDouble("reviewavg")));
				
				
				list.add(dto);
			}
			return list;
			
			
		} catch (Exception e) {
			System.out.println("FoodDAO.listFood");
			e.printStackTrace();

		} 
		
		return null;
	}

	public int getTotalCount(String cseq) {
		try {
			String sql = "select count(*) as cnt from tblLodging where cseq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cseq);
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));	
			}
			
		} catch (Exception e) {
			System.out.println("FoodDAO.getTotalCount");
			e.printStackTrace();

		} 
		
		return 0;
	}

	
	public ArrayList<LodgingDTO> searchByName(String cseq, String keyword, int page) {
		
		try {
			
			int begin = (page - 1) * 10 + 1; 
			int end = page * 10;

			String sql = String.format("select * from (select rownum as rnum,a.* from\r\n"
					+ "(select\r\n"
					+ "l.seq, l.name, l.address, l.checkin, l.checkout, l.image, l.cseq, lc.category, (select count(*) from tblLikeLodging ll where ll.lseq = l.seq) as likecnt, (select count(*) from tblLodgingReview lr where lr.lseq = l.seq) as reviewcnt, (select avg(star) from tblLodgingReview lr where lr.lseq = l.seq) as reviewavg\r\n"
					+ "from tblLodging l inner join tblCity c on l.cseq = c.seq\r\n"
					+ "        inner join tblLodgingCategory lc on l.lcseq = lc.seq where c.seq = ? and l.name like '%%%s%%' order by likecnt desc) a) where rnum between ? and ?", keyword);
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, cseq);
			pstat.setInt(2, begin);
			pstat.setInt(3, end);
			
			rs = pstat.executeQuery();
			
			ArrayList<LodgingDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				LodgingDTO dto = new LodgingDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setImage(rs.getString("image"));
				dto.setCategory(rs.getString("category"));
				dto.setCseq(rs.getString("cseq"));
				dto.setLikecnt(rs.getString("likecnt"));
				dto.setReviewcnt(rs.getString("reviewcnt"));
				dto.setReviewavg(String.format("%.1f" ,rs.getDouble("reviewavg")));
				
				
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("FoodDAO.searchByName");
			e.printStackTrace();

		} 
		
		return null;
	}
	
	public ArrayList<LodgingDTO> searchByCategory(String cseq, String keyword, int page) {
		
			try {
			
			int begin = (page - 1) * 10 + 1; 
			int end = page * 10;
			
			String sql = String.format("select * from (select rownum as rnum,a.* from\r\n"
					+ "(select\r\n"
					+ "l.seq, l.name, l.address, l.checkin, l.checkout, l.image, l.cseq, lc.category, (select count(*) from tblLikeLodging ll where ll.lseq = l.seq) as likecnt, (select count(*) from tblLodgingReview lr where lr.lseq = l.seq) as reviewcnt, (select avg(star) from tblLodgingReview lr where lr.lseq = l.seq) as reviewavg\r\n"
					+ "from tblLodging l inner join tblCity c on l.cseq = c.seq\r\n"
					+ "        inner join tblLodgingCategory lc on l.lcseq = lc.seq where c.seq = ? and lc.category like '%%%s%%' order by likecnt desc) a) where rnum between ? and ?", keyword);
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, cseq);
			pstat.setInt(2, begin);
			pstat.setInt(3, end);
			
			rs = pstat.executeQuery();
			
			ArrayList<LodgingDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				LodgingDTO dto = new LodgingDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setImage(rs.getString("image"));
				dto.setCategory(rs.getString("category"));
				dto.setCseq(rs.getString("cseq"));
				dto.setLikecnt(rs.getString("likecnt"));
				dto.setReviewcnt(rs.getString("reviewcnt"));
				dto.setReviewavg(String.format("%.1f" ,rs.getDouble("reviewavg")));
				
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("FoodDAO.searchByCategory");
			e.printStackTrace();

		}
		
		return null;
	}

	public int getSearchByNameCount(String cseq, String keyword) {
		
		try {
			String sql = String.format("select count(*) as cnt from tblLodging where name like '%%s%' and cseq = ?", keyword);
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cseq);
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));	
			}
			
		} catch (Exception e) {
			System.out.println("FoodDAO.getSearchByNameCount");
			e.printStackTrace();

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
		
		try {
			String sql = String.format("select count(*) as cnt from tblLodging l inner join tblLodgingCategory lc on l.lcseq = lc.seq where category like '%%s%' and l.cseq = ?", keyword);
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cseq);
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));	
			}
			
		} catch (Exception e) {
			System.out.println("FoodDAO.getSearchByNameCount");
			e.printStackTrace();

		} 
		
		return 0;
		
	}


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~서영 DAO 끝~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


}
