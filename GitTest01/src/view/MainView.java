package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.DBcontroller;
import controller.DbStockSearch_controller;
import model.StockVO;
import model.UserVO;

public class MainView {

	public static void main(String[] args) {
		int my_money = 50000000;
		int my_yield = 0;
		String my_item = null;

		DBcontroller mdao = new DBcontroller();

		Scanner sc = new Scanner(System.in);
		int cnt = 0;

		while(cnt!=1) {
			System.out.println("*개미는 뚠뚠*");
			System.out.println("[1]회원가입 [2]로그인 [3]게임시작 [4]전체순위조회 [5]종료");
			int sel; // 사용자의 선택
			sel = sc.nextInt();

			switch (sel) {// 게임 종료
			case 5: //
				System.out.println("종료되었습니다.");
				cnt=1;
				// 종료

			case 1:
				// 회원가입
				System.out.println("====회원가입 창입니다====");
				System.out.println("[1]뒤로가기 [2]회원가입 진행");
				int sel2;
				sel2 = sc.nextInt();

				switch(sel2) {//회원가입 스위치문
				case 1:
					break;

				case 2:
					System.out.print("id를 입력하세요 : ");
					String user_id = sc.next();

					// ID 중복시 실행 if(join_id == )

					System.out.print("pw를 입력하세요 : ");
					String user_pw = sc.next();

					mdao = new DBcontroller();

					UserVO uv = new UserVO(user_id, user_pw, my_money, my_yield);
					int row = mdao.insertMember(uv);

					if (row > 0) {
						System.out.println("회원가입 성공!");
					} else {
						System.out.println("회원가입 실패!");
					}
					break;


					// DB에 회원가입정보 저장
					// DAO ?? = new DAO();
					// DTO ?? = new DTO(user_id,user_pw);

				default :
					// 잘못입력
					System.out.println("잘못 입력된 값 입니다.");
					System.out.println();
					break;


				}//switch
				break;


			case 2:
				// 로그인
				System.out.println("====로그인 창입니다====");
				System.out.println("[1]뒤로가기 [2]로그인 진행");
				int sel3;	
				sel3 = sc.nextInt();
				switch(sel3) {

				case 1 :// 뒤로가기
					break;
				case 2 :// 로그인 진행

					System.out.print("id를 입력하세요 : ");
					String user_id = sc.next();

					System.out.print("pw를 입력하세요 : ");
					String user_pw = sc.next();

					mdao = new DBcontroller();
					int row = mdao.userLogin(user_id, user_pw);
					if (row > 0) {
						System.out.println("로그인 성공");
					} else {
						System.out.println("로그인 실패");
					}

					// join_id와 join_pw가 db에 저장된 값과 일치하면 로그인성공 출력

					break;

				default : // 잘못입력
					System.out.println("잘못된 입력입니다.");
					System.out.println();
					break;
				}//case 2 로그인switch


			case 3: // 게임시작



				System.out.println("====게임 화면 ====");
				System.out.println("[1]메인화면 [2]전체 주식종목 [3]종목검색 [4]보유 주식 [5]아이템 상점 [6]하루 마감하기");
				sel = sc.nextInt();

				switch(sel) {  // 게임화면 switch
				case 1:// 메인화면
					break;
				case 2: // 전체 주식 종목

					System.out.println("전체 주식 종목");
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
					}

					System.out.println();

					// 해당 인덱스번호 주식으로 이동

					System.out.println("[1]뒤로가기 [2]번호 입력");
					int sel8;
					sel8 = sc.nextInt();
					switch(sel8) {
					case 1 : //뒤로가기	
						break;

					case 2 : //번호 입력

						int Stock_index = sc.nextInt() - 1;

						break;

					default : 
						System.out.println("잘못된 입력입니다.");
						break;

					
					
					}//
					break;

				case 3: // 종목 검색
					System.out.println("종목검색");

					System.out.println("[1]뒤로가기 [2]종목 검색하기");
					sel = sc.nextInt();
					switch(sel){
					case 1 : // 뒤로 가기
						break;

					case 2 : // 종목 검색하기
						System.out.print("검색할 종목 이름 : ");
						String stockName = sc.next();

						// 검색한 종목이 DB와 일치하면 해당 종목으로 이동
						DbStockSearch_controller dao2 = new DbStockSearch_controller();
						ArrayList<StockVO> SearchList = dao2.searchStock(stockName);

						// 해당 종목 정보 보여주기(출력) -- 현재 시가, 등락율 표시
						for (StockVO e : SearchList) {
							System.out.println(e.getStockName());
							System.out.println(e.getNowPrice());
						}

						System.out.println("[1]뒤로가기 [2]매수하기 [3]매도하기");
						sel = sc.nextInt();
						switch(sel){
						case 1  :  // 뒤로가기
							break;
						case 2 :  // 매수하기
							System.out.println("==매수하기 화면==");
							System.out.println("[1]뒤로가기 [2]매수 수량 입력");

							int choice;
							choice = sc.nextInt();
							switch(choice){
							case 1 : // 뒤로가기	
								break;

							case 2 : // 매수 수량 입력
								System.out.print("매수 갯수 : ");
								int stockCount = sc.nextInt();

								// 매수 메소드 적용 칸
								System.out.println("주식이름 " + stockCount + "주 매수완료");
								break;
							default : 
								System.out.println("잘못된 입력입니다.");
								break;
							}


						case 3 :// 매도하기


							System.out.println("==매도하기 화면==");
							System.out.println("[1]뒤로가기 [2]매도 수량 입력");
							int sel5;
							sel5 = sc.nextInt();
							switch(sel5){
							case 1 : // 뒤로가기
								break;
							case 2 :  // 매도 수량 입력
								System.out.print("매도 갯수 : ");
								int stockCount = sc.nextInt();

								System.out.println(stockName + stockCount + "주 매도완료");

								mdao.stockSale(stockName, stockCount);

								break;

							default : 
								System.out.println("잘못된 입력입니다.");
								break;
							}//매도하기 switch

						}//switch 매매하기

					

				}//switch 종목검색
					break;

			case 4:// 보유 주식
				System.out.println("보유주식 페이지");
				System.out.println("[1]뒤로가기 [2]보유주식 이동");
				int choice;
				choice = sc.nextInt();
				
				
				switch(choice) {//보유주식 페이지 switch
				case 1 : //뒤로가기
					break;
				case 2 : //보유주식 
					System.out.println("보유주식 밑에 나열해야함");
					// 보유주식 나열해야함
					// 보유주식에서 인덱스를골라서 해당 주식페이지로 이동
				default : 
					System.out.println("잘못된 입력 값 입니다.");
					break;
				
				}//보유주식 페이지 switch
				
				break;
				


			case 5 : //아이템 상점
				System.out.println("아이템 상점");
				break;
			case 6 : //하루마감하기
				System.out.println("하루 마감하기");
				break;

			}//게임화면 switch 

				break;


			//break;

		case 4 : // 전체순위조회




		default:
			System.out.println("잘못된 입력입니다.3");
			System.out.println();


		}

	}// while 첫화면






}//main
}//class
