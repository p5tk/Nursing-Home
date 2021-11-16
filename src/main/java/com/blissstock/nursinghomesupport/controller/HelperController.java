
package com.blissstock.nursinghomesupport.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blissstock.nursinghomesupport.entity.HelperShiftDays;
import com.blissstock.nursinghomesupport.entity.LoginUser;
import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.repository.HelperRepository;
import com.blissstock.nursinghomesupport.repository.HelperShiftDaysRepository;
import com.blissstock.nursinghomesupport.repository.UserRepository;
import com.blissstock.nursinghomesupport.storage.StorageFileNotFoundException;
import com.blissstock.nursinghomesupport.storage.StorageService;
import com.blissstock.nursinghomesupport.utilities.FileNameGenerator;
import com.blissstock.nursinghomesupport.utilities.NullStringChecker;
import com.blissstock.nursinghomesupport.utilities.WriteDataToCSV;
import com.blissstock.nursinghomesupport.utilities.checkUploadFileType;

@Controller
public class HelperController {
	
	private final StorageService storageService;

	@Autowired
	public HelperController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@Autowired
	HelperRepository helperRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	HelperShiftDaysRepository shiftDayRepo;
	

	@GetMapping("/helperRegisterForm")
	public String getHelperForm(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		HelperInfo helper = new HelperInfo();
		
		//produce list of shift days to loop in view
		List<HelperShiftDays> shiftDayList = shiftDayRepo.findAll();
		model.addAttribute("allShiftDays", shiftDayList);
		model.addAttribute("helper", helper);
		return "N00016_HelperInputForm.html";
	}

