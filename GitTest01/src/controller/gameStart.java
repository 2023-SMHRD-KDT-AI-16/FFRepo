package controller;

import java.util.ArrayList;

import model.StockVO;

public class gameStart {
	int my_money = 50000000; // 초기자금
	int my_turn = 0; // 턴을 카운터 측정 30턴이면 종료
	
//	int[][] my_stock = new int[][2];
	DBcontroller dbco = new DBcontroller();
	ArrayList<StockVO> stList = new ArrayList<StockVO>();

	void start() {
		// TODO Auto-generated method stub
		for (StockVO x : dbco.everyStock()) {
			System.out.print(x.getStockName()+ "\t"+ "\t");
			System.out.print(x.getNowPrice()+ "\t");
			System.out.print(x.getYesterdayPrice()+ "\t");
			System.out.println(x.getRate()+ "\t");
		}
		
		for (int i = 0; i < 30; i++) {
			
			
			
			
			//하루 마감 했을때
			my_turn++;
		}

	}

}
