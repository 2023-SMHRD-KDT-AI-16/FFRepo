package controller;

import java.sql.SQLException;

public class testMain {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		MainController mainController = new MainController();
		gameStart playStart = new gameStart();
		
		// TODO Auto-generated method stub
//		mainController.stock_Rate_Update();
		playStart.start();
		
	}

}
