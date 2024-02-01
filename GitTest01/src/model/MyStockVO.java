package model;

public class MyStockVO {

	private int purchased_stock_amount;
	private int current_stock_amount;
	private String stock_name;
	private int stock_yield;
	private int stock_count;
	
	
	public MyStockVO(int purchased_stock_amount,int current_stock_amount, String stock_name, int stock_yield, int stock_count ) {
		this.purchased_stock_amount = purchased_stock_amount;
		this.current_stock_amount = current_stock_amount;
		this.stock_name = stock_name;
		this.stock_yield = stock_yield;
		this.stock_count = stock_count;
		
		
	}


	public int getPurchased_stock_amount() {
		return purchased_stock_amount;
	}


	public void setPurchased_stock_amount(int purchased_stock_amount) {
		this.purchased_stock_amount = purchased_stock_amount;
	}


	public int getCurrent_stock_amount() {
		return current_stock_amount;
	}


	public void setCurrent_stock_amount(int current_stock_amount) {
		this.current_stock_amount = current_stock_amount;
	}


	public String getStock_name() {
		return stock_name;
	}


	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}


	public int getStock_yield() {
		return stock_yield;
	}


	public void setStock_yield(int stock_yield) {
		this.stock_yield = stock_yield;
	}


	public int getStock_count() {
		return stock_count;
	}


	public void setStock_count(int stock_count) {
		this.stock_count = stock_count;
	}


	@Override
	public String toString() {
		return "MyStockVO [purchased_stock_amount=" + purchased_stock_amount + ", current_stock_amount="
				+ current_stock_amount + ", stock_name=" + stock_name + ", stock_yield=" + stock_yield
				+ ", stock_count=" + stock_count + "]";
	}


	
	
	
	
	
	
}
