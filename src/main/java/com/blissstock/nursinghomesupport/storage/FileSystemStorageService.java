package com.blissstock.nursinghomesupport.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.blissstock.nursinghomesupport.storage.StorageProperties;
@Service
public class FileSystemStorageService implements StorageService {

	private final Path elderImagelocation;
	private final Path helperImagelocation;
	
	//update
	
	private final Path elderFile1Location;
	private final Path elderFile2Location;
	

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.elderImagelocation = Paths.get(properties.getElderImagelocation());
		this.helperImagelocation=Paths.get(properties.getHelperImagelocation());
		this.elderFile1Location=Paths.get(properties.getElderFile1Location());
		this.elderFile2Location=Paths.get(properties.getElderFile2Location());
	}

	@Override
	public void storeHelperImage(MultipartFile file,String fileName) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + fileName);
			}
			if (fileName.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ fileName);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.helperImagelocation.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + fileName, e);
		}
	}
	
	@Override
	public void storeElderImage(MultipartFile file,String fileName) {
		
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + fileName);
			}
			if (fileName.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ fileName);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.elderImagelocation.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + fileName, e);
		}
	}
	
	//update
	@Override
	public void storeHelperFile1(MultipartFile file,String fileName) {
		
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + fileName);
			}
			if (fileName.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ fileName);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.elderImagelocation.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + fileName, e);
		}
	}
	
	@Override
	public void storeHelperFile2(MultipartFile file,String fileName) {
		
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + fileName);
			}
			if (fileName.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ fileName);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.elderImagelocation.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + fileName, e);
		}
	}
	
	
	@Override
	public void storeElderFile1(MultipartFile file,String fileName) {
		
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + fileName);
			}
			if (fileName.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ fileName);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.elderFile1Location.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + fileName, e);
		}
	}
	
	@Override
	public void storeElderFile2(MultipartFile file,String fileName) {
		
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + fileName);
			}
			if (fileName.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ fileName);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.elderFile2Location.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + fileName, e);
		}
	}

	
	  @Override public Stream<Path> loadAll() { try { return
	  Files.walk(this.helperImagelocation, 1) .filter(path ->
	  !path.equals(this.helperImagelocation)) .map(this.helperImagelocation::relativize); } catch
	  (IOException e) { throw new StorageException("Failed to read stored files",
	  e); }
	  
	  }
	 

	
	
	  @Override public Path load(String filename) { return
	  helperImagelocation.resolve(filename); }
	 
	  
	  @Override public Resource loadAsResource(String filename) { try { Path file =
	  load(filename); Resource resource = new UrlResource(file.toUri()); if
	  (resource.exists() || resource.isReadable()) { return resource; } else {
	  throw new StorageFileNotFoundException( "Could not read file: " + filename);
	  
	  } } catch (MalformedURLException e) { throw new
	  StorageFileNotFoundException("Could not read file: " + filename, e); } }
	  
	
	  @Override public void deleteAll() {
	  FileSystemUtils.deleteRecursively(helperImagelocation.toFile()); }
	 
	 

	@Override
	public void init() {
		try {
			Files.createDirectories(elderImagelocation);
			Files.createDirectories(helperImagelocation);
			Files.createDirectories(elderFile1Location);
			Files.createDirectories(elderFile2Location);
			
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
