package com.blissstock.nursinghomesupport.utilities;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.blissstock.nursinghomesupport.entity.ContactPerson;
import com.blissstock.nursinghomesupport.entity.DailyRecord;
import com.blissstock.nursinghomesupport.entity.ElderHobbies;
import com.blissstock.nursinghomesupport.entity.ElderInfo;
import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.entity.HelperShiftDays;

public class WriteDataToCSV {
	
	//write elder`s information into csv file
	public static void writeObjectToCSV(PrintWriter writer,List<ElderInfo> elders) {
	    try (
	        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
	                      .withHeader("ID", "FirstName", "LastName","Sex",
	                    		  "Date Of Birth","Admission Date","Room No",
	                    		  "Dislike Meals","Favourite Meals","Hobbies",
	                    		  "Illness","Medication Information","Phone No",
	                    		  "Address:Building No","Address:Street",
	                    		  "Address:City","Address:State","Address:Postal Code","Address:Country",
	                    		  "Contact Person1:First Name","Contact Person1:Last Name","Contact Person1:Relationship",
	                    		  "Contact Person1:Country Code","Contact Person1:Phone No","Contact Person1Address:Building No","Contact Person1Address:Street",
	                    		  "Contact Person1Address:City","Contact Person1Address:State","Contact Person1Address:Postal Code","Contact Person1Address:Country",
	                    		  "Contact Person2:First Name","Contact Person2:Last Name","Contact Person2:Relationship",
	                    		  "Contact Person2:Country Code","Contact Person2:Phone No","Contact Person2Address:Building No","Contact Person2Address:Street",
	                    		  "Contact Person2Address:City","Contact Person2Address:State","Contact Person2Address:Postal Code","Contact Person2Address:Country"));
	    ) {
	      for (ElderInfo elder : elders) {
	    	  
	    	//get elder`s hobbies and change it into a comma separated string
	    	List<ElderHobbies> hobbies=elder.getElder_hobby();
    		String hobbyStr="";
	    	for(ElderHobbies hobby:hobbies) {
	    		hobbyStr=hobbyStr+hobby.getHobbyName()+",";
	    	}
	    	
	    	//get elder`s contact person information
	    	List<ContactPerson> gurdians = elder.getContact_person();
	    	String[] fname = new String[2];
	    	String[] lname = new String[2];
	    	String[] relationship = new String[2];
	    	String[] countryCode = new String[2];
	    	String[] phoneNo = new String[2];
	    	String[] buildingNo = new String[2];
	    	String[] street = new String[2];
	    	String[] city = new String[2];
	    	String[] state = new String[2];
	    	String[] postalCode = new String[2];
	    	String[] country = new String[2];

	    	int index=0;
	    	for(ContactPerson cp:gurdians) {
	    		fname[index] = cp.getFirstName();
	    		lname[index] = cp.getLastName();
	    		relationship[index] = cp.getRelationship();
	    		countryCode[index] = cp.getCountryCode();
	    		phoneNo[index] = cp.getPhoneNo();
	    		buildingNo[index] = cp.getBuildingOrRoomNo();
	    		street[index] = cp.getStreet();
	    		city[index] = cp.getCity();
	    		state[index] = cp.getState();
	    		postalCode[index] = cp.getPostalCode();
	    		country[index] = cp.getCountry();
	    		index+=1;
	    	}
	    	
	        List<String> data = Arrays.asList(elder.getElderId().toString(),
	        		elder.getFirstName(),
	        		elder.getLastName(),
	        		elder.getSex(),
	        		elder.getBirthday().toString(),
	        		elder.getAdmissionDate().toString(),
	        		elder.getRoomNo(),
	        		elder.getDislikeMeal(),
	        		elder.getFavouriteMeal(),
	        		hobbyStr,
	        		elder.getIllness(),
	        		elder.getMedicationInfo(),
	        		elder.getPhoneNo(),
	        		elder.getBuildingOrRoomNo(),
	        		elder.getStreet(),
	        		elder.getCity(),
	        		elder.getState(),
	        		elder.getPostalCode(),
	        		elder.getCountry(),
	        		fname[0],
	        		lname[0],
	        		relationship[0],
	        		countryCode[0],
	        		phoneNo[0],
	        		buildingNo[0],
	        		street[0],
	        		city[0],
	        		state[0],
	        		postalCode[0],
	        		country[0],
	        		fname[1],
	        		lname[1],
	        		relationship[1],
	        		countryCode[1],
	        		phoneNo[1],
	        		buildingNo[1],
	        		street[1],
	        		city[1],
	        		state[1],
	        		postalCode[1],
	        		country[1]);
	        
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	    } catch (Exception e) {
	      System.out.println("Writing CSV error!");
	      e.printStackTrace();
	    }
	    }

