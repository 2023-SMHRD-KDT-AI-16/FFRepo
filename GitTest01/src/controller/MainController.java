package controller;

public class MainController {
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//=====================아래것은 MP3 프로젝트 할때 클래스들이니 참고하세요 주석처리함.
//	static int count = 0;
//	static int time_count = 0;
//	
//	int index = 0;
//	// 자료조작과 계산을 수행하는 비즈니스 로직.
//	// View 로 부터 데이터를 받아서 가공하거나 처리하는 역할.
//	// Model의 데이터를 수정하거나 가지고 오는 역할.
//
//	// 곡을 5개 저장할 수 있는 ArrayList 생성
//	ArrayList<MusicVO> musicList = new ArrayList<MusicVO>(5);
//	MP3Player mp3 = new MP3Player();
//	Random rd = new Random();
//
//	// 노래를 재생/ 정지 할 수 있는 기능을 가진 객체.
//	// Player.jar에 있는 MP3Player를 클래스를 이용해서 생성
//
//	public MainController() {
//		// Controller 객체가 생성되자마자 음악을 미리 준비하도록 하기 위해서.
//		// 1. MusicVO 객체 생성.
//		// 2. 해당 객체를 musicList라고 하는 ArrayList에 add
//		musicList.add(new MusicVO("꽃", "지수", 173, "../Ex10MusicPlayer/src/musicList/JISOO  꽃.mp3"));
//		musicList.add(new MusicVO("Spicy", "에스파", 204, "../Ex10MusicPlayer/src/musicList/에스파 Spicy .mp3"));
//		musicList.add(new MusicVO("퀸카", "여자아이들", 164, "../Ex10MusicPlayer/src/musicList/여자아이들GIDLE  퀸카 .mp3"));
//		musicList.add(new MusicVO("Violet", "Connor Price", 122,
//				"../Ex10MusicPlayer/src/musicList/Connor Price  Violet.mp3"));
//		musicList.add(new MusicVO("I AM", "아이브", 184, "../Ex10MusicPlayer/src/musicList/IVE 아이브 I AM.mp3"));
//	}
//
//	// 재생
//	public void play() {
////		count = 0;
//		// 현재 재생중인 음악이 있는지 확인하기
//		if (mp3.isPlaying()) {
//			// 재생중인 음악을 중지
//			mp3.stop();
//		}
//		//
//		mp3.play(musicList.get(index).getMusicPath());
//		playTime();
//		timeSize();
//		show();
//
//	}
//
//	public void stop() {
//
//		mp3.stop();
//
//		// 재생중인 음악을 중지
//
//	}
//
//	public void next() {
//		time_count = 0;
//		if (mp3.isPlaying()) {
//			mp3.stop();
//		}
//
//		if (0 < musicList.size() - 1) {
//			index++;
//		} else {
//			index = 0;
//		}
//		mp3.play(musicList.get(index).getMusicPath());
//		show();
//		timeSize();
//
//	}
//
//	public void pre() {
//		time_count = 0;
//		if (time_count < 5) {  //5초 이전에 이전곡으로 갈려고 할때
//			if (mp3.isPlaying()) {
//				mp3.stop();
//			}
//			if (index > 0) {
//				index--;
//			} else {
//				index = 0;
//			}
//			mp3.play(musicList.get(index).getMusicPath());
//			show();
//			timeSize();
//		} else {
//			if (mp3.isPlaying()) {
//				mp3.stop();
//			}	
//			index--;
//			mp3.play(musicList.get(index).getMusicPath());
//			show();
//			timeSize();
//		}
//
//	}
//
//	public void search(String name) {
//		for (int i = 0; i < musicList.size(); i++) {
//			MusicVO music = musicList.get(i);
//
//			if (music.getTitle().equals(name)) {
//				if (mp3.isPlaying()) {
//					mp3.stop();
//				}
//				mp3.play(music.getMusicPath());
//				index = i;
//				show();
//				timeSize();
//
//			}
//
//		}
//	}
//
//	public void exit() {
//
//	}
//
//	public void show() {
//
//		System.out.println(musicList.get(index).getTitle() + " - " + musicList.get(index).getSinger());
//	}
//
//	public void allShow() {
//		for (MusicVO x : musicList) {
//			System.out.print(x.getTitle() + "\t");
//			System.out.println(x.getSinger());
//		}
//	}
//
//	public void playTime() {
//		time_count = 0;
//		Timer timer = new Timer();
//		TimerTask task = new TimerTask() {
////			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				if ((time_count < musicList.get(index).getPlaytime()) && mp3.isPlaying() ) {
//
//					int min = time_count / 60;
//					int sec = time_count % 60;
//
//					System.out.print("플레이시간 : ");
//
//					if (sec < 10) {
//						System.out.print(min + ":0" + sec + " / ");
//
//					} else {
//						System.out.print(+min + ":" + sec + " / ");
//					}
//
//					timeSize();
//					time_count++;
//				} else {
//					timer.cancel();
//				}
//
//			}
//		};
//		
//		
//		timer.schedule(task, 1000, 1000);
//
////		
////		float startTime = System.currentTimeMillis();
////
////		// 종료 시간 기록
////		float endTime = System.currentTimeMillis();
////
////		// 경과 시간 계산 (밀리초 단위)
////		float elapsedTime = endTime - startTime;
////
////		// 경과 시간 출력
////		System.out.println("경과 시간: " + elapsedTime + "밀리초");
//
//	}
//
//	public void timeSize() {
//
//		MusicVO music = musicList.get(index);
//		int min = music.getPlaytime() / 60;
//		int sec = music.getPlaytime() % 60;
//
//		if (sec < 10) {
//			System.out.println(min + ":0" + sec);
//		} else {
//
//			System.out.println(min + ":" + sec);
//		}
//
//	}
//
//	public void randomPlay() {
//		index = rd.nextInt(musicList.size());
//		play();
//	}
//
}
