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

			// ?채우기 - ?가 없으면 생략
			psmt.setString(1, search_stock_name);
			// sql통과 하세요!
			psmt = conn.prepareStatement(sql);
			//

			rs = psmt.executeQuery();

			while (rs.next()) {
				String stockName = rs.getString(1);
				int stockCount = rs.getInt(2);
				int buyPrice = rs.getInt(3);
				int nowPrice = rs.getInt(4);

				StockVO sdvo = new StockVO(stockName, stockCount, buyPrice, nowPrice);
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
