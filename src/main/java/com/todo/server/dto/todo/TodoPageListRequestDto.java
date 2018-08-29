package com.todo.server.dto.todo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.data.domain.Sort;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoPageListRequestDto {
	private int start;
	private int length;
	private int page;

	private String[] columnList;
	private Sort sort;

    public TodoPageListRequestDto(int start, int length) {
        this.start = start;
        this.length = length;
    }
    
    public void setOrders(String[] orders) {
    	List<Sort.Order> orderList = new ArrayList<Sort.Order>();
    	Sort.Order sortOrder;
    	
    	for (String order : orders) {
    		String[] orderValue = order.split("_");
    		
    		String columnName = this.columnList[Integer.parseInt(orderValue[0])];
    		
    		if(orderValue[1].equals("asc"))	sortOrder = Sort.Order.asc(columnName);
    		else							sortOrder = Sort.Order.desc(columnName);
    		    		
    		orderList.add(sortOrder);
    	}
    	
    	sort = Sort.by(orderList);
    }
    
    public int getPage() {
    	return this.start/this.length;
    }
}