	@PostMapping("/helperRegisterPost")
	public  String RegHelper(@Valid @ModelAttribute("helper") HelperInfo inputHelper,BindingResult bindingResult,Model model, @RequestParam("helperphoto") MultipartFile photo) {
		
		//produce list of shift days to loop in view
		List<HelperShiftDays> shiftDayList = shiftDayRepo.findAll();
		
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("allShiftDays", shiftDayList);
			return "N00016_HelperInputForm.html";
			
		}else {
			
			//check if input username and own code already exist
			List<LoginUser> users = userRepo.findAll();
			List<String> usernames = new ArrayList<>();
			List<String> ownCodes = new ArrayList<>();
			for(LoginUser user:users) {
				usernames.add(user.getUserName());
				ownCodes.add(user.getOwnCodeNumber());
			}
			
			if(usernames.contains(inputHelper.getAcc().getUserName())) {
				model.addAttribute("usernameErr", "Username already exist. Please choose another name.");
				model.addAttribute("allShiftDays", shiftDayList);
				return "N00016_HelperInputForm.html";
			}
			
			if(ownCodes.contains(inputHelper.getAcc().getOwnCodeNumber())) {
				model.addAttribute("ownCodeErr", "Own Code already exist. Please choose another code.");
				model.addAttribute("allShiftDays", shiftDayList);
				return "N00016_HelperInputForm.html";
			}
			
			//validate contract start and end date
			Date startDate = inputHelper.getContractStartDate();
			Date endDate = inputHelper.getContractEndDate();
			if(startDate.after(endDate)) {
				model.addAttribute("startEndDateErr", "Contract start sate must come before contract end date.");
				model.addAttribute("allShiftDays", shiftDayList);
				return "N00016_HelperInputForm.html";
			}
			
			//validate shift days 
			if(inputHelper.getHelper_shift_days().isEmpty()) {
				model.addAttribute("shiftErr", "Please choose shift days.");
				model.addAttribute("allShiftDays", shiftDayList);
				return "N00016_HelperInputForm.html";
			}
			
			HelperInfo newhelper = new HelperInfo(inputHelper.getFirstName(), inputHelper.getLastName(),
					inputHelper.getGender(), inputHelper.getAcc().getBirthDate(), inputHelper.getCountryCode(),
					inputHelper.getPhoneNo(), inputHelper.getBuildingNo(), inputHelper.getStreetAddress(),
					inputHelper.getCity(), inputHelper.getState(), inputHelper.getPostalCode(), inputHelper.getCountry(),
					inputHelper.getHireDate(), inputHelper.getContractStartDate(), inputHelper.getContractEndDate(),
					inputHelper.getHourlyWage(), inputHelper.getHourlyWageCurrency(), inputHelper.getShiftType(),
					inputHelper.getRemark(),inputHelper.getEducation());

			//insert account of helper
			  LoginUser user = inputHelper.getAcc(); 
			  LoginUser loginuser = new LoginUser(user.getUserName(), user.getPassword(), "2",
			  user.getOwnCodeNumber(), user.getBirthDate());
			  
			//insert current helper in current account obj
			  loginuser.setHelper(newhelper);
			  newhelper.setAcc(loginuser);

			//insert shift days of helper
			for (HelperShiftDays shiftday : inputHelper.getHelper_shift_days()) {
				String ShiftDayName = shiftday.getShiftDayName();
				if (ShiftDayName != null && !ShiftDayName.isEmpty()) {
					newhelper.getHelper_shift_days().add(shiftday);
				}
			}
			//check if photo is not selected
			if(!photo.isEmpty()) {
				if(checkUploadFileType.checkType(photo)) {
					//get original photo name and generate a new file name
					  String originalFileName =StringUtils.cleanPath(photo.getOriginalFilename());
					  String saveFileName = FileNameGenerator.getRandomFileName(originalFileName);
					  
					
					//upload photo of helper
					storageService.storeHelperImage(photo,saveFileName);

					//insert photo of helper
					System.out.println(saveFileName);	
					newhelper.setPhoto(saveFileName);
				}else {
					model.addAttribute("photoTypeErr", "Files other than image file cannot be uploaded.");
		        	model.addAttribute("allShiftDays", shiftDayList);
					return "N00016_HelperInputForm.html";
				}
	        	
	        }else {
	        	model.addAttribute("photoErr", "Please upload the photo.");
	        	model.addAttribute("allShiftDays", shiftDayList);
				return "N00016_HelperInputForm.html";
	        }
			
			
			
			helperRepo.save(newhelper); 
			return "redirect:/HelperList";
		}

		
	}
	
	//return all helpers
		@RequestMapping(value = "/HelperList",method = RequestMethod.GET)
		public String getAllHelpers(Model model){
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userName = auth.getName();
			model.addAttribute("userName", userName);
			
			List<HelperInfo> allHelpers =helperRepo.findAll();
			
			//change shift days of each helper to string
			List<String> shiftDaysForAllHelpers = new ArrayList<>();
			for(HelperInfo helper:allHelpers) {
				String shiftDayList = "";
				for(HelperShiftDays shiftDays: helper.getHelper_shift_days()) {
					shiftDayList+=shiftDays.getShiftDayName()+",";
				}
				shiftDaysForAllHelpers.add(shiftDayList);
			}
			//return shiftDaysForAllHelpers;
			model.addAttribute("shiftDaysList", shiftDaysForAllHelpers);
			model.addAttribute("helperList", allHelpers);
			return "N00014_HelperListForm.html";
		}
		
		//when helper users are chosen and Delete button is clicked in helperList
		@RequestMapping(value="/tableOperationsforHelpers", method=RequestMethod.POST,params="btnDeleteConfirm")
		public String deleteHelpers(@RequestParam("chosen") List<Long> chosenHelpers) {
			for(Long helperId:chosenHelpers) {
				helperRepo.deleteById(helperId);
			}
			return "redirect:/HelperList";
		}
		@RequestMapping(value="/tableOperationsforHelpers", method=RequestMethod.POST, params="btnDeleteCancel")
		public String cancelDailyRecordDeletion() {
			return "redirect:HelperList";
		}
		
		//when service users are chosen and Download button is clicked in elderList
		@RequestMapping(value="/tableOperationsforHelpers", method=RequestMethod.POST, params="downloadBtn")
		public void downloadElders(@RequestParam("chosen") List<Long> chosenHelpers,HttpServletResponse response) throws IOException {
			
			List<HelperInfo> returnedHelperList = new ArrayList<HelperInfo>();
			for(Long helperId:chosenHelpers) {
				HelperInfo h = helperRepo.findById(helperId).orElse(null);
				returnedHelperList.add(h);
			}
			
			response.setContentType("text/plain");
		    response.setHeader("Content-Disposition", "attachment; filename=Helpers Information.csv");
		    
		    WriteDataToCSV.writeObjectToCSVofHelper(response.getWriter(),returnedHelperList);
		}
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}

