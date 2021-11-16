package com.blissstock.nursinghomesupport.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.entity.LoginUser;
import com.blissstock.nursinghomesupport.entity.TaskInfo;
import com.blissstock.nursinghomesupport.repository.ElderRepository;
import com.blissstock.nursinghomesupport.repository.TaskRepository;
import com.blissstock.nursinghomesupport.repository.UserRepository;

@Controller
public class TaskDetailsController {
	//Breadcrumb navigates to admin or user home screen
	@RequestMapping(method = RequestMethod.GET, value = "AdminOrHelperHome")
	public String adminOrHelperHome() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		LoginUser user= userRepo.getUserDetails(userName);
		if("1".equals(user.getRole())) {
			return "redirect:/AdminHome";
			
		}
		
		return "redirect:/UserHome";
		
	}
	@Autowired
	TaskRepository taskRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ElderRepository elderRepo;
	
	//Show task Details
	@RequestMapping(value="/TaskDetails",method=RequestMethod.GET)
	public String taskDetails(@RequestParam("taskId") Long taskId, Model model) {
		
		 TaskInfo taskInfo=taskRepo.findById(taskId).orElse(null);
		 model.addAttribute("taskInfo", taskInfo);
		 
		 HelperInfo helperInfo=taskInfo.getHelperInfo(); 
		 String helperName=helperInfo.getFirstName().toString()+" "+helperInfo.getLastName().
		 toString(); 
		 model.addAttribute("helperInfo", helperInfo);
		 model.addAttribute("helperName", helperName);
		 model.addAttribute("shiftType", helperInfo.getShiftType()); 
		 
		 List<ElderInfo> taskElder=taskInfo.getElderInfo(); 
		 List<String> elderNameList= new ArrayList<String>(); 
		 for (ElderInfo elderInfo : taskElder) { 
		 String elderName=elderInfo.getFirstName().toString()+" "+elderInfo.getLastName().toString(); 
		 elderNameList.add(elderName); 
		 }
		 model.addAttribute("elderNameList",elderNameList);
		 
		 List<Long> elderIdList= new ArrayList<Long>(); 
		 for (ElderInfo elderInfo : taskElder) { 
		 Long elderId=elderInfo.getElderId();
		 elderIdList.add(elderId); 
		 }
		 taskInfo.getCheckedElders().addAll(elderIdList);
		 
		 LocalTime startTime=taskInfo.getStart().toLocalTime();
		 LocalTime endTime=taskInfo.getFinish().toLocalTime();
		 model.addAttribute("startTime", startTime);
		 model.addAttribute("endTime", endTime);
		 
		 List<ElderInfo> allElders=elderRepo.findAll();
		 model.addAttribute("allElderList", allElders);
		 return "N00006_TaskDetails";
}
	
	//Back button navigate to admin or helper schedule screen
	@RequestMapping(method = RequestMethod.GET, value = "AdminOrHelperSchedule")
	public String adminOrHelperSchedule() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		LoginUser user= userRepo.getUserDetails(userName);
		if("1".equals(user.getRole())) {
			return "redirect:/Schedule";
			
		}
		
		return "redirect:/HelperSchedule";
		
	}

	//Helper's task registration
       @PostMapping("/updateTaskDetails") 
       public String helperTaskPost(@RequestParam("taskId") Long taskID, @ModelAttribute @Valid TaskInfo taskInfo, BindingResult result, Model model,RedirectAttributes redirectAttr) { 
       TaskInfo task= taskRepo.findById(taskID).orElse(null);
       task.setTaskStatus(taskInfo.getTaskStatus());
       task.setHelperTaskRemark(taskInfo.getHelperTaskRemark());
	   taskRepo.save(task);
	   return "redirect:/TaskDetails?taskId="+taskID;
	  
	  }
	 
    //Admin update task details
    @RequestMapping(value="/postTaskDetails",method=RequestMethod.POST)  
    public  String adminTaskPost(@ModelAttribute @Valid TaskInfo task, BindingResult result, Model model,RedirectAttributes redirectAttr) {
    	
    	List<ElderInfo> allEldernames=elderRepo.findAll();
    	for(Long checkedElder:task.getCheckedElders()) {
    		for(ElderInfo elder:allEldernames) {
    			Long elderId=elder.getElderId();
    			if(elderId==checkedElder) {
//    				ElderInfo elderInfo=new ElderInfo();
//    				elderInfo.setElderId(elder.getElderId());
//    				elderInfo.setFirstName(elder.getFirstName());
//    				elderInfo.setLastName(elder.getLastName());
    	    		task.getElderInfo().add(elder);
    	    		elder.getTaskInfo().add(task);
    	    		break;
    			}
    		
    		}
    	}
    	if (result.hasErrors()) {
    		redirectAttr.addFlashAttribute("activityErr", "Please enter activity name");    		  		
            return "redirect:/TaskDetails?taskId="+task.getId();
           }
    
    	
    	taskRepo.save(task);
    	return "redirect:/TaskDetails?taskId="+task.getId();
    	//return task;
    }
}
