package com.project.tour.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.tour.DBUtil;
import com.project.tour.dto.CityDTO;
import com.project.tour.dto.CommentDTO;
import com.project.tour.dto.FoodCategoryDTO;
import com.project.tour.dto.FoodDTO;
import com.project.tour.dto.InvitationDTO;
import com.project.tour.dto.LodgingCategoryDTO;
import com.project.tour.dto.LodgingDTO;
import com.project.tour.dto.PlaceDTO;
import com.project.tour.dto.PlanDTO;
import com.project.tour.dto.PlanUserDTO;
import com.project.tour.dto.TourCategoryDTO;
import com.project.tour.dto.TourDTO;

public class PlanDAO {
	
	Connection conn = null;
	PreparedStatement pstat = null;
	Statement stat = null;
	ResultSet rs = null;

	// ===================================================수아 DAO 시작===============================================
	public PlanDTO getPlan(String seq) {
		try {
			
			conn = DBUtil.open();
			
			String sql = "select p.*, (select count(*) from tblLikePlan lp where p.seq = lp.pseq) as likecnt, (select profile from tblUser u where u.id = p.author) as author_profile, c.name from tblPlan p inner join tblCity c on p.cseq = c.seq where p.seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();

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
		} 
		return null;
	}
	
	public ArrayList<CommentDTO> listComment(String seq) {
		try {
			
			String sql = "select * from tblComment where pseq = ? order by seq";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
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
		} 
		
		return null;
	}

	public void addFoodPlace(String seq, ArrayList<PlaceDTO> list) {
		
		try {
			
			String sql = "select d.day, df.regdate, f.name, f.address, f.image, f.open, f.close, f.lat, f.lng, fc.category from tblDaily d inner join tblDailyFood df on df.dseq = d.seq inner join tblFood f on df.fseq = f.seq inner join tblFoodCategory fc on f.fcseq =fc.seq where pseq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();

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
		} 
	}
	
	public void addTourPlace(String seq, ArrayList<PlaceDTO> list) {
		
		try {
			
			String sql = "select d.day, dt.regdate, t.placename, t.address, t.image, t.open, t.close, t.lat, t.lng, tc.category from tblDaily d inner join tblDailyTour dt on dt.dseq = d.seq inner join tblTour t on dt.tseq = t.seq inner join tblTourCategory tc on t.tcseq =tc.seq where pseq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
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
		} 
	}
	
	public void addLodgingPlace(String seq, ArrayList<PlaceDTO> list) {
		
		try {
			
			String sql = "select d.day, dl.regdate, l.name, l.address, l.image, l.checkin, l.checkout, l.lat, l.lng, lc.category from tblDaily d inner join tblDailyLodging dl on dl.dseq = d.seq inner join tblLodging l on dl.lseq = l.seq inner join tblLodgingCategory lc on l.lcseq =lc.seq where pseq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
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
		} 
	}

