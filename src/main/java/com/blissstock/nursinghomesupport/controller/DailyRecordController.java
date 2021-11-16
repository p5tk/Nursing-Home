package com.blissstock.nursinghomesupport.controller;


import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blissstock.nursinghomesupport.dto.DailyRecordWrapper;
import com.blissstock.nursinghomesupport.entity.DailyRecord;
import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.LoginUser;
import com.blissstock.nursinghomesupport.entity.Meal;
import com.blissstock.nursinghomesupport.entity.Medication;
import com.blissstock.nursinghomesupport.entity.OtherActivity;
import com.blissstock.nursinghomesupport.entity.PersonalCare;
import com.blissstock.nursinghomesupport.entity.Snack;
import com.blissstock.nursinghomesupport.entity.Vital;
import com.blissstock.nursinghomesupport.repository.DailyRecordRepository;
import com.blissstock.nursinghomesupport.repository.ElderRepository;
import com.blissstock.nursinghomesupport.repository.MealRepository;
import com.blissstock.nursinghomesupport.repository.MedicationRepository;
import com.blissstock.nursinghomesupport.repository.OtherActivityRepository;
import com.blissstock.nursinghomesupport.repository.PersonalCareRepository;
import com.blissstock.nursinghomesupport.repository.SnackRepository;
import com.blissstock.nursinghomesupport.repository.UserRepository;
import com.blissstock.nursinghomesupport.repository.VitalRepository;
import com.blissstock.nursinghomesupport.utilities.WriteDataToCSV;

@Controller
public class DailyRecordController {
	
	@Autowired
	ElderRepository elderRepo;
	
	@Autowired
	UserRepository userRepo;
	
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
	
	@RequestMapping(value="/DailyConditionIndividual", method=RequestMethod.GET)
	public String getDailyRecordForm(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		DailyRecord dr = new DailyRecord();
		
		//to be able to choose elder in N00007
		List<ElderInfo> elderList=elderRepo.findAll();
		model.addAttribute("elderListObj", elderList);
		
		//to be able to choose the consumption amount of food
		List<String> foodAmount=new ArrayList<>();
		
		foodAmount.add("");
		foodAmount.add("10/10");
		foodAmount.add("9/10");
		foodAmount.add("8/10");
		foodAmount.add("7/10");
		foodAmount.add("6/10");
		foodAmount.add("5/10");
		foodAmount.add("4/10");
		foodAmount.add("3/10");
		foodAmount.add("2/10");
		foodAmount.add("1/10");
		foodAmount.add("0/10");
		model.addAttribute("foodAmountList", foodAmount);	

		model.addAttribute("drObj",dr);
		return "N00007_DailyConditionIndividual.html";
	}
	
	@RequestMapping(value="/DailyConditionIndividualPost", method=RequestMethod.POST,params="save")
	public String  precessDailyRecordForm( @ModelAttribute("drObj") DailyRecord dailyRecord,Model model) {
		Date date = dailyRecord.getDate();
		Long elderId = dailyRecord.getElder().getElderId();
		DailyRecord checkRecord = dailyRecordRepo.getDailyRecord(elderId,date);
		if(checkRecord!=null) {
			DailyRecord dr = new DailyRecord();
			
			//to be able to choose elder in N00007
			List<ElderInfo> elderList=elderRepo.findAll();
			model.addAttribute("elderListObj", elderList);
			
			//to be able to choose the consumption amount of food
			List<String> foodAmount=new ArrayList<>();
			
			foodAmount.add("");
			foodAmount.add("10/10");
			foodAmount.add("9/10");
			foodAmount.add("8/10");
			foodAmount.add("7/10");
			foodAmount.add("6/10");
			foodAmount.add("5/10");
			foodAmount.add("4/10");
			foodAmount.add("3/10");
			foodAmount.add("2/10");
			foodAmount.add("1/10");
			foodAmount.add("0/10");
			model.addAttribute("foodAmountList", foodAmount);	

			model.addAttribute("drObj",dr);
			
			model.addAttribute("recordExistErr", "Chosen service user already have his record for chosen date.");
			return "N00007_DailyConditionIndividual.html";
		}
		dailyRecordRepo.save(dailyRecord);
		return "redirect:/dailyRecordPostRedirect";
		
	}
	
