package com.project.tour.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.project.tour.DBUtil;
import com.project.tour.dto.CommentDTO;
import com.project.tour.dto.DayDTO;
import com.project.tour.dto.PlaceDTO;
import com.project.tour.dto.PlanDTO;
import com.project.tour.dto.PlanUserDTO;

public class PlanDAO {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;

	public PlanDTO getPlan(String seq) {
		try {
			
			conn = DBUtil.open();
			
			String sql = "select p.*, (select count(*) from tblLikePlan lp where p.seq = lp.pseq) as likecnt, (select profile from tblUser u where u.id = p.author) as author_profile, c.name from tblPlan p inner join tblCity c on p.cseq = c.seq where p.seq = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			rs = pstmt.executeQuery();

			PlanDTO dto = new PlanDTO();
			
			if (rs.next()) {
				
				dto.setSeq(rs.getString("seq"));
				dto.setCseq(rs.getString("cseq"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcount(rs.getString("readcount"));
				dto.setStartdate(rs.getString("startdate"));
				dto.setEnddate(rs.getString("enddate"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setAuthor(rs.getString("author"));
				dto.setLikeCnt(rs.getString("likecnt"));
				dto.setAuthorProfile(rs.getString("author_profile"));
				dto.setCity(rs.getString("name"));
			}
			
			return dto;
			
		} catch (Exception e) {
			System.out.println("PlanDAO.getPlan");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
		return null;
	}
	
	public ArrayList<CommentDTO> listComment(String seq) {
		try {
			
			conn = DBUtil.open();
			
			String sql = "select * from tblComment where pseq = ? order by seq";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			rs = pstmt.executeQuery();
			
			ArrayList<CommentDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				
				CommentDTO dto = new CommentDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setId(rs.getString("id"));
				dto.setPseq(rs.getString("pseq"));
				
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("PlanDAO.listComment");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
		
		return null;
	}

	public void addFoodPlace(String seq, ArrayList<PlaceDTO> list) {
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "select d.day, df.regdate, f.name, f.address, f.image, f.open, f.close, f.lat, f.lng, fc.category from tblDaily d inner join tblDailyFood df on df.dseq = d.seq inner join tblFood f on df.fseq = f.seq inner join tblFoodCategory fc on f.fcseq =fc.seq where pseq = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				
				PlaceDTO dto = new PlaceDTO();
				
				dto.setDay(rs.getString("day"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setImage(rs.getString("image"));
				dto.setOpen(rs.getString("open"));
				dto.setClose(rs.getString("close"));
				dto.setLat(rs.getString("lat"));
				dto.setLng(rs.getString("lng"));
				dto.setCategory(rs.getString("category"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("PlanDAO.addFoodPlace");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
	}
	
	public void addTourPlace(String seq, ArrayList<PlaceDTO> list) {
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "select d.day, dt.regdate, t.placename, t.address, t.image, t.open, t.close, t.lat, t.lng, tc.category from tblDaily d inner join tblDailyTour dt on dt.dseq = d.seq inner join tblTour t on dt.tseq = t.seq inner join tblTourCategory tc on t.tcseq =tc.seq where pseq = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				PlaceDTO dto = new PlaceDTO();
				
				dto.setDay(rs.getString("day"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setName(rs.getString("placename"));
				dto.setAddress(rs.getString("address"));
				dto.setImage(rs.getString("image"));
				dto.setOpen(rs.getString("open"));
				dto.setClose(rs.getString("close"));
				dto.setLat(rs.getString("lat"));
				dto.setLng(rs.getString("lng"));
				dto.setCategory(rs.getString("category"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("PlanDAO.addTourPlace");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
	}
	
	public void addLodgingPlace(String seq, ArrayList<PlaceDTO> list) {
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "select d.day, dl.regdate, l.name, l.address, l.image, l.checkin, l.checkout, l.lat, l.lng, lc.category from tblDaily d inner join tblDailyLodging dl on dl.dseq = d.seq inner join tblLodging l on dl.lseq = l.seq inner join tblLodgingCategory lc on l.lcseq =lc.seq where pseq = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				PlaceDTO dto = new PlaceDTO();
				
				dto.setDay(rs.getString("day"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setImage(rs.getString("image"));
				dto.setOpen(rs.getString("checkIn"));
				dto.setClose(rs.getString("checkOut"));
				dto.setLat(rs.getString("lat"));
				dto.setLng(rs.getString("lng"));
				dto.setCategory(rs.getString("category"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("PlanDAO.addLodgingPlace");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
	}

	public void addReadCount(String seq) {
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "update tblPlan set readcount = readcount + 1 where seq = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.addTourPlace");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
	}
	
	public int findLike(String seq, String id) {
		try {
			
			conn = DBUtil.open();
			
			String sql = "select count(*) as cnt from tblLikePlan where pseq = ? and id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt")); 
			}
			
		} catch (Exception e) {
			System.out.println("FoodDAO.findLike");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
	}

	public int deleteLike(String seq, String id) {
		try {
			conn = DBUtil.open();
			String sql = "delete from tblLikePlan where pseq = ? and id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			pstmt.setString(2, id);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("FoodDAO.deleteLike");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
	}
	
	public int insertLike(String seq, String id) {
		try {
			conn = DBUtil.open();
			
			String sql = "insert into tblLikePlan values (seqLikePlan.nextVal, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, seq);
			
			return pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("FoodDAO.deleteLike");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
		
	}

	public int addComment(CommentDTO cdto) {
		try {
			conn = DBUtil.open();
			
			String sql = "insert into tblComment values (seqComment.nextVal, ?, default, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cdto.getContent());
			pstmt.setString(2, cdto.getId());
			pstmt.setString(3, cdto.getPseq());
			
			return pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("PlanDAO.addComment");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
	}

	public int delComment(String seq) {
		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblComment where seq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delComment");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
	}

	public int delDaily(String seq) {
		
		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblDaily where pseq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delDaily");
			e.printStackTrace();

		} finally {
			DBUtil.close();
		}
		return 0;
	}
	
	public int delComments(String seq) {
		
		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblComment where pseq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delComments");
			e.printStackTrace();
			
		} finally {
			DBUtil.close();
		}
		return 0;
	}
	
	public int delPlanUser(String seq) {
		
		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblPlanUser where pseq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delPlanUser");
			e.printStackTrace();
			
		} finally {
			DBUtil.close();
		}
		return 0;
	}
	
	public int delInvitation(String seq) {
		
		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblInvitation where pseq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delInvitation");
			e.printStackTrace();
			
		} finally {
			DBUtil.close();
		}
		return 0;
	}

	public int delLike(String seq) {

		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblLikePlan where pseq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delLike");
			e.printStackTrace();
			
		} finally {
			DBUtil.close();
		}
		return 0;
		
	}

	public int delPlan(String seq) {
		
		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblPlan where seq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delPlan");
			e.printStackTrace();
			
		} finally {
			DBUtil.close();
		}
		return 0;
		
	}

	public ArrayList<PlanUserDTO> getPlanUser(String seq) {
		
		try {
			conn = DBUtil.open();
			
			String sql = "select u.id, u.profile from tblplanuser pu inner join tblUser u on pu.id = u.id where pseq = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			rs = pstmt.executeQuery();
			
			ArrayList<PlanUserDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				PlanUserDTO dto = new PlanUserDTO();
				
				dto.setId(rs.getString("id"));
				dto.setProfile(rs.getString("profile"));
				
				list.add(dto);
			}
			return list;
			
			
		} catch (Exception e) {
			System.out.println("PlanDAO.getPlanUser");
			e.printStackTrace();
			
		} finally {
			DBUtil.close();
		}
		return null;
	}

	public int delDailyFood(String seq) {
		
		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblDailyFood where dseq in (select distinct dseq from tblDaily d inner join tblDailyFood df on d.seq = df.dseq where d.pseq = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delDailyFood");
			e.printStackTrace();
			
		} finally {
			DBUtil.close();
		}
		return 0;
	}
	
	public int delDailyLodging(String seq) {
		
		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblDailyLodging where dseq in (select distinct dseq from tblDaily d inner join tblDailyLodging dl on d.seq = dl.dseq where d.pseq = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delDailyLodging");
			e.printStackTrace();
			
		} finally {
			DBUtil.close();
		}
		return 0;
	}
	
	public int delDailyTour(String seq) {
		
		try {
			conn = DBUtil.open();
			
			String sql = "delete from tblDailyTour where dseq in (select distinct dseq from tblDaily d inner join tblDailyTour dt on d.seq = dt.dseq where d.pseq = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delDailyTour");
			e.printStackTrace();
			
		} finally {
			DBUtil.close();
		}
		return 0;
	}

	
}
