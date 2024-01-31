package model;

public class StockVO {
	
	private String stockName;
	private int stockCount;
	private int buyPrice;
	private int nowPrice;
	
	
	public StockVO(String stockName, int stockCount, int buyPrice, int nowPrice) {
		super();
		this.stockName = stockName;
		this.stockCount = stockCount;
		this.buyPrice = buyPrice;
		this.nowPrice = nowPrice;
	}
	
	
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public int getNowPrice() {
		return nowPrice;
	}
	public void setNowPrice(int nowPrice) {
		this.nowPrice = nowPrice;
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
