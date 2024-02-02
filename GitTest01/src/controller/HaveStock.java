package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.MyStockVO;
import model.StockVO;

public class HaveStock extends DBcontroller {

	public ArrayList<MyStockVO> MyStock() {// 내 주식 확인
		return select_my_stock();
	}

//	public ArrayList<MyStockVO> MyStock(String giupName) {// 내 주식 확인
//		ArrayList<MyStockVO> myStockList = new ArrayList<MyStockVO>();
//
//		getConn();
//
//		// 동적로딩
//		try {
//			// sql통과 통로
//			String sql = "select * from my_stock  where stock_name = ?";
//			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, giupName);
//
//			// sql통과
//			rs = psmt.executeQuery();
//
//			// select 한줄의 데이터 확인 rs.next()
//
//			while (rs.next()) {
//				int purchased_stock_amount = rs.getInt("purchased_stock_amount");
//				int current_stock_amount = rs.getInt("current_stock_amount");
//				String stock_name = rs.getString("stock_name");
//				int stock_yield = rs.getInt("stock_yield");
//				int stock_count = rs.getInt("stock_count");
//
//				MyStockVO mvo = new MyStockVO(stock_name,purchased_stock_amount, current_stock_amount, stock_count, stock_yield
//						);
//				myStockList.add(mvo);
//
//			}
//			return myStockList;
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			allClose();
//		}
//
//	}

	// 선택창에서 주식 매도 기능 메소드
	public int stockSale(int sale_stock_index, int count) {

		ArrayList<StockVO> all_stocks = select_all_stock();
		ArrayList<MyStockVO> my_stocks = new ArrayList<MyStockVO>(); // 내 소유 주식 담을 어레이리스트

		getConn();

		try {
			String sale_stock_name = all_stocks.get(sale_stock_index).getStockName(); // 회사 이름 담을 변수
			int sale_stock_price = all_stocks.get(sale_stock_index).getNowPrice(); // 현재 판매가격
			String sql = "select * from my_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, sale_stock_name);
			rs = psmt.executeQuery();

			while (rs.next()) {// 내 주식 담는 코드
				String giup_name = rs.getString("stock_name");
				int my_price = rs.getInt("purchased_stock_amount");
				int stockCount = rs.getInt("stock_count");
				int current_amount = rs.getInt("current_stock_amount");
				float stock_yieled = rs.getFloat("stock_yield");
				my_stocks.add(new MyStockVO(giup_name, my_price, stockCount, current_amount, stock_yieled));
			}
			// 보유하고 있는 주식 수량 담을 변수
			// 내가 가지고 있는 금액
			for (int i = 0; i < my_stocks.size(); i++) {// 내 주식 수
				if (my_stocks.get(i).getStock_name().equals(sale_stock_name)) {

					if (my_stocks.get(i).getStock_count() == count) {
						// sql 통과 통로
						String sql_2 = "delete from my_stock where stock_name = ?";
						psmt = conn.prepareStatement(sql_2);

						// ? 채우기
						psmt.setString(1, my_stocks.get(i).getStock_name());

						// sql통과
						int row = psmt.executeUpdate();
						score = score + (all_stocks.get(sale_stock_index).getNowPrice() * count);

						return score;
					} else {
						String sql_3 = "update my_stock set stock_count = ?, purchased_stock_amount = ? where stock_name = ?";
						psmt = conn.prepareStatement(sql_3);

						// ? 채우기
						psmt.setInt(1, (my_stocks.get(i).getStock_count() - count));
						psmt.setInt(2, my_stocks.get(i).getPurchased_stock_amount()
								- (all_stocks.get(sale_stock_index).getNowPrice() * count)); // 가지고 있던 금액 - 현재 매도할 금액
						psmt.setString(3, my_stocks.get(i).getStock_name());

						// sql통과

						int row = psmt.executeUpdate();
						score = score + (all_stocks.get(sale_stock_index).getNowPrice() * count);
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

	// 선택창에서 주식 매수 기능 메소드
	public int stockBuy(int buy_stock_index, int count) {
		ArrayList<String> stock_name = new ArrayList<String>(); // 회사 이름 담을 어레이리스트
		ArrayList<Integer> pur_price = new ArrayList<Integer>(); // 회사의 현재 가격 담을 어레이리스트

		getConn(); // DB 연결 메소드

		try {
			String sql = "select * from all_stock";
			
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

			ArrayList<MyStockVO> myvos = select_my_stock();

			for (int i = 0; i < myvos.size(); i++) {
				if (myvos.get(i).getStock_name().equals(stock_name.get(buy_stock_index))) {
					if (myvos.get(i).getStock_count() == 0) {// 원하는 주식 처음 구매 시
						// sql 통과 통로
						String sql_2 = "insert into my_stock values(?,?,?,?,?)";
						psmt = conn.prepareStatement(sql_2);

						// ? 채우기
						psmt.setInt(1, pur_price.get(buy_stock_index) * count); // 전체 소유 금액
						psmt.setInt(2, pur_price.get(buy_stock_index)); // 현재 금액
						psmt.setString(3, myvos.get(i).getStock_name());
						psmt.setInt(4, 0); // 수익률
						psmt.setInt(5, count); // 수량
						// sql통과
						if (score >= pur_price.get(buy_stock_index) * count) {
							int row = psmt.executeUpdate();
							score = score - (pur_price.get(buy_stock_index) * count);
							return score;
						} else {
							return score;
						}

					} else {// 원하는 종목에 대한 주식을 이미 소유하고 있을 때
						getConn();
						String sql_2 = "update my_stock set stock_count = ?, purchased_stock_amount = ?,current_stock_amount = ?, stock_yield = ? where stock_name = ?";
						psmt = conn.prepareStatement(sql_2);

						// ? 채우기
						psmt.setInt(1, (myvos.get(i).getStock_count() + count));
						psmt.setInt(2,
								myvos.get(i).getPurchased_stock_amount() + (pur_price.get(buy_stock_index) * count)); // 전체
																														// 구매한
																														// 금액
						psmt.setInt(3, pur_price.get(buy_stock_index));
						psmt.setFloat(4, myvos.get(i).getStock_yield()); // 수익룰
						psmt.setString(5, myvos.get(i).getStock_name());
						
						int row = psmt.executeUpdate();
						// sql통과
						if (score >= pur_price.get(buy_stock_index) * count) {
							 row = psmt.executeUpdate();
							score = score - (pur_price.get(buy_stock_index) * count * count);
							return score;
						}//if

					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();

		}
		return score;

	}
}
