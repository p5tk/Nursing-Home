package com.blissstock.nursinghomesupport.controller;


import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blissstock.nursinghomesupport.entity.ContactPerson;
import com.blissstock.nursinghomesupport.entity.ElderHobbies;
import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.repository.ElderRepository;
import com.blissstock.nursinghomesupport.repository.elderHobbyRepository;
import com.blissstock.nursinghomesupport.storage.StorageService;
import com.blissstock.nursinghomesupport.utilities.FileNameGenerator;
import com.blissstock.nursinghomesupport.utilities.NullStringChecker;
import com.blissstock.nursinghomesupport.utilities.checkUploadFileType;

@Controller
public class UpdateElderDetailsController {
	
	private final StorageService storageService;

	@Autowired
	public UpdateElderDetailsController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@Autowired
	ElderRepository elderRepo;	
	
	@Autowired 
	elderHobbyRepository elderHobyyRepository;
	
	@RequestMapping(value="/editElder/{elderId}", method=RequestMethod.GET)
	public  String editElder(@PathVariable("elderId") Long elderId,Model model) {
		
			//retrieve information of elder by elder Id
			ElderInfo e= elderRepo.findById(elderId).orElse(null);
			
			//get the names of the hobbies 
			//add them to String list
			//the list will be used in view to check the checkbox
			List<String> elderHobbyNames= new ArrayList<>();
			for(ElderHobbies hb:e.getElder_hobby()) {
				elderHobbyNames.add(hb.getHobbyName());
			}
			
			//create a list of hobbies that will be displayed in elder update screen
			List<String> allHobbyNames = new ArrayList<>();
			allHobbyNames.add("reading");
			allHobbyNames.add("mediation");
			allHobbyNames.add("painting");
			allHobbyNames.add("gardening");
			allHobbyNames.add("knitting");
			allHobbyNames.add("others");
			
			//check number of contact person for elder
			//add one more contact person object if there is only one contact person
			if(e.getContact_person().size()<2) {
				e.addContactOfElder(new ContactPerson());
			}
			
			
			String basePath = "../images/elder-images/";
			String file1Path = "../images/file1/";
			String file2Path = "../images/file2/";
			
			model.addAttribute("elderHobbyNames", elderHobbyNames);
			model.addAttribute("newHobbyList", allHobbyNames);
			model.addAttribute("elder", e);
			model.addAttribute("imagePath",basePath+e.getPhoto());
			model.addAttribute("file1Path",file1Path+e.getFile1());
			model.addAttribute("file2Path",file2Path+e.getFile2());
			
			
			return "N00010_UpdateElderDetails";
	}

