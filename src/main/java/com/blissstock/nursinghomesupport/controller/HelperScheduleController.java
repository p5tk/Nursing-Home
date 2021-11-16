package com.blissstock.nursinghomesupport.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.entity.LoginUser;
import com.blissstock.nursinghomesupport.entity.TaskInfo;
import com.blissstock.nursinghomesupport.repository.ElderRepository;
import com.blissstock.nursinghomesupport.repository.HelperRepository;
import com.blissstock.nursinghomesupport.repository.TaskRepository;
import com.blissstock.nursinghomesupport.repository.UserRepository;

@Controller
public class HelperScheduleController {
	/*@Autowired
	HelperInfo helperInfo;*/
	@Autowired
	HelperRepository helperRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TaskRepository taskRepo;
	
	@Autowired
	ElderRepository elderRepo;
	@RequestMapping(value="/HelperSchedule", method=RequestMethod.GET)
    public String getHelperSchedule(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName",userName);
		
		LoginUser loginUser=userRepo.getUserDetails(userName);
        HelperInfo helperInfo=loginUser.getHelper();
        model.addAttribute("helperInfo",helperInfo);
        
        //Display shift type start time and end time
        String shiftType=helperInfo.getShiftType();
        if("morning".equals(shiftType)) {
        helperInfo.setShiftStartTime("07:00");
        helperInfo.setShiftEndTime("16:00");
        }
        if("day".equals(shiftType)) {
            helperInfo.setShiftStartTime("08:30");
            helperInfo.setShiftEndTime("17:30");
        }
        if("evening".equals(shiftType)) {
            helperInfo.setShiftStartTime("11:00");
            helperInfo.setShiftEndTime("20:00");
        }
        if("night".equals(shiftType)) {
            helperInfo.setShiftStartTime("17:00");
            helperInfo.setShiftEndTime("09：00");
       }
        return "N00005_Helper'sSchedule";
    }
	
	//return helper schedule by searching with helperId
	@RequestMapping(value="/HelperSchedule/{helperId}", method=RequestMethod.GET)
    public String getHelperScheduleById(@PathVariable("helperId") Long helperId ,Model model) {
		
		HelperInfo helper = helperRepo.findById(helperId).orElse(null);
        
        
        //Display shift type start time and end time
        String shiftType=helper.getShiftType();
        if("morning".equals(shiftType)) {
        	helper.setShiftStartTime("07:00");
        	helper.setShiftEndTime("16:00");
        }
        if("day".equals(shiftType)) {
        	helper.setShiftStartTime("08:30");
            helper.setShiftEndTime("17:30");
        }
        if("evening".equals(shiftType)) {
        	helper.setShiftStartTime("11:00");
        	helper.setShiftEndTime("20:00");
        }
        if("night".equals(shiftType)) {
        	helper.setShiftStartTime("17:00");
        	helper.setShiftEndTime("09:00");
       }
        model.addAttribute("helperInfo",helper);
        
        return "N00005_Helper'sSchedule(Admin)";
    }

	
	@RequestMapping(value="/helperEvents/{helperId}", method=RequestMethod.GET)
	public @ResponseBody List<TaskInfo> helperEvents(@PathVariable("helperId") Long helperId) {
		
		//get current user`s username and find it`s id
		//Long id=helper.getHelperId();
		 
		
        //find current user`s tasks
		
         List<TaskInfo>taskOfHelper = taskRepo.findTask(helperId);
        
        //set each task`s url for fullcalendar
       for(TaskInfo singleTask:taskOfHelper) {
            String url = "/TaskDetails?taskId="+singleTask.getId();
        	singleTask.setUrl(url);
	       
             //set event color
             if("未".equals(singleTask.getTaskStatus())) {
         		  singleTask.setColor("orange");
             }
             if("中".equals(singleTask.getTaskStatus())) {
            	 singleTask.setColor("green");
             }
             if("完".equals(singleTask.getTaskStatus())) {
            	 singleTask.setColor("grey");
             }
             if("中止".equals(singleTask.getTaskStatus())) {
            	 singleTask.setColor("red");
             } 
        }
        
        return taskOfHelper;
        }
	
}


@RestController

class HelperScheduleRestController {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TaskRepository taskRepo;
	
	@Autowired
	HelperRepository helperRepo;
	
	@RequestMapping(value="/allevents", method=RequestMethod.GET)
	public List<TaskInfo> helperEvents(@ModelAttribute("helper") final HelperInfo helper) {
		
		//get current user`s username and find it`s id
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		LoginUser loginUser=userRepo.getUserDetails(userName);
        HelperInfo helperInfo=loginUser.getHelper();
        long helperId=helperInfo.getHelperId();
		
        //find current user`s tasks
          List<TaskInfo>taskOfHelper = taskRepo.findTask(helperId);
        
        //set each task`s url for fullcalendar
       for(TaskInfo singleTask:taskOfHelper) {
            String url = "/TaskDetails?taskId="+singleTask.getId();
        	singleTask.setUrl(url);
	       
             //set event color
             if("未".equals(singleTask.getTaskStatus())) {
         		  singleTask.setColor("orange");
             }
             if("中".equals(singleTask.getTaskStatus())) {
            	 singleTask.setColor("green");
             }
             if("完".equals(singleTask.getTaskStatus())) {
            	 singleTask.setColor("grey");
             }
             if("中止".equals(singleTask.getTaskStatus())) {
            	 singleTask.setColor("red");
             } 
        }
        
        return taskOfHelper;
        }
		
	
}