package com.blissstock.nursinghomesupport.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String helperImagelocation = "src/main/resources/images/helper-images";
	private String elderImagelocation = "src/main/resources/images/elder-images";
	
	private String elderFile1Location = "src/main/resources/images/file1";
	private String elderFile2Location = "src/main/resources/images/file2";
	
	public String getHelperImagelocation() {
		return helperImagelocation;
	}

	public String getElderFile1Location() {
		return elderFile1Location;
	}

	public void setElderFile1Location(String elderFile1Location) {
		this.elderFile1Location = elderFile1Location;
	}

	public String getElderFile2Location() {
		return elderFile2Location;
	}

	public void setElderFile2Location(String elderFile2Location) {
		this.elderFile2Location = elderFile2Location;
	}

	public void setHelperImagelocation(String helperImagelocation) {
		this.helperImagelocation = helperImagelocation;
	}

	

	public String getElderImagelocation() {
		return elderImagelocation;
	}

	public void setElderImagelocation(String elderImagelocation) {
		this.elderImagelocation = elderImagelocation;
	}


	

	

}
