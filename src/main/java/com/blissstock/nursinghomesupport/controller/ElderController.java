package com.blissstock.nursinghomesupport.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blissstock.nursinghomesupport.dto.DailyRecordWrapper;
import com.blissstock.nursinghomesupport.entity.ContactPerson;
import com.blissstock.nursinghomesupport.entity.DailyRecord;
import com.blissstock.nursinghomesupport.entity.ElderHobbies;
import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.Meal;
import com.blissstock.nursinghomesupport.entity.Medication;
import com.blissstock.nursinghomesupport.entity.OtherActivity;
import com.blissstock.nursinghomesupport.entity.PersonalCare;
import com.blissstock.nursinghomesupport.entity.Snack;
import com.blissstock.nursinghomesupport.entity.Vital;
import com.blissstock.nursinghomesupport.repository.DailyRecordRepository;
import com.blissstock.nursinghomesupport.repository.ElderRepository;
import com.blissstock.nursinghomesupport.storage.StorageService;
import com.blissstock.nursinghomesupport.utilities.FileNameGenerator;
import com.blissstock.nursinghomesupport.utilities.NullStringChecker;
import com.blissstock.nursinghomesupport.utilities.WriteDataToCSV;
import com.blissstock.nursinghomesupport.utilities.checkUploadFileType;


@Controller
public class ElderController {
	
	private final StorageService storageService;

	@Autowired
	public ElderController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@Autowired
	ElderRepository elderRepo;	
	
	@Autowired
	DailyRecordRepository dailyRecordRepo;
	
	//get elder form
	@GetMapping("/ElderRegisterForm")
	public String getElderForm(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName",userName);
		
		ElderInfo elder=new ElderInfo();
		for(int i=0;i<6;i++) {
			elder.addHobbyOfElder(new ElderHobbies());
		}
		for(int j=0;j<2;j++) {
			elder.addContactOfElder(new ContactPerson());
		}
		model.addAttribute("elder", elder);
		return "N00011_ElderInputForm.html";
	}
	
