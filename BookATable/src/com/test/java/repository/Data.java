package com.test.java.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import com.test.java.model.Admin;
import com.test.java.model.BusinessUser;
import com.test.java.model.Member;
import com.test.java.model.Menu;
import com.test.java.model.Review;
import com.test.java.model.Store;
import com.test.java.model.User;

public class Data {
	
	//여러개면 Path 클래스를 만들어서 넣는게 좋음
	private final static String USER = "dat\\User.txt";
	private final static String BUSINESSUSER = "dat\\BusinessUser.txt";
	private final static String ADMIN = "dat\\Admin.txt";
	private final static String STORE = "dat\\Store.txt";
	
	//필요한 자료들
	public static ArrayList<Member> memberList = new ArrayList<>();
	public static ArrayList<Store> storeList = new ArrayList<>();
	public static ArrayList<Menu> menuList = new ArrayList<>();
	
	public static void loadStore() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Data.STORE));
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				String[] tmp = line.split(",");
				Store store = new Store(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], Double.parseDouble(tmp[5]), Integer.parseInt(tmp[6]));
				Data.storeList.add(store);
			}
		reader.close();
		} catch (Exception e) {
			System.out.println("StoreController.loadStore");
			e.printStackTrace();
		}
	}
	
	//프로그램을 시작할 때 file의 내용을 모두 메모리에 넣는 작업
	public static void loadAdmin() {
		try {
			BufferedReader reader
				= new BufferedReader(new FileReader(Data.ADMIN));
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				
				String[] tmp = line.split(",");
				
				
				
				Admin admin = new Admin(Integer.parseInt(tmp[0]), tmp[1], tmp[2]); 
				memberList.add(admin);
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void loadUser() {
		try {
			BufferedReader reader
			= new BufferedReader(new FileReader(Data.USER));
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				
				String[] tmp = line.split(",");
				User user = new User(Integer.parseInt(tmp[0]), tmp[1], tmp[2], tmp[3], tmp[4], tmp[5]
									, tmp[6], tmp[7]
									, Integer.parseInt(tmp[8]), Integer.parseInt(tmp[9]), Integer.parseInt(tmp[10]), tmp[11], tmp[12],tmp[13]);
				
				memberList.add(user);
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void loadBusinessUser() {
		try {
			BufferedReader reader
			= new BufferedReader(new FileReader(Data.BUSINESSUSER));
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				
				String[] tmp = line.split(",");
				
				BusinessUser businessUser = new BusinessUser(Integer.parseInt(tmp[0]), tmp[1], tmp[2], tmp[3]
															, tmp[4], tmp[5], tmp[6],  tmp[7], tmp[8], tmp[9]);
				
				memberList.add(businessUser);
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//프로그램을 끝낼 때 메모리의 내용을 모두 file에 넣는 작업
	public static void saveUser() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Data.USER));
			
			for(Member user : Data.memberList) {
				if (user.getUserType() == 1) {
					
					String line = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%d,%d,%d,%s,%s"
												, user.getUserType(), user.getId(), user.getPw(), user.getName()
												, user.getPhoneNumber(), user.getPhoneNumber(), ((User)user).getJumin()
												, ((User)user).getBank(), ((User)user).getAccount(), ((User)user).getBalance()
												, ((User)user).getReservationCount(), ((User)user), ((User)user).getNoshowCount()
												, ((User)user).getCheckStop(), ((User)user).getSignIn(), ((User)user).getSignOut()); 
					writer.write(line);
					
				}
			}
			
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void saveBusinessUser() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Data.BUSINESSUSER));
			
			for(Member businessUser : Data.memberList) {
				if (businessUser.getUserType() == 1) {
					String line = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s"
												, businessUser.getUserType(), businessUser.getId(), businessUser.getPw()
												, businessUser.getName(), ((BusinessUser)businessUser).getLicenseNumber()
												, businessUser.getPhoneNumber(), ((BusinessUser)businessUser).getBank()
												, ((BusinessUser)businessUser).getAccount()
												, ((BusinessUser)businessUser).getSignIn()
												, ((BusinessUser)businessUser).getSignOut());
							
							
					writer.write(line);
					
				}
			}
			
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static String getUser() {
		return USER;
	}
}