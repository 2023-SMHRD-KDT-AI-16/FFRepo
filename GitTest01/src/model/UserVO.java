package model;

public class UserVO {
	private String user_id;
	private String user_pw;
	private int my_money;
	
	
	public UserVO(String user_id, String user_pw) {
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.my_money = 50000000;
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

}
