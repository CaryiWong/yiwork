package cn.yi.gather.v20.admin.controller;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.editor.comm.CreateHtml;
import cn.yi.gather.v20.editor.entity.Htmlpagecode;
import cn.yi.gather.v20.editor.service.IHtmlpagecodeService;
import cn.yi.gather.v20.entity.*;
import cn.yi.gather.v20.entity.Activity.ActivityTypeValue;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.permission.entity.AdminUser;
import cn.yi.gather.v20.service.*;

import com.common.Jr;
import com.common.R;
import com.tools.utils.*;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("articleControllerV20")
@RequestMapping(value="v20/admin/article")
public class ArticleController {

	private static Logger log = Logger.getLogger(ArticleController.class);
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "articleServiceV20")
	private IArticleService articleService;
	
	
	@Resource(name = "activityServiceV20")
	private IActivityService activityService;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "activityJoinServiceV20")
	private IActivityJoinService activityJoinService;
	
	@Resource(name = "gatheringServiceV20")
	private IGatheringService gatheringService;
	
	@Resource(name = "gCommentServiceV20")
	private IGCommentService gCommentService;
	
	@Resource(name = "galleryServiceV20")
	private IGalleryService galleryService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	@Resource(name = "eventlogServiceV20")
	private IEventlogService eventlogService;
	
	@Resource(name = "courseServiceV20")
	private ICourseService courseService;
	
	@Resource(name = "cCommentServiceV20")
	private ICCommentService cCommentService;
	
	@Resource(name = "noticeMsgServiceV20")
	private INoticeMsgService noticeMsgService;
	
	@Resource(name = "questionServiceV20")
	private IQuestionService questionService;
	
	@Resource(name = "itemServiceV20")
	private IItemService itemService;
	
	@Resource(name = "skuServiceV20")
	private ISkuService skuService;
	
	@Resource(name = "skuInventoryServiceV20")
	private ISkuInventoryService skuInventoryService;
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	
	@Resource(name = "itemInstanceLogServiceV20")
	private IItemInstanceLogService itemInstanceLogService;
	
	@Resource(name ="yigatherItemInventoryLogServiceV20")
	private IYigatherItemInventoryLogService yigatherItemInventoryLogService;
	
	@Resource(name = "alipayServiceV20")
	private IAlipayService alipayService;
	
	@Resource(name="htmlpagecodeServiceV20")
	private IHtmlpagecodeService htmlpagecodeService;//页面代码
	
	/**
	 * 创建文章活动 2.0
	 * @param response
	 * @param activity
	 * @param labels
	 * @param open
	 * @param end
	 * @author kcm
	 * @time 2015年4月9日 下午5:04:02
	 */
	@Transactional
	@RequestMapping(value="createarticle")
	public ModelAndView createActivity(HttpServletRequest request,HttpServletResponse response,Article article,ModelMap modelMap){
		Jr jr = new Jr();
		jr.setMethod("createarticle");
		if(article==null ||response==null){
			modelMap.put("tips", "非法传参");
			return new ModelAndView("admin/article/addArticle");
		}
		//User loginu=(User)request.getSession().getAttribute(R.User.SESSION_USER); //登录用户
		AdminUser loginu=(AdminUser)request.getSession().getAttribute(R.User.SESSION_USER); //登录用户
		article.setIscheck(1);  // //是否审核   0 待审核 ，1 通过审核 ，2 审核失败 默认审核
		article.setIsshelves(0);  //是否上架  /活动上架(0)/下架(1)  默认上架
		article.setUser(loginu); 
		article.setSpaceInfo(loginu.getWorkspaceinfo());
		//event_log表增加记录
		Article art=articleService.saveOrupdateArticle(article);
		modelMap.put("tips", "新建文章成功");
		
		Eventlog eventlog = new Eventlog();
		eventlog.setArticle(art);
	    eventlog.setActtype("article");
		eventlog.setEventtype("create");
		//eventlog.setUser(loginu);
		eventlog.setUpdatetime(new Date());
		eventlog.setCity(article.getSpaceInfo().getSpacecity());
		eventlogService.eventlogSaveOrUpdate(eventlog);
		
		return new ModelAndView("admin/article/addArticle");
	}
	
	@RequestMapping(value="getcreatearticle")
	public ModelAndView getcreateArticle(HttpServletResponse response,Article article,ModelMap modelMap){
		return new ModelAndView("admin/article/addArticle");
	}
	
	/**
	 * 获取文章列表
	 * @param response
	 * @param page
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="articlelist")
	public ModelAndView articleList(HttpServletResponse response,com.common.Page<Article> page,ModelMap modelMap,String keyword){
		page = articleService.articleQueryPage(page.getCurrentPage(),page.getPageSize(),keyword);
		modelMap.put("page", page);
		modelMap.put("keyword", keyword);
		return new ModelAndView("admin/article/articleList");
	}

	/***
	 * 上下架文章
	 * @param response
	 * @param id
	 * @param onsale
	 */
	@RequestMapping(value="setonsale")
	public void setOnsale(HttpServletResponse response,String id,Integer onsale){
		Map<String, String> result = new HashMap<String, String>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> msg = new HashMap<String, String>();
		Article article = articleService.findById(id);
		if(article==null||onsale==null){
			result.put("result", "f");
			msg.put("msg", "操作失败");
		}else {
			article.setIsshelves(onsale);//上下架状态
			article = articleService.saveOrupdateArticle(article);
			if(article==null){
				result.put("result", "f");
				msg.put("msg", "操作失败");
			}else {
				result.put("result", "s");
				msg.put("msg", "操作成功");
				data.put("data", article);
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(result, data, msg, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 获取更新的文章
	 * @param modelMap
	 * @param activityid
	 * @return
	 */
	@RequestMapping(value = "getarticle")
	public ModelAndView getArticle(ModelMap modelMap, String activityid){
		Article article = articleService.findById(activityid);
		modelMap.put("article", article);
		modelMap.put("labels", null);
		return new ModelAndView("admin/article/updatearticle");
	}
	
	/**
	 * 保存更新文章
	 * @param modelMap
	 * @param article
	 * @return
	 */
	@RequestMapping(value = "updatearticle")
	public ModelAndView updateArticle(ModelMap modelMap, Article article){
		Article original = articleService.findById(article.getId());
		original.setTitle(article.getTitle());
		original.setCoverimg(article.getCoverimg());
		original.setLinkurl(article.getLinkurl());
		original = articleService.saveOrupdateArticle(original);
		modelMap.put("tips", "修改成功"); 
		modelMap.put("article", original);
		return new ModelAndView("admin/article/updatearticle");
	}
	
}
