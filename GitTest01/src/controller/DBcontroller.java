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

	public Connection conn;
	public PreparedStatement psmt;
	public ResultSet rs;
	public int score = 50000000;

	// DB 연결 메소드
	public void getConn() {

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
	public void allClose() {

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

	// 1. 회원등록 기능

	public int insertName(String Nickname) {

		getConn();

		try {
			String sql_1 = "select stock_yield, purchased_stock_amount from my_stock";
			psmt = conn.prepareStatement(sql_1);
			rs = psmt.executeQuery();

			float final_yield = 0.0f;
			int cnt = 0;
			int sum = 0;

			while (rs.next()) {
				float id = rs.getFloat("stock_yield");
				int my_price = rs.getInt("purchased_stock_amount");
				final_yield += id;
				cnt += 1;
				sum += my_price;

			}
			final_yield = sum / 50000000;

			// sql통과 통로
			String sql_2 = "insert into my_user values(?,?,?)";
			psmt = conn.prepareStatement(sql_2);

			// ?채우기
			psmt.setString(1, Nickname);
			psmt.setInt(2, sum);
			psmt.setFloat(3, final_yield);

			// sql통과 하세요!
			int row = psmt.executeUpdate();
			return 50000000;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			allClose();
		}

	}

//	// 2. 로그인
//   public int userLogin(String input_id, String input_pw) {
//      ArrayList<String> idList = new ArrayList<String>();
//      ArrayList<String> pwList = new ArrayList<String>();
//
//      getConn();
//
//      // 동적로딩
//      try {
//         // sql통과 통로
//         String sql = "select user_id,user_pw from my_user";
//         psmt = conn.prepareStatement(sql);
//
//         // sql통과
//         rs = psmt.executeQuery();
//
//         // select 한줄의 데이터 확인 rs.next()
//
//         while (rs.next()) {
//            String id = rs.getString(1);
//            String pw = rs.getString(2);
//            idList.add(id);
//            pwList.add(pw);
//         }
//         for (int i = 0; i < idList.size(); i++) {
//            if (idList.get(i).equals(input_id)) {
//               if (pwList.get(i).equals(input_pw)) {
//                  return 1;
//               } else {
//                  return 0;
//               }
//            } else {
//               return 0;
//            }
//         }
//
//      } catch (SQLException e) {
//         e.printStackTrace();
//      } finally {
//         allClose();
//      }
//      return 0;
//
//   }

	// 3. 전체 주식 보기 메소드
	public ArrayList<StockVO> everyStock() {
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

				StockVO svo = new StockVO(stockName, yesterdayPrice, nowPrice, stockrate);
				svoList.add(svo);

			}
			return svoList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			allClose();
		}

	}

	// 4. 주식 매도 기능 메소드
	public int stockSale(int sell_stock_index, int count) {

		ArrayList<String> stock_names = new ArrayList<String>(); // 회사 이름 담을 어레이리스트
		ArrayList<Integer> sell_prices = new ArrayList<Integer>(); // 회사의 현재 가격 담을 어레이리스트
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
			}

			String sell_stockName = stock_names.get(sell_stock_index);
			int sell_stockPrice = sell_prices.get(sell_stock_index);
			String sql_2 = "select stock_count, purchased_stock_amount, current_stock_amount from my_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql_2);
			psmt.setString(1, sell_stockName);
			rs = psmt.executeQuery();

			int stockCount = 0; // 보유하고 있는 주식 수량 담을 변수
			int my_price = 0; // 내가 가지고 있는 금액
			while (rs.next()) {
				stockCount = rs.getInt("stock_count");
				my_price = rs.getInt("purchased_stock_amount");
				System.out.println("보유 주식 : " + stockCount);

			}

			if (stockCount == count) {
				// sql 통과 통로
				String sql_3 = "delete from my_stock where stock_name = ?";
				psmt = conn.prepareStatement(sql_3);

				// ? 채우기
				psmt.setString(1, sell_stockName);

				// sql통과
				int row = psmt.executeUpdate();
				score = score + (sell_stockPrice * count);

				return row;
			} else {
				String sql_3 = "update my_stock set stock_count = ?, purchased_stock_amount = ? where stock_name = ?";
				psmt = conn.prepareStatement(sql_3);

				// ? 채우기
				psmt.setInt(1, (stockCount - count));
				psmt.setInt(2, my_price - (sell_stockPrice * count)); // 가지고 있던 금액 - 현재 매도할 금액
				psmt.setString(3, sell_stockName);

				// sql통과

				int row = psmt.executeUpdate();
				score = score + (sell_stockPrice * count);
				return row;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
		return 0;
	}

	// 5. 주식 매수 기능 메소드
	public int stockBuy(int buy_stock_name, int count) {
		ArrayList<String> stock_name = new ArrayList<String>(); // 회사 이름 담을 어레이리스트
		ArrayList<Integer> pur_price = new ArrayList<Integer>(); // 회사의 현재 가격 담을 어레이리스트

		getConn(); // DB 연결 메소드

		try {
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
				stock_name.add(stockName);
				pur_price.add(nowPrice);
			}

			// 내 주식 테이블에서 stock_count, purchased_stock_amount 출력
			String sql_2 = "select stock_count, purchased_stock_amount, stock_yield from my_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql_2);

			String stockName = stock_name.get(buy_stock_name); // 기업 이름
			int buy_price = pur_price.get(buy_stock_name); // 기업 현재 주식 금액

			// ? 채우기
			psmt.setString(1, stockName);

			rs = psmt.executeQuery();

			int stockCount = 0; // 보유하고 있는 주식 수량 담을 변수
			int purchased_amount = 0; // 구매 총액
			float yield = 0.0f;
			while (rs.next()) {
				stockCount = rs.getInt("stock_count");
				purchased_amount = rs.getInt("purchased_stock_amount");
				yield = rs.getFloat("stock_yield");

			}
			if (score > purchased_amount * stockCount) {

				if (stockCount == 0) {// 원하는 주식 처음 구매 시
					// sql 통과 통로
					String sql_3 = "insert into my_stock values(?,?,?,?,?)";
					psmt = conn.prepareStatement(sql_3);

					// ? 채우기
					psmt.setInt(1, buy_price * count); // 전체 소유 금액
					psmt.setInt(2, buy_price); // 현재 금액
					psmt.setString(3, stockName);
					psmt.setInt(4, 0);
					psmt.setInt(5, count);
					// sql통과
					if (score >= buy_price * count) {
						int row = psmt.executeUpdate();

						score = score - (buy_price * count);
						return score;
					}
				} else {// 원하는 종목에 대한 주식을 이미 소유하고 있을 때
					String sql_3 = "update my_stock set stock_count = ?, purchased_stock_amount = ?,current_stock_amount = ?, stock_yield = ? where stock_name = ?";
					psmt = conn.prepareStatement(sql_3);

					// ? 채우기
					psmt.setInt(1, (stockCount + count));
					psmt.setInt(2, purchased_amount + (buy_price * count)); // 전체 구매한 금액
					psmt.setInt(3, buy_price);
					psmt.setFloat(4, yield); // 수익룰
					psmt.setString(5, stockName);
					// sql통과
					if (score >= buy_price * purchased_amount + (buy_price * count)) {
						int row = psmt.executeUpdate();
						score = score - (buy_price * count);
						return score;
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

	public ArrayList<UserVO> userRank() {
		getConn();
		try {
			String sql = "select * from my_user order by my_money";
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			ArrayList<UserVO> uvos = new ArrayList<UserVO>();

			while (rs.next()) {
				String a = rs.getString("user_id");
				int b = rs.getInt("my_money");
				int c = rs.getInt("my_yield");

				UserVO uvo = new UserVO(a, b, c);
				uvos.add(uvo);
				return uvos;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			allClose();
		}
		return null;

	}

}
