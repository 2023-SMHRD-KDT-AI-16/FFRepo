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

				MyStockVO mvo = new MyStockVO(purchased_stock_amount, current_stock_amount, stock_name, stock_yield,
						stock_count);
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

				MyStockVO mvo = new MyStockVO(purchased_stock_amount, current_stock_amount, stock_name, stock_yield,
						stock_count);
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

	int score = 0;

	// 선택창에서 주식 매도 기능 메소드
	public int stockSale(int sell_stock_index, int count) {

		ArrayList<String> stock_names = new ArrayList<String>(); // 회사 이름 담을 어레이리스트
		ArrayList<Integer> sell_prices = new ArrayList<Integer>(); // 회사의 현재 가격 담을 어레이리스트
		ArrayList<String> my_stock_names = new ArrayList<String>(); // 내 소유 현재 가격 담을 어레이리스트
		ArrayList<Integer> my_stock_prices = new ArrayList<Integer>(); // 내 소유 현재 가격 담을 어레이리스트
		ArrayList<Integer> my_rates = new ArrayList<Integer>(); // 내 소유 현재 가격 담을 어레이리스트

		getConn();

		try {
			String sql = "select * from all_stock";
			psmt = conn.prepareStatement(sql);

			// sql통과
			rs = psmt.executeQuery();

			// select 한줄의 데이터 확인 rs.next()
			while (rs.next()) {
				String stockName = rs.getString("stock_name");
				int nowPrice = rs.getInt("stock_now_price");
				int yesterdayPrice = rs.getInt("stock_yesterday_price");
				int stockrate = rs.getInt("stock_rate");
				stock_names.add(stockName);
				sell_prices.add(nowPrice);
				my_rates.add(stockrate);
			}

			String sell_stockName = stock_names.get(sell_stock_index);
			int sell_stockPrice = sell_prices.get(sell_stock_index);
			String sql_2 = "select stock_count, purchased_stock_amount, current_stock_amount, stock_name from my_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql_2);
			psmt.setString(1, sell_stockName);
			rs = psmt.executeQuery();

			int stockCount = 0; // 보유하고 있는 주식 수량 담을 변수
			int my_price = 0; // 내가 가지고 있는 금액
			String giup_name = null;
			while (rs.next()) {
				stockCount = rs.getInt("stock_count");
				my_price = rs.getInt("purchased_stock_amount");
				giup_name = rs.getString("stock_name");
				my_stock_names.add(giup_name);
				my_stock_prices.add(my_price);
				my_rates.add(stockCount);

			}
			for (int i = 0; i < my_rates.size(); i++) {
				if (my_stock_names.get(sell_stock_index).equals(stock_names.get(i))) {

					if (stockCount == count) {
						// sql 통과 통로
						String sql_3 = "delete from my_stock where stock_name = ?";
						psmt = conn.prepareStatement(sql_3);

						sell_stockName = stock_names.get(i);

						// ? 채우기
						psmt.setString(1, sell_stockName);

						// sql통과
						int row = psmt.executeUpdate();
						score = score + (sell_stockPrice * count);

						return row;
					} else {
						String sql_3 = "update my_stock set stock_count = ?, purchased_stock_amount = ? where stock_name = ?";
						psmt = conn.prepareStatement(sql_3);

						sell_stockName = stock_names.get(i);
						sell_stockPrice = sell_prices.get(i);

						// ? 채우기
						psmt.setInt(1, (stockCount - count));
						psmt.setInt(2, my_price - (sell_stockPrice * count)); // 가지고 있던 금액 - 현재 매도할 금액
						psmt.setString(3, sell_stockName);

						// sql통과

						int row = psmt.executeUpdate();
						score = score + (sell_stockPrice * count);
						return row;

					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
		return 0;
	}

	// 선택창에서 주식 매수 기능 메소드
	public int stockBuy(int buy_stock_name, int count) {
		ArrayList<String> stock_name = new ArrayList<String>(); // 회사 이름 담을 어레이리스트
		ArrayList<Integer> pur_price = new ArrayList<Integer>(); // 회사의 현재 가격 담을 어레이리스트

		getConn(); // DB 연결 메소드

		try {
			String sql = "select * from all_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql);
			// sql통과
			rs = psmt.executeQuery();

			// select 한줄의 데이터 확인 rs.next()
			int nowPrice = 0;
			while (rs.next()) {
				String stockName = rs.getString("stock_name");
				nowPrice = rs.getInt("stock_now_price");
				int yesterdayPrice = rs.getInt("stock_yesterday_Price");
				int stockrate = rs.getInt("stock_rate");
				stock_name.add(stockName);
				pur_price.add(nowPrice);
			}

			String sql_2 = "select * my_stock";
			psmt = conn.prepareStatement(sql_2);

			ArrayList<MyStockVO> myvo = new ArrayList<MyStockVO>();
			rs = psmt.executeQuery();
			int my_cnt = 0; 
			int my_purchased = 0;
			String my_stock = null;
			float my_yield = 0f;
			int my_current_stock = 0;
			while(rs.next()) {
				my_cnt = rs.getInt("stock_count");
				my_purchased = rs.getInt("purchased_stock_amount");
				my_yield = rs.getFloat("stock_yield");
				my_current_stock = rs.getInt("current_stock_amount");
				my_stock = rs.getString("stock_name");
				MyStockVO mvo = new MyStockVO( my_purchased, my_current_stock, my_stock,  my_yield, my_cnt);
				
			}
		
			
			for(int i = 0 ; i <myvo.size(); i++) {
			if(myvo.get(i).getStock_name().equals(stock_name.get(buy_stock_name))) {
			if (my_cnt == 0) {// 원하는 주식 처음 구매 시
				// sql 통과 통로
				String sql_3 = "insert into my_stock values(?,?,?,?,?)";
				psmt = conn.prepareStatement(sql_3);

				// ? 채우기
				psmt.setInt(1, my_purchased * count); // 전체 소유 금액
				psmt.setInt(2, my_purchased); // 현재 금액
				psmt.setString(3, my_stock);
				psmt.setInt(4, 0); // 수익률
				psmt.setInt(5, count); // 수량
				// sql통과
				if (score >= my_purchased * count) {
					int row = psmt.executeUpdate();
					score = score - (my_purchased * count);
					return score;
					}else {
						return 0;
					}
					
				
			} else {// 원하는 종목에 대한 주식을 이미 소유하고 있을 때
				String sql_3 = "update my_stock set stock_count = ?, purchased_stock_amount = ?,current_stock_amount = ?, stock_yield = ? where stock_name = ?";
				psmt = conn.prepareStatement(sql_3);

				// ? 채우기
				psmt.setInt(1, (my_cnt + count));
				psmt.setInt(2, my_purchased + (nowPrice * count)); // 전체 구매한 금액
				psmt.setInt(3, my_purchased);
				psmt.setFloat(4, my_yield); // 수익룰
				psmt.setString(5, my_stock);
				// sql통과
				if (score >= my_purchased  + (nowPrice * count)) {
					int row = psmt.executeUpdate();
					score = score - (my_purchased * count);
					return score;
				}
				
			}
			}}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();

		}		return 0;
	
}
	}
