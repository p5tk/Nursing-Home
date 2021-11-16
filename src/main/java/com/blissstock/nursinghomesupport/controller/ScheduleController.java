package com.blissstock.nursinghomesupport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.Event;
import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.entity.LoginUser;
import com.blissstock.nursinghomesupport.entity.Resource;
import com.blissstock.nursinghomesupport.entity.TaskInfo;
import com.blissstock.nursinghomesupport.repository.ElderRepository;
import com.blissstock.nursinghomesupport.repository.HelperRepository;
import com.blissstock.nursinghomesupport.repository.TaskRepository;
import com.blissstock.nursinghomesupport.repository.UserRepository;


@Controller
public class ScheduleController {
	@RequestMapping(value="/Schedule", method=RequestMethod.GET)
    public String customerSubmit(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName",userName);
        return "N00017_Schedule";
    }
	
}

@RestController
class ResourceRestController {
	
	@Autowired
	HelperRepository helperRepo;
	
	@Autowired
	TaskRepository taskRepo;
	
	@RequestMapping(value="/resources", method=RequestMethod.GET)
	public List<Resource> resources() {
 
       List<HelperInfo> helperInfo=helperRepo.findAll();
       List<Resource> resources = new ArrayList<Resource>();
       
       for(HelperInfo helper:helperInfo) {
       Resource resource=new Resource();
       resource.setId(helper.getHelperId());
       resource.setTitle(helper.getFirstName()+" "+helper.getLastName());

       resources.add(resource);
       
       }
       return resources;
	}
		
}

@RestController
class EventRestController {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TaskRepository taskRepo;

	@Autowired
	ElderRepository elderRepo;

	@RequestMapping(value="/events", method=RequestMethod.GET)
	public List<Event> allEvents(Model model) {
		
        List<TaskInfo> taskInfo=taskRepo.findAll();
        List<Event> events = new ArrayList<Event>();
        for(TaskInfo task:taskInfo) {
            Event event=new Event();
            event.setId(task.getId());
            
            String url = "/TaskDetails?taskId="+task.getId();
            event.setUrl(url);
            
            HelperInfo helperInfo=task.getHelperInfo();
            event.setResourceId(helperInfo.getHelperId());            
            event.setStart(task.getStart());            
            event.setFinish(task.getFinish());
            event.setTitle(task.getTitle());
            //set event color
       	 	if("未".equals(task.getTaskStatus())) {
        		  event.setColor("orange");
            }
            if("中".equals(task.getTaskStatus())) {
           	 	event.setColor("green");
            }
            if("完".equals(task.getTaskStatus())) {
           	 	event.setColor("grey");
            }
            if("中止".equals(task.getTaskStatus())) {
            	event.setColor("red");
            } 
            events.add(event);
            
            }
        
        return events;	
	}
		
}