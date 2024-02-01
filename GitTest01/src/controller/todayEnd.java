package controller;

import java.sql.SQLException;

public class todayEnd {
		int number =0;
//		MainController mcr = new MainController();
		number = mcr.stock_Rate_Update(number); //첫째날 마감
		System.out.println(number);
		
		number = mcr.stock_Rate_Update(number); //둘째날 마감
		System.out.println(number);
		
		number = mcr.stock_Rate_Update(number); //셋째날 마감
		System.out.println(number);

}
