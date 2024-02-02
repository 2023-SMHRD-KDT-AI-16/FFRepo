package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javazoom.jl.player.MP3Player;

public class MainController extends DBcontroller {
	ArrayList<MusicVO> musicList = new ArrayList<MusicVO>(1);
	MP3Player mp3 = new MP3Player();
	

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

	// all_stock 등락률 이용해 다음날로 넘어갈 수 있도록 하는 메소드(하루 마감)
	public int stock_Rate_Update() {
		int cnt = 0;
		
		if (cnt < 20) {

			cnt++;
			Random random = new Random();

			float[] stock_rate = new float[20];
			float randomFloatInRange = 0;

			for (int i = 0; i < all_stocks.size(); i++) {
				do {// do-while문 사용해 두 조건 아니면 다시 random값 받기
					randomFloatInRange = random.nextFloat(); // 등락률 퍼센트로 -30 ~ 30를 나타냄
					if (randomFloatInRange >= 0.7) {
						stock_rate[i] = randomFloatInRange;
					} else if (randomFloatInRange <= 0.3) {
						stock_rate[i] = 1 + randomFloatInRange;
					}
				} while (!(randomFloatInRange >= 0.7 || randomFloatInRange <= 0.3));
			}

			// 다음날 주식으로 DB update
			getConn();
			try {
				for (int i = 0; i < all_stocks.size(); i++) {
					String name = all_stocks.get(i).getStockName(); // 전체 주식을 담고 있는 어레이리스트에서 i번째 순서의 있는 StockVO 객체의 이름을
																	// name에 담기
					int yesterday_price = all_stocks.get(i).getNowPrice(); // 현재 가격을 어제의 가격으로 이동
					float rate = Math.round((stock_rate[i] - 1) * 10000.0) / 100.0f; // 등락률 집어 넣기(소수점 3번째 자리에서 반올림)
					int now_price = (int) (yesterday_price * stock_rate[i]); // 현재가격*등락률을 새로운 현재 가격으로 이동

					String sql = "UPDATE all_stock SET STOCK_now_price = ?, stock_yesterday_price = ?, STOCK_RATE = ? WHERE STOCK_NAME = ?";
					psmt = conn.prepareStatement(sql);

					psmt.setInt(1, now_price);
					psmt.setInt(2, yesterday_price);
					psmt.setFloat(3, rate);
					psmt.setString(4, name);

					psmt.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				allClose();
			}
		} // if문 끝
		return cnt;
	} 

	// my_stock 다음 날로 업데이트
	public void next_day() {

		for (int i = 0; i < my_stocks.size(); i++) {
			getConn();
			int my_purchased_amount = my_stocks.get(i).getPurchased_stock_amount(); // i번째에 해당하는 기업의 주식을 구매한 총 금액
			int my_count = my_stocks.get(i).getStock_count(); // i번째에 해당하는 기업의 주식을 몇 주나 가지고 있는지
			String my_stock_name = my_stocks.get(i).getStock_name(); // i번째 기업의 이름
			int my_current_price = all_stocks.get(i).getNowPrice() * my_count; // 수익률 계산 시 필요(전체 금액/보유 수량)
			float yield = (float) (my_current_price / my_purchased_amount);

			try {
				String sql = "update my_stock set stock_yield = ?, current_stock_amount = ? where stock_name = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setFloat(1, yield);
				psmt.setInt(2, all_stocks.get(i).getNowPrice());
				psmt.setString(3, my_stock_name);

				psmt.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally {
				allClose();
			}
		} // for문 끝
	}
	
	// 사람 바뀔 때 초기화하는 코드
	public void new_people() { 
		int[] today_stock = { 74600, 134400, 389000, 832000, 60300, 114700, 224000, 182700, 448000, 213500, 461000,
								3992500, 147000, 55500, 65900, 44150, 22700, 26600, 97000, 54000 };
		int[] yesterday_stock = new int[20];
		float[] stock_rate = { 1.36f, 1.28f, 2.10f, 0.36f, 1.01f, 8.00f, 7.45f, 2.18f, 2.52f, 5.43f, 7.33f, 4.24f,
								1.01f, 5.31f, 7.34f, 3.65f, 3.64f, 2.50f, 2.10f, 3.46f };
		String[] stock_name = { "삼성전자", "SK하이닉스", "LG에너지솔루션", "삼성바이오로직스", "삼성전자우", "기아", "현대차", "셀트리온", "POSCO홀딩스",
								"NAVER", "LG화학", "삼성SDI", "삼성물산", "카카오", "KB금융", "신한지주", "현대모비스", "포스코퓨처엠", "LG전자", "하나금융지수" };

		for (int i = 0; i < 20; i++) {

			getConn();

			try {// 게임 끝나면 전체 주식 창의 가격 초기 값으로 이동
				String sql = "update all_stock set stock_now_price = ?, stock_yesterday_price = ?, stock_rate = ? where stock_name = ? ";

				psmt = conn.prepareStatement(sql);

				psmt.setInt(1, today_stock[i]);
				psmt.setInt(2, yesterday_stock[i]);
				psmt.setFloat(3, stock_rate[i]);
				psmt.setString(4, stock_name[i]);

				psmt.executeUpdate();

				String sql_2 = "delete from my_stock";

				psmt = conn.prepareStatement(sql_2);

				psmt.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				allClose();
			}

		}
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