	//submit elder form with data
	@PostMapping("/ElderRegisterPost")
	public String RegElder(@Valid @ModelAttribute("elder")ElderInfo inputElder,BindingResult bindingResult,Model model, @RequestParam("elderphoto") MultipartFile photo, @RequestParam("elderfile1") MultipartFile file1, @RequestParam("elderfile2") MultipartFile file2) {
		if(bindingResult.hasErrors()) {          
			return "N00011_ElderInputForm.html";
		}else {

			ContactPerson cp = inputElder.getContact_person().get(0);
			String firstName = cp.getFirstName();
			String lastName = cp.getLastName();
			String relation = cp.getRelationship();
			String buildingNo = cp.getBuildingOrRoomNo();
			String city = cp.getCity();
			String country = cp.getCountry();
			String phone = cp.getPhoneNo();
			String postalCode = cp.getPostalCode();
			String state = cp.getState();
			String street = cp.getStreet();
			
			if(NullStringChecker.isStringEmpty(firstName)) {
				model.addAttribute("cpfNameErr", "Please enter first name of contact person.");
				return "N00011_ElderInputForm.html";
			}
			if(NullStringChecker.isStringEmpty(lastName)) {
				model.addAttribute("cplNameErr", "Please enter last name of contact person.");
				return "N00011_ElderInputForm.html";
			}
			if(NullStringChecker.isStringEmpty(relation)) {
				model.addAttribute("cpRSErr", "Please relationship of elder with contact person.");
				return "N00011_ElderInputForm.html";
			}
			if(NullStringChecker.isStringEmpty(phone)) {
				model.addAttribute("cpPhoneErr", "Please enter phone number of contact person.");
				return "N00011_ElderInputForm.html";
			}
			if(phone.length()<10 || phone.length()>10) {
				model.addAttribute("cpPhoneErr", "Phone number should be of 10 digits");
				return "N00011_ElderInputForm.html";
			}
			if(NullStringChecker.isStringEmpty(buildingNo)) {
				model.addAttribute("cpRoomNoErr", "Please enter building or room number.");
				return "N00011_ElderInputForm.html";
			}
			if(NullStringChecker.isStringEmpty(street)) {
				model.addAttribute("cpStreetErr", "Please enter the street address.");
				return "N00011_ElderInputForm.html";
			}
			if(NullStringChecker.isStringEmpty(city)) {
				model.addAttribute("cpCityErr", "Please enter city.");
				return "N00011_ElderInputForm.html";
			}
			if(NullStringChecker.isStringEmpty(state)) {
				model.addAttribute("cpStateErr", "Please enter the state.");
				return "N00011_ElderInputForm.html";
			}
			if(NullStringChecker.isStringEmpty(postalCode)) {
				model.addAttribute("cpPostalErr", "Please enter the postal code.");
				return "N00011_ElderInputForm.html";
			}
			if(NullStringChecker.isStringEmpty(country)) {
				model.addAttribute("cpCountryErr", "Please choose the country.");
				return "N00011_ElderInputForm.html";
			}
			
			 ElderInfo newelder=new ElderInfo(inputElder.getFirstName(),
					  inputElder.getLastName(),inputElder.getSex(), inputElder.getBirthday(),
					  inputElder.getCountryCode(), inputElder.getPhoneNo(),
					  inputElder.getBuildingOrRoomNo(), inputElder.getStreet(),
					  inputElder.getCity(), inputElder.getState(), inputElder.getPostalCode(),
					  inputElder.getCountry(), inputElder.getAdmissionDate(),
					  inputElder.getRoomNo(),inputElder.getDislikeMeal(),
					  inputElder.getFavouriteMeal(), inputElder.getIllness(),
					  inputElder.getMedicationInfo(), inputElder.getRemarks());
			
			 
			if(!photo.isEmpty()) {
				if(checkUploadFileType.checkType(photo)) {
					//get original photo name and generate a new file name
					  String originalFileName =StringUtils.cleanPath(photo.getOriginalFilename());
					  String saveFileName = FileNameGenerator.getRandomFileName(originalFileName);
					  
					  //upload photo of elder
					  storageService.storeElderImage(photo,saveFileName);
					  
					  //insert photo of elder 
					  System.out.println(saveFileName);		  
					  newelder.setPhoto(saveFileName);
				}else {
					model.addAttribute("photoTypeErr", "Files other than image file cannot be uploaded.");
					return "N00011_ElderInputForm.html";
				}
	        	
	        }else {
	        	model.addAttribute("photoErr", "Please upload the photo.");
	        	return "N00011_ElderInputForm.html";
	        }
			
			if(!file1.isEmpty()) {
				String originalFile1Name = StringUtils.cleanPath(file1.getOriginalFilename());
				String saveFile1Name = FileNameGenerator.getRandomFileName(originalFile1Name);
				
				storageService.storeElderFile1(file1,saveFile1Name);
				
				System.out.println(saveFile1Name);
				newelder.setFile1(saveFile1Name);
			}
			if(!file2.isEmpty()) {
				String originalFile2Name = StringUtils.cleanPath(file2.getOriginalFilename());
				String saveFile2Name = FileNameGenerator.getRandomFileName(originalFile2Name);
				
				storageService.storeElderFile2(file2,saveFile2Name);
				
				System.out.println(saveFile2Name);
				newelder.setFile2(saveFile2Name);
			}
			
			
			 
			  
			  for(ElderHobbies hobby:inputElder.getElder_hobby()) { 
				  String HobbyObj=hobby.getHobbyName(); 
				  if(HobbyObj != null && !HobbyObj.isEmpty()) {
					  hobby.setElder(newelder); newelder.addHobbyOfElder(hobby); 
					  } 
				  }
			  if(newelder.getElder_hobby().isEmpty()) {
				  model.addAttribute("hobbyErr", "Please choose hobbies of elder.");
		        	return "N00011_ElderInputForm.html";
			  }
			  
			  for(ContactPerson gurdian:inputElder.getContact_person()) { 
				  String gurdianObjFname=gurdian.getFirstName(); 
				  String gurdianObjLname=gurdian.getLastName();
			  
				  if(!NullStringChecker.isStringEmpty(gurdianObjFname) && !NullStringChecker.isStringEmpty(gurdianObjLname)) { 
					  ContactPerson person=new ContactPerson(gurdian.getFirstName(),gurdian.getLastName(),gurdian.getRelationship(),
							  gurdian.getCountryCode(),gurdian.getPhoneNo(),gurdian.getBuildingOrRoomNo(),gurdian.getStreet(),
							  gurdian.getCity(),gurdian.getState(),gurdian.getPostalCode(),gurdian.getCountry());
					  
					  person.addElder(newelder); newelder.addContactOfElder(person); 
				  } 
				}
			  
			  
			  elderRepo.save(newelder);
		      return "redirect:ElderList";
		}
	
	}


