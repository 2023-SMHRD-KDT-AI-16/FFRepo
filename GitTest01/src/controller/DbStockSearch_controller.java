package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.StockVO;

public class DbStockSearch_controller extends DBcontroller {
	DBcontroller dbc = new DBcontroller();

	public ArrayList<StockVO> searchStock(String search_stock_name) {
		ArrayList<StockVO> usertoList = new ArrayList<>();

		
		getConn();
		try {
			// sql통과 통로
			String sql = "select *  from ALL_STOCK where STOCK_NAME = ?";

			// sql통과 하세요!
			psmt = conn.prepareStatement(sql);
			//
			// ?채우기 - ?가 없으면 생략
			psmt.setString(1, search_stock_name);

			rs = psmt.executeQuery();

			while (rs.next()) {
				String stockName = rs.getString("STOCK_NAME");
				int stockCount = rs.getInt("STOCK_YESTERDAY_PRICE");
				int nowPrice = rs.getInt("STOCK_NOW_PRICE");
				int anything = rs.getInt("STOCK_RATE");


				StockVO sdvo = new StockVO(stockName, stockCount, nowPrice, anything);
				usertoList.add(sdvo);
			}
			return usertoList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			allClose();
		}
	}

}
