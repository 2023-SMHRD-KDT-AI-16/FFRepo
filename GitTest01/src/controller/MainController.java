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
		public int stock_Rate_Update() throws SQLException, ClassNotFoundException {
			if (cnt < 21) {

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

				// 다음날 주식으로 DB update
				for (int i = 0; i < 20; i++) {
					String name = Db_stock_name[i]; // 0 대신 주식 이름 넣어야 함.
					yesterday_price = Db_yesterday_price[i];
					String sql = "UPDATE all_stock SET STOCK_now_price = ?, stock_yesterday_price = ?, STOCK_RATE = ? WHERE STOCK_NAME = ?";
					rate = Math.round(-(1 - stock_rate[i]) * 10000.0) / 100.0f; // 등락률 집어 넣기(소수점 3번째 자리에서 반올림)
					float price = yesterday_price * stock_rate[i];
					now_price = (int) price;

					psmt = conn.prepareStatement(sql);

					psmt.setInt(1, now_price);
					psmt.setFloat(2, yesterday_price);
					psmt.setFloat(3, rate);
					psmt.setString(4, name);

					row = psmt.executeUpdate();
				cnt++;
				}
			}finally {
				allClose();
			}return cnt;
		} // all_stock update 끝

	//=============================================================================================

		// my_stock 수정 시작
		public int next_day() {
			ArrayList<MyStockVO> my_stocks = select_my_stock();
			ArrayList<StockVO> all_stocks = select_all_stock();

		for(int i = 0; i < my_stocks.size(); i++){
			getConn();
			int my_purchased_amount = my_stocks.get(i).getPurchased_stock_amount();
			int my_count = my_stocks.get(i).getStock_count();
			String my_stock_name = my_stocks.get(i).getStock_name();

			int my_current_price =  all_stocks.get(i).getNowPrice()* my_count; // 수익률 계산 시 필요(전체 금액/보유주)
			
			int yield = my_current_price / my_purchased_amount;
			String sql = "update my_stock set stock_yield = ?, current_stock_amount = ? where stock_name = ?";

			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, yield);
			psmt.setInt(2, my_purchased_amount);
			psmt.setString(3, my_stock_name);
			int row = psmt.executeUpdate();

		}try
		{
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}catch(
		SQLException e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}finally
		{
			allClose();

		}

		cnt++;return cnt;

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
