package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.Spaceaddress;
import cn.yi.gather.v20.service.ISpaceaddressService;

@Controller("spaceaddressControllerV20")
@RequestMapping( value="v20/spaceaddress")
public class SpaceaddressController {

	@Resource(name = "spaceaddressServiceV20")
	private ISpaceaddressService spaceaddressService;
	
	@RequestMapping(value="getallspaceaddress")
	public void getAllSpaceaddress(HttpServletResponse response,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getallspaceaddress");
		try {
			List<Spaceaddress> list = spaceaddressService.spaceaddresslist();
			jr.setData(list);
			jr.setCord(0);
			try {
				JSONUtil jsonUtil = new JSONUtil();
				jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}
	
	@RequestMapping(value="savespaceaddress")
	public void saveSpaceaddress(HttpServletResponse response,Spaceaddress spaceaddress){
		Jr jr = new Jr();
		jr.setMethod("savespaceaddress");
		try {
			spaceaddress=spaceaddressService.saveOrupdate(spaceaddress);
			jr.setData(spaceaddress);
			jr.setCord(0);
			try {
				JSONUtil jsonUtil = new JSONUtil();
				jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}
	
}
