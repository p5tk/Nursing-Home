
package com.blissstock.nursinghomesupport.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blissstock.nursinghomesupport.entity.ContactPerson;
import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.entity.HelperShiftDays;
import com.blissstock.nursinghomesupport.entity.LoginUser;
import com.blissstock.nursinghomesupport.entity.TaskInfo;
import com.blissstock.nursinghomesupport.repository.HelperRepository;
import com.blissstock.nursinghomesupport.repository.HelperShiftDaysRepository;
import com.blissstock.nursinghomesupport.repository.UserRepository;
import com.blissstock.nursinghomesupport.utilities.FileNameGenerator;
import com.blissstock.nursinghomesupport.utilities.checkUploadFileType;
import com.blissstock.nursinghomesupport.storage.StorageService;


@Controller
public class UpdateHelperDetailsController {
	
	private final StorageService storageService;
	
	@Autowired
	public UpdateHelperDetailsController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@Autowired
	HelperRepository helperRepo;
	
	@Autowired
	HelperShiftDaysRepository shiftDaysRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/editHelper/{id}")
	public String showUpdateForm(@PathVariable("id") long helperId,Model model) {
		
		HelperInfo helper = helperRepo.findById(helperId).orElse(null);
		model.addAttribute("helper", helper);
		
		//get path to display helper photo
		String basePath = "../images/helper-images/";
		model.addAttribute("imagePath",basePath+helper.getPhoto());

		
		//Retrieve all shift days
		List<HelperShiftDays> allShiftDays=shiftDaysRepo.findAll();
		List<String> shiftDayList= new ArrayList<String>(); 
		for(HelperShiftDays shiftDay:allShiftDays) {
			String shiftDayName=shiftDay.getShiftDayName();
		shiftDayList.add(shiftDayName);
		}
		model.addAttribute("shiftDayList",shiftDayList);
		
		//Retrieve shift days of helper
		List<HelperShiftDays> checkedShiftDays=helper.getHelper_shift_days();
		List<String> checkedShiftDayList=new ArrayList<String>();
		for(HelperShiftDays checkedShiftDay:checkedShiftDays) {
			checkedShiftDayList.add(checkedShiftDay.getShiftDayName());	
		}
		helper.getCheckedShiftDays().addAll(checkedShiftDayList);
		
		return "N00015_UpdateHelpersDetails";
	}

	
	 @PostMapping("/updateHelper")
	    public String updateHelper(@Valid @ModelAttribute("helper") HelperInfo updhelper, BindingResult bindingResult,
	        Model model, @RequestParam(value = "helperphoto", required=false) MultipartFile photo) {
		 
		 //if there is error in binding result
		 if(bindingResult.hasErrors()) {
			 
			 	//Retrieve all shift days
				List<HelperShiftDays> allShiftDays=shiftDaysRepo.findAll();
				List<String> shiftDayList= new ArrayList<String>(); 
				for(HelperShiftDays shiftDay:allShiftDays) {
					String shiftDayName=shiftDay.getShiftDayName();
				shiftDayList.add(shiftDayName);
				}
				model.addAttribute("shiftDayList",shiftDayList);
				return "N00015_UpdateHelpersDetails";
			}else {
				
				//check if input user name already exist
				LoginUser user = userRepo.getUserDetails(updhelper.getAcc().getUserName());
				if(user!=null) {
					Long oldId=user.getUserId();
					Long newId=updhelper.getAcc().getUserId();
					if(!oldId.equals(newId)) {
						System.out.println(user.getUserId());
						System.out.println(updhelper.getAcc().getUserId());
						return "redirect:returnHelperError?helperId="+updhelper.getHelperId()+"&uniqueUserErr=Username already exist. Please choose another name.";
					}
				}
				
				//check if input own code already exist
				LoginUser user1 = userRepo.getUserByOwnCode(updhelper.getAcc().getOwnCodeNumber());
				if(user1!=null) {
					Long oldId=user1.getUserId();
					Long newId=updhelper.getAcc().getUserId();
					if(!oldId.equals(newId)) {
						return "redirect:returnHelperError?helperId="+updhelper.getHelperId()+"&uniqueOwnCodeErr=Own Code already exist. Please choose another code.";
					}
				}
				
			      //validate contract start and end date
					Date startDate = updhelper.getContractStartDate();
					Date endDate = updhelper.getContractEndDate();
					if(startDate.after(endDate)) {
						return "redirect:returnHelperError?helperId="+updhelper.getHelperId()+"&startEndDateErr=Contract start sate must come before contract end date.";
					}
					
					//validate shift days 
					if(updhelper.getCheckedShiftDays().isEmpty()) {
						return "redirect:returnHelperError?helperId="+updhelper.getHelperId()+"&shiftErr=Please choose shift days.";
					}
		        
		        if(!photo.isEmpty()) {
		        	if(checkUploadFileType.checkType(photo)) {
		        		//get original photo name and generate a new file name
						  String originalFileName =StringUtils.cleanPath(photo.getOriginalFilename());
						  String saveFileName = FileNameGenerator.getRandomFileName(originalFileName);
						  
						
						//upload photo of helper
						storageService.storeHelperImage(photo,saveFileName);

						//insert photo of helper
						updhelper.setPhoto(saveFileName);
		        	}else {
						return "redirect:returnHelperError?helperId="+updhelper.getHelperId()+"&photoTypeErr=Files other than images cannot be uploaded.";
		        	}
		        }
		        

				
		        
		        //get birthday from account and set it in helper
		        updhelper.setBirthDate(updhelper.getAcc().getBirthDate());
		        
		      //get the shift days of helper from form and set in helper_shift_days
		        List<HelperShiftDays> allShiftDays=shiftDaysRepo.findAll();
		        for(String shiftDayName : updhelper.getCheckedShiftDays()) {
		        	for(HelperShiftDays siftDays : allShiftDays) {
		        		if(siftDays.getShiftDayName().equals(shiftDayName)) {
		        			HelperShiftDays helperShiftDays = new HelperShiftDays();
		        			helperShiftDays.setHelperShiftDayId(siftDays.getHelperShiftDayId());
		        			helperShiftDays.setShiftDayName(siftDays.getShiftDayName());
		        			updhelper.getHelper_shift_days().add(helperShiftDays);
		        			break;
		        		}
		        	}
		        }

		       helperRepo.save(updhelper);	        
	           return "redirect:/getHelperList";
			}
			
	    }


