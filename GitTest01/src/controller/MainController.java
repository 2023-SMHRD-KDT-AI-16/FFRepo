package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javazoom.jl.player.MP3Player;
import model.MyStockVO;
import model.StockVO;

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

	// all_stock 등락률 이용해 다음날로 넘어갈 수 있도록 하는 메소드(하루 마감)
	public int stock_Rate_Update() {
	
		if(cnt<20) {
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

			ArrayList<MyStockVO> my_stocks = select_my_stock();
			for (int i = 0; i < my_stocks.size(); i++) { // DB에 있는 1~20위 종목 이름
				Db_stock_name[i] = my_stocks.get(i).getStock_name();
				Db_yesterday_price[i] = my_stocks.get(i).getCurrent_stock_amount();
			}

			int yesterday_price = 0;
			float rate = 0;
			int now_price = 0;
			float price = 0;

			// 다음날 주식으로 DB update
			getConn();
			try {
				for (int i = 0; i < 20; i++) {
					String name = Db_stock_name[i]; // 0 대신 주식 이름 넣어야 함.
					yesterday_price = Db_yesterday_price[i];
					rate = Math.round(-(1 - stock_rate[i]) * 10000.0) / 100.0f; // 등락률 집어 넣기(소수점 3번째 자리에서 반올림)
					price = yesterday_price * stock_rate[i];
					now_price = (int) price;

					String sql = "UPDATE all_stock SET STOCK_now_price = ?, stock_yesterday_price = ?, STOCK_RATE = ? WHERE STOCK_NAME = ?";
					psmt = conn.prepareStatement(sql);

					psmt.setInt(1, now_price);
					psmt.setInt(2, yesterday_price);
					psmt.setFloat(3, rate);
					psmt.setString(4, name);

					row = psmt.executeUpdate();
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				allClose();
			
		}
			cnt++;
		}//if cnt
		return cnt;
	} // all_stock update 끝

	// =============================================================================================

	// my_stock 수정 시작
	public void next_day() {
		ArrayList<MyStockVO> my_stocks = select_my_stock();
		ArrayList<StockVO> all_stocks = select_all_stock();
		for (int i = 0; i < my_stocks.size(); i++) {
			getConn();
			int my_purchased_amount = my_stocks.get(i).getPurchased_stock_amount();
			int my_count = my_stocks.get(i).getStock_count();
			String my_stock_name = my_stocks.get(i).getStock_name();

			int my_current_price = all_stocks.get(i).getNowPrice() * my_count; // 수익률 계산 시 필요(전체 금액/보유주)

			float yield = (float)my_current_price / (float)my_purchased_amount;
			String sql = "update my_stock set stock_yield = ?, current_stock_amount = ? where stock_name = ?";

			try {
				psmt = conn.prepareStatement(sql);
				psmt.setFloat(1, yield);
				psmt.setInt(2, all_stocks.get(i).getNowPrice() );
				psmt.setString(3, my_stock_name);
				int row = psmt.executeUpdate();
				
				
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
	
	
	public void new_people() {
		int[] today_stock = {74600, 134400, 389000, 832000, 60300, 114700,224000,182700,448000,213500,461000,3992500,147000,55500,65900,44150,22700,26600,97000,54000};
		int[] yesterday_stock = new int[20];
		float[] stock_rate = {1.36f, 1.28f, 2.10f, 0.36f, 1.01f, 8.00f, 7.45f, 2.18f, 2.52f, 5.43f, 7.33f, 4.24f, -1.01f, 5.31f, 7.34f, 3.65f, 3.64f, 2.50f, 2.10f, 3.46f };
		String [] stock_name = {"삼성전자", "SK하이닉스", "LG에너지솔루션", "삼성바이오로직스", "삼성전자우", "기아", "현대차", "셀트리온", "POSCO홀딩스", "NAVER", "LG화학", "삼성SDI", "삼성물산", "카카오", "KB금융", "신한지주", "현대모비스", "포스코퓨처엠", "LG전자", "하나금융지수"};
		
		for(int i = 0; i < 20; i++) {
			yesterday_stock[i] = today_stock[i] * (int)stock_rate[i];
			int yes_stoc = yesterday_stock[i];
			int to_stoc = today_stock[i];
			float st_rate = stock_rate[i];
			String st_name = stock_name[i];
			
			
			
			getConn();
			try {
				
				String sql = "update all_stock set stock_now_price = ?, stock_yesterday_price = ?, stock_rate = ? where stock_name = ? ";
				
				psmt = conn.prepareStatement(sql);
				
				psmt.setInt(1, to_stoc);
				psmt.setInt(2, yes_stoc);
				psmt.setFloat(3, st_rate);
				psmt.setString(4, st_name);
				
				int row = psmt.executeUpdate();
				
				
				String sql_2 = "delete from my_stock";
				
				psmt = conn.prepareStatement(sql_2);
				
				psmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				allClose();
			}
			
			
			
		}
	}
}
