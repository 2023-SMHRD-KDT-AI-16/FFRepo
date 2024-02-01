package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import controller.DBcontroller;

public class subView {
	DBcontroller dbcon = new DBcontroller();
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;

	public void gameEnd(String name, int score) {

		System.out.println("[1]점수 등록 [2]종료");
		Scanner sc = new Scanner(System.in);
		int cho = sc.nextInt();

		switch (cho) {
		case 1: {
			dbcon.getConn();

			
			try {
				String name_input = name;
				int score_input = score;
				int rank_input = 3;
				
				String sql_1 = "select name, score, rank_score from rank";
				rs = psmt.executeQuery();
				String str = null;
				int num1=0;
				int num2=0;
				
				
				while (rs.next()) {
					String name_input_1 = rs.getString("NAME");
					int score_input_1 = rs.getInt("SCORE");
					int rank_input_1 = rs.getInt("RANK_SCORE");
					
					str = name_input_1;
					num1=score_input_1;
					num2=rank_input_1;
//					for (int i = 0; i < 20; i++) {
//						rank_input
//					}
				}
				System.out.println(" 1");
				System.out.println(str+" "+num1+" "+num2);

				System.out.println("닉네임을 입력하세요");
				;
				String sql = "insert into rank values(?,?,?)";
				psmt = conn.prepareStatement(sql);

				// ?채우기
				psmt.setString(1, name_input);
				psmt.setInt(2, score_input);
				psmt.setInt(3, rank_input);

				// sql통과 하세요!
				psmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbcon.allClose();
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
