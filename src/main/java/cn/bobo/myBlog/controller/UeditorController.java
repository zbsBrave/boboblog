package cn.bobo.myBlog.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.bobo.myBlog.pojo.UeditorUploadResult;

@Controller
@RequestMapping("/ueditor")
public class UeditorController {
	/**
	 * 读取配置的请求
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value="/configJson")  
	@ResponseBody
    public String config(HttpServletRequest request, HttpServletResponse response){  
        response.setContentType("application/json");
        StringBuilder sb = new StringBuilder();
        String line;
        //获取config.json路径
        Resource resource = new ClassPathResource("config.json");
        
        try(BufferedReader bf = new BufferedReader(new FileReader(resource.getFile()));) {
			while((line=bf.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return sb.toString();
    } 
	/**
	 * 用来设置图片上传,upfile是前端传来的file对象,需要返回UeditorUploadResult
	 */
	@RequestMapping("/uploadImage")
	@ResponseBody
	public UeditorUploadResult uploadImage(MultipartFile upfile) {
		//获取上传图片的原名
		String oldName = upfile.getOriginalFilename();
		//避免重复重新取名
		String newName = new Date().getTime() + oldName;
		String path = "E:\\udUpdate\\" + newName;
		try {
			upfile.transferTo(new File(path));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		//设置返回值
		UeditorUploadResult result = new UeditorUploadResult();
		//localhost:8080/boboblog/pic + newName
		result.setUrl("/pic/" + newName);
		result.setTitle(newName);
		result.setOriginal(oldName);
		result.setState("SUCCESS");
		return  result;
	}
}
