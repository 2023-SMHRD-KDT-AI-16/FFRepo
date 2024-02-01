package controller;

import java.util.ArrayList;

import model.StockVO;

public class gameStart {
	int my_money = 50000000; // 초기자금
	int my_turn = 0; // 턴을 카운터 측정 30턴이면 종료
	DBcontroller dbco = new DBcontroller();
	ArrayList<StockVO> stList = new ArrayList<StockVO>();
	
	void start() {
		// TODO Auto-generated method stub
	System.out.println(dbco.everyStock());
	
//	switch (key) {
//	case value: {
//		
//		yield type;
//	}
//	default:
//		throw new IllegalArgumentException("Unexpected value: " + key);
//	}

	}

}
