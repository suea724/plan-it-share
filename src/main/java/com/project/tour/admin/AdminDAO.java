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
import com.project.tour.dto.CityDTO;
import com.project.tour.dto.FoodCategoryDTO;
import com.project.tour.dto.FoodDTO;
import com.project.tour.dto.LodgingCategoryDTO;
import com.project.tour.dto.LodgingDTO;
import com.project.tour.dto.TourCategoryDTO;
import com.project.tour.dto.TourDTO;

public class AdminDAO {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pstat = null;
	Statement stat = null;

	//===================================================수아 DAO 시작==================================================
	
	/**
	 * 전체 숙소 리스트를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @param page
	 * @return ArrayList<AdminLodgingDTO>
	 */
	public ArrayList<AdminLodgingDTO> listLodging(int page) {
		
		try {
			
			int begin = (page - 1) * 20 + 1; 
			int end = page * 20;
			
			String sql = "select * from (select rownum as rnum, a.* from (select l.seq, c.name as city, lc.category, l.name, l.address, l.image, l.checkin, l.checkout  from tblLodging l inner join tblCity c on c.seq = l.cseq inner join tblLodgingCategory lc on l.lcseq = lc.seq order by l.seq desc) a) where rnum between ? and ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, begin);
			pstat.setInt(2, end);
			rs = pstat.executeQuery();
			
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

		} 
		
		return null;
	}

	/**
	 * 전체 관광명소 리스트를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @param page
	 * @return ArrayList<AdminTourDTO>
	 */
	public ArrayList<AdminTourDTO> listTour(int page) {
		try {
			
			int begin = (page - 1) * 20 + 1; 
			int end = page * 20;
			
			String sql = "select * from (select rownum as rnum, a.* from (select t.seq, c.name as city, tc.category, t.placename, t.address, t.image, t.open, t.close  from tblTour t inner join tblCity c on c.seq = t.cseq inner join tblTourCategory tc on t.tcseq = tc.seq order by t.seq desc) a) where rnum between ? and ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, begin);
			pstat.setInt(2, end);
			rs = pstat.executeQuery();
			
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
		} 
		
		return null;
	}

	/**
	 * 전체 음식점 리스트를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @param page
	 * @return ArrayList<AdminFoodDTO>
	 */
	public ArrayList<AdminFoodDTO> listFood(int page) {
		try {
			
			int begin = (page - 1) * 20 + 1; 
			int end = page * 20;
			
			String sql = "select * from (select rownum as rnum, a.* from (select f.seq, c.name as city, fc.category, f.name, f.address, f.image, f.open, f.close  from tblFood f inner join tblCity c on c.seq = f.cseq inner join tblFoodCategory fc on f.fcseq = fc.seq order by f.seq desc) a) where rnum between ? and ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, begin);
			pstat.setInt(2, end);
			rs = pstat.executeQuery();
			
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
		} 
		
		return null;
	}

