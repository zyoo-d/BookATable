package com.test.java.controller;

import com.test.java.view.MainView;
import com.test.java.view.StoreView;

public class MainController {

	/**
	 * 메인 화면 시작 
	 */
	public static void start() {
		
		
		boolean loop = true;
		while(loop) {

			MainView.logo();
			MainView.show();
			int choice = MainView.get();
			
			switch(choice) {
			case 1:
				SignInController signInController = new SignInController();
				signInController.signIn();
				break;
			case 2:
				//LogInController logInController = new LogInController();
				//logInController.logIn();
				break;
			case 3:
				FindAccountController findAccountController = new FindAccountController();
				findAccountController.findAccount();
				break;
			case 4:
				break;
			default:
				loop = false;
			}
		}
	}
	
	private static void searchMenu() {
		StoreView storeView = new StoreView();
		storeView.searchMenu();
	
		
	}
}
