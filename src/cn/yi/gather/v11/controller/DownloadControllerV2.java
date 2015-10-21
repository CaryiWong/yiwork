package cn.yi.gather.v11.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.common.R;
import com.tools.utils.JSONUtil;

/**
 * 下载控制器
 * @author Lee.J.Eric
 * @time 2014年5月29日下午5:03:50
 */
@Controller("downloadControllerV2")
@RequestMapping(value="v2/download")
public class DownloadControllerV2 {

	/**
	 * 下载图片
	 * @param response
	 * @param type
	 * @param request
	 * @param path
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午5:02:06
	 */
	@RequestMapping(value="img")
	public void downloadImg(HttpServletResponse response,Integer type,HttpServletRequest request,String path){
		Jr jr = new Jr();
		jr.setMethod("img");
		if (path==null||path.isEmpty()) {
			try {
				if(type!=null&&type != 1){//非web端
					//OutputStream out = new BufferedOutputStream(response.getOutputStream());
					//out.flush();
					//out.close();
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "");//response已commit,exception
				}else {
					String url = request.getRequestURI();
					url +="?path=z_default800";
					response.sendRedirect(url);
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}else{
			String pre = path.substring(0, 1);//前缀，即目录名
			String basedir = R.Common.IMG_DIR+pre;
			File target = new File(basedir, path);
			String subpath = path.substring(0,path.lastIndexOf("_"));
			if(target.exists()){//图片存在
				try {
					response.setContentType("image");
					OutputStream out = new BufferedOutputStream(response.getOutputStream());
					InputStream is = new BufferedInputStream(new FileInputStream(target));
					byte[] buffer = new byte[is.available()];
					is.read(buffer);
					is.close();
					out.write(buffer);
					out.flush();
					out.close();
				} catch (IOException e) {//读取图片文件异常
					// TODO Auto-generated catch block
					try {
						if(type!=null&&type != 1){//非web端
							//OutputStream out = new BufferedOutputStream(response.getOutputStream());
							//out.flush();
							//out.close();
							response.sendError(HttpServletResponse.SC_BAD_REQUEST, "");//response已commit,exception
						}else {
							String url = request.getRequestURI();
							url +="?path=z_default800";
							if(type!=null)
								url += "&type="+type;
							response.sendRedirect(url);
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}else if (!target.exists()&&subpath.length()>3) {//图片不存在且当前取的是压缩图，则取源图
				try {
					String url = request.getRequestURI();
					url +="?path="+subpath;
					if(type!=null)
						url += "&type="+type;
					response.sendRedirect(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {//图片不存在
				try {
					if (type != null && type != 1) {//非web端
						//OutputStream out = new BufferedOutputStream(response.getOutputStream());
						//out.flush();
						//out.close();
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "");//response已commit,exception
					} else {
						String url = request.getRequestURI();
						url += "?path=z_default800";
						if (type != null)
							url += "&type=" + type;
						response.sendRedirect(url);
					}
					} catch (IOException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
	
	/**
	 * 下发静态页访问地址
	 * @param response
	 * @param request
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午5:02:33
	 */
	@RequestMapping(value="staticurl")
	public void staticURL(HttpServletResponse response,HttpServletRequest request,Integer type){
		Jr jr = new Jr();
		jr.setMethod("staticurl");
		Map<String, String> map = R.User.STATICURL;
		jr.setCord(0);
		jr.setData(map);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
