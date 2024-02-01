package controller;

import java.sql.SQLException;

public class todayEnd {
	MainController mcr = new MainController();
	
	public todayEnd() throws ClassNotFoundException, SQLException {
		int number =0;
//		MainController mcr = new MainController();
		number = mcr.stock_Rate_Update(number); //첫째날 마감
		System.out.println(number);
	}

}