	 @RequestMapping(value="returnHelperError")
	 public String returnHelperError(Model model,Long helperId,
			 @RequestParam(value="uniqueUserErr",required=false) String uniqueUserErr,
			 @RequestParam(value="uniqueOwnCodeErr",required=false) String uniqueOwnCodeErr,
			 @RequestParam(value="photoTypeErr",required=false) String photoTypeErr,
			 @RequestParam(value="shiftErr",required=false) String shiftErr,
			 @RequestParam(value="startEndDateErr",required=false) String startEndDateErr) {
		 
		 HelperInfo helper = helperRepo.findById(helperId).orElse(null);
			model.addAttribute("helper", helper);
			model.addAttribute("uniqueUserErr", uniqueUserErr);
			model.addAttribute("uniqueOwnCodeErr", uniqueOwnCodeErr);
			model.addAttribute("photoTypeErr", photoTypeErr);
			model.addAttribute("shiftErr", shiftErr);
			model.addAttribute("startEndDateErr", startEndDateErr);
			
			//get path to display helper photo
			String basePath = "../images/helper-images/";
			model.addAttribute("imagePath",basePath+helper.getPhoto());

			
			//Retrieve all shift days
			List<HelperShiftDays> allShiftDays=shiftDaysRepo.findAll();
			List<String> shiftDayList= new ArrayList<String>(); 
			for(HelperShiftDays shiftDay:allShiftDays) {
				String shiftDayName=shiftDay.getShiftDayName();
			shiftDayList.add(shiftDayName);
			}
			model.addAttribute("shiftDayList",shiftDayList);
			
			//Retrieve shift days of helper
			List<HelperShiftDays> checkedShiftDays=helper.getHelper_shift_days();
			List<String> checkedShiftDayList=new ArrayList<String>();
			for(HelperShiftDays checkedShiftDay:checkedShiftDays) {
				checkedShiftDayList.add(checkedShiftDay.getShiftDayName());	
			}
			helper.getCheckedShiftDays().addAll(checkedShiftDayList);
			return "N00015_UpdateHelpersDetails";
	 }
}

