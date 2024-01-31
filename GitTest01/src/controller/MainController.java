package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import model.StockVO;

public class MainController extends DBcontroller {

	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	public static void main(String[] args) {
		int row;

		int[] now_price = new int[20];
		ArrayList<StockVO> results = new ArrayList<>(); // 등락률 보내기 위한 최종 어레이리스트
		ArrayList<StockVO> svoList = new ArrayList<StockVO>(); // 주식정보 어레이리스트
		float[] stock_rate = new float[20];
		Random random = new Random();
		float randomFloat = random.nextFloat();

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
			System.out.println(svoList);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
	}
}

//	public void stock_Rate_Update() {

//		for (int i = 0; i < stock_rate.length; i++) {
//			float randomFloatInRange = random.nextFloat(60) - 30; // 등락률 퍼센트로 -30 ~ 30를 나타냄
//			stock_rate[i] = randomFloatInRange / 100;
//			System.out.print(stock_rate[i] + "\t"); // 배열에 랜덤 등락률 적용된 현재가
//			System.out.println(randomFloatInRange); // 등락률 퍼센트로 -30 ~ 30를 나타냄
//		}

//		getConn();
//		for (int i = 0; i < 20; i++) {
//			try {
//				String sql = "UPDATE ALL_stock SET STOCK_RATE = ? WHERE STOCK_NAME = ?";
//				float rate = stock_rate[i]; // 등락률 집어 넣기
//				String name = 0; /// 0 대신 주식 이름 넣어야 함.
//				psmt = conn.prepareStatement(sql);
//				psmt.setFloat(1, rate);
//				psmt.setString(2, name);
//
//				row = psmt.executeUpdate();
//
//				if (row > 0) {
//					System.out.println("UPDATE SUCCESS");
//				} else {
//					System.out.println("UPDATE FAIL");
//				}
//
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//			// 통로 닫기
//			finally {
//				allClose();
//				try {
//					if (psmt != null) {
//						psmt.close();
//					}
//					if (conn != null) {
//						conn.close();
//					}
//
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}

//	}
//}
