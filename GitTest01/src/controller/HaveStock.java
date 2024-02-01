package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MyStockVO;

public class HaveStock {
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
	
	public ArrayList<MyStockVO> MyStock() {// 내 주식 확인
		ArrayList<MyStockVO> myStockList = new ArrayList<MyStockVO>();

		getConn();

		// 동적로딩
		try {
			// sql통과 통로
			String sql = "select * from my_stock";
			psmt = conn.prepareStatement(sql);

			// sql통과
			rs = psmt.executeQuery();

			// select 한줄의 데이터 확인 rs.next()

			while (rs.next()) {
				int purchased_stock_amount = rs.getInt("purchased_stock_amount");
				int current_stock_amount = rs.getInt("current_stock_amount");
				String stock_name = rs.getString("stock_name");
				int stock_yield = rs.getInt("stock_yield");
				int stock_count = rs.getInt("stock_count");
				
				MyStockVO mvo = new MyStockVO(purchased_stock_amount,current_stock_amount,stock_name,stock_yield,stock_count);
				myStockList.add(mvo); 

			}
			return myStockList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			allClose();
		}

	}
	
	public ArrayList<MyStockVO> MyStock(String giupName) {// 내 주식 확인
		ArrayList<MyStockVO> myStockList = new ArrayList<MyStockVO>();

		getConn();

		// 동적로딩
		try {
			// sql통과 통로
			String sql = "select * from my_stock  where stock_name = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, giupName);

			// sql통과
			rs = psmt.executeQuery();

			// select 한줄의 데이터 확인 rs.next()

			while (rs.next()) {
				int purchased_stock_amount = rs.getInt("purchased_stock_amount");
				int current_stock_amount = rs.getInt("current_stock_amount");
				String stock_name = rs.getString("stock_name");
				int stock_yield = rs.getInt("stock_yield");
				int stock_count = rs.getInt("stock_count");
				
				MyStockVO mvo = new MyStockVO(purchased_stock_amount,current_stock_amount,stock_name,stock_yield,stock_count);
				myStockList.add(mvo); 

			}
			return myStockList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			allClose();
		}

	}
	
	
	
	
	
	
	
	
	
	
}
