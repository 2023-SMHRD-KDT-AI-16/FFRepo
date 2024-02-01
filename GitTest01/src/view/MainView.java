package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.DBcontroller;
import model.StockVO;

public class MainView {

	public static void main(String[] args) {
		int my_money = 50000000; // 초기자금
		int my_turn = 0; // 턴을 카운터 측정 30턴이면 종료

		DBcontroller mdao = new DBcontroller();

		Scanner sc = new Scanner(System.in);

		int sel = 0;
		int page = 0;

		while (true) {/// 메인 while
			// 페이지 메뉴 표시,선택
			switch (sel) {

			case 0:
				System.out.println("개미는 뚠뚠");// 초기 메인화면
				System.out.println("[1]게임시작 [2]랭킹보기 [3]룰 설명 [4]종료");
				sel = sc.nextInt();
				break;

			case 1: // 게임 화면

				System.out.println("[1]전체종목보기 [2]보유주식 [3]상점 [4]하루마감 [0]메인화면");
				int choice = sc.nextInt();

				if (choice == 1) {// 전체 종목

					System.out.println("전체 종목");
					mdao = new DBcontroller();
					ArrayList<StockVO> svoList = mdao.everyStock();
					int Stock_cnt = 0; // 주식종목 인덱스

					for (StockVO e : svoList) {
						Stock_cnt++;
						System.out.println("===" + (Stock_cnt) + "===");
						System.out.println(e.getStockName());
						System.out.println("시가 :" + e.getNowPrice());
						System.out.println("전일가 : " + e.getYesterdayPrice());
						System.out.println("등락률 : " + e.getRate());
					} // for 전체종목

					System.out.println("[1]뒤로가기 [2]주식번호 입력");
					int choice2 = sc.nextInt();

					if (choice2 == 1) {// 게임화면으로 뒤로가기
						break;
					} else if (choice2 == 2) {// 주식번호 입력
						System.out.print("주식번호 입력 : ");
						int stockNum = sc.nextInt();
//						ArrayList<StockVO> stockList = mdao.everyStock();
//						System.out.println(mdao.everyStock().get(stockNum).getStockName());
//						System.out.println(mdao.everyStock().get(stockNum).getNowPrice());
//						System.out.println(mdao.everyStock().get(stockNum).getYesterdayPrice());
//						System.out.println(mdao.everyStock().get(stockNum).getRate());
//						
			
						
						mdao.stockBuy(stockNum, 2);
					
						
						
//						for (StockVO x : stockList) {
//							System.out.println(x.getStockName());
//							System.out.println(x.getNowPrice());
//							System.out.println(x.getYesterdayPrice());
//							System.out.println(x.getRate());
//						}

					}

					// ==========choice 1 전체종목 =============
				} else if (choice == 2) {// 보유주식
					System.out.println("보유주식");

					// ===========choice 2 보유주식 ============
				} else if (choice == 3) {// 상점
					System.out.println("상점");

					// ===========choice 3 보유주식 ============
				} else if (choice == 4) {// 하루마감
					System.out.println("하루마감");

					// ===========choice 4 보유주식 ============
				} else if (choice == 0) {// 메인화면
					sel = 0;
					break;
				} else {
					System.out.println("잘못된 입력입니다 1");
				}

			case 2: // 랭킹보기

				break;

			case 3: // 룰설명
				System.out.println("룰설명");
				System.out.println("뒤로가기 [1]");
				sel = sc.nextInt();
				if (sel == 1) {
					sel = 0;
					break;
				}

				break;
			case 4: // 종료
				System.exit(0);

			}// switch 게임화면

		} // while -- 메인 while

	}// main
}// class
