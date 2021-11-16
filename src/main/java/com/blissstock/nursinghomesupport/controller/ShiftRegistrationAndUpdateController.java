package com.blissstock.nursinghomesupport.controller;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.entity.TaskInfo;
import com.blissstock.nursinghomesupport.entity.TaskRegistration;
import com.blissstock.nursinghomesupport.repository.ElderRepository;
import com.blissstock.nursinghomesupport.repository.HelperRepository;
import com.blissstock.nursinghomesupport.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.blissstock.nursinghomesupport.entity.SearchCriteria;
import com.blissstock.nursinghomesupport.entity.Suggestions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class ShiftRegistrationAndUpdateController {
	@Autowired
	ElderRepository elderRepo;
	
	@Autowired
	HelperRepository helperRepo;
	
	//Search helper Name with auto complete
		@RequestMapping("helperNameAutoComplete")
		@ResponseBody
		public List<Suggestions> helperNameAutoComplete(@RequestParam(value="term", required=false, defaultValue="")String term){
			List<Suggestions> suggestions= new ArrayList<Suggestions>();
			try {
				List<HelperInfo> allHelpers=helperRepo.fetchHelpers(term);
				for (HelperInfo helperInfo : allHelpers) {
					Suggestions suggestion=new Suggestions();
					String helperName=helperInfo.getFirstName().toString()+" "+helperInfo.getLastName().toString();
					Long helperId=helperInfo.getHelperId();
					suggestion.setLabel(helperName);
					suggestion.setValue(helperId.toString());
					
					suggestions.add(suggestion);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Exception in autocomplete",e);
			}
			return suggestions;
		}
		// Get shift type from searched helper name
		@PostMapping(value="/getShiftType")
		 public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody SearchCriteria search, Errors errors) {
				String result="";
				if (errors.hasErrors()) {

		            result=errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));
		            return ResponseEntity.badRequest().body(result);

		        }
				
				HelperInfo helperInfo=helperRepo.findByNameId(search.getHelperName(), search.getHelperId());
				if(helperInfo==null || helperInfo.getShiftType()==null || helperInfo.getShiftType().trim().isEmpty()) {
					result = "User not existed";
				}else {
					result = helperInfo.getShiftType();
				}
				return ResponseEntity.ok(result);
				
	}
		
	//Validate date and helper
    @PostMapping(value="/getHelperDate")
    public ResponseEntity<?> validateHelperDate(@Valid @RequestBody SearchCriteria search) {
    	
    	String dateResult="";
    	    	
    	HelperInfo helperInfo=helperRepo.findByNameId(search.getHelperName(),search.getHelperId());
    	Long helperId=helperInfo.getHelperId();
		LocalDate assignedDate=search.getDate();
		
		List<TaskInfo> taskByDate=taskRepo.findDate(assignedDate,helperId);
		if(!taskByDate.isEmpty()) {	
			dateResult="Input date is already registered. Please choose another date!";
			
		}
		return ResponseEntity.ok(dateResult);
    }
		
	// generate tabs and modal check boxes
	Logger log = LoggerFactory.getLogger(this.getClass());
	@RequestMapping(value="/ShiftRegistration", method=RequestMethod.GET)
    public String helperForm(Model model) {
          
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  String userName = auth.getName();
		  model.addAttribute("userName",userName);
		
          TaskRegistration taskRegister = new TaskRegistration();           
		  model.addAttribute("taskRegister",taskRegister);
		  
		  TaskInfo taskInfo=new TaskInfo();
		  model.addAttribute("taskInfo", taskInfo);
		  List<ElderInfo> allElders=elderRepo.findAll();
		  model.addAttribute("allElderList", allElders);
        return "N00018_ShiftRegistrationAndUpdate";
    }
	//After form submission, return to another page
	@Autowired
	TaskRepository taskRepo;
	
	@RequestMapping(value="/ShiftRegistration", method=RequestMethod.POST)
	public String shiftSubmit(@ModelAttribute TaskRegistration taskRegister, @ModelAttribute TaskInfo taskInfo, Model model , BindingResult result,RedirectAttributes redirectAttr) {
		TaskInfo newTask1= new TaskInfo();
		TaskInfo newTask2= new TaskInfo();
		TaskInfo newTask3= new TaskInfo();
		TaskInfo newTask4= new TaskInfo();
		//helperName
		HelperInfo helperInfo=helperRepo.findByNameId(taskRegister.getName(),taskRegister.getId());
		newTask1.setHelperInfo(helperInfo);
		newTask2.setHelperInfo(helperInfo);
		newTask3.setHelperInfo(helperInfo);
		newTask4.setHelperInfo(helperInfo);
		
		//AssignedDate
		LocalDate assignedDate=taskRegister.getDate();
				
		newTask1.setAssignedDate(taskRegister.getDate());
		newTask2.setAssignedDate(taskRegister.getDate());
		newTask3.setAssignedDate(taskRegister.getDate());
		newTask4.setAssignedDate(taskRegister.getDate());
		
		//ElderList
		for(Long firstShiftElder:taskInfo.getCheckedFirstShift()) {		
		ElderInfo elderInfo=elderRepo.findById(firstShiftElder).orElse(null);		
	    elderInfo.getTaskInfo().add(newTask1);
	    newTask1.getElderInfo().add(elderInfo);
		 }
		for(Long secondShiftElder:taskInfo.getCheckedSecondShift()) {
			ElderInfo elderInfo=elderRepo.findById(secondShiftElder).orElse(null);
		    elderInfo.getTaskInfo().add(newTask2);
		    newTask2.getElderInfo().add(elderInfo);
		}
		for(Long thirdShiftElder:taskInfo.getCheckedThirdShift()) {
			ElderInfo elderInfo=elderRepo.findById(thirdShiftElder).orElse(null);
		    elderInfo.getTaskInfo().add(newTask3);
		    newTask3.getElderInfo().add(elderInfo);
		}
		for(Long forthShiftElder:taskInfo.getCheckedForthShift()) {
			ElderInfo elderInfo=elderRepo.findById(forthShiftElder).orElse(null);
		    elderInfo.getTaskInfo().add(newTask4);
		    newTask4.getElderInfo().add(elderInfo);
		}

		//Shift
		if("morning".equals(helperInfo.getShiftType().toLowerCase())){
			
			newTask1.setStart(assignedDate.atTime(07, 00));
			newTask1.setFinish(assignedDate.atTime(9, 00));
			newTask1.setTitle("出勤・起床介助、洗面介助・排せつ介助・朝食介助・口腔ケア");
			
			newTask2.setStart(assignedDate.atTime(9, 00));
			newTask2.setFinish(assignedDate.atTime(11, 00));
			newTask2.setTitle("入浴介助　（1人15分）");
			
			newTask3.setStart(assignedDate.atTime(11, 30));
			newTask3.setFinish(assignedDate.atTime(13, 00));
			newTask3.setTitle("昼食の配膳を介助・食休みのためにベッドに横になってくださる"); 
			
			newTask4.setStart(assignedDate.atTime(13, 30));
			newTask4.setFinish(assignedDate.atTime(15, 00));
			newTask4.setTitle("おやつとお茶の準備・ラジオ体操・リハビリなど・退社");  
		}
		if("day".equals(helperInfo.getShiftType().toLowerCase())){
			newTask1.setStart(assignedDate.atTime(8, 30));
			newTask1.setFinish(assignedDate.atTime(11, 00));
			newTask1.setTitle("出勤・入浴介助　（1人15分）");
			
			newTask2.setStart(assignedDate.atTime(11, 30));
			newTask2.setFinish(assignedDate.atTime(13, 00));
			newTask2.setTitle("昼食の配膳を介助・食休みのためにベッドに横になってくださる");
			
			newTask3.setStart(assignedDate.atTime(13, 30));
			newTask3.setFinish(assignedDate.atTime(15, 00));
			newTask3.setTitle("おやつとお茶の準備・ラジオ体操・リハビリなど");
			
			newTask4.setStart(assignedDate.atTime(15, 00));
			newTask4.setFinish(assignedDate.atTime(17, 30));
			newTask4.setTitle("おやつの配膳・多少の食事介助カーテンを閉めて落ち着く環境づくり・夜勤者に申し送りをして退社）");
		}
		if("evening".equals(helperInfo.getShiftType().toLowerCase())){
			newTask1.setStart(assignedDate.atTime(11, 30));
			newTask1.setFinish(assignedDate.atTime(13, 00));
			newTask1.setTitle("出勤・昼食の配膳を介助・食休みのためにベッドに横になってくださる");
			
			newTask2.setStart(assignedDate.atTime(13, 30));
			newTask2.setFinish(assignedDate.atTime(15, 00));
			newTask2.setTitle("おやつとお茶の準備・ラジオ体操・リハビリなど");
			
			newTask3.setStart(assignedDate.atTime(15, 00));
			newTask3.setFinish(assignedDate.atTime(17, 30));
			newTask3.setTitle("おやつの配膳・多少の食事介助・カーテンを閉めて落ち着く環境づく");
			
			newTask4.setStart(assignedDate.atTime(17, 30));
			newTask4.setFinish(assignedDate.atTime(20, 00));
			newTask4.setTitle("離床介助・夕食介助・口腔ケア・排せつ介助・就寝介助・服薬介助・退社");
		}
		if("night".equals(helperInfo.getShiftType().toLowerCase())){
			
			newTask1.setStart(assignedDate.atTime(17, 30));
			newTask1.setFinish(assignedDate.atTime(19, 00));
			newTask1.setTitle("申し送り・離床介助・夕食介助・口腔ケア");
			
			newTask2.setStart(assignedDate.atTime(19, 00));
			newTask2.setFinish(assignedDate.atTime(21, 00));
			newTask2.setTitle("排せつ介助・就寝介助・服薬介助");
			
			newTask3.setStart(assignedDate.atTime(21, 00));
			newTask3.setFinish(assignedDate.atTime(07, 00));
			newTask3.setTitle("巡視・体位変換（寝返り介助）・おむつ交換・トイレ誘導・コール対応");
			
			newTask4.setStart(assignedDate.atTime(07, 00));
			newTask4.setFinish(assignedDate.atTime(9, 00));
			newTask4.setTitle("起床介助、洗面介助・排せつ介助・朝食介助・口腔ケア・申し送り・退社");
		}
		
		//Remark
		newTask1.setAdminTaskRemark(taskRegister.getadminRemark0());
		newTask2.setAdminTaskRemark(taskRegister.getadminRemark1());
		newTask3.setAdminTaskRemark(taskRegister.getadminRemark2());
		newTask4.setAdminTaskRemark(taskRegister.getadminRemark3());
		
		newTask1.setTaskStatus("未");
		newTask2.setTaskStatus("未");
		newTask3.setTaskStatus("未");
		newTask4.setTaskStatus("未");
		
		taskRepo.save(newTask1);
		taskRepo.save(newTask2);
		taskRepo.save(newTask3);
		taskRepo.save(newTask4);
		
		//otherTask
		if(taskRegister.getOtherActivityName()!=null && taskRegister.getOtherStartTime()!=null && taskRegister.getOtherEndTime()!=null) {
		TaskInfo otherTask=new TaskInfo();
		otherTask.setHelperInfo(helperInfo);
		otherTask.setAssignedDate(taskRegister.getDate());
		otherTask.setTitle(taskRegister.getOtherActivityName());
		
		LocalDateTime otherStartTime=assignedDate.atTime(taskRegister.getOtherStartTime());
		otherTask.setStart(otherStartTime);
		LocalDateTime otherEndTime=assignedDate.atTime(taskRegister.getOtherEndTime());
		otherTask.setFinish(otherEndTime);
		
		otherTask.setTitle(taskRegister.getOtherActivityName());
		
		for(Long otherShiftElder:taskInfo.getCheckedOtherActivity()) {
			ElderInfo elderInfo=elderRepo.findById(otherShiftElder).orElse(null);
		    elderInfo.getTaskInfo().add(otherTask);
		    otherTask.getElderInfo().add(elderInfo);
			}
		otherTask.setAdminTaskRemark(taskRegister.getOtherRemark());
		otherTask.setTaskStatus("未");
		
		taskRepo.save(otherTask);
		}
		return "redirect:/ShiftRegistration";
	}
	
}

