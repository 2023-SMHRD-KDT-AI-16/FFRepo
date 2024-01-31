package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class MainController extends DBcontroller {

	public void stock_Rate_Update() throws SQLException, ClassNotFoundException {
		float[] stock_rate = new float[20];
		String[] Db_stock_name = new String[20];
		Random random = new Random();
		int row;
		
		for (int i = 0; i < stock_rate.length; i++) {
			float randomFloatInRange = random.nextFloat(60) - 30; // 등락률 퍼센트로 -30 ~ 30를 나타냄
			
			stock_rate[i] = randomFloatInRange / 100;
			System.out.print(stock_rate[i] + "\t"); // 배열에 랜덤 등락률 적용된 현재가
			System.out.println(randomFloatInRange); // 등락률 퍼센트로 -30 ~ 30를 나타냄
		}

		getConn();
		ArrayList<String> stock_name =  new ArrayList<>();
		// 동적로딩
		try {
			// sql통과 통로
			String sql = "select * from ALL_STOCK";
			psmt = conn.prepareStatement(sql);

			// sql통과
			rs = psmt.executeQuery();

			// select 한줄의 데이터 확인 rs.next()
			
			while (rs.next()) {
				String name = rs.getString(1);
				int now_price = rs.getInt(2);
				int yester_price = rs.getInt(3);
				int rate = rs.getInt(4);
				stock_name.add(name);
			}
			
			for (int i = 0; i < stock_name.size(); i++) {   //DB에 있는 1~20위 종목 이름
				Db_stock_name[i] = stock_name.get(i);
			}
			
			System.out.println(Db_stock_name[2]);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
		
		
		getConn();

			String name_1 = Db_stock_name[2]; /// 0 대신 주식 이름 넣어야 함.
			float rate_1 = stock_rate[2];
			
			
			try {
				String sql = "UPDATE ALL_stock SET STOCK_RATE = ? WHERE STOCK_NAME = ?";
				float rate = rate_1; // 등락률 집어 넣기
				String name = name_1;
				psmt = conn.prepareStatement(sql);
				
				psmt.setFloat(1, rate);
				psmt.setString(2, name);

				row = psmt.executeUpdate();

				if (row > 0) {
					System.out.println("UPDATE SUCCESS");
				} else {
					System.out.println("UPDATE FAIL");
				}

			}
			// 통로 닫기
			finally {
				allClose();
				try {
					if (psmt != null) {
						psmt.close();
					}
					if (conn != null) {
						conn.close();
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		

	}

}
