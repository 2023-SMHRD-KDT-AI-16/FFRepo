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

	public void gameEnd(String name, int score) {

		System.out.println("[1]점수 등록 [2]종료");
		Scanner sc = new Scanner(System.in);
		int cho = sc.nextInt();

		switch (cho) {
		case 1: {

			dbcon.getConn();

			try {
				String sql = "insert into my_user values(?,?,?)";
				psmt = conn.prepareStatement(sql);

				String NAME = null;
				int SCORE = 0;
				int RANK_SCORE = 0;

				// ?채우기
				psmt.setString(1, NAME);
				psmt.setInt(2, SCORE);
				psmt.setFloat(3, RANK_SCORE);

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