	@PostMapping(value="/update")
	public String   saveDailyRecord(DailyRecord dailyRecord, BindingResult result, ModelMap model) {

	    dailyRecordRepo.save(dailyRecord);
	    return "redirect:/dailyRecordPostRedirect";
	}
	
	@GetMapping(value="dailyRecordPostRedirect")
	public String dailyRecordPostRedirect() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		LoginUser user= userRepo.getUserDetails(userName);
		if("1".equals(user.getRole())) {
			return "redirect:/DailyRecordList";
		}
		
		return "redirect:/UserHome";
	}
	
	
	@GetMapping("/editDailyRecord/{id}")
	public String editDailyRecord(@PathVariable("id") Long id, ModelMap model ) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName",userName);
		
		//to be able to choose elder in N00007
				List<ElderInfo> elderList=elderRepo.findAll();
				model.addAttribute("elderListObj", elderList);
		//to be able to choose the consumption amount of food
				List<String> foodAmount=new ArrayList<>();
				foodAmount.add("");
				foodAmount.add("10/10");
				foodAmount.add("9/10");
				foodAmount.add("8/10");
				foodAmount.add("7/10");
				foodAmount.add("6/10");
				foodAmount.add("5/10");
				foodAmount.add("4/10");
				foodAmount.add("3/10");
				foodAmount.add("2/10");
				foodAmount.add("1/10");
				foodAmount.add("0/10");
				model.addAttribute("foodAmountList", foodAmount);
	    model.addAttribute("dailyRecord", dailyRecordRepo.findById(id));
	    return  "N00007_UpdateDailyCondition.html";
	}
	
	
	
	@RequestMapping(value = "DailyRecordList",method = RequestMethod.GET)
	public String getAllDailyRecord(Model model){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		List<DailyRecord> allRecord = dailyRecordRepo.findAll();
		model.addAttribute("dailyRecordList", allRecord);
		//return allRecord;
		return "N00012_DailyConditionList.html";
	}
	
	@RequestMapping(value="/dailyRecordTableOperations", method=RequestMethod.POST, params="btnDeleteConfirm")
	public  String deleteDailyRecord(@RequestParam("chosen") List<Long> chosenDailyRecord) {
		for(Long dailyRecordID:chosenDailyRecord) {
			dailyRecordRepo.deleteById(dailyRecordID);
		}
		return "redirect:DailyRecordList";
	}
	
	@RequestMapping(value="dailyRecordTableOperations", method=RequestMethod.POST, params="btnDeleteCancel")
	public String cancelDailyRecordDeletion() {
		return "redirect:DailyRecordList";
	}
	
	@RequestMapping(value="/dailyRecordTableOperations", method=RequestMethod.POST, params="btnDownload")
	public void downloadDailyRecords(@RequestParam("chosen") List<Long> chosenDailyRecords,HttpServletResponse response) throws IOException {
		
		List<DailyRecord> returnedDailyRecordList = new ArrayList<DailyRecord>();
		for(Long dailyRecordID:chosenDailyRecords) {
			DailyRecord d= dailyRecordRepo.findById(dailyRecordID).orElse(null);
			returnedDailyRecordList.add(d);
		}
		
		response.setContentType("text/plain");
	    response.setHeader("Content-Disposition", "attachment; filename=Daily Condition Information.csv");
	    
	    WriteDataToCSV.writeObjectToCsvOfDailyRecord(response.getWriter(), returnedDailyRecordList);
	}
	
	//View Daily Condition from ElderList (N00019)
	@RequestMapping(value="/viewDailyCondition/{id}", method=RequestMethod.GET)
	public String viewDailyConditionIndividual(@PathVariable ("id") Long id, Model model) {
		List<DailyRecord> individualElder =  dailyRecordRepo.findByElderId(id);
		model.addAttribute("currentElder", elderRepo.findById(id).orElse(null));
		model.addAttribute("individualElder", individualElder);
		return "N00019_DailyConditionListIndividual.html";
	}
	
	//Delete individual Daily Record (N00019)
	@RequestMapping(value="/individualDailyRecordTableOperations", method=RequestMethod.POST, params="btnDelete")
	public String deleteIndividualDailyRecord(@RequestParam("chosen") List<Long> chosenDailyRecord) {
		for(Long dailyRecordID:chosenDailyRecord) {
			dailyRecordRepo.deleteById(dailyRecordID);
		}
		return "redirect:/DailyRecordList";
	}
	
	//Download individual Daily Record (N00019)
	@RequestMapping(value="/individualDailyRecordTableOperations", method=RequestMethod.POST, params="btnDownload")
	public void downloadIndividualDailyRecords(@RequestParam("chosen") List<Long> chosenDailyRecords,HttpServletResponse response) throws IOException {
		
		List<DailyRecord> returnedDailyRecordList = new ArrayList<DailyRecord>();
		for(Long dailyRecordID:chosenDailyRecords) {
			DailyRecord d= dailyRecordRepo.findById(dailyRecordID).orElse(null);
			returnedDailyRecordList.add(d);
		}
		
		response.setContentType("text/plain");
	    response.setHeader("Content-Disposition", "attachment; filename=Daily Condition Information.csv");
	    
	    WriteDataToCSV.writeObjectToCsvOfIndividualDailyRecord(response.getWriter(), returnedDailyRecordList);
	}
	
	
	@RequestMapping(value="initialPage", method=RequestMethod.GET)
	public String showInitialPage(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName",userName);
		
		return "N00013_InitialPage.html";
		
	}
	
	@RequestMapping(value="chooseElders",method=RequestMethod.POST)
	public String chooseElders(@RequestParam("record_date") Date date, @RequestParam("category") String category, Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName",userName);
		
		model.addAttribute("date",date);
		model.addAttribute("category", category);
		model.addAttribute("elderList",elderRepo.findAll());
		return "N00009_ElderList.html";
	}
	
	@PostMapping(value="postDailyRecordList")
	public String postDailyRecordList(@ModelAttribute("wrapper") DailyRecordWrapper drp,Model model){
		
		for(DailyRecord record:drp.getRecordList()) {
			if(record.getMeal()==null) {
				record.setMeal(new Meal());
			}
			if(record.getMedication()==null) {
				record.setMedication(new Medication());
			}
			if(record.getOtherActivity()==null) {
				record.setOtherActivity(new OtherActivity());
			}
			if(record.getPersonalCare()==null) {
				record.setPersonalCare(new PersonalCare());
			}
			if(record.getSnack()==null) {
				record.setSnack(new Snack());
			}
			if(record.getVital()==null) {
				record.setVital(new Vital());
			}
		}
		
		dailyRecordRepo.saveAll(drp.getRecordList());
		//return drp;
		return "redirect:DailyRecordList";
	}
	
	@PostMapping(value="/DailyConditionIndividualPost",params="checkRecord")
	public String goToInsertOrUpdate(Model model,@RequestParam("date") Date date,@RequestParam("elder") Long elderId) {
		DailyRecord dr = dailyRecordRepo.getDailyRecord(elderId,date);
		if(dr==null) {
			DailyRecord newRecord = new DailyRecord();
			ElderInfo elder = elderRepo.findById(elderId).orElse(null);
			//to be able to choose elder in N00007
			List<ElderInfo> elderList=elderRepo.findAll();
			model.addAttribute("elderListObj", elderList);
			
			//to be able to choose the consumption amount of food
			List<String> foodAmount=new ArrayList<>();
			
			foodAmount.add("");
			foodAmount.add("10/10");
			foodAmount.add("9/10");
			foodAmount.add("8/10");
			foodAmount.add("7/10");
			foodAmount.add("6/10");
			foodAmount.add("5/10");
			foodAmount.add("4/10");
			foodAmount.add("3/10");
			foodAmount.add("2/10");
			foodAmount.add("1/10");
			foodAmount.add("0/10");
			model.addAttribute("foodAmountList", foodAmount);	

			newRecord.setDate(date);
			newRecord.setElder(elder);
			model.addAttribute("drObj",newRecord);
			return "N00007_DailyConditionIndividual.html";		
		}else {
			return "redirect:editDailyRecord/"+dr.getDailyRecordID();
		}

	}

	
	
}
	