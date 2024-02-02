package model;

public class StockVO {

	private String stockName;
	private int yesterdayPrice;
	private int nowPrice;
	private float rate;

	public StockVO(String stockName, int yesterdayPrice , int nowPrice, float rate) {
//		super();
		this.stockName = stockName;
		this.yesterdayPrice = yesterdayPrice;
		this.nowPrice = nowPrice;
		this.rate = rate;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public int getYesterdayPrice() {
		return yesterdayPrice;
	}

	public void setYesterdayPrice(int yesterdayPrice) {
		this.yesterdayPrice = yesterdayPrice;
	}

	public int getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(int nowPrice) {
		this.nowPrice = nowPrice;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "StockVO [stockName=" + stockName + ", yesterdayPrice=" + yesterdayPrice + ", nowPrice=" + nowPrice
				+ ", rate=" + rate + "]";
	}
	
	
	
}
