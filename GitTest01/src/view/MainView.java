package view;

import java.util.Scanner;

public class MainView {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int sel; // 사용자의 선택
		
		while(true) {
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
				System.out.println("[1]회원가입 진행 [2] 뒤로가기");
				sel = sc.nextInt();
				if(sel==1) {//회원가입 진행
				
				
//				ArrayList<UserVO> dtoList =  //회원정보 배열 만들기
					
				System.out.print("id를 입력하세요 : ");
				String user_id = sc.next();
				
				//	ID 중복시 실행	 if(join_id ==  )
				
				
				System.out.print("pw를 입력하세요 : ");
				String user_pw = sc.next();
				
				System.out.println("회원가입 성공!");
				break;
				// DB에 회원가입정보 저장
				// DAO ?? = new DAO();
				// DTO ?? = new DTO(user_id,user_pw);
				
				
				
				}else if(sel ==2) {//뒤로가기
					break;
				}else {//잘못입력
					System.out.println("잘못 입력된 값 입니다.");
					break;
				}
				
				
				
			case 2:
				//로그인
				System.out.println("====로그인 창입니다====");
				System.out.println("[1]로그인 진행 [2]뒤로가기");
				sel = sc.nextInt();
				if(sel == 1) {//로그인 진행
				
				
				System.out.print("id를 입력하세요 : ");
				String user_id = sc.next();
				
				
				System.out.print("pw를 입력하세요 : ");
				String user_pw = sc.next();
				
			
				
				//join_id와 join_pw가 db에 저장된 값과 일치하면 로그인성공 출력
				System.out.println("로그인 성공!");
				break;
				
				
				}else if(sel == 2) {//뒤로가기
					break;
				}else {//잘못입력
					System.out.println("잘못 입력된 값입니다.");
					break;
				}
				
			case 3:
				//게임시작
				
				
				
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
