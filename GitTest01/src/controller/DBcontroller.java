package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.StockVO;
import model.UserVO;

public class DBcontroller {

	protected Connection conn;
	protected PreparedStatement psmt;
	protected ResultSet rs;

	// DB 연결 메소드
	protected void getConn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String user = "mp_21K_bigdata22_p1_2";
			String pw = "smhrd2";
			String url = "jdbc:oracle:thin:@project-db-campus.smhrd.com:1523:xe";
			conn = DriverManager.getConnection(url, user, pw);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (conn != null) {
			System.out.println("연결 성공");
		} else {
			System.out.println("연결 실패");
		}

	}

	// 통로 close 하는 메소드
	protected void allClose() {

		try {

			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (conn != null)
				conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 주식 판매 기능 메소드
	public int stockSale(String sale_stock_name) {

		getConn();

		try {
			// sql통과 통로
			String sql = "delete from my_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql);

			// ?채우기 - ?가 없으면 생략
			psmt.setString(1, sale_stock_name);
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

	// 1. 회원가입 기능
	
	public int insertMember(UserVO user) {

		getConn();

		try {
			// sql통과 통로
			String sql = "insert into my_user values(?,?,?,?)";
			psmt = conn.prepareStatement(sql);

			// ?채우기 - ?가 없으면 생략
			psmt.setString(1, user.getUser_id());
			psmt.setString(2, user.getUser_pw());
			psmt.setInt(3, user.getMy_money());
			psmt.setInt(4, user.getMy_yield());

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

	// 2. 로그인
	public int userLogin(String input_id, String input_pw) {
		ArrayList<String> idList = new ArrayList<String>();
		ArrayList<String> pwList = new ArrayList<String>();

		getConn();

		// 동적로딩
		try {
			// sql통과 통로
			String sql = "select user_id,user_pw from my_user";
			psmt = conn.prepareStatement(sql);

			// sql통과
			rs = psmt.executeQuery();

			// select 한줄의 데이터 확인 rs.next()
			
			while (rs.next()) {
				String id = rs.getString(1);
				String pw = rs.getString(2);
				idList.add(id);
				pwList.add(pw);
			}
			for(int i = 0; i<idList.size(); i++) {
				if(idList.get(i).equals(input_id)) {
					if(pwList.get(i).equals(input_pw)) {
						return 1;
					}else {
						return 0;
					}
				}else {
					return 0;
				}
			}			
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
		return 0;
		

	}
	
	// 4. 전체 주식 보기 메소드
	public ArrayList<StockVO> selectMember() {
		ArrayList<StockVO> svoList = new ArrayList<StockVO>();

		getConn();

		// 동적로딩
		try {
			// sql통과 통로
			String sql = "select * from all_stock";
			psmt = conn.prepareStatement(sql);

			// sql통과
			rs = psmt.executeQuery();

			// select 한줄의 데이터 확인 rs.next()
			
			while (rs.next()) {
				String stockName = rs.getString("stock_name");
				int nowPrice = rs.getInt("stock_now_price");
				int yesterdayPrice = rs.getInt("stock_yesterday_Price");
				int stockrate = rs.getInt("stock_rate");
				
				StockVO svo= new StockVO(stockName,yesterdayPrice, nowPrice, stockrate);
				svoList.add(svo);
				
			}
			return svoList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			allClose();
		}

<<<<<<< HEAD
	}
=======
	}

>>>>>>> branch 'master' of https://github.com/2023-SMHRD-KDT-AI-16/FFRepo.git

	// 5. 주식 매도 기능 메소드
	public int stockSale(String sale_stock_name, int count) {

		getConn();

		try {
			String sql = "select stock_count from my_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql);

			// ? 채우기
			psmt.setString(1, sale_stock_name);

			rs = psmt.executeQuery();

			int stockCount = 0; // 보유하고 있는 주식 수량 담을 변수
			while(rs.next()) { 
				stockCount = rs.getInt(1);
			}
			
			if(stockCount == count) {
				// sql 통과 통로
				String sql_2 = "delete from my_stock where stock_name = ?";
				psmt = conn.prepareStatement(sql_2);
				
				// ? 채우기
				psmt.setString(1, sale_stock_name);
				
				// sql통과
				int row = psmt.executeUpdate();
				
				return row;
			}else {
				String sql_2 = "update my_stock set stock_count = ? where stock_name = ?";
				psmt = conn.prepareStatement(sql_2);
				
				// ? 채우기
				psmt.setInt(1, (stockCount - count));
				psmt.setString(2, sale_stock_name);
				
				
				// sql통과
				int row = psmt.executeUpdate();
				return row;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
		return 0;
	}

	// 6. 주식 매수 기능 메소드
	public int stockBuy(String buy_stock_name, int count) {

		getConn();

		try {
			String sql = "select stock_count from my_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql);

			// ? 채우기
			psmt.setString(1, buy_stock_name);

			rs = psmt.executeQuery();

			int stockCount = 0; // 보유하고 있는 주식 수량 담을 변수
			while(rs.next()) { 
				stockCount = rs.getInt(1);
			}
			
			if(stockCount == 0) {// 매수 원하는 주식 가지고 있지 않을 때
				// sql 통과 통로
				String sql_2 = "insert into my_stock values(?,?,?,?,?)";
				psmt = conn.prepareStatement(sql_2);
				
				// ? 채우기
//				psmt.setString(1, );
//				psmt.setString(2, );
//				psmt.setString(3, buy_stock_name);
//				psmt.setInt(4, count);
//				psmt.setString(5, );
				
				// sql통과
				int row = psmt.executeUpdate();
				
				return row;
			}else {// 원하는 종목에 대한 주식을 이미 소유하고 있을 때
				String sql_2 = "update my_stock set stock_count = ? where stock_name = ?";
				psmt = conn.prepareStatement(sql_2);
				
				// ? 채우기
				psmt.setInt(1, (stockCount + count));
				
				psmt.setString(2, buy_stock_name);
				
				
				// sql통과
				int row = psmt.executeUpdate();
				return row;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
		return 0;
	}

<<<<<<< HEAD
//	// select 기능 메소드
//	public ArrayList<MemberDTO> selectMember() {
//		ArrayList<MemberDTO> dtoList = new ArrayList<MemberDTO>();
//
//		getConn();
//
//		// 전체회원 조회 -- select 문과 관련 (Ex05, Ex06참고)
//		// 동적로딩
//		try {
//			// sql통과 통로
//			String sql = "select * from member";
//			psmt = conn.prepareStatement(sql);
//
//			// ?채우기 - ?가 없으면 생략
//
//			// sql통과 하세요!
//			rs = psmt.executeQuery();
//
//			// select 한줄의 데이터 확인 rs.next()
//			
//			while (rs.next()) {
//				String id = rs.getString(1);
//				String table_pw = rs.getString(2);
//				String name = rs.getString(3);
//				int age = rs.getInt(4);
//				
//				MemberDTO mdto= new MemberDTO(id, table_pw, name, age);
//				dtoList.add(mdto);
//				
//			}
//			return dtoList;
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			allClose();
//		}
//
//	}
//
//	public ArrayList<MemberDTO> selectSearchMember(String family_name) {
//		ArrayList<MemberDTO> dtoList = new ArrayList<MemberDTO>();
//		// 동적로딩
//		// DB 연결
//		getConn();
//		
//		try {
//			String sql = "select * from member where name like ? ";
//			psmt = conn.prepareStatement(sql);
//			
//			// ? 채워주기 -- like문 같은 경우 %가 필요
//			// % 넣는 방법 2가지 존재
//			// 1번 방법이 간단
//			// psmt.set타입(index, "%"+변수+"%")
//			psmt.setString(1, family_name + "%");
//			// 2번째 방법
//			// sql문 내에 % 입력
//			// "select * from member where name '%' || ? || '%'"
//			
//			// sql문 실행
//			rs = psmt.executeQuery();
//			
//			while(rs.next()) {
//				String select_id = rs.getString(1);
//				String select_pw = rs.getString(2);
//				String select_name = rs.getString(3);
//				int select_age = rs.getInt(4);
//				
//				MemberDTO mdto = new MemberDTO(select_id, select_pw, select_name, select_age);
//				
//				dtoList.add(mdto);
//			}
//			
//			return dtoList;
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			
//			
//			
//		}finally {
//			allClose();
//		}return null;
//		
=======

>>>>>>> branch 'master' of https://github.com/2023-SMHRD-KDT-AI-16/FFRepo.git


}
