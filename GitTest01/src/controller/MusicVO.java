package controller;

public class MusicVO {
	private String title;
	private String singer;
	private int playtime;
	private String musicPath;
	static int musicIndex = 0;
	
	public MusicVO(String title, String singer, int playtime, String musicPath) {
		this.title = title;
		this.singer = singer;
		this.playtime = playtime;
		this.musicPath = musicPath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public int getPlaytime() {
		return playtime;
	}
	public void setPlaytime(int playtime) {
		this.playtime = playtime;
	}
	public String getMusicPath() {
		return musicPath;
	}
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}
	public static int getMusicIndex() {
		return musicIndex;
	}
	public static void setMusicIndex(int musicIndex) {
		MusicVO.musicIndex = musicIndex;
	}
	
	
}
