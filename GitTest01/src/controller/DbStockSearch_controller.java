package controller;

import java.sql.SQLException;

public class DbStockSearch_controller extends DBcontroller{
	public int stockSale(String sale_stock_name) {
		DBcontroller dbc = new DBcontroller();
		
		getConn();
		try {
			// sql통과 통로
			String sql = "delete from my_stock where stock_name = ?";
			psmt = conn.prepareStatement(sql);

			// ?채우기 - ?가 없으면 생략
			psmt.setString(1, sale_stock_name);
			// sql통과 하세요!
			int row = psmt.executeUpdate();
			return row;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			allClose();
		}
	}

}
