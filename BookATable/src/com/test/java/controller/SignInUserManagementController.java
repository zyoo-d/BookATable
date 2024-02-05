package com.test.java.controller;

import java.util.ArrayList;
import java.util.HashMap;

import com.test.java.model.Member;
import com.test.java.model.Reservation;
import com.test.java.model.User;
import com.test.java.repository.MemberRepository;
import com.test.java.repository.ReservationRepository;
import com.test.java.repository.ReviewRepository;
import com.test.java.repository.StoreRepository;
import com.test.java.view.SignInUserManagementView;
import com.test.java.view.View;

public class SignInUserManagementController {

	SignInUserManagementView signInUserManagementView = new SignInUserManagementView();
	
	public void signInUserManagement() {
		
		boolean loop = true;
		while(loop) {

			signInUserManagementView.findAllUser();
			signInUserManagementView.showSelectBox();
			int choice = signInUserManagementView.getSelectType();
			
			if(choice == 1) {
				//아이디 입력 받기
				String id = signInUserManagementView.getId();
				
				//아이디를 사용해서 Member조회
				Member member = MemberRepository.findOneById(id);
				
				//member가 없으면
				if(member==null) {
					signInUserManagementView.incorrectInputMessage();
					continue;
				}
				
				//멤버를 사용해서 기본정보 받아오기
				String basic = findBasicById(member);
				
				//기본 정보 출력
				signInUserManagementView.show(basic);
				

				//아이디를 사용해서 세부 예약 내역을 찾기
				ArrayList<Reservation> reservations = ReservationRepository.findAllById(id);
				
				//예약 내역이 없으면
				if(reservations.isEmpty()) {
					signInUserManagementView.show("이용 내역을 찾을 수 없습니다.");
					continue;
				}
				
				
				makeDetailCard(reservations);
				
			}
			else if(choice == 0) {
				loop = false;
			}
			
		}
	}

	private String findBasicById(Member member) {
		String tmp = "";
		tmp += "회원명 : " + member.getName() +"\n";
		tmp += "회원ID : " + member.getId() +"\n";
		tmp += "예약 횟수 : " + ((User)member).getReservationCount() +"\n";
		tmp += "노쇼 횟수 : " + ((User)member).getNoshowCount() +"\n";
		return tmp;
	}

	
	
	private void makeDetailCard(ArrayList<Reservation> reservations) {


		signInUserManagementView.showDetailTitle();

		for(Reservation r : reservations) {			
			int reservationNumber = r.getReservationNumber();
			String storeName = StoreRepository.findOneByLicenseNumber(r.getLicenseNumber()).getStoreName();
			String date = r.getReservationDate();
			int numOfPeople = r.getNumOfPeople();
			ArrayList<String> menulist = r.getMenulist();
			
			double score = ReviewRepository.findScoreByReservationNumber(reservationNumber);
			String content = ReviewRepository.findContentByReservationNumber(reservationNumber);
			
			//예약내역 중 노쇼,취소를 제외한 내역의 메뉴 개수 구하기
			HashMap<String, Integer> menuCount = ReservationRepository.findMenuCountByReservationNumber(reservationNumber);;

			
			signInUserManagementView.showDetail(reservationNumber, storeName,date,numOfPeople,menuCount,score,content);

		}
	
		View.pause();
	}



}