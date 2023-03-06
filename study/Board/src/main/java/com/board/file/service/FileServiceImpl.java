package com.board.file.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.board.exception.AttachFileException;
import com.board.file.dto.FileDto;
import com.board.file.exception.FileException;
import com.board.file.exception.FileExceptionType;

public class FileServiceImpl implements FileService {
	
	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
	private final String uploadPath = Paths.get("D:","SpringBootProject","upload",today).toString();
	
	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	@Override
public List<FileDto> save(MultipartFile[] multipartFile,Long boardIdx) {
	// TODO Auto-generated method stub
List<FileDto> attachList = new ArrayList<>();
		
		File dir = new File(uploadPath);
		if(dir.exists()==false) {
			dir.mkdirs();
		}
		
		for(MultipartFile file:multipartFile){
			if(file.getSize()<1) {
				continue;
			}
			try {
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				final String saveName = getRandomString() + '.' + extension;
				
				File target = new File(uploadPath,saveName);
				file.transferTo(target);
				
				FileDto attach = new FileDto();
				attach.setBoardIdx(boardIdx);
				attach.setOriginalName(file.getOriginalFilename());
				attach.setSaveName(saveName);
				attach.setImageSize(file.getSize());
				System.out.println(attach.getOriginalName()+attach.getSaveName());
				attachList.add(attach);
			} catch (IOException e) {
				throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);

			} 
		}
	return attachList;
}

	@Override
	public void delete(String filePath) {
		// TODO Auto-generated method stub
		
	}
}
