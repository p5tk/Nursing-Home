package com.blissstock.nursinghomesupport.dto;

import java.util.ArrayList;
import java.util.List;

import com.blissstock.nursinghomesupport.entity.Vital;

import lombok.Data;
@Data
public class VitalList {

	public List<Vital> vtList=new ArrayList<>();

	public List<Vital> getVtList() {
		return vtList;
	}

	public void setVtList(List<Vital> vtList) {
		this.vtList = vtList;
	}
	
	public void addVtObjToList(Vital vital) {
		this.vtList.add(vital);
	}
	
	
}