	/**
	 * 전체 숙소 개수를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @return int
	 */
	public int getTotalLodgingCnt() {
		
		try {
			
			String sql = "select count(*) as cnt from tblLodging";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));
			}
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getTotalLodgingCnt");
			e.printStackTrace();

		} 
		
		return 0;
	}

	/**
	 * 전체 관광명소 개수를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @return int
	 */
	public int getTotalTourCnt() {
		
		try {
			
			String sql = "select count(*) as cnt from tblTour";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getTotalTourCnt");
			e.printStackTrace();

		} 
		return 0;
	}

	/**
	 * 전체 음식점 개수를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @return int
	 */
	public int getTotalFoodCnt() {
		
		try {
			
			String sql = "select count(*) as cnt from tblFood";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString("cnt"));
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getTotalFoodCnt");
			e.printStackTrace();

		} 

		return 0;
	}

	/**
	 * 지역 분류 리스트를 중복없이 가져오는 메서드
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getDistrincts() {
		try {
			
			String sql = "select distinct distrinct from tblCity";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			ArrayList<String> list = new ArrayList<>();
			
			while(rs.next()) {
				list.add(rs.getString("distrinct"));
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getDistirinct");
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * 모든 숙소 카테고리를 가져오는 메서드
	 * 
	 * @return ArrayList<LodgingCategoryDTO>
	 */
	public ArrayList<LodgingCategoryDTO> getLodgingCategory() {
		
		try {
			String sql = "select * from tblLodgingCategory";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			ArrayList<LodgingCategoryDTO> list = new ArrayList<>();
			
			while (rs.next()) {
				LodgingCategoryDTO dto = new LodgingCategoryDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setCategory(rs.getString("category"));
				
				list.add(dto);
			}
			return list;
		
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getLodgingCategory");
			e.printStackTrace();

		} 
		
		return null;
	}

	/**
	 * 모든 관광명소 카테고리를 가져오는 메서드
	 * 
	 * @return ArrayList<TourCategoryDTO>
	 */
	public ArrayList<TourCategoryDTO> getTourCategory() {
		
		try {
			
			String sql = "select * from tblTourCategory";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			ArrayList<TourCategoryDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				
				TourCategoryDTO dto = new TourCategoryDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setCategory(rs.getString("category"));
				
				list.add(dto);
			}
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getTourCategory");
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 모든 음식 카테고리를 가져오는 메서드
	 * 
	 * @return ArrayList<FoodCategoryDTO>
	 */
	public ArrayList<FoodCategoryDTO> getFoodCategory() {
		
		try {
			
			String sql = "select * from tblFoodCategory";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			ArrayList<FoodCategoryDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				
				FoodCategoryDTO dto = new FoodCategoryDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setCategory(rs.getString("category"));
				
				list.add(dto);
			}
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getFoodCategory");
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * 행정 구역에 해당하는 도시를 가져오는 메서드
	 * 
	 * @param distrinct
	 * @return ArrayList<CityDTO>
	 */
	public ArrayList<CityDTO> getCities(String distrinct) {

		try {
			
			String sql = "select * from tblCity where distrinct = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, distrinct);
			rs = pstat.executeQuery();
			
			ArrayList<CityDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				CityDTO dto = new CityDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				
				list.add(dto);
			}

			return list;
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getCities");
			e.printStackTrace();
		} 
		
		return null;
	}

	/**
	 * 숙소를 추가하는 메서드 
	 * 
	 * @param dto
	 * @return int
	 */
	public int addLodging(LodgingDTO dto) {
		
		try {
			
			if (dto.getImage() != null) {
				
				String sql = "insert into tblLodging values (seqLodging.nextVal, ?, ?, ?, ?, ?, ?, ?, null, null)";
				pstat = conn.prepareStatement(sql);
				
				pstat.setString(1, dto.getName());
				pstat.setString(2, dto.getAddress());
				pstat.setString(3, dto.getLcseq());
				pstat.setString(4, dto.getCseq());
				pstat.setString(5, dto.getImage());
				pstat.setString(6, dto.getCheckin());
				pstat.setString(7, dto.getCheckout());
				
			} else {
				String sql = "insert into tblLodging values (seqLodging.nextVal, ?, ?, ?, ?, default, ?, ?, null, null)";
				pstat = conn.prepareStatement(sql);
				
				pstat.setString(1, dto.getName());
				pstat.setString(2, dto.getAddress());
				pstat.setString(3, dto.getLcseq());
				pstat.setString(4, dto.getCseq());
				pstat.setString(5, dto.getCheckin());
				pstat.setString(6, dto.getCheckout());
			}
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} 
		return 0;
	}

	/**
	 * 음식점을 추가하는 메서드 
	 * 
	 * @param dto
	 * @return int
	 */
	public int addFood(FoodDTO dto) {
		try {
			
			if (dto.getImage() != null) {
				
				String sql = "insert into tblFood values (seqFood.nextVal, ?, ?, ?, ?, ?, ?, ?, null, null)";
				pstat = conn.prepareStatement(sql);
				
				pstat.setString(1, dto.getName());
				pstat.setString(2, dto.getAddress());
				pstat.setString(3, dto.getOpen());
				pstat.setString(4, dto.getClose());
				pstat.setString(5, dto.getFcseq());
				pstat.setString(6, dto.getCseq());
				pstat.setString(7, dto.getImage());
				
			} else {
				
				String sql = "insert into tblFood values (seqFood.nextVal, ?, ?, ?, ?, ?, ?, default, null, null)";
				pstat = conn.prepareStatement(sql);
				
				pstat.setString(1, dto.getName());
				pstat.setString(2, dto.getAddress());
				pstat.setString(3, dto.getOpen());
				pstat.setString(4, dto.getClose());
				pstat.setString(5, dto.getFcseq());
				pstat.setString(6, dto.getCseq());
			}
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} 
		return 0;
	}

	/**
	 * 관광명소 테이블에 추가하는 메서드
	 * 
	 * @param dto
	 * @return int
	 */
	public int addTour(TourDTO dto) {
		try {
			
			if (dto.getImage() != null) {
				
				String sql = "insert into tblTour values (seqTour.nextVal, ?, ?, ?, ?, ?, ?, ?, null, null)";
				pstat = conn.prepareStatement(sql);
				
				pstat.setString(1, dto.getPlaceName());
				pstat.setString(2, dto.getAddress());
				pstat.setString(3, dto.getOpen());
				pstat.setString(4, dto.getClose());
				pstat.setString(5, dto.getTcseq());
				pstat.setString(6, dto.getCseq());
				pstat.setString(7, dto.getImage());
				
			} else {
				String sql = "insert into tblTour values (seqTour.nextVal, ?, ?, ?, ?, ?, ?, default, null, null)";
				pstat = conn.prepareStatement(sql);
				
				pstat.setString(1, dto.getPlaceName());
				pstat.setString(2, dto.getAddress());
				pstat.setString(3, dto.getOpen());
				pstat.setString(4, dto.getClose());
				pstat.setString(5, dto.getTcseq());
				pstat.setString(6, dto.getCseq());
			}
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} 
		return 0;
	}

	public void delFoodReviews(String seq) {
		try {
			
			String sql = "delete from tblFoodReview where fseq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} 
	}
	
	public void delTourReviews(String seq) {
		try {
			
			String sql = "delete from tblTourReview where tseq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} 
	}
	
	public void delLodgingReviews(String seq) {
		try {
			
			String sql = "delete from tblLodgingReview where lseq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		}
	}

	public int delFood(String seq) {
		
		try {
			
			String sql = "delete from tblFood where seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} 
		
		return 0;
		
	}
	
	public int delTour(String seq) {
		
		try {
			
			String sql = "delete from tblTour where seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} 
		return 0;
		
	}
	
	public int delLodging(String seq) {
		
		try {
			
			String sql = "delete from tblLodging where seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} 
		return 0;
	}

	public void delLikeFood(String seq) {
		try {
			
			String sql = "delete from tblLikeFood where fseq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.delLikeFood");
			e.printStackTrace();
		} 
	}
	
	public void delLikeTour(String seq) {
		try {
			
			String sql = "delete from tblLikeTour where tseq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.delLikeFood");
			e.printStackTrace();
		} 
	}
	
	public void delLikeLodging(String seq) {
		try {
			
			String sql = "delete from tblLikeLodging where 'lseq' = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.delLikeFood");
			e.printStackTrace();
		} 
	}

	public FoodDTO getFood(String seq) {
		
		try {
			
			String sql = "select f.seq, f.name, f.address, f.open, f.close, f.fcseq, f.cseq, f.image, c.distrinct from tblFood f inner join tblCity c on f.cseq = c.seq where f.seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();

			FoodDTO dto = new FoodDTO();
			
			if (rs.next()) {
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setOpen(rs.getString("open"));
				dto.setClose(rs.getString("close"));
				dto.setCseq(rs.getString("cseq"));
				dto.setFcseq(rs.getString("fcseq"));
				dto.setImage(rs.getString("image"));
				dto.setDistrinct(rs.getString("distrinct"));
			}
			
			return dto;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.delLikeFood");
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public LodgingDTO getLodging(String seq) {
		
		try {
			
			String sql = "select l.seq, l.name, l.address, l.checkin, l.checkout, l.lcseq, l.cseq, l.image, c.distrinct from tblLodging l inner join tblCity c on l.cseq = c.seq where l.seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
			LodgingDTO dto = new LodgingDTO();
			
			if (rs.next()) {
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setCheckin(rs.getString("checkin"));
				dto.setCheckout(rs.getString("checkout"));
				dto.setCseq(rs.getString("cseq"));
				dto.setLcseq(rs.getString("lcseq"));
				dto.setImage(rs.getString("image"));
				dto.setDistrinct(rs.getString("distrinct"));
			}
			
			return dto;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.delLikeFood");
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public TourDTO getTour(String seq) {
		
		try {
			String sql = "select t.seq, t.placename, t.address, t.open, t.close, t.tcseq, t.cseq, t.image, c.distrinct from tblTour t inner join tblCity c on t.cseq = c.seq where t.seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
			TourDTO dto = new TourDTO();
			
			if (rs.next()) {
				
				dto.setSeq(rs.getString("seq"));
				dto.setPlaceName(rs.getString("placename"));
				dto.setAddress(rs.getString("address"));
				dto.setOpen(rs.getString("open"));
				dto.setClose(rs.getString("close"));
				dto.setCseq(rs.getString("cseq"));
				dto.setTcseq(rs.getString("tcseq"));
				dto.setImage(rs.getString("image"));
				dto.setDistrinct(rs.getString("distrinct"));
			}
			
			return dto;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.delLikeFood");
			e.printStackTrace();
		} 
		
		return null;
	}

	public int updateLodging(LodgingDTO dto) {
		try {
			
			String sql = "update tblLodging set name = ?, lcseq = ?, cseq = ?, checkin = ?, checkout = ?, address = ? where seq = ?";
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getLcseq());
			pstat.setString(3, dto.getCseq());
			pstat.setString(4, dto.getCheckin());
			pstat.setString(5, dto.getCheckout());
			pstat.setString(6, dto.getAddress());
			pstat.setString(7, dto.getSeq());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.updateLodging");
			e.printStackTrace();
		} 
		return 0;
	}

	public int updateFood(FoodDTO dto) {
		
		try {
			
			String sql = "update tblFood set name = ?, fcseq = ?, cseq = ?, open = ?, close = ?, address = ? where seq = ?";
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getFcseq());
			pstat.setString(3, dto.getCseq());
			pstat.setString(4, dto.getOpen());
			pstat.setString(5, dto.getClose());
			pstat.setString(6, dto.getAddress());
			pstat.setString(7, dto.getSeq());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.updateFood");
			e.printStackTrace();
		} 
		
		return 0;
	}

	public int updateTour(TourDTO dto) {
		
		try {
			
			String sql = "update tblTour set placename = ?, tcseq = ?, cseq = ?, open = ?, close = ?, address = ? where seq = ?";
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getPlaceName());
			pstat.setString(2, dto.getTcseq());
			pstat.setString(3, dto.getCseq());
			pstat.setString(4, dto.getOpen());
			pstat.setString(5, dto.getClose());
			pstat.setString(6, dto.getAddress());
			pstat.setString(7, dto.getSeq());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.updateLodging");
			e.printStackTrace();
		} 
		
		return 0;
	}
	
	//===================================================수아 DAO 끝==================================================
	

}
