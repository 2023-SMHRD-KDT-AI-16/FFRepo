package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javazoom.jl.player.MP3Player;

public class MainController extends DBcontroller {
	ArrayList<MusicVO> musicList = new ArrayList<MusicVO>(1);
	MP3Player mp3 = new MP3Player();
	int cnt = 0;

	public MainController() {
		// TODO Auto-generated constructor stub
		musicList.add(new MusicVO("짱구 개미송개미의 하루 (20초짜리)", "짱구", 020, "../GitTest01/src/짱구 개미송개미의 하루 (20초짜리).mp3"));
	}

	public void play() {
//		count = 0;
		// 현재 재생중인 음악이 있는지 확인하기
		if (mp3.isPlaying()) {
			// 재생중인 음악을 중지
			mp3.stop();
		}
		//
		mp3.play(musicList.get(0).getMusicPath());

	}

	public int stock_Rate_Update() throws SQLException, ClassNotFoundException {
		if (cnt < 20) {

			float[] stock_rate = new float[20];
			String[] Db_stock_name = new String[20];
			int[] Db_yesterday_price = new int[20];
			Random random = new Random();
			int row;
			float randomFloatInRange = 0;

			for (int i = 0; i < stock_rate.length; i++) {
				do {// do-while문 사용해 두 조건 아니면 다시 random값 받기
					randomFloatInRange = random.nextFloat(); // 등락률 퍼센트로 -30 ~ 30를 나타냄
					if (randomFloatInRange >= 0.7) {
						stock_rate[i] = randomFloatInRange;
					} else if (randomFloatInRange <= 0.3) {
						stock_rate[i] = 1 + randomFloatInRange;
					}
				} while (!(randomFloatInRange >= 0.7 || randomFloatInRange <= 0.3));

			}

			// 동적로딩
			getConn();
			ArrayList<String> stock_name = new ArrayList<>();
			ArrayList<Integer> stock_now_price = new ArrayList<>(); // 현재 가격(다음날 어제가격으로 이동)

			// sql통과 통로
			String sql = "select * from ALL_STOCK";
			psmt = conn.prepareStatement(sql);

			// sql통과
			rs = psmt.executeQuery();

			// select 한줄의 데이터 확인 rs.next()
			// 회사명과 현재 가격 배열에 담기
			while (rs.next()) {
				String name = rs.getString("stock_name");
				int now_price = rs.getInt("stock_now_price");
				int yester_price = rs.getInt("stock_yesterday_price");
				int rate = rs.getInt("stock_rate");
				stock_name.add(name);
				stock_now_price.add(now_price);
			}

			for (int i = 0; i < stock_name.size(); i++) { // DB에 있는 1~20위 종목 이름
				Db_stock_name[i] = stock_name.get(i);
				Db_yesterday_price[i] = stock_now_price.get(i);

			}

			int yesterday_price = 0;
			float rate = 0;
			int now_price = 0;

			// 다음날 주식으로 DB update
			for (int i = 0; i < 20; i++) {
				String name_l = Db_stock_name[i]; /// 0 대신 주식 이름 넣어야 함.
				yesterday_price = Db_yesterday_price[i];
				String sql_2 = "UPDATE all_stock SET STOCK_now_price = ?, stock_yesterday_price = ?, STOCK_RATE = ? WHERE STOCK_NAME = ?";
				rate = Math.round(-(1 - stock_rate[i]) * 10000.0) / 100.0f; // 등락률 집어 넣기(소수점 3번째 자리에서 반올림)
				float price = yesterday_price * stock_rate[i];
				now_price = (int) price;
				String name = name_l;

				psmt = conn.prepareStatement(sql_2);

				psmt.setInt(1, now_price);
				psmt.setFloat(2, yesterday_price);
				psmt.setFloat(3, rate);
				psmt.setString(4, name);

				row = psmt.executeUpdate();

				
			} // all_stock update 끝

//=============================================================================================

			// my_stock 수정 시작

			String sql_3 = "select purchased_stock_amount, stock_count, stock_name from my_stock";
			psmt = conn.prepareStatement(sql_3);

			rs = psmt.executeQuery();

			ArrayList<Integer> purchased_prices = new ArrayList<Integer>();
			ArrayList<Integer> counts = new ArrayList<Integer>();
			ArrayList<String> my_stock_names = new ArrayList<String>();

			while (rs.next()) {
				int price = rs.getInt("purchased_stock_amount");
				int cnot = rs.getInt("stock_count");
				String name = rs.getString("stock_name");
				purchased_prices.add(price);
				counts.add(cnot);
				my_stock_names.add(name);
			}
			for (int i = 0; i < counts.size(); i++) {
				int purchased_price = purchased_prices.get(i);
				int count = counts.get(i);
				String my_stock_name = my_stock_names.get(i);

				int first_price = purchased_price * count; // 수익률 계산 시 필요(전체 금액/보유주)
				int yield = now_price / first_price;
				String sql_4 = "update my_stock set stock_yield = ?, current_stock_amount = ? where stock_name = ?";

				psmt = conn.prepareStatement(sql_4);

				psmt.setInt(1, yield);
				psmt.setInt(2, now_price);
				psmt.setString(3, my_stock_name);
				row = psmt.executeUpdate();

			}
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				allClose();

			}

			cnt++;
			return cnt;
		}else{
			
		}return cnt;

	}

	public void art() {

		System.out.println("		▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}

		System.out.println("		▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		█░░░░░░░░▀█▄▀▄▀██████░▀█▄▀▄▀██████░");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		░░░░░░░░░░░▀█▄█▄███▀░░░ ▀██▄█▄███▀░");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⡴⠛⠉⣤⣌⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠙⠁⢠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣄⣤⣀⣀⣴⣿⡿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡧⠜⠿⠁⠀⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⣀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⡀⢀⡀⠀⠉⠛⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⢸⣇⠀⠀⠀⠀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⡀⢀⡀⠀⠉⠛⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⢸⣿⡀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⣿⡇⠸⣶⣶⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("		⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣭⣭⣾⣿⣿⣾⣭⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}

		System.out.println("□□□□□□□■■□■□□□□□□□□□□■■□□□■■□□□□□□□□□□□□□□■■■■■■■■■■□□■■■■■■■■■■□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□■■■■■□■■□■□□■■■■■■■□■■□□□■■□□□□□□□□□□□□□□■■□□□■■□□□□□■■□□□■■□□□□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□□□□■■□■■□■□□■■□□□■■□■■□□□■■□□□□□□□□□□□□□□■■□□□■■□□□□□■■□□□■■□□□□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□□□□■■□■■□■□□■■□□□■■□■■□□□■■■■■■■■■□□□□□□□■■■■■■■■■■□□■■■■■■■■■■□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□□□□■■□■■□■□□■■□□□■■□■■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□□□■■■□■■■■□□■■□□□■■□■■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□□■■■□□■■□■□□■■□□□■■□■■□■■■■■■■■■■■■□□□□□■■■■■■■■■■■■■■■■■■■■■■■■");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□■■■□□□■■□■□□■■□□□■■□■■□□□□□□□□□□□□□□□□□□□□□□□■■□□□□□□□□□□■■□□□□□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("■■■□□□□■■□■□□■■■■■■■□■■□□□■■□□□□□□□□□□□□□□□■□□■■□□□□□□□■□□■■□□□□□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□□□□□□□■■□■□□□□□□□□□□■■□□□■■□□□□□□□□□□□□□□□■□□■■□□□□□□□■□□■■□□□□□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□□□□□□□■■□■□□□□□□□□□□■■□□□■■□□□□□□□□□□□□□□□■□□□□□□□□□□□■□□□□□□□□□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
		System.out.println("□□□□□□□■■□■□□□□□□□□□□■■□□□■■■■■■■■■□□□□□□□□■■■■■■■■■□□□■■■■■■■■■□");
		try {
			Thread.sleep(150);
		} catch (Exception e) {
		}
	}
}
