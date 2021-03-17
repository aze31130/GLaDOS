package aze.GLaDOS.Utils;

public class LoadingBar {
	public static String loading(double current, int style) {
		String loadingBar = "";
		char Filled = ' ';
		char Empty = ' ';
		//[            ]
		//[======>     ]
		//[============]
		switch(style) {
			case 1:
				Filled = '▰';
				Empty = '▱';
				break;
			case 2:
				Filled = '⣿';
				Empty = '⣀';
				break;
			case 3:
				Filled = '█';
				Empty = '░';
				break;
			default:
				Filled = 'X';
				Empty = '_';
				break;
		}
		
		while(current > 0.1) {
			loadingBar += Filled;
			current -= 0.1;
		}
		
		while(loadingBar.length() < 10) {
			loadingBar += Empty;
		}
		
		return loadingBar;
	}
	
	public static String test(int progress) {
		String str = "a";
		
		switch(progress) {
			case 1:
				str = "███";
				break;
			case 2:
				str = "███▉";
				break;
			case 3:
				str = "███▊";
				break;
			case 4:
				str = "███▋";
				break;
			case 5:
				str = "███▌";
				break;
		}
		
		return str;
		
	}
}
