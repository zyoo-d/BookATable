package com.test.java.controller;

import com.test.java.model.Table;
import com.test.java.repository.Data;
import com.test.java.view.UnableCancelTableView;

/**
 * 예약 취소 불가능한 테이블을 처리하는 컨트롤러 클래스입니다.
 */
public class UnableCancelTableController {
    
    /**
     * 주어진 라이선스 번호에 해당하는 업체의 예약 취소 불가능한 테이블을 처리합니다.
     *
     * @param licenseNumber 처리할 업체의 라이선스 번호
     */
    public void unableCancel(String licenseNumber) {
        boolean foundTable = false;
        do {
            int ableTableCapacity = UnableCancelTableView.getTableCapacity(); 
            
            for (Table t : Data.tableList) {
                if (t.getLicenseNumber().equals(licenseNumber) && ableTableCapacity == t.getTableCapacity()) {
                    if (!t.isUsage()) { //예약 불가면 가능으로 전환
                        Table e = new Table(licenseNumber, t.getTableCapacity(), t.getNumber(), true);
                        int index = Data.tableList.indexOf(t);
                        Data.tableList.set(index, e);
                        foundTable = true;
                    } else {
                        System.out.println("예약가능으로 전환할 수 없는 테이블이거나 잘못 입력하셨습니다.");
                    }
                }
            }
            
            if (!foundTable) {
                System.out.println("입력한 테이블이 없거나 예약가능으로 전환할 수 없는 테이블입니다. 다시 입력하세요.");
            }
        } while (!foundTable);
        
      
        
        TableManagementController back = new TableManagementController();
        back.showTableManagement(licenseNumber);
    }
}
