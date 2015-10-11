package com.greatwall.platform.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

/** 
* @ClassName: FileController 
* @Description: 文件控制 
* @author fudk fudk_k@sina.com 
* @date 2015年7月4日 下午4:56:13 
*  
*/
@Controller
@RequestMapping("/con")
public class FileController {

	Logger logger = Logger.getLogger(FileController.class);
	
	private String imgPath;
	
	@Value("#{propertiesReader['sys.imgPath']}") 
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	@RequestMapping("/upload")
	public@ResponseBody String upload(@RequestParam MultipartFile upfile,String fileType){
		Map<String,String> map = new HashMap<String,String>();

		String fileName = upfile.getOriginalFilename();  
		
		String dateStr =  (new SimpleDateFormat("yyyyMM")).format(new Date());

		String filest =  new Date().getTime()+"";
		String fileend = fileName.substring(fileName.indexOf(".")+1, fileName.length());
		fileName =filest+"."+fileend; 
		String path = File.separator+"sendfile"+File.separator+dateStr+File.separator;
		String filePath = this.imgPath+path;

		File targetFile = new File(filePath, fileName);  
		if(!targetFile.exists()){  
			targetFile.mkdirs();  
		}  
		map.put("filePath", path+fileName);
		map.put("status", "success");
		//保存  
		try {  
			upfile.transferTo(targetFile);  
		} catch (Exception e) {  
			logger.error("文件上传错误",e);
			map.put("status", "fail");
		}  

		Gson gson = new Gson();
		System.out.println(map);
		return gson.toJson(map);
	}
	
//	@RequestMapping("/getImg/{fileName}/{suffix}")
//	public @ResponseBody FileInputStream getImg(@PathVariable String fileName,@PathVariable String suffix) throws Exception {
//		System.out.println(imgPath+fileName+"."+suffix);
//		File sf = new File(imgPath+fileName+"."+suffix);
//		FileInputStream in = new FileInputStream(sf);
//		return in;
//	}
	@RequestMapping("/getImg/{fileName}/{suffix}")
	public @ResponseBody ResponseEntity<byte[]> getImg(@PathVariable String fileName,@PathVariable String suffix)  {
		 HttpHeaders headers = new HttpHeaders();  
		    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		    headers.setContentDispositionFormData("attachment", fileName+"."+suffix);  
		    
		    ResponseEntity<byte[]> imgf = null;
			try {
				imgf = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(imgPath+fileName+"."+suffix)),  
				        headers, HttpStatus.CREATED);
			} catch (IOException e) {
//				e.printStackTrace();
			}
		    return  imgf;
	}
	
}
