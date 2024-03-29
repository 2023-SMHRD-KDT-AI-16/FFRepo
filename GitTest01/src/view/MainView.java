package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import controller.DBcontroller;
import controller.HaveStock;
import controller.MainController;
import model.MyStockVO;
import model.StockVO;
import model.UserVO;

public class MainView {
	static int score = 50000000; // 초기자금

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		DBcontroller mdao = new DBcontroller();
		MainController mco = new MainController();
		HaveStock hvo = new HaveStock();
		ArrayList<UserVO> uvoList = new ArrayList<UserVO>();

		Scanner sc = new Scanner(System.in);

		int sel = 0;

		mco.art();
		mco.play();
		while (true) {/// 메인 while
			// 페이지 메뉴 표시,선택
			switch (sel) {

			case 0:
				System.out.println("[1]게임시작 [2]랭킹보기 [3]룰 설명 [4]종료 ");// [97]닉네임 등록(시크릿창)
				sel = sc.nextInt();
				break;

			case 1: // 게임 화면
				System.out.println("현재 보유 금액 : " + score);
				System.out.println("[1]전체종목보기 [2]보유주식 [3]하루마감  [0]메인화면");
				int choice = sc.nextInt();

				// ==========choice 1 전체 종목 =============
				if (choice == 1) {// 전체 종목

					System.out.println("전체 종목");
					mdao = new DBcontroller();
					ArrayList<StockVO> svoList = mdao.everyStock();
					int Stock_cnt = 0; // 주식종목 인덱스

					for (StockVO e : svoList) {
						Stock_cnt++;
						System.out.println("===" + (Stock_cnt) + "===");
						System.out.println(e.getStockName());
						System.out.println("전일가 : " + e.getYesterdayPrice());
						System.out.println("시가 :" + e.getNowPrice());
						System.out.println("등락률 : " + e.getRate() + "%");
					} // for 전체종목

					System.out.println("[1]뒤로가기 [2]주식번호 선택");
					int choice2 = sc.nextInt();

					if (choice2 == 1) {// 게임화면으로 뒤로가기
						break;
					} else if (choice2 == 2) {// 주식번호 입력
						System.out.print("주식번호 입력 : ");
						int stockNum = sc.nextInt() - 1;

						ArrayList<StockVO> stockList = mdao.everyStock();
						System.out.println("종목이름 : " + mdao.everyStock().get(stockNum).getStockName());
						System.out.println("전일가 : " + mdao.everyStock().get(stockNum).getYesterdayPrice() + "원");
						System.out.println("시가 : " + mdao.everyStock().get(stockNum).getNowPrice() + "원");
						System.out.println("전일대비 등락률 : " + mdao.everyStock().get(stockNum).getRate() + "%");
						System.out.println("======");
						System.out.println("[1]매수하기 [2]매도하기 [3]뒤로가기");
						int choice3 = sc.nextInt();

						if (choice3 == 1) {// 매수하기

							System.out.print("매수량 : ");
							int buyCount = sc.nextInt();
							score = mdao.stockBuy(stockNum, buyCount, score);
						
						} else if (choice3 == 2) {// 매도하기

							System.out.print("매도량 : ");
							int sellCount = sc.nextInt();
							score = mdao.stockSale(stockNum, sellCount, score);
							System.out.println(
									mdao.everyStock().get(stockNum).getStockName() + " " + sellCount + "주 매도완료");

						} else if (choice3 == 3) {// 뒤로가기
							sel = 1;
							choice = 2;
							break;
						}

					} else {
						System.out.println("잘못된 입력입니다.");
					}

					// ==========choice 2 보유주식 =============
				} else if (choice == 2) {// 보유주식
					System.out.println("보유주식");

					ArrayList<MyStockVO> mvoList = hvo.MyStock();
					int Stock_cnt = 0;

					for (MyStockVO e : mvoList) {
						Stock_cnt++;
						System.out.println("===" + (Stock_cnt) + "===");
						System.out.println(e.getStock_name());
						System.out.println("보유 : " + e.getStock_count() + "주");
						System.out.println("시가 : " + e.getCurrent_stock_amount());
						System.out.println("수익률 : " + e.getStock_yield() + "%");
						System.out.println("평가 총액 : " + e.getPurchased_stock_amount() + "원");
					} // for 전체종목

					System.out.println("[1]주식번호 선택 [2]뒤로가기");
					int choice4 = sc.nextInt();
					if (choice4 == 1) {

						// 선택된 보유주식 출력
						System.out.print("주식번호 입력 : ");
						int select5 = sc.nextInt() - 1;

						MyStockVO igohome = mvoList.get(select5);
						System.out.println("===" + (select5 + 1) + "===");
						System.out.println(igohome.getStock_name());
						System.out.println("보유 : " + igohome.getStock_count() + "주");
						System.out.println("시가 : " + igohome.getCurrent_stock_amount());
						System.out.println("수익률 : " + igohome.getStock_yield() + "%");
						System.out.println("평가 총액 : " + igohome.getPurchased_stock_amount() + "원");

						System.out.println("[1]매수하기 [2]매도하기 [3]뒤로가기");
						System.out.print("번호 입력 : ");
						int numselect = sc.nextInt();

						if (numselect == 1) {
							System.out.print("매수량 : ");
							int buyCount = sc.nextInt();
							score = hvo.stockBuy(select5, buyCount);
							System.out.println(mvoList.get(select5).getStock_name() + " " + buyCount + "주 매수 완료");

							// 매수하기

						} else if (numselect == 2) {
							System.out.print("매도량 : ");
							int sellCount = sc.nextInt();
							score = hvo.stockSale(select5, sellCount);
							System.out.println(mvoList.get(select5).getStock_name() + " " + sellCount + "주 매도 완료");

						} else if (numselect == 3) {

						}

						// =====================

					} else if (choice4 == 2) {
						break;
					} else {
						System.out.println("잘못 입력");
					}

					// ===========choice 3 하루마감 ============
				} else if (choice == 3) {// 하루마감

					System.out.println("하루마감");
					int count = mco.stock_Rate_Update();
					mco.next_day();

					System.out.println(count + "턴 종료");

					if (count == 20) {

						System.out.print("닉네임을 입력해주세요 : ");
						String nickname = sc.next();
						mdao.insertName(nickname);
						mco.new_people();
						System.out.println("랭킹에 등록되었습니다.");
						score = 50000000;
						sel = 0;

//						sel = 97;

//						sel = 0;
//						break;
					} // if count
					break;
					// while'

					// ===========choice 4 메인화면 ============
				} else if (choice == 0) {// 메인화면
					sel = 0;
					break;
				} else {
					System.out.println("잘못된 입력입니다 ");
				}
				break;

			case 2: // 랭킹보기
				uvoList = mdao.userRank();
				for (int i = 1; i <uvoList.size(); i++) {
					System.out.print(i+"등 \t"+"이름 : "+uvoList.get(i).getUser_id()+ "\t");
					System.out.println("총 점수 : "+uvoList.get(i).getMy_money());
					
				}
				sel = 0;
				break;

			case 3: // 룰설명
				System.out.println("턴제식 주식모의투자\r\n" + "초기자금 : 5000만, 턴 20턴\r\n" + "턴 지날때마다 주식전체종목의 현재가 랜덤으로 변동\r\n"
						+ "(단 2턴마다 이벤트 발생)\r\n" + "하루마감 버튼입력시 당일 장 마감+다음턴으로 이동\r\n" + "마지막 20턴까지 끝낼 시 닉네임 등록후 랭킹적용");
				System.out.println("[1]뒤로가기");
				choice = sc.nextInt();
				if (choice == 1) {
					sel = 0;

					break;
				}

				break;

			case 4: // 종료
				System.exit(0);


			default:
				System.out.println("잘못된 입력입니다.");
				sel = 0;
				break;

			}// switch 게임화면

		} // while -- 메인 while

	}// main
}// class
