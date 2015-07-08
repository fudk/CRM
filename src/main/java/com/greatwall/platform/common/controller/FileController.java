package com.greatwall.platform.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

	private String imgPath;
	
	@Value("#{propertiesReader['sys.imgPath']}") 
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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
