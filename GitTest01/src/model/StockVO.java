package model;

public class StockVO {
	
	private String user_id;
	private String user_pw;
	private int my_money;
	
	
	public StockVO(String user_id, String user_pw, int my_money) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.my_money = my_money;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getUser_pw() {
		return user_pw;
	}


	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}


	public int getMy_money() {
		return my_money;
	}


	public void setMy_money(int my_money) {
		this.my_money = my_money;
	}
	
	
	













//=====================아래것은 MP3 프로젝트 할때 클래스들이니 참고하세요 주석처리함.
//	// 음악의 설계도 만들기
//	// VO = Value Object
//	// 필드만들기->생성자를 이용해서 초기값을 가진 객체를 만들기.
//	// 제목, 가수명, 음악길이(초), 음악파일 경로
//
//	private String title;
//	private String singer;
//	private int playtime;
//	private String musicPath;
//	static int musicIndex = 0;
//
////생성자
//	public MusicVO(String title, String singer, int playtime, String musicPath) {
////		super();
//		this.title = title;
//		this.singer = singer;
//		this.playtime = playtime;
//		this.musicPath = musicPath;
//	}
//
////Getter
//	public String getTitle() {
//		return title;
//	}
//
//	public String getSinger() {
//		return singer;
//	}
//
//	public int getPlaytime() {
//		return playtime;
//	}
//
//	public String getMusicPath() {
//		return musicPath;
//	}
//
}
