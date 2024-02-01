package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.StockVO;

public class gameStart {
	int my_money = 50000000; // 초기자금
	int my_turn = 0; // 턴을 카운터 측정 30턴이면 종료
	int cnt = 0;
//	int[][] my_stock = new int[][2];
	DBcontroller dbco = new DBcontroller();
	ArrayList<StockVO> stList = new ArrayList<StockVO>();
	MainController mainController = new MainController();

	void start() {
		// TODO Auto-generated method stub
		for (StockVO x : dbco.everyStock()) {
			System.out.print(x.getStockName() + "\t" + "\t");
			System.out.print(x.getNowPrice() + "\t");
			System.out.print(x.getYesterdayPrice() + "\t");
			System.out.println(x.getRate() + "\t");
		}

		for (int i = 0; i < 5; i++) {
			try {
				int cnt_1 = mainController.stock_Rate_Update(cnt);
				System.out.println(cnt_1);
				if (cnt == 30) {
					// 랭킹 등록 하는 구간, 메인화면으로 나가기

				}
				cnt++;

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 하루 마감 했을때
		

	}

}
