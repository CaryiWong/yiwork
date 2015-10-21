package cn.yi.gather.v20.editor.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.editor.comm.CreateHtml;
import cn.yi.gather.v20.editor.entity.CommHtml;
import cn.yi.gather.v20.editor.service.ICommHtmlService;

@Controller("commHtmlController")
public class CommHtmlController {

	@Resource(name="commHtmlService")
	private ICommHtmlService htmlService;
	
	@RequestMapping(value="comm/createhtml")
	public void createhtml(HttpServletRequest request,HttpServletResponse response,CommHtml commHtml){
		Jr jr = new Jr();
		jr.setMethod("createhtml");
		CreateHtml createHtml=new CreateHtml(commHtml.getHtmlcode(),request.getServletContext().getRealPath("/"),"commhtml");
		commHtml.setUrl(createHtml.getUrl_path());
		commHtml=htmlService.saveOrUpdate(commHtml);
		jr.setData(commHtml);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="comm/findone")
	public void findone(HttpServletRequest request,HttpServletResponse response,String id){
		Jr jr = new Jr();
		jr.setMethod("findone");
		jr.setData(htmlService.findById(id));
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="comm/findall")
	public void findall(HttpServletRequest request,HttpServletResponse response){
		Jr jr = new Jr();
		jr.setMethod("findone");
		jr.setData(htmlService.findAll());
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="comm/findbyuser")
	public void findbyuser(HttpServletRequest request,HttpServletResponse response,String uid){
		Jr jr = new Jr();
		jr.setMethod("findone");
		jr.setData(htmlService.findByUserId(uid));
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
