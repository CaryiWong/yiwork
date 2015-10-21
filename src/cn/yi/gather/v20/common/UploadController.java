package cn.yi.gather.v20.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.common.Jr;
import com.common.R;
import com.tools.utils.ImageUtil;
import com.tools.utils.FileUtil;
import com.tools.utils.JSONUtil;
import com.tools.utils.RandomUtil;

/**
 * 上传控制器ssss
 * @author Lee.J.Eric
 * @time 2014年5月29日下午5:39:11
 */
@Controller("uploadControllerV20")
@RequestMapping(value = "v20/upload")
public class UploadController {
	
	private static Logger log = Logger.getLogger(UploadController.class);
		

	/**
	 * 单图片上传
	 * @param response
	 * @param img
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午6:05:47
	 */
	@RequestMapping(value="uploadimg",method = RequestMethod.POST)
	public void uploadImg(HttpServletResponse response,MultipartFile img,String type){
		Jr jr = new Jr();
		jr.setMethod("uploadimg");
		if(img==null){
			jr.setCord(-1);//非法传参
		}else {
			Random random = new Random();
			Integer i = random.nextInt(R.Common.IMG_TMP.length);//随机选取一个图片目录
			String pre = R.Common.IMG_TMP[i]+"_";//图片前缀
			String base = R.Common.IMG_DIR+R.Common.IMG_TMP[i];//图片目录
			File basedir = new File(base);
			if(!basedir.exists())
				basedir.mkdirs();
			String fileName = pre+"img"+RandomUtil.getRandomeStringForLength(20)+System.currentTimeMillis();
			FileUtil fileUtil = new FileUtil();
			File desFile = new File(basedir, fileName);
			try {
				fileUtil.copyFile(img.getInputStream(), desFile);
				jr.setData(fileName);
				jr.setCord(0);
				
				String target0 = fileName+R.Img._1280X600;
				String target1 = fileName+R.Img._640X640;
				String target2 = fileName+R.Img._300X420;
				String target3 = fileName+R.Img._160X160;
				String target4 = fileName+R.Img._320X320;
				ImageUtil imageUtil = new ImageUtil();
				String targetdir = R.Common.IMG_DIR+pre.substring(0, 1);
				imageUtil.compressPic(targetdir, targetdir, fileName, target0, 1280, 600, null, true);
				imageUtil.compressPic(targetdir, targetdir, fileName, target1, 640, 640, null, true);
				imageUtil.compressPic(targetdir, targetdir, fileName, target2, 300, 420, null, true);
				imageUtil.compressPic(targetdir, targetdir, fileName, target3, 160, 160, null, true);
				imageUtil.compressPic(targetdir, targetdir, fileName, target4, 320, 320, null, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jr.setCord(1);
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 多图片上传
	 * @param response
	 * @param imgs
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午6:06:51
	 */
	@RequestMapping(value="uploadimgs",method = RequestMethod.POST)
	public void uploadImgs(HttpServletResponse response,@RequestParam MultipartFile[] imgs,String type){
		Jr jr = new Jr();
		jr.setMethod("uploadimgs");
		if(imgs==null){
			jr.setCord(-1);//非法传参
		}else {
			List<String> result = new ArrayList<String>();
			for (MultipartFile img : imgs) {
				Random random = new Random();
				Integer i = random.nextInt(R.Common.IMG_TMP.length);//随机选取一个图片目录
				String pre = R.Common.IMG_TMP[i]+"_";//图片前缀
				String base = R.Common.IMG_DIR+R.Common.IMG_TMP[i];//图片目录
				File basedir = new File(base);
				if(!basedir.exists())
					basedir.mkdirs();
				String fileName = pre+"img"+RandomUtil.getRandomeStringForLength(20)+System.currentTimeMillis();
				FileUtil fileUtil = new FileUtil();
				File desFile = new File(basedir, fileName);
				try {
					fileUtil.copyFile(img.getInputStream(), desFile);
					
					String target0 = fileName+R.Img._1280X600;
					String target1 = fileName+R.Img._640X640;
					String target2 = fileName+R.Img._300X420;
					String target3 = fileName+R.Img._160X160;
					ImageUtil imageUtil = new ImageUtil();
					String targetdir = R.Common.IMG_DIR+pre.substring(0, 1);
					imageUtil.compressPic(targetdir, targetdir, fileName, target0, 1280, 600, null, true);
					imageUtil.compressPic(targetdir, targetdir, fileName, target1, 640, 640, null, true);
					imageUtil.compressPic(targetdir, targetdir, fileName, target2, 300, 420, null, true);
					imageUtil.compressPic(targetdir, targetdir, fileName, target3, 160, 160, null, true);
					
					result.add(fileName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			jr.setCord(0);
			jr.setData(result);
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
