package cn.yi.gather.v11.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v11.entity.Classsort;
import cn.yi.gather.v11.service.IClasssortServiceV2;

import com.common.Jr;
import com.tools.utils.JSONUtil;

/**
 * 行业、职业
 * @author Lee.J.Eric
 * @time 2014年6月3日上午11:30:04
 */
@Controller("classSortControllerV2")
@RequestMapping(value="v2/classsort")
public class ClassSortControllerV2 {
	
	@Resource(name = "classsortServiceV2")
	private IClasssortServiceV2 classsortServiceV2;
	
	/**
	 * 获取所有的行业与其职业
	 * @param response
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午4:12:23
	 */
	@RequestMapping(value="getallclasssort")
	public void getAllClasssort(HttpServletResponse response,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getallclasssort");
		try {
			List<Classsort> list = classsortServiceV2.getClasssortsByPid(888888888888L);
			for (int i = 0; i < list.size(); i++) {
				List<Classsort> sub = classsortServiceV2.getClasssortsByPid(list.get(i).getId());
				list.get(i).setSubClasssorts(sub);
			}
			jr.setCord(0);
			jr.setData(list);
			try {
				JSONUtil jsonUtil = new JSONUtil();
				jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 根据父级行业获取子级行业
	 * @param response
	 * @param pid
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午4:18:33
	 */
	@RequestMapping(value="getclasssortlist")
	public void getClasssortList(HttpServletResponse response,Long pid,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getclasssortlist");
		if(pid==null){
			jr.setCord(-1);//非法传值
		}else {
			if(pid==0)
				pid = 888888888888L;//表示查询顶级行业，0入库后类型变为int，类型不兼容
			List<Classsort> list = classsortServiceV2.getClasssortsByPid(pid);
			jr.setCord(0);
			jr.setData(list);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
