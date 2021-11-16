package com.blissstock.nursinghomesupport.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void storeHelperImage(MultipartFile file,String fileName);
	
	void storeElderImage(MultipartFile file,String fileName);
	
	//update
	void storeHelperFile1(MultipartFile file,String fileName);
	void storeHelperFile2(MultipartFile file,String fileName);
	
	void storeElderFile1(MultipartFile file,String fileName);
	void storeElderFile2(MultipartFile file,String fileName);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

}