	@PostMapping("/updateElderPost")
	public String updateElderPost(@Valid @ModelAttribute("elder") ElderInfo elder,BindingResult bindingResult,
			@RequestParam(value = "newHobbies", required=false,defaultValue = "") List<String> newHobbiesFromForm,@RequestParam("elderphoto") MultipartFile photo,Model model) {
		if(bindingResult.hasErrors()) {   
			
			//retrieve information of elder by elder Id
			ElderInfo e= elderRepo.findById(elder.getElderId()).orElse(null);
			
			//get the names of the hobbies 
			//add them to String list
			//the list will be used in view to check the checkbox
			List<String> elderHobbyNames= new ArrayList<>();
			for(ElderHobbies hb:e.getElder_hobby()) {
				elderHobbyNames.add(hb.getHobbyName());
			}
			
			//create a list of hobbies that will be displayed in elder update screen
			List<String> allHobbyNames = new ArrayList<>();
			allHobbyNames.add("reading");
			allHobbyNames.add("mediation");
			allHobbyNames.add("painting");
			allHobbyNames.add("gardening");
			allHobbyNames.add("knitting");
			allHobbyNames.add("others");
			
						
			model.addAttribute("elderHobbyNames", elderHobbyNames);
			model.addAttribute("newHobbyList", allHobbyNames);
			return "N00010_UpdateElderDetails";
		}else {
			ContactPerson cp = elder.getContact_person().get(0);
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
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpfNameErr=Please enter first name of contact person.";
			}
			if(NullStringChecker.isStringEmpty(lastName)) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cplNameErr=Please enter last name of contact person.";
			}
			if(NullStringChecker.isStringEmpty(relation)) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpRSErr=Please relationship of elder with contact person.";
			}
			if(NullStringChecker.isStringEmpty(phone)) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpPhoneErr=Please enter phone number of contact person.";
			}
			if(phone.length()<10 || phone.length()>10) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpPhoneErr=Phone number should be of 10 digits";
			}
			if(NullStringChecker.isStringEmpty(buildingNo)) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpRoomNoErr=Please enter building number.";
			}
			if(NullStringChecker.isStringEmpty(street)) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpStreetErr=Please enter the street address.";
			}
			if(NullStringChecker.isStringEmpty(city)) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpCityErr=Please enter city.";
			}
			if(NullStringChecker.isStringEmpty(state)) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpStateErr=Please enter the state.";
			}
			if(NullStringChecker.isStringEmpty(postalCode)) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpPostalErr=Please enter the postal code.";
			}
			if(NullStringChecker.isStringEmpty(country)) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&cpCountryErr=Please choose the country.";
			}
			//check if photo is not selected
			if(!photo.isEmpty()) {
				if(checkUploadFileType.checkType(photo)) {
				  String originalFileName =StringUtils.cleanPath(photo.getOriginalFilename());
				  String saveFileName = FileNameGenerator.getRandomFileName(originalFileName);
				  
				  storageService.storeElderImage(photo,saveFileName);
  
				  elder.setPhoto(saveFileName);
				}else {
					return "redirect:returnElderError?elderId="+elder.getElderId()+"&photoTypeErr=Files other than image file cannot be uploaded.";
				}
	        }
			
			//check if hobby is not chosen
			if(newHobbiesFromForm.isEmpty()) {
				return "redirect:returnElderError?elderId="+elder.getElderId()+"&hobbyErr=Please choose the hobby of elder.";
			}
			
			
				//delete hobbies records of current elder
				try{
					List<ElderHobbies> eb = elder.getElder_hobby();
					eb.clear();
					elderHobyyRepository.deleteByElderId(elder.getElderId());
					System.out.println(elder.getElderId());
				}catch(Exception e) {
					System.out.println("Error Deleting elders` hobbies!!");
				}
				
				//recreate and save new ElderHobbies objects for current elder
				List<ElderHobbies> neweb = new ArrayList<>();
				for(String nhb:newHobbiesFromForm) {
					ElderHobbies insertnhb=new ElderHobbies(nhb);
					insertnhb.setElder(elder);
					neweb.add(insertnhb);
				}
				

				
				//add elder in each contact person object
				List<ContactPerson> cpList= elder.getContact_person();
				List<ContactPerson> newcpList = new ArrayList<>();
				
				for(ContactPerson cp1:cpList) {
					 String gurdianObjFname = cp1.getFirstName();
					 String gurdianObjLname = cp1.getLastName();
					if(!NullStringChecker.isStringEmpty(gurdianObjFname) && !NullStringChecker.isStringEmpty(gurdianObjLname)) {
						cp1.addElder(elder);
						newcpList.add(cp1);
					}
				}
				
				elder.getContact_person().clear();
				elder.setContact_person(newcpList);
				elder.setElder_hobby(neweb);
				elderRepo.save(elder);

				return "redirect:ElderList";
				
			}
		}
	
	@RequestMapping("returnElderError")
	public String returnElderError(Model model,Long elderId,@RequestParam(value="hobbyErr",required=false) String hobbyErr,
			@RequestParam(value="photoTypeErr",required=false) String photoTypeErr,@RequestParam(value="cpfNameErr",required=false) String cpfNameErr,
			@RequestParam(value="cplNameErr",required=false) String cplNameErr,@RequestParam(value="cpRSErr",required=false) String cpRSErr,
			@RequestParam(value="cpPhoneErr",required=false) String cpPhoneErr,@RequestParam(value="cpRoomNoErr",required=false) String cpRoomNoErr,
			@RequestParam(value="cpStreetErr",required=false) String cpStreetErr,@RequestParam(value="cpCityErr",required=false) String cpCityErr,
			@RequestParam(value="cpStateErr",required=false) String cpStateErr,@RequestParam(value="cpPostalErr",required=false) String cpPostalErr
			,@RequestParam(value="cpCountryErr",required=false) String cpCountryErr) {
		try {
			//retrieve information of elder by elder Id
			ElderInfo e= elderRepo.findById(elderId).orElse(null);
			
			//get the names of the hobbies 
			//add them to String list
			//the list will be used in view to check the checkbox
			List<String> elderHobbyNames= new ArrayList<>();
			for(ElderHobbies hb:e.getElder_hobby()) {
				elderHobbyNames.add(hb.getHobbyName());
			}
			
			//create a list of hobbies that will be displayed in elder update screen
			List<String> allHobbyNames = new ArrayList<>();
			allHobbyNames.add("reading");
			allHobbyNames.add("mediation");
			allHobbyNames.add("painting");
			allHobbyNames.add("gardening");
			allHobbyNames.add("knitting");
			allHobbyNames.add("others");
			
			//check number of contact person for elder
			//add one more contact person object if there is only one contact person
			if(e.getContact_person().size()<2) {
				e.addContactOfElder(new ContactPerson());
			}
			
			
			String basePath = "../images/elder-images/";
			
			model.addAttribute("elderHobbyNames", elderHobbyNames);
			model.addAttribute("newHobbyList", allHobbyNames);
			model.addAttribute("elder", e);
			model.addAttribute("imagePath",basePath+e.getPhoto());
			model.addAttribute("hobbyErr", hobbyErr);
			model.addAttribute("photoTypeErr", photoTypeErr);
			model.addAttribute("cpfNameErr", cpfNameErr);
			model.addAttribute("cplNameErr", cplNameErr);
			model.addAttribute("cpRSErr", cpRSErr);
			model.addAttribute("cpPhoneErr", cpPhoneErr);
			model.addAttribute("cpRoomNoErr", cpRoomNoErr);
			model.addAttribute("cpStreetErr", cpStreetErr);
			model.addAttribute("cpCityErr", cpCityErr);
			model.addAttribute("cpStateErr", cpStateErr);
			model.addAttribute("cpPostalErr", cpPostalErr);
			model.addAttribute("cpCountryErr", cpCountryErr);
			return "N00010_UpdateElderDetails";

		}catch(Exception e){
			e.getStackTrace();
		}
		return "";
	}
		
}
