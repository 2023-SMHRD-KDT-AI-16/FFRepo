package controller;

import java.sql.SQLException;

public class todayEnd {
	MainController mcr = new MainController();
	static int cnt;
	public todayEnd(){
		
		try {
			int num = mcr.stock_Rate_Update(); //첫째날 마감
			System.out.println(num);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			int num = mcr.stock_Rate_Update(); //첫째날 마감
			System.out.println(num);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			int num = mcr.stock_Rate_Update(); //첫째날 마감
			System.out.println(num);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
