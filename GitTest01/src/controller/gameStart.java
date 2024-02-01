package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.StockVO;

public class gameStart {
	int my_money = 50000000; // 초기자금
	int my_turn = 0; // 턴을 카운터 측정 30턴이면 종료
	int cnt = 0;
//	int[][] my_stock = new int[][2];
	ArrayList<StockVO> stList = new ArrayList<StockVO>();
	MainController mainController = new MainController();

	void start() {
//			System.out.print(dbco.everyStock().get(i).getStockName() + "\t \t \t" +dbco.everyStock().get(i).getYesterdayPrice()
//					+ "\t \t" + dbco.everyStock().get(i).getNowPrice() + "\t \t"+ dbco.everyStock().get(i).getRate());
	}

}
