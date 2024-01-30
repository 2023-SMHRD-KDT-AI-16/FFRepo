import java.util.ArrayList;
import java.util.Scanner;

public class maber_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		main문 코드 입니다
		Scanner sc = new Scanner(System.in);
		MemberDAO mdao = new MemberDAO();

		while (true) {
			System.out.println("회원관리 프로그램");
			System.out.println("1.전체 회원 조회 2.회원가입 3.회원정보 삭제 4. 비밀번호 수정 5. 성으로 검색  6. 종료");

			int choice = sc.nextInt();
			if (choice == 6) {
				System.out.println("프로그램 종료");
				break;
			} else if (choice == 5) {
				System.out.println("검색 할 성을 입력하세요 >>");
				String family_name = sc.next();
				ArrayList<MemberDTO> dtoList = mdao.selectSearchMember(family_name);

				for (int i = 0; i < dtoList.size(); i++) {
					// System.out.println(dtoList.get(i)); // 주소값
					System.out.println("id :" + dtoList.get(i).getId());
					System.out.println("pw :" + dtoList.get(i).getPw());
					System.out.println("name :" + dtoList.get(i).getName());
					System.out.println("age :" + dtoList.get(i).getAge());
					System.out.println();
				}

			}

			else if (choice == 1) {
				mdao = new MemberDAO();
				ArrayList<MemberDTO> dtoList = mdao.selectMember();

//				for (int i = 0; i < dtoList.size(); i++) {
//					// System.out.println(dtoList.get(i)); // 주소값
//					System.out.println("id :" + dtoList.get(i).getId());
//					System.out.println("pw :" + dtoList.get(i).getPw());
//					System.out.println("name :" + dtoList.get(i).getName());
//					System.out.println("age :" + dtoList.get(i).getAge());
//				}

				for (MemberDTO x : dtoList) {
					System.out.println("id :" + x.getId());
					System.out.println("pw :" + x.getPw());
					System.out.println("name :" + x.getName());
					System.out.println("age :" + x.getAge());
					System.out.println();
				}

			} else if (choice == 2) {
				System.out.println("회원가입 페이지 입니다.");

				System.out.print("id를 입력하세요 :");
				String join_id = sc.next();

				System.out.print("pw를 입력하세요 :");
				String join_pw = sc.next();

				System.out.print("name를 입력하세요 :");
				String join_name = sc.next();

				System.out.print("age를 입력하세요 :");
				int join_age = sc.nextInt();

				mdao = new MemberDAO();
				MemberDTO mdto = new MemberDTO(join_id, join_pw, join_name, join_age);

				int row = mdao.insertMember(mdto);

				if (row > 0) {
					System.out.println("insert success");
				} else {
					System.out.println("insert fail");
				}

			} else if (choice == 3) {
				System.out.println("회원정보 삭제 페이지 입니다.");
				System.out.print("삭제할 id를 입력하세요 :");
				String delete_id = sc.next();

				mdao = new MemberDAO();
				int row = mdao.deleteMember(delete_id);

				if (row > 0) {
					System.out.println("delete success");
				} else {
					System.out.println("delete fail");
				}

			} else if (choice == 4) {
				System.out.println("회원정보 수정 페이지 입니다.");

				System.out.print("id를 입력하세요 :");
				String update_id = sc.next();

				System.out.print("pw를 입력하세요 :");
				String update_pw = sc.next();

				mdao = new MemberDAO();
				int row = mdao.updateMember(update_id, update_pw);

				if (row > 0) {
					System.out.println("update success");
				} else {
					System.out.println("update fail");
				}

			} else {
				System.out.println("잘못 눌렀습니다!");
			}
		}
	}

}
