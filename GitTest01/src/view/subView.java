package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import controller.DBcontroller;

public class subView {
	DBcontroller dbcon = new DBcontroller();
	protected Connection conn;
	protected PreparedStatement psmt;
	protected ResultSet rs;

	public void gameEnd() {
		
		System.out.println("[1]점수 등록 [2]종료");
		Scanner sc = new Scanner(System.in);
		int cho = sc.nextInt();
		
		switch (cho) {
		case 1: {


			dbcon.getConn();

			try {
				String sql_1 = "select NAME, SCORE, RANK_SCORE from RANK";
				rs = psmt.executeQuery();
				
				String NAME = null;
				int SCORE = 0;
				int RANK_SCORE = 0;
				
				while (rs.next()) {
					float id = rs.getFloat("stock_yield");
					int my_price = rs.getInt("purchased_stock_amount");
					final_yield += id;
					cnt +=1 ;
					sum += my_price;

				}
				
				// sql통과 통로
				String sql_2 = "insert into my_user values(?,?,?)";
				psmt = conn.prepareStatement(sql_2);

				// ?채우기
				psmt.setString(1,Nickname);
				psmt.setInt(2, sum);
				psmt.setFloat(3, final_yield);

				// sql통과 하세요!
				int row = psmt.executeUpdate();
				return row;

			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			} finally {
				allClose();
			}
			
			
			break;
		}
		case 2: {
			
			break;
		}
		default:
			break;
		}
	}
	
	public void rank() {
		
	}
}
