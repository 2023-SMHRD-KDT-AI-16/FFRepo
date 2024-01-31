package model;

public class UserVO {
	private String user_id;
	private String user_pw;
	private int my_money; 
	private int my_yield;
	private String my_item;
	
	public UserVO(String user_id, String user_pw, int my_money, int my_yield, String my_item) {
//		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.my_money = my_money;
		this.my_yield = my_yield;
		this.my_item = my_item;
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
	public int getMy_yield() {
		return my_yield;
	}
	public void setMy_yield(int my_yield) {
		this.my_yield = my_yield;
	}
	public String getMy_item() {
		return my_item;
	}
	public void setMy_item(String my_item) {
		this.my_item = my_item;
	}
	
	

}