	//return all elders
	@RequestMapping(value = "ElderList",method = RequestMethod.GET)
	public String getAllElders(Model model){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName",userName);
		
		List<ElderInfo> allElders = elderRepo.findAll();
		model.addAttribute("elderList", allElders);
		return "N00009_ElderList.html";
	}
	
	
	//when service users are chosen and Add Daily Condition button is clicked in elderList
	@RequestMapping(value="/tableOperations", method=RequestMethod.POST, params="addDailyConditionBtn")
	public  String  addDailyConditions(@RequestParam("chosen") List<Long> chosenElders,@RequestParam("record_date") Date date,@RequestParam("category") String category, Model model) {
		
			
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
		
		
		
		//create record list for the chosen elders
		List<DailyRecord> eldersWithDailyRecord = new ArrayList<>();
		List<DailyRecord> displayRecords = new ArrayList<>();
		
		//fetch daily records by using elder id and a date
		//add daily record of elder to eldersWithDailyRecord if he already had daily record for the given date
		//create new daily record of elder and add to displayRecords if he does not have daily record for the given date
		for(Long id:chosenElders) {
			DailyRecord dr = dailyRecordRepo.getDailyRecord(id,date);
			if(dr!=null) {
				eldersWithDailyRecord.add(dr);
				displayRecords.add(dr);
			}else {
				ElderInfo elder = elderRepo.findById(id).orElse(null);
				DailyRecord record = new DailyRecord();
				//set the date to new record
				record.setDate(date);
				//set the current elder to new record
				record.setElder(elder);
				//create new vital obj for the current obj
				record.setVital(new Vital());
				record.setMeal(new Meal());
				record.setOtherActivity(new OtherActivity());
				record.setPersonalCare(new PersonalCare());
				record.setSnack(new Snack());
				record.setMedication(new Medication());
				displayRecords.add(record);
			}
		}
		//return displayRecords;
		//if chosen elders does not have records yet
//		if(eldersWithDailyRecord.isEmpty()) {	
			//create new record wrapper and set displayRecords to its attribute
			DailyRecordWrapper wrapper = new DailyRecordWrapper();
			wrapper.setRecordList(displayRecords);
			model.addAttribute("foodAmountList", foodAmount);
			model.addAttribute("date", date);
			model.addAttribute("wrapper", wrapper);
			model.addAttribute("category",category);
			
			//choose what page should be redirected by using category
			switch(category) {
				case "vital": return "N00013_DailyConditionGroupVital.html"; 
				case "mealBreakfast": return "N00013_DailyConditionGroupMealBreakfast.html"; 
				case "mealLunch": return "N00013_DailyConditionGroupMealLunch.html"; 
				case "mealDinner": return "N00013_DailyConditionGroupMealDinner.html"; 
				case "snack": return "N00013_DailyConditionGroupSnack.html";
				case "personalCare": return "N00013_DailyConditionGroupPersonalCare.html";
				case "medication" : return "N00013_DailyConditionGroupMedication.html";
				case "activity": return "N00013_DailyConditionGroupOtherActivity.html";
			}
//			
//		}else {
//		//if chosen elders already have records
//			String error = "Your choice includes elders who already have their record";
//			model.addAttribute("error",error);
//			return "N00013_InitialPage.html";
//		}
		return "";
	}
	
	//when service users are chosen and Delete button is clicked in elderList
	@RequestMapping(value="/tableOperations", method=RequestMethod.POST, params="btnDeleteConfirm")
	public String deleteElders(@RequestParam("chosen") List<Long> chosenElders) {
		for(Long elderId:chosenElders) {
			elderRepo.deleteById(elderId);
		}
		return "redirect:ElderList";
	}
	@RequestMapping(value="/tableOperations", method=RequestMethod.POST, params="btnDeleteCancel")
	public String cancelDailyRecordDeletion() {
		return "redirect:ElderList";
	}
	
	
	//when service users are chosen and Download button is clicked in elderList
	@RequestMapping(value="/tableOperations", method=RequestMethod.POST, params="downloadBtn")
	public void downloadElders(@RequestParam("chosen") List<Long> chosenElders,HttpServletResponse response) throws IOException {
		
		List<ElderInfo> returnedElderList = new ArrayList<ElderInfo>();
		for(Long elderId:chosenElders) {
			ElderInfo e= elderRepo.findById(elderId).orElse(null);
			returnedElderList.add(e);
		}
		
		response.setContentType("text/plain");
	    response.setHeader("Content-Disposition", "attachment; filename=Elders Information.csv");
	    
	    WriteDataToCSV.writeObjectToCSV(response.getWriter(), returnedElderList);
	}
	
}

