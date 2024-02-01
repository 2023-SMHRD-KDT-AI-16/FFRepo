package model;

public class UserVO
{
	private String user_id;
	private int my_money; 
	private int my_yield;
	
	public UserVO(String user_id, int my_money, int my_yield) {
//		super();
		this.user_id = user_id;
		this.my_money = my_money;
		this.my_yield = my_yield;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	@Override
	public String toString() {
		return "UserVO [user_id=" + user_id + ", my_money=" + my_money + ", my_yield=" + my_yield + "]";
	}
	
	
	

}
