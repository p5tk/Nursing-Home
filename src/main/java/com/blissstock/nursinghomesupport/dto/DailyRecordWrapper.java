package com.blissstock.nursinghomesupport.dto;

import java.util.List;

import com.blissstock.nursinghomesupport.entity.DailyRecord;

public class DailyRecordWrapper {
	
	private List<DailyRecord> recordList;

	public List<DailyRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<DailyRecord> recordList) {
		this.recordList = recordList;
	}
	
}
