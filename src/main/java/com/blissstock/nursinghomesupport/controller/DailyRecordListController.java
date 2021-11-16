package com.blissstock.nursinghomesupport.controller;


import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blissstock.nursinghomesupport.entity.DailyRecord;
import com.blissstock.nursinghomesupport.entity.ElderInfo;

import com.blissstock.nursinghomesupport.repository.DailyRecordRepository;
import com.blissstock.nursinghomesupport.repository.ElderRepository;
import com.blissstock.nursinghomesupport.repository.MealRepository;
import com.blissstock.nursinghomesupport.repository.MedicationRepository;
import com.blissstock.nursinghomesupport.repository.OtherActivityRepository;
import com.blissstock.nursinghomesupport.repository.PersonalCareRepository;
import com.blissstock.nursinghomesupport.repository.SnackRepository;
import com.blissstock.nursinghomesupport.repository.VitalRepository;
import com.blissstock.nursinghomesupport.utilities.WriteDataToCSV;

@Controller
public class DailyRecordListController {
	
	@Autowired
	ElderRepository elderRepo;
	
	@Autowired
	DailyRecordRepository dailyRecordRepo;
	
	@Autowired
	VitalRepository vitalRepo;
	
	@Autowired 
	MealRepository mealRepo;
	
	@Autowired
	SnackRepository snackRepo;
	
	@Autowired
	PersonalCareRepository personalCareRepo;
	
	@Autowired
	MedicationRepository medicationRepo;
	
	@Autowired
	OtherActivityRepository otherActivityRepo;
	
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	//search from N00012
	@RequestMapping(value="/dailyRecordTableSearch", method=RequestMethod.POST)
	public String searchRecords(@RequestParam("name") String name,@RequestParam("start") String startDate,
			@RequestParam("end")String endDate, Model model) throws ParseException{
		
		List<DailyRecord> dailyRecordList = new ArrayList<>();
		//find by name
		if(name!="" && startDate=="" && endDate=="") {
			ElderInfo elder = elderRepo.findByElderName(name);
			if(elder==null) {
				model.addAttribute("noElderErr", "There is no elder with the given name.");
				return "N00012_DailyConditionList.html";
			}else {
			Long elderId = elder.getElderId();
			dailyRecordList =  dailyRecordRepo.findByElderId(elderId);
			model.addAttribute("dailyRecordList", dailyRecordList);
			return "N00012_DailyConditionList.html";
			}
		}
		//find by start and end date
		else if(name=="" && startDate!="" && endDate!="") {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date start = formatter.parse(startDate);
			Date end = formatter.parse(endDate);
			dailyRecordList = dailyRecordRepo.searchRecordsByDates(start,end);
			model.addAttribute("dailyRecordList", dailyRecordList);
			return "N00012_DailyConditionList.html";
		}
		//find by name,start and end date
		else if(name!="" && startDate!="" && endDate!="" ) {
			ElderInfo elder = elderRepo.findByElderName(name);
			if(elder==null) {
				model.addAttribute("noElderErr", "There is no elder with the given name.");
				return "N00012_DailyConditionList.html";
			}else {
				Long elderId = elder.getElderId();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date start = formatter.parse(startDate);
				Date end = formatter.parse(endDate);
				
				dailyRecordList = dailyRecordRepo.searchRecordsByDatesAndName(elderId,start,end);
				model.addAttribute("dailyRecordList", dailyRecordList);
				return "N00012_DailyConditionList.html";
			}
		}
		//if only one date is inserted
		else if(startDate!="" && endDate=="" || startDate=="" && endDate!=""){
			model.addAttribute("bothDateErr", "Please choose both start date and end date.");
			return "N00012_DailyConditionList.html";
		}
		
		dailyRecordList = dailyRecordRepo.findAll();
		model.addAttribute("dailyRecordList", dailyRecordList);
		return "N00012_DailyConditionList.html";
	}
	
	//search from N00019
	@RequestMapping(value="/recordSearchIndividualTable", method=RequestMethod.POST)
	public String recordSearchIndividual(@RequestParam("start") String startDate, @RequestParam("end")String endDate,
			@RequestParam("elderId")Long elderId,Model model) throws ParseException {
		
		model.addAttribute("currentElder", elderRepo.findById(elderId).orElse(null));
		List<DailyRecord> individualElder = new ArrayList<>();
		
		if(startDate!="" && endDate!="") {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date start = formatter.parse(startDate);
			Date end = formatter.parse(endDate);
			individualElder = dailyRecordRepo.searchRecordsByDatesAndName(elderId,start,end);
			model.addAttribute("individualElder", individualElder);
			return "N00019_DailyConditionListIndividual.html";
		}
		else if(startDate!="" && endDate=="" || startDate=="" && endDate!="") {
			model.addAttribute("bothDateErr", "Please choose both start date and end date.");
			return "N00019_DailyConditionListIndividual.html";
		}
		
		individualElder =  dailyRecordRepo.findByElderId(elderId);
		model.addAttribute("individualElder", individualElder);
		return "N00019_DailyConditionListIndividual.html";
	}
	
	

	
}
	