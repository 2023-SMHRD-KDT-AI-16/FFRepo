package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.DBcontroller;
import controller.DbStockSearch_controller;
import model.StockVO;
import model.UserVO;

public class MainView {

	public static void main(String[] args) {
		int my_money=50000000; 
		int my_yield  = 0;
		String my_item = null;
		
		DBcontroller mdao = new DBcontroller();
		
		Scanner sc = new Scanner(System.in);
		
		int sel; // 사용자의 선택
		
		while(true) {
			System.out.println("*개미는 뚠뚠*");
			System.out.println("[1]회원가입 [2]로그인 [3]게임시작 [4]전체순위조회 [5]종료");
			sel = sc.nextInt();
			
			switch(sel) {
			case 5:
				System.out.println("종료되었습니다.");
				break;
				//종료
			
			
			case 1:
				//회원가입
				System.out.println("====회원가입 창입니다====");
				System.out.println("[1]뒤로가기 [2]회원가입 진행");
				sel = sc.nextInt();
				
				 if(sel ==1) {//뒤로가기
						break;
					}
				else if(sel==2) {//회원가입 진행
				
				System.out.print("id를 입력하세요 : ");
				String user_id = sc.next();
				
				//	ID 중복시 실행	 if(join_id ==  )
				
				System.out.print("pw를 입력하세요 : ");
				String user_pw = sc.next();
				
				mdao = new DBcontroller();
				
				UserVO uv = new UserVO(user_id, user_pw, my_money, my_yield) ;
				int row = mdao.insertMember(uv);
				
				if(row>0) {
					System.out.println("회원가입 성공!");
				}else {
					System.out.println("회원가입 실패!");
				}
				
				
				
				
				break;
				// DB에 회원가입정보 저장
				// DAO ?? = new DAO();
				// DTO ?? = new DTO(user_id,user_pw);
				
				
				
				}else if(sel ==1) {//뒤로가기
					break;
				}else {//잘못입력
					System.out.println("잘못 입력된 값 입니다.");
					break;
				}
				
				
				
			case 2:
				//로그인
				System.out.println("====로그인 창입니다====");
				System.out.println("[1]뒤로가기 [2]로그인 진행");
				sel = sc.nextInt();
				if(sel == 1) {//뒤로가기
					break;
				}
				else if(sel == 2) {//로그인 진행
				
				
				System.out.print("id를 입력하세요 : ");
				String user_id = sc.next();
				
				
				System.out.print("pw를 입력하세요 : ");
				String user_pw = sc.next();
				
				mdao = new DBcontroller();
				int row = mdao.userLogin(user_id, user_pw);
				if(row>0) {
					System.out.println("로그인 성공");
				}else {
					System.out.println("로그인 실패");
				}
				
				
				//join_id와 join_pw가 db에 저장된 값과 일치하면 로그인성공 출력
				
				break;
				
				
				
				}else {//잘못입력
					System.out.println("잘못 입력된 값입니다.");
					break;
				}
				
			case 3:
				//게임시작
				System.out.println("====게임 화면 ====");
				System.out.println("[1]메인화면 [2]전체 주식종목 [3]종목검색 [4]보유 주식 [5]아이템 상점 [6]하루 마감하기");
				sel = sc.nextInt();
				
				
				if(sel==1) {//메인화면 
					break;
				}else if(sel ==2) {//전체 주식 종목
					System.out.println("전체 주식 종목");
					mdao = new DBcontroller();
					
					
					ArrayList<StockVO> svoList = mdao.selectMember();
					int Stock_cnt = 0; // 주식종목 인덱스
					
					
					for(StockVO e : svoList ) {
						Stock_cnt++;
						System.out.println("==="+(Stock_cnt)+"===");
						System.out.println(e.getStockName());
						System.out.println("시가 :" + e.getNowPrice());
						System.out.println("전일가 : " + e.getYesterdayPrice());
						System.out.println("등락률 : "+e.getRate());
					}
					
					sel = sc.nextInt();
					System.out.println("[1]뒤로가기 [2]번호 입력");
					int Stock_index = sc.nextInt()-1;
					
					
					break;
					
					
					
					
					
					
				}else if(sel ==3) {//종목 검색
					System.out.println("종목검색");
					
					System.out.println("[1]뒤로가기 [2]종목 검색하기");
					sel = sc.nextInt();
					if(sel == 1) {//뒤로 가기
						break;
					}else if(sel == 2) {//종목 검색하기
						System.out.print("검색할 종목 이름 : ");
						String stockName = sc.next();
						
						//검색한 종목이 DB와 일치하면 해당 종목으로 이동
						DbStockSearch_controller dao2 = new DbStockSearch_controller();
						ArrayList<StockVO> SearchList = dao2.searchStock(stockName);
						
						
						
						
						//해당 종목 정보 보여주기(출력) -- 현재 시가, 등락율 표시
						for(StockVO e : SearchList) {
							System.out.println(e.getStockName());
							System.out.println(e.getNowPrice());
						}
						
						
						
						
						System.out.println("[1]뒤로가기 [2]매수하기 [3]매도하기");
						sel =sc.nextInt();
						if(sel == 1) {//뒤로가기
							break;
						}else if(sel == 2) {//매수하기
							System.out.println("==매수하기 화면==");
							System.out.println("[1]뒤로가기 [2]매수 수량 입력");
							int choice = sc.nextInt();
							if(choice == 1) {//뒤로가기
								break;
							}else if(choice == 2) {//매수 수량 입력
								System.out.print("매수 갯수 : ");
								int stockCount = sc.nextInt();
								
								//매수 메소드 적용 칸
								System.out.println("주식이름 "+stockCount+"주 매수완료");
								break;
							}
							
						}else if(sel == 3) {//매도하기
							System.out.println("==매도하기 화면==");
							System.out.println("[1]뒤로가기 [2]주문 수량");
							
							
						}
						
						
						
						
					}
					
					
				}else if(sel == 4) {//보유 주식
					
				}else if(sel == 5) {//아이템 상점
					
				}else if(sel == 6) {
					
				}
				
				
				
				
				
				
				
				
			case 4:
				//전체순위조회
			
				
			
			
			}//switch sel
			
			
			
			
			
		}
		
		
		
	        
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//  =====================아래것은 MP3 프로젝트 할때 클래스들이니 참고하세요 주석처리함.
//		//View 의 역할 : 사용자의 인터페이스 U
//		//기능을 사용자가 선택할 수 있도록 만들기.
//		//재생, 정지, 다음곡, 이전곡, 검색, 종료
//						
//		Scanner sc = new Scanner(System.in);
//		MainController c = new MainController();
//		
//		int sel; // 사용자의 선택
//		while (true) {
//			System.out.println("[1]재생 [2]정지 [3]다음곡 [4]이전곡 [5]음악찾기 [6]종료 [7]전체목록보기 [8]랜덤재생  >>");
//			 sel = sc.nextInt();
//			 
//			 switch (sel) {
//			case 1:
//				c.play();
//				break;
//			case 2:
//				c.stop();
//				break;
//			case 3:
//				c.next();
//				break;
//			case 4:
//				c.pre();
//				break;
//			case 5:
//				System.out.println("재생할 음악 이름");
//				c.search(sc.next());
//				break;
//			case 6:
//				System.exit(0);
//				break;
//			case 7:
//				c.allShow();
//				break;	
//			case 8:
//				c.randomPlay();
//				break;
//			default :
//				break;
//
//			}
//			
//			
//		}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//				GUI
//	
//        Frame f= new Frame();
//        f.setTitle("첫 번째 프레임 입니다.");
//        f.setBounds(100, 100, 300, 300);
//        f.setVisible(true);

//	new Button1("버튼 만들기");
	
//	  JFrame frame = new JFrame("Input Box 예제");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300, 150);
//
//        JPanel panel = new JPanel();
//        frame.add(panel);
//
//        JLabel label = new JLabel("입력하세요:");
//        panel.add(label);
//
//        JTextField textField = new JTextField(20);
//        panel.add(textField);
//
//        JButton button = new JButton("출력");
//        panel.add(button);
//
//        // 버튼 클릭 이벤트 처리
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String inputText = textField.getText(); // 입력된 텍스트 가져오기
//                JOptionPane.showMessageDialog(null, "입력된 내용: " + inputText, "출력", JOptionPane.INFORMATION_MESSAGE);
//                System.out.println(inputText);
//            }
//        });
//
//        // UI 표시
//        frame.setVisible(true);

	}

}
