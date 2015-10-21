package cn.yi.gather.v20.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.Jr;
import com.common.R;
import com.google.zxing.WriterException;
import com.tools.utils.ImageUtil;
import com.tools.utils.JSONUtil;

/**
 * 下载控制器
 * @author Lee.J.Eric
 * @time 2014年5月29日下午5:03:50
 */
@Controller("downloadControllerV20")
@RequestMapping(value="v20/download")
public class DownloadController {
	
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
	public void downloadImg(HttpServletResponse response,String type,HttpServletRequest request,String path){
		Jr jr = new Jr();
		jr.setMethod("img");
		if (path==null||path.isEmpty()) {
			try {
				if(type!=null&&!"web".equalsIgnoreCase(type)){//非web端
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
						if(type!=null&&!"web".equalsIgnoreCase(type)){//非web端
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
					if (type!=null&&!"web".equalsIgnoreCase(type)) {//非web端
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
	public void staticURL(HttpServletResponse response,HttpServletRequest request,String type){
		Jr jr = new Jr();
		jr.setMethod("staticurl");
		Map<String, String> map = R.User.STATICURL;
		jr.setCord(0);
		jr.setData(map);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取二维码(自动加上服务器地址)
	 * @param response
	 * @param request
	 * @param path
	 * @param w
	 * @param h
	 * @author Lee.J.Eric
	 * @time 2014年12月17日 下午4:03:38
	 */
	@RequestMapping(value = "qrcode",method = RequestMethod.GET)
	public void qrcode(HttpServletResponse response,HttpServletRequest request,String path,Integer w,Integer h){
		String contextPath = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
		try {
			path = URLDecoder.decode(path, "utf-8");
			String target = basePath + path;
			response.setCharacterEncoding("utf-8");
			response.setContentType("image/png");
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			if(w==null||h==null){
				w=200;
				h=200;
			}
			ImageUtil.generateQRcode(target, w, h, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 根据文本生成二维码
	 * @param response
	 * @param request
	 * @param path
	 * @param w
	 * @param h
	 * @author Lee.J.Eric
	 * @time 2015年3月18日 上午11:58:09
	 */
	@RequestMapping(value = "generate_qrcode",method = RequestMethod.GET)
	public void generateQRcode(HttpServletResponse response,String path,Integer w,Integer h){
		try {
			String target = URLDecoder.decode(path, "utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("image/png");
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			if(w==null||h==null){
				w=200;
				h=200;
			}
			ImageUtil.generateQRcode(target, w, h, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
