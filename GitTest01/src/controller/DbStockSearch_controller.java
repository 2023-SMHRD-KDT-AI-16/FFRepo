package controller;

import java.sql.SQLException;

public class DbStockSearch_controller extends DBcontroller{
	DBcontroller dbc = new DBcontroller();
	
	
	public int searchStock (String search_stock_name) {
		
		ArrayList<MemberDTO> dtoList = new ArrayList<MemberDTO>();
		//
//				getConn();
		//
//				// 전체회원 조회 -- select 문과 관련 (Ex05, Ex06참고)
//				// 동적로딩
//				try {
//					// sql통과 통로
//					String sql = "select * from member";
//					psmt = conn.prepareStatement(sql);
		//
//					// ?채우기 - ?가 없으면 생략
		//
//					// sql통과 하세요!
//					rs = psmt.executeQuery();
		//
//					// select 한줄의 데이터 확인 rs.next()
//					
//					while (rs.next()) {
//						String id = rs.getString(1);
//						String table_pw = rs.getString(2);
//						String name = rs.getString(3);
//						int age = rs.getInt(4);
//						
//						MemberDTO mdto= new MemberDTO(id, table_pw, name, age);
//						dtoList.add(mdto);
//						
//					}
//					return dtoList;
		//
//				} catch (SQLException e) {
//					e.printStackTrace();
//					return null;
//				} finally {
//					allClose();
//				}
		getConn();
		try {
			// sql통과 통로
			String sql = "delete from my_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql);

			// ?채우기 - ?가 없으면 생략
			psmt.setString(1, search_stock_name);
			// sql통과 하세요!
			int row = psmt.executeUpdate();
			return row;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			allClose();
		}
	}

}
