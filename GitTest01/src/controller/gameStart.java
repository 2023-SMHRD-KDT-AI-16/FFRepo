package controller;

import java.sql.SQLException;

public class gameStart {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		int conut = 0;
		MainController mcr = new MainController();
		int number = mcr.stock_Rate_Update(conut);
		System.out.println(number);
	}
	
	

}
