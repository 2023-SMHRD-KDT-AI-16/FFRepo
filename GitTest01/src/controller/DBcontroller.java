package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MyStockVO;
import model.StockVO;
import model.UserVO;

public class DBcontroller {

	public Connection conn;
	public PreparedStatement psmt;
	public ResultSet rs;
	public int score = 50000000;
	float my_money = 0; // 전체 구매한 금액*수익률 -> 내 자산으로 이동(score)

	// i. DB 연결 메소드
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
	}

	// ii. 통로 close 하는 메소드
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

	// iii. all_stock 데이터 담을 객체 생성 메소드
	public ArrayList<StockVO> select_all_stock() {

		// 동적로딩
		getConn();
		ArrayList<StockVO> all_stocks = new ArrayList<StockVO>();
		try {
			String sql = "select * from all_stock";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				String stock_name;
				stock_name = rs.getString("stock_name");
				int stock_now_price = rs.getInt("stock_now_price");
				int stock_yesterday_price = rs.getInt("stock_yesterday_Price");
				int stock_rate = rs.getInt("stock_rate");
				StockVO svo = new StockVO(stock_name, stock_yesterday_price, stock_now_price, stock_rate);
				all_stocks.add(svo);
			}
			return all_stocks;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			allClose();
		}
		return null;
	}

	// iv. my_stock 데이터 담을 객체 생성 메소드
	public ArrayList<MyStockVO> select_my_stock() {

		// 동적로딩
		getConn();
		ArrayList<MyStockVO> my_stocks = new ArrayList<MyStockVO>();
		try {
			String sql = "select * from my_stock";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				String stock_name = rs.getString("stock_name");
				int purchased_stock_amount = rs.getInt("purchased_stock_amount");
				int current_stock_amount = rs.getInt("current_stock_amount");
				int stock_count = rs.getInt("stock_count");
				float stock_yield = rs.getFloat("stock_yield");
				MyStockVO msvo = new MyStockVO(stock_name, purchased_stock_amount, current_stock_amount, stock_count,
						stock_yield);
				my_stocks.add(msvo);
			}
			return my_stocks;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			allClose();
		}
		return null;
	}

	// 1. 회원등록 기능

	public int insertName(String Nickname) {
		ArrayList<MyStockVO> my_stocks = select_my_stock();

		getConn();

		int sum = 0;
		try {// 최종 점수와 수익률
			for (int i = 0; i < my_stocks.size(); i++) {
				int stock_sold_money = my_stocks.get(i).getPurchased_stock_amount()
						* (int) my_stocks.get(i).getStock_yield();
				sum += stock_sold_money;
			}
			int final_score = sum + score;
			float final_yield = final_score / 50000000;

			// sql통과 통로
			String sql = "insert into my_user values(?,?,?)";
			psmt = conn.prepareStatement(sql);
			// ?채우기
			psmt.setString(1, Nickname);
			psmt.setInt(2, final_score);
			psmt.setFloat(3, final_yield);

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
		return select_all_stock(); // all_stock 전체 불러오는 메소드를 리턴값으로 사용
	}

	// 4. 주식 매도 기능 메소드
	public int stockSale(int sale_stock_index, int count, int score) {
		// all_stock 전체 뽑는 select문 메소드
		ArrayList<StockVO> all_stocks = select_all_stock();
		ArrayList<MyStockVO> my_stocks = select_my_stock();

		int index = 0;
		String my_stock_name = all_stocks.get(index).getStockName(); // 인덱스로 뽑은 회사 이름
		for (int i = 0; i < my_stocks.size(); i++) {
			if (my_stocks.get(i).getStock_name().equals(my_stock_name)) {
				i = index;
			}
		}

		int my_stock_count = my_stocks.get(index).getStock_count(); // 보유하고 있는 주식 수량 담을 변수
		int my_purchased_amount = my_stocks.get(index).getPurchased_stock_amount(); // 내가 가지고 있는 한 종목 당 총 금액
		float my_stock_yield = my_stocks.get(index).getStock_yield(); // 뽑은 회사의 수익률
		int my_current_price = my_stocks.get(index).getCurrent_stock_amount(); // 뽑은 회사 현재 금액

		my_money = my_purchased_amount * my_stock_yield;

		getConn();

		try {

			if (my_stock_count == count) {

				// sql 통과 통로
				String sql = "delete from my_stock where stock_name = ?";
				psmt = conn.prepareStatement(sql);

				// ? 채우기
				psmt.setString(1, my_stock_name);

				// sql 통과
				int row = psmt.executeUpdate();
				my_money = my_purchased_amount * (int) my_stock_yield;

				score = score + ((int) my_money);

				return score;
			} else {// my_stock에서 구매 원하는 회사의 purchased_price, stock_count 수량 올리기
				my_money = my_current_price * count; // 현재가*수량 -> 판매한 총액 = 내 자산
				String sql = "update my_stock set stock_count = ?, purchased_stock_amount = ? where stock_name = ?";
				psmt = conn.prepareStatement(sql);

				// ? 채우기
				psmt.setInt(1, (my_stock_count - count));
				psmt.setInt(2, my_purchased_amount - (int) my_money); // 가지고 있던 금액 - 현재 매도할 금액
				psmt.setString(3, my_stock_name);

				// sql 통과
				int row = psmt.executeUpdate();
				score = score + (int) my_money;
				return score;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
		return score;
	}

	// 5. 주식 매수 기능 메소드
	public int stockBuy(int buy_stock_index, int count, int score) {

		ArrayList<StockVO> all_stocks = select_all_stock();
		ArrayList<MyStockVO> my_stocks = select_my_stock();
		String my_stock_name = all_stocks.get(buy_stock_index).getStockName(); // 인덱스로 뽑은 회사 이름
		int all_now_price = all_stocks.get(buy_stock_index).getNowPrice(); // 사길 원하는 회사 현재 금액


		getConn(); // DB 연결 메소드
		int index = 0;
		int my_stock_count = 0; // 현재 내가 보유하고 있는 수량
		float my_stock_yield = 0.0f;
		int my_purchased_amount = 0;
		
		if(my_stocks.size() != 0) {
			for(int i = 0; i <my_stocks.size(); i++) {
				if(my_stock_name.equals(my_stocks.get(i).getStock_name())){
					my_stock_count = my_stocks.get(i).getStock_count();
					my_stock_yield = my_stocks.get(i).getStock_yield();
					my_purchased_amount = my_stocks.get(i).getPurchased_stock_amount();
				}
			}
		}else {
			my_stock_count = 0;
		}
		
		my_money = (all_now_price * count);


	try
	{

		if (score > my_money) { // score가 사려는 주식보다 높을 때만 구매 가능
			
			if (my_stock_count == 0) {// 원하는 주식 처음 구매 시

				// sql 통과 통로
				String sql = "insert into my_stock values(?,?,?,?,?)";
				psmt = conn.prepareStatement(sql);

				// ? 채우기
				psmt.setInt(1, (int) my_money); // 전체 소유 금액
				psmt.setInt(2, all_now_price); // 1주 당 현재 금액
				psmt.setString(3, my_stock_name);
				psmt.setInt(4, 0); // 수익률은 아직 0
				psmt.setInt(5, count); // 내가 원한 수량

				// sql통과
				int row = psmt.executeUpdate();
//				System.out.println("1번 my_money" + my_money);
//				System.out.println(all_stocks.get(buy_stock_index).getStockName() + " " + count + "주 매수완료");

				score = score - (int) my_money;
//				System.out.println("if score" + score);
				return score;

			} else {// 원하는 종목에 대한 주식을 이미 소유하고 있을 때
				String sql = "update my_stock set stock_count = ?, purchased_stock_amount = ?,current_stock_amount = ?, stock_yield = ? where stock_name = ?";
				psmt = conn.prepareStatement(sql);

				// ? 채우기
				psmt.setInt(1, (my_stock_count + count));
				psmt.setInt(2, my_purchased_amount + (int) my_money); // 전체 구매한 금액
				psmt.setInt(3, all_now_price);
				psmt.setFloat(4, my_stock_yield); // 수익룰
				psmt.setString(5, my_stock_name);

				// sql통과
				int row = psmt.executeUpdate();

				System.out.println(all_stocks.get(buy_stock_index).getStockName() + " " + count + "주 추가 매수완료");

				System.out.println("anp" + all_now_price);
				System.out.println("cnt" + count);
				System.out.println("산돈" + my_money);

				score = score - (int) my_money;

				System.out.println((int) my_money);
				System.out.println(score);
				return score;
			}
		} else {
			System.out.println("돈이 없습니다.");
		}

		return score;

	}catch(
	SQLException e)
	{
		e.printStackTrace();
	}finally
	{
		allClose();

	}
	return score;
	}
	// 순위 출력 메소드

	public ArrayList<UserVO> userRank() { // 등록 보기
		ArrayList<UserVO> uvos = new ArrayList<UserVO>();
		getConn();
		try {
			String sql = "select * from my_user order by my_money desc";
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			// 랭킹부분 안풀리는데..?
			while (rs.next()) {
				String name = rs.getString("user_id");
				int money = rs.getInt("my_money");
				int yiyi = rs.getInt("my_yield");

				UserVO uvo = new UserVO(name, money, yiyi);
				uvos.add(uvo);
			}
			return uvos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			allClose();
		}
		return null;

	}

	// 콘솔 클리어 하는 코드

}