	//write helper`s information into csv file
	public static void writeObjectToCSVofHelper(PrintWriter writer,List<HelperInfo> helpers) {
		//defining the headers of the data
	    try (
	        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
	                      .withHeader("ID","Own Code Number","User Name","Password", "FirstName", "LastName","Sex",
	                    		  "Date Of Birth","Phone No",
	                    		  "Address:Building No","Address:Street",
	                    		  "Address:City","Address:State","Address:Postal Code","Address:Country",
	                    		  "Hire Date","Contract Start Date","Contract End Date","Hourly Wage","Shift Type","Education",
	                    		  "Shift Days","Remarks"
	                    		  ));
	    ) {
	      for (HelperInfo helper : helpers) {
	    	
	    	
	    	//get helper's shift days and change it into a comma separated string
	    	List<HelperShiftDays> shiftDays=helper.getHelper_shift_days();
    		String shiftDaysStr="";
	    	for(HelperShiftDays shiftDay:shiftDays) {
	    		shiftDaysStr=shiftDaysStr+shiftDay.getShiftDayName()+",";
	    	}
	    	
	    	//add data according to headers
	    	List<String> writtenHelper = Arrays.asList(
	    			helper.getHelperId().toString(),
	    	    	helper.getAcc().getOwnCodeNumber().toString(),
	    			helper.getAcc().getUserName(),
	    			helper.getAcc().getPassword(),
	        		helper.getFirstName(),
	        		helper.getLastName(),
	        		helper.getGender(),
	        		helper.getBirthDate().toString(),
	        		helper.getPhoneNo().toString(),
	        		helper.getBuildingNo(),
	        		helper.getStreetAddress(),
	        		helper.getCity(),
	        		helper.getState(),
	        		helper.getPostalCode(),
	        		helper.getCountry(),
	        		helper.getHireDate().toString(),
	        		helper.getContractStartDate().toString(),
					helper.getContractEndDate().toString(),
					helper.getHourlyWage().toString(),
					helper.getShiftType(),
					helper.getEducation(),
					shiftDaysStr,
					helper.getRemark()
	    	        );
	        
	        csvPrinter.printRecord(writtenHelper);
	      }
	      csvPrinter.flush();
	    } catch (Exception e) {
	      System.out.println("Writing CSV error!");
	      e.printStackTrace();
	    }
	    }
	
	//write daily conditions of service user in csv file
	public static void writeObjectToCsvOfDailyRecord(PrintWriter writer,List<DailyRecord> dailyRecords) {
		
		try (
		        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
		                      .withHeader("ID","Date","Blood Pressure (AM Upper)",
		                    		  	"Blood Pressure (AM Lower)", "Blood Pressure (PM Upper)",
		                    		  	"Blood Pressure (PM Lower)", "Body Temperature(AM)",
		                    		  	"Body Temperature(PM)", "Mililiter of Pee", "Pee Times",
		                    		  	"Poop Volume", "Poop Times","Breakfast Observation Content",
		                    		  	"Lunch Observation Content", "Dinner Observation Content",
		                    		  	"Snack Name", "Personal Care Observation Content",
		                    		  	"Medicine Observation Content", "Other Activity Observation Content"
		                    		  ));
		    ){
			for (DailyRecord dailyRecord : dailyRecords) {
				
				List<String> data = Arrays.asList(dailyRecord.getDailyRecordID().toString(),
							dailyRecord.getDate().toString(),
							dailyRecord.getVital().getBpAMUpper().toString(),
							dailyRecord.getVital().getBpAMLower().toString(),
							dailyRecord.getVital().getBpPMUpper().toString(),
							dailyRecord.getVital().getBpPMLower().toString(),
							dailyRecord.getVital().getBtAM().toString(),
							dailyRecord.getVital().getBtPM().toString(),
							dailyRecord.getVital().getPeeMl().toString(),
							dailyRecord.getVital().getPeeTimes().toString(),
							dailyRecord.getVital().getPoopVolume(),
							dailyRecord.getVital().getPoopTimes().toString(),
							dailyRecord.getMeal().getBreakfasObservationContent(),
							dailyRecord.getMeal().getLunchObservationContent(),
							dailyRecord.getMeal().getDinnerObservationContent(),
							dailyRecord.getSnack().getSnackName(),
							dailyRecord.getPersonalCare().getOralCareAM() + dailyRecord.getPersonalCare().getOralCarePM(),
							dailyRecord.getMedication().getMedicationObservation(),
							dailyRecord.getOtherActivity().getActivityObserContent()
							
							);
				csvPrinter.printRecord(data);
							
										
			}
			csvPrinter.flush();
		} catch(Exception e){
			System.out.println("Writing CSV error!");
		      e.printStackTrace();
		}
	}
	

	//Individual Daily Record (N00012)
	
	public static void writeObjectToCsvOfIndividualDailyRecord(PrintWriter writer,List<DailyRecord> dailyRecords) {
		
		try (
		        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
		                      .withHeader("ID","Date","Blood Pressure (AM Upper)",
		                    		  	"Blood Pressure (AM Lower)", "Blood Pressure (PM Upper)",
		                    		  	"Blood Pressure (PM Lower)", "Body Temperature(AM)",
		                    		  	"Body Temperature(PM)", "Mililiter of Pee", "Pee Times",
		                    		  	"Poop Volume", "Poop Times","Breakfast Observation Content",
		                    		  	"Lunch Observation Content", "Dinner Observation Content",
		                    		  	"Snack Name", "Personal Care Observation Content",
		                    		  	"Medicine Observation Content", "Other Activity Observation Content"
		                    		  ));
		    ){
			for (DailyRecord dailyRecord : dailyRecords) {
				
				List<String> data = Arrays.asList(dailyRecord.getDailyRecordID().toString(),
							dailyRecord.getDate().toString(),
							dailyRecord.getVital().getBpAMUpper().toString(),
							dailyRecord.getVital().getBpAMLower().toString(),
							dailyRecord.getVital().getBpPMUpper().toString(),
							dailyRecord.getVital().getBpPMLower().toString(),
							dailyRecord.getVital().getBtAM().toString(),
							dailyRecord.getVital().getBtPM().toString(),
							dailyRecord.getVital().getPeeMl().toString(),
							dailyRecord.getVital().getPeeTimes().toString(),
							dailyRecord.getVital().getPoopVolume(),
							dailyRecord.getVital().getPoopTimes().toString(),
							dailyRecord.getMeal().getBreakfasObservationContent(),
							dailyRecord.getMeal().getLunchObservationContent(),
							dailyRecord.getMeal().getDinnerObservationContent(),
							dailyRecord.getSnack().getSnackName(),
							dailyRecord.getPersonalCare().getOralCareAM() + dailyRecord.getPersonalCare().getOralCarePM(),
							dailyRecord.getMedication().getMedicationObservation(),
							dailyRecord.getOtherActivity().getActivityObserContent()
							
							);
				csvPrinter.printRecord(data);
							
										
			}
			csvPrinter.flush();
		} catch(Exception e){
			System.out.println("Writing CSV error!");
		      e.printStackTrace();
		}
	}
	
}	

