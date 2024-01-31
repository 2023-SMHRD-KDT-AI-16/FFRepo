package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.UserVO;

public class DBcontroller {

	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;

	// DB 연결 메소드
	private void getConn() {
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
	private void allClose() {

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

	// 기능 메소드
	public int updateMember(String update_id, String update_pw) {

		getConn();

		try {
			// sql통과 통로
			String sql = "update member set pw = ? where id = ?";
			psmt = conn.prepareStatement(sql);

			// ?채우기 - ?가 없으면 생략
			psmt.setString(1, update_pw);
			psmt.setString(2, update_id);

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

	// 2. 로그인 기능 메소드
	public void userLogin(String user_id, String user_pw) {
		ArrayList<UserVO> logList = new ArrayList<UserVO>();

		getConn();

		// 동적로딩
		try {
			// sql통과 통로
			String sql = "select * from my_user";
			psmt = conn.prepareStatement(sql);

			// sql통과
			rs = psmt.executeQuery();

			// select 한줄의 데이터 확인 rs.next()
			
			while (rs.next()) {
				String id = rs.getString(1);
				String table_pw = rs.getString(2);
				int money = rs.getInt(3);
				int yield = rs.getInt(4);
				
				UserVO UV= new UserVO(id, table_pw, money, yield);
				logList.add(UV);
			}
			
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();
		}

	}
	
	
	
	
	
	
	// 주식 구매 기능 메소드
//	public void byStock(StockVO stockVO) {
//
//		getConn();
//
//		try {
//			// sql통과 통로
//			String sql = "insert into member values(?,?,?,?)";
//			psmt = conn.prepareStatement(sql);
//
//			// ?채우기 - ?가 없으면 생략
//			psmt.setString(1, mdto.getId());
//			psmt.setString(2, mdto.getPw());
//			psmt.setString(3, mdto.getName());
//			psmt.setInt(4, mdto.getAge());
//
//			// sql통과 하세요!
//			int row = psmt.executeUpdate();
//			return row;
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return 0;
//		} finally {
//			allClose();
//		}
//
//	}
//
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

//	}

}
