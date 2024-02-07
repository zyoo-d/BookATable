package com.test.java.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.test.java.model.Member;
import com.test.java.model.Reservation;
import com.test.java.model.Review;
import com.test.java.repository.Data;
import com.test.java.repository.ReviewRepository;
import com.test.java.repository.StoreRepository;
import com.test.java.view.InquiryCompletedReservationView;
import com.test.java.view.UserWriteReviewView;

public class UserWriteReviewController {

	public void userWriteReview() {
		boolean loop = true;
		while(loop) {
			ArrayList<Reservation> noReviewReservation = findAllNoReviewReservation(Member.id);
			
			if (noReviewReservation.isEmpty()) {
				UserWriteReviewView.showNoReviewMessage();
				break;
			}
			
			String userName = findNameById(Member.id);
			UserWriteReviewView.showNoReviewReservation(userName);
			
			ArrayList<Integer> stringArr = new ArrayList<>();
			for(Reservation r : noReviewReservation) {
				String storeName = StoreRepository.findNameOneByLicenseNumber(r.getLicenseNumber());
				stringArr.add(r.getReservationNumber());
				String cancelState = r.getState().equals("취소")== true? "O" : "X";
				String noShowState = r.getState().equals("노쇼")== true? "O" : "X";
				String reviewState = ReviewRepository.findOneById(Member.id, r.getLicenseNumber()) == true ? "O" : "X";
				InquiryCompletedReservationView.showOneReservation(r, storeName, cancelState, noShowState, reviewState);
			}
			
			int inputReservationNumber = UserWriteReviewView.getReservationNumber();
			if (stringArr.contains(inputReservationNumber)) {
				String reviewContent = UserWriteReviewView.getReviewContent();
				double score = UserWriteReviewView.getReviewScore();
				addReview(reviewContent, inputReservationNumber, score);
				System.out.println("리뷰 작성이 완료되었습니다.");
				loop = false;
			}
			break;
		}
	}

	private void addReview(String reviewContent, int reservationNumber, double score) {		
		int reviewNumber = Data.reviewList.size()+1;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String dateWritten = dateformat.format(c.getTime());
		String userId = "";
		String licenseNumber = "";
		for(Reservation r : Data.reservationList) {
			if (r.getReservationNumber() ==  reservationNumber){
				userId = r.getUserId();
				licenseNumber = r.getLicenseNumber();
			}
		}
        
		Review newReview = new Review(reviewNumber, userId, licenseNumber, dateWritten, reviewContent, score, reservationNumber);
		Data.reviewList.add(newReview);
	}

	private String findNameById(String id) {
		for (Member u : Data.memberList) {
			if (u.getId().equals(id)) {
				return u.getName();
			}
		}
		return null;
	}

	private ArrayList<Reservation> findAllNoReviewReservation(String id) {
		ArrayList<Reservation> tmp = new ArrayList<Reservation>();
		for(Reservation r : Data.reservationList) {
			if (r.getUserId().equals(id) && ReviewRepository.findOneById(id, r.getLicenseNumber()) == false){
				tmp.add(r);
			}
		}
		return tmp;
	}

}