	public void addReadCount(String seq) {
		
		try {
			
			String sql = "update tblPlan set readcount = readcount + 1 where seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.addTourPlace");
			e.printStackTrace();
		} 
	}
	
	public int findLike(String seq, String id) {
		try {
			
			String sql = "select count(*) as cnt from tblLikePlan where pseq = ? and id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			pstat.setString(2, id);
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt")); 
			}
			
		} catch (Exception e) {
			System.out.println("FoodDAO.findLike");
			e.printStackTrace();

		} 
		return 0;
	}

	public int deleteLike(String seq, String id) {
		try {
			String sql = "delete from tblLikePlan where pseq = ? and id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			pstat.setString(2, id);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("FoodDAO.deleteLike");
			e.printStackTrace();

		} 
		return 0;
	}
	
	public int insertLike(String seq, String id) {
		try {
			
			String sql = "insert into tblLikePlan values (seqLikePlan.nextVal, ?, ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setString(2, seq);
			
			return pstat.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("FoodDAO.deleteLike");
			e.printStackTrace();

		} 
		return 0;
		
	}

	public int addComment(CommentDTO cdto) {
		try {
			
			String sql = "insert into tblComment values (seqComment.nextVal, ?, default, ?, ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cdto.getContent());
			pstat.setString(2, cdto.getId());
			pstat.setString(3, cdto.getPseq());
			
			return pstat.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("PlanDAO.addComment");
			e.printStackTrace();

		} 
		return 0;
	}

	public int delComment(String seq) {
		try {
			
			String sql = "delete from tblComment where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delComment");
			e.printStackTrace();

		} 
		return 0;
	}

	public int delDaily(String seq) {
		
		try {
			
			String sql = "delete from tblDaily where pseq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delDaily");
			e.printStackTrace();

		} 
		return 0;
	}
	
	public int delComments(String seq) {
		
		try {
			
			String sql = "delete from tblComment where pseq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delComments");
			e.printStackTrace();
			
		} 
		return 0;
	}
	
	public int delPlanUser(String seq) {
		
		try {
			
			String sql = "delete from tblPlanUser where pseq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delPlanUser");
			e.printStackTrace();
			
		} 
		return 0;
	}
	
	public int delInvitation(String seq) {
		
		try {
			
			String sql = "delete from tblInvitation where pseq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delInvitation");
			e.printStackTrace();
			
		} 
		return 0;
	}

	public int delLike(String seq) {

		try {
			
			String sql = "delete from tblLikePlan where pseq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delLike");
			e.printStackTrace();
			
		} 
		return 0;
		
	}

	public int delPlan(String seq) {
		
		try {
			
			String sql = "delete from tblPlan where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delPlan");
			e.printStackTrace();
			
		} 
		return 0;
		
	}

	public ArrayList<PlanUserDTO> getPlanUser(String seq) {
		
		try {
			
			String sql = "select u.id, u.profile from tblplanuser pu inner join tblUser u on pu.id = u.id where pseq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
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
			
		} 
		return null;
	}

	public int delDailyFood(String seq) {
		
		try {
			
			String sql = "delete from tblDailyFood where dseq in (select distinct dseq from tblDaily d inner join tblDailyFood df on d.seq = df.dseq where d.pseq = ?)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delDailyFood");
			e.printStackTrace();
			
		}
		return 0;
	}
	
	public int delDailyLodging(String seq) {
		
		try {
			
			String sql = "delete from tblDailyLodging where dseq in (select distinct dseq from tblDaily d inner join tblDailyLodging dl on d.seq = dl.dseq where d.pseq = ?)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delDailyLodging");
			e.printStackTrace();
			
		}
		return 0;
	}
	
	public int delDailyTour(String seq) {
		
		try {
			
			String sql = "delete from tblDailyTour where dseq in (select distinct dseq from tblDaily d inner join tblDailyTour dt on d.seq = dt.dseq where d.pseq = ?)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.delDailyTour");
			e.printStackTrace();
			
		} 
		return 0;
	}

	public ArrayList<String> getIdList(String seq, String id) {
		
		try {
			
			
			String sql = "select * from tblUser where id not in (select u.id from tblUser u inner join tblPlanUser pu on u.id = pu.id where pseq = ?) and id != ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			pstat.setString(2, id);
			
			rs = pstat.executeQuery();
			
			ArrayList<String> list = new ArrayList<>();
			
			while(rs.next()) {
				list.add(rs.getString("id"));
			}
			return list;
			
		} catch (Exception e) {
			System.out.println("PlanDAO.getIdList");
			e.printStackTrace();

		} 
		
		return null;
	}

	public int makeInvite(InvitationDTO dto) {
		
		try {
			
			String sql = "insert into tblInvitation values (seqInvitation.nextval, ?, ?, ?, default)";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getPseq());
			pstat.setString(2, dto.getHost());
			pstat.setString(3, dto.getGuest());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("PlanDAO.makeInvite");
			e.printStackTrace();

		} 
		
		return 0;
	}
	
	// ===================================================수아 DAO 끝===============================================
	

	
}
