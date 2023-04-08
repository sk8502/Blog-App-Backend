package com.Sk.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Sk.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadimage(String path, MultipartFile file) throws IOException {

		String name = file.getOriginalFilename();

		// rendom name generate file
		String random = UUID.randomUUID().toString();
		String filename1 = random.concat(name.substring(name.lastIndexOf(".")));
		// Fullpath
		String filepath = path + File.separator + filename1;

		// created if not exist
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// file copy
		Files.copy(file.getInputStream(), Paths.get(filepath));
		return name;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
