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
	PreparedStatement pstmt = null;
	Statement stmt = null;

	/**
	 * 전체 숙소 리스트를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @param page
	 * @return ArrayList<AdminLodgingDTO>
	 */
	public ArrayList<AdminLodgingDTO> listLodging(int page) {
		
		try {
			conn = DBUtil.open();
			
			int begin = (page - 1) * 20 + 1; 
			int end = page * 20;
			
			String sql = "select * from (select rownum as rnum,  l.seq, c.name as city, lc.category, l.name, l.address, l.image, l.checkin, l.checkout  from tblLodging l inner join tblCity c on c.seq = l.cseq inner join tblLodgingCategory lc on l.lcseq = lc.seq order by l.seq desc) where rnum between ? and ?";
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

	/**
	 * 전체 관광명소 리스트를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @param page
	 * @return ArrayList<AdminTourDTO>
	 */
	public ArrayList<AdminTourDTO> listTour(int page) {
		try {
			
			conn = DBUtil.open();
			
			int begin = (page - 1) * 20 + 1; 
			int end = page * 20;
			
			String sql = "select * from (select rownum as rnum, t.seq, c.name as city, tc.category, t.placename, t.address, t.image, t.open, t.close  from tblTour t inner join tblCity c on c.seq = t.cseq inner join tblTourCategory tc on t.tcseq = tc.seq order by t.seq desc) where rnum between ? and ?";
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

	/**
	 * 전체 음식점 리스트를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @param page
	 * @return ArrayList<AdminFoodDTO>
	 */
	public ArrayList<AdminFoodDTO> listFood(int page) {
		try {
			
			conn = DBUtil.open();
			
			int begin = (page - 1) * 20 + 1; 
			int end = page * 20;
			
			String sql = "select * from (select rownum as rnum, f.seq, c.name as city, fc.category, f.name, f.address, f.image, f.open, f.close  from tblFood f inner join tblCity c on c.seq = f.cseq inner join tblFoodCategory fc on f.fcseq = fc.seq order by f.seq desc) where rnum between ? and ?";
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

	/**
	 * 전체 숙소 개수를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @return int
	 */
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

	/**
	 * 전체 관광명소 개수를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @return int
	 */
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

	/**
	 * 전체 음식점 개수를 가져오는 메서드
	 * 
	 * @author 안수아
	 * @return int
	 */
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

	/**
	 * 지역 분류 리스트를 중복없이 가져오는 메서드
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getDistrincts() {
		try {
			
			conn = DBUtil.open();
			
			String sql = "select distinct distrinct from tblCity";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			ArrayList<String> list = new ArrayList<>();
			
			while(rs.next()) {
				list.add(rs.getString("distrinct"));
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.getDistirinct");
			e.printStackTrace();
		} finally {
			DBUtil.close();
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
			conn = DBUtil.open();
			String sql = "select * from tblLodgingCategory";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
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

		} finally {
			DBUtil.close();
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
			conn = DBUtil.open();
			
			String sql = "select * from tblTourCategory";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
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
		} finally {
			DBUtil.close();
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
			conn = DBUtil.open();
			
			String sql = "select * from tblFoodCategory";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
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
		} finally {
			DBUtil.close();
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
			conn = DBUtil.open();
			
			String sql = "select * from tblCity where distrinct = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, distrinct);
			rs = pstmt.executeQuery();
			
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
		} finally {
			DBUtil.close();
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
			conn = DBUtil.open();
			
			if (dto.getImage() != null) {
				
				String sql = "insert into tblLodging values (seqLodging.nextVal, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getAddress());
				pstmt.setString(3, dto.getLcseq());
				pstmt.setString(4, dto.getCseq());
				pstmt.setString(5, dto.getImage());
				pstmt.setString(6, dto.getCheckIn());
				pstmt.setString(7, dto.getCheckOut());
				
			} else {
				String sql = "insert into tblLodging values (seqLodging.nextVal, ?, ?, ?, ?, default, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getAddress());
				pstmt.setString(3, dto.getLcseq());
				pstmt.setString(4, dto.getCseq());
				pstmt.setString(5, dto.getCheckIn());
				pstmt.setString(6, dto.getCheckOut());
			}
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} finally {
			DBUtil.close();
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
			conn = DBUtil.open();
			
			if (dto.getImage() != null) {
				
				String sql = "insert into tblFood values (seqFood.nextVal, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getAddress());
				pstmt.setString(3, dto.getOpen());
				pstmt.setString(4, dto.getClose());
				pstmt.setString(5, dto.getFcseq());
				pstmt.setString(6, dto.getCseq());
				pstmt.setString(7, dto.getImage());
				
			} else {
				
				String sql = "insert into tblFood values (seqFood.nextVal, ?, ?, ?, ?, ?, ?, default)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getAddress());
				pstmt.setString(3, dto.getOpen());
				pstmt.setString(4, dto.getClose());
				pstmt.setString(5, dto.getFcseq());
				pstmt.setString(6, dto.getCseq());
			}
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} finally {
			DBUtil.close();
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
			conn = DBUtil.open();
			
			if (dto.getImage() != null) {
				
				String sql = "insert into tblTour values (seqTour.nextVal, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getPlaceName());
				pstmt.setString(2, dto.getAddress());
				pstmt.setString(3, dto.getOpen());
				pstmt.setString(4, dto.getClose());
				pstmt.setString(5, dto.getTcseq());
				pstmt.setString(6, dto.getCseq());
				pstmt.setString(7, dto.getImage());
				
			} else {
				String sql = "insert into tblTour values (seqTour.nextVal, ?, ?, ?, ?, ?, ?, default)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getPlaceName());
				pstmt.setString(2, dto.getAddress());
				pstmt.setString(3, dto.getOpen());
				pstmt.setString(4, dto.getClose());
				pstmt.setString(5, dto.getTcseq());
				pstmt.setString(6, dto.getCseq());
			}
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addLodging");
			e.printStackTrace();
		} finally {
			DBUtil.close();
		}
		return 0;
	}
	

}
