package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.Invitations;
import cn.yi.gather.v20.entity.ItemClass;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserFocus;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceStatus;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceTypeName;
import cn.yi.gather.v20.entity.UserAccount;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IItemService;
import cn.yi.gather.v20.service.INoticeMsgService;
import cn.yi.gather.v20.service.ISkuService;
import cn.yi.gather.v20.service.IUserAccountService;
import cn.yi.gather.v20.service.IUserFocusService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IinvitationsService;
import cn.yi.gather.v20.service.serviceImpl.JPushService;

import com.common.Jr;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tools.utils.JSONUtil;
import com.tools.utils.RandomUtil;

@Controller("userAccountControllerV20")
@RequestMapping(value = "v20/user_account")
public class UserAccountController {
	private static Logger log = Logger.getLogger(UserAccountController.class);
	
	@Resource(name = "userAccountServiceV20")
	private IUserAccountService userAccountService;
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource; 
	
	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	@Resource(name="itemServiceV20")
	private IItemService itemService;
	
	@Resource(name="skuServiceV20")
	private ISkuService skuService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "jPushServiceV20")
	private JPushService pushService;
	
	@Resource(name="invitationsServiceV20")
	private IinvitationsService invitationsService;
	
	@Resource(name="userFocusServiceV20")
	private IUserFocusService userFocusService;
	
	@Resource(name = "noticeMsgServiceV20")
	private INoticeMsgService noticeMsgService;
	
	/**
	 * 获取用户账号信息
	 */
	@RequestMapping(value="get_balance")
	public void getBalance(HttpServletResponse response, String user_id){
		Jr jr = new Jr();
		jr.setMethod("get_balance");
		try {
			Connection conn = dataSource.getConnection();
			List<UserAccount> account_list = userAccountService.getUserAccount(user_id);
//			List<UserAccount> account_list = userAccountService.getUserAccount(conn, user_id);
			jr.setCord(0);
			jr.setData(account_list);
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			jr.setCord(1);
			log.warn(String.format("failed to get balance, user_id=%s, exception:%s", user_id, e.toString()));
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
	 * 新建账号
	 */
	@RequestMapping(value="create_account")
	public void createAccount(HttpServletResponse response, String user_id){
		Jr jr = new Jr();
		jr.setMethod("create_account");
		try {
			Connection conn = dataSource.getConnection();
			try {
				conn.setAutoCommit(false);
				userAccountService.createAccount(conn, user_id);
				conn.commit();
				jr.setCord(0);
				log.info(String.format("succeed to create account for user %s", user_id));
			}
			catch (Exception e) {
				conn.rollback();
				throw e;
			}
			finally {
				conn.close();
			}
		} catch (Exception e) {
			log.warn(String.format("failed to create account for user %s, exception:%s", user_id, e));
			jr.setCord(1);
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
	 * 获取所有咖啡劵总数
	 * @param response
	 * @param userid
	 */
	@RequestMapping(value="getmycoffeesum")
	public void getMycoffeeSum(HttpServletResponse response, String userid,String type){
		Jr jr = new Jr();
		jr.setMethod("getmycoffeesum");
		if(userid==null||userid.length()<1){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		ItemClass item=itemService.findByCode("coffee");
		SKU sku0=null;
		SKU sku1=null;
		List<SKU> skulist=skuService.findListByItemClass(item.getId());
		for (SKU sku : skulist) {
			if("normal".equals(sku.getCoffeetype())){
				sku0=sku;
			}
			if("giveaway".equals(sku.getCoffeetype())){
				sku1=sku;
			}
		}
		Map<String, Integer> map=new HashMap<String, Integer>();
		//自己购买的
		List<ItemInstance> me=itemInstanceService.findCoffee(userid, ItemInstanceStatus.UNUSED.getCode(),sku0.getId(),"me");
		if(me==null){
			map.put("me", 0);
		}else{
			map.put("me", me.size());
		}
		//收到的限时卷
		List<ItemInstance> receive=itemInstanceService.findCoffee(userid, ItemInstanceStatus.UNUSED.getCode(),sku1.getId(),"receive");
		if(receive==null){
			map.put("receive", 0);
		}else{
			map.put("receive", receive.size());
		}
		//待分享出去的卷
		List<ItemInstance> share=itemInstanceService.findCoffee(userid, ItemInstanceStatus.UNUSED.getCode(),sku1.getId(),"share");
		if(share==null){
			map.put("share", 0);
		}else{
			map.put("share", share.size());
		}
		jr.setData(map);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有消费劵总数
	 * @param response
	 * @param userid
	 * 2.2   2015-08-05
	 */
	@RequestMapping(value="getcouponsum")
	public void getCouponSum(HttpServletResponse response, String user_id,String type){
		Jr jr = new Jr();
		jr.setMethod("getcouponsum");
		if(user_id==null||user_id.length()<1){
			
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		
		List<SKU> skus=new ArrayList<SKU>();
		//拿咖啡的
		ItemClass item=itemService.findByCode("coffee");
		//拿阳光100的
		ItemClass item1=itemService.findByCode("inn");
		
		List<SKU> skulist=skuService.findListByItemClass(item.getId());
		for (SKU sku : skulist) {
			if("normal".equals(sku.getCoffeetype())){
				skus.add(sku);
			}
			if("giveaway".equals(sku.getCoffeetype())){
				skus.add(sku);
			}
		}
		List<SKU> skulist1=skuService.findListByItemClass(item1.getId());
		if(skulist1!=null){
			for (SKU sku : skulist1) {
				skus.add(sku);
			}
		}
		
		List<ItemInstance> allitems=itemInstanceService.findAllCoupon(user_id, ItemInstanceStatus.UNUSED.getCode(),null, skus);
		jr.setData(allitems.size());
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取所有消费劵
	 * @param response
	 * @param userid
	 * 2.2   2015-08-05
	 */
	@RequestMapping(value="getcouponlist")
	public void getCouponList(HttpServletResponse response, String user_id,String type){
		Jr jr = new Jr();
		jr.setMethod("getcouponsum");
		if(user_id==null||user_id.length()<1){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		
		List<SKU> skus=new ArrayList<SKU>();
		//拿咖啡的
		ItemClass item=itemService.findByCode("coffee");
		//拿阳光100的
		ItemClass item1=itemService.findByCode("inn");
		//以后多种卷在优化 得SKU 的方法
		List<SKU> skulist=skuService.findListByItemClass(item.getId());
		for (SKU sku : skulist) {
			if("normal".equals(sku.getCoffeetype())){
				skus.add(sku);
			}
			if("giveaway".equals(sku.getCoffeetype())){
				skus.add(sku);
			}
		}
		List<SKU> skulist1=skuService.findListByItemClass(item1.getId());
		if(skulist1!=null){
			for (SKU sku : skulist1) {
				skus.add(sku);
			}
		}

		List<ItemInstance> allitems=itemInstanceService.findAllCoupon(user_id, ItemInstanceStatus.UNUSED.getCode(), null, skus);
		jr.setData(allitems);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分类获取自己拥有的咖啡卷的列表
	 * @param response
	 * @param userid  用户Id
	 * @param type
	 * @param listtype me/receive/share
	 */
	@RequestMapping(value="getmycoffeelist")
	public void getMycoffeelist(HttpServletResponse response, String userid,String type,String listtype){
		Jr jr = new Jr();
		jr.setMethod("getmycoffeelist");
		if(userid==null||userid.length()<1){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		ItemClass item=itemService.findByCode("coffee");
		SKU sku0=null;
		SKU sku1=null;
		List<SKU> skulist=skuService.findListByItemClass(item.getId());
		for (SKU sku : skulist) {
			if("normal".equals(sku.getCoffeetype())){
				sku0=sku;
			}
			if("giveaway".equals(sku.getCoffeetype())){
				sku1=sku;
			}
		}
		List<ItemInstance> list=new ArrayList<ItemInstance>();
		if("me".equals(listtype)){
			List<ItemInstance> me=itemInstanceService.findCoffee(userid, ItemInstanceStatus.UNUSED.getCode(),sku0.getId(),"me");
			for (ItemInstance i : me) {
				i.setUser(userService.findById(i.getUserId()));
				list.add(i);
			}
		}else if("receive".equals(listtype)){
			List<ItemInstance> receive=itemInstanceService.findCoffee(userid, ItemInstanceStatus.UNUSED.getCode(),sku1.getId(),"receive");
			for (ItemInstance i : receive) {
				i.setUser(userService.findById(i.getUserId()));
				list.add(i);
			}
		} else if("share".equals(listtype)){
			List<ItemInstance> share=itemInstanceService.findCoffee(userid, ItemInstanceStatus.UNUSED.getCode(),sku1.getId(),"share");
			for (ItemInstance i : share) {
				i.setUser(userService.findById(i.getUserId()));
				list.add(i);
			}
		}
		
		jr.setData(list);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 分享咖啡给你
	 * @param response
	 * @param userid  用户ID
	 * @param receiveuserid  接受者ID
	 * @param type
	 * @param iteminstanceid 咖啡卷ID（分享劵列表里面的ID）
	 * @param speaktext 附带说的那句话
	 */
	@RequestMapping(value="sharecoffeeforyou")
	public void sharecoffeeforyou(HttpServletResponse response, String userid,String receiveuserid, String type,String iteminstanceid,String speaktext){
		Jr jr = new Jr();
		jr.setMethod("sharecoffeeforyou");
		if(userid==null||userid.length()<1||receiveuserid==null||receiveuserid.length()<1||iteminstanceid==null||iteminstanceid.length()<1){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		ItemInstance itemInstance=itemInstanceService.findById(iteminstanceid);
		if(itemInstance!=null&&"giveaway".equals(itemInstance.getSku().getCoffeetype())&&itemInstance.getStatus()==ItemInstanceStatus.UNUSED.getCode()){
			if(userid.equals(itemInstance.getUserId())){
				if(receiveuserid.equals(itemInstance.getUserId())){
					jr.setCord(4);
					jr.setMsg("不能给自己");
				}else{
					if(itemInstance.getUserId().equals(itemInstance.getReceiveuserId())){
						User re_user=userService.findById(receiveuserid);
						User pu_user=userService.findById(userid);
						if(re_user!=null)
						itemInstance.setReceiveuserId(receiveuserid);
						Calendar c = Calendar.getInstance();
						c.add(Calendar.MONTH, 1);
						itemInstance.setEndTime(c.getTime());
						itemInstance.setEffective(1);
						itemInstance.setCouponnumber(RandomUtil.getRandomeStringFornum(10));//重新给卷号
						itemInstance.setModifyTime(new Timestamp(System.currentTimeMillis()));
						itemInstance.setShowname(ItemInstanceTypeName.TIMECOFFEE.getName());
						itemInstance.setShowplatform("一起咖啡");
						itemInstance.setItemtype(ItemInstanceTypeName.TIMECOFFEE.getCode());
						itemInstanceService.saveOrUpdate(itemInstance);
						//需要往邀请函表入一条交情
						Invitations invitations =new Invitations();
						invitations.setAddress("");
						invitations.setCoffee("Y");
						invitations.setItype("sj");
						invitations.setThingstexts(speaktext+"");
						invitations.setUser(pu_user);
						invitations.setInviteuser(re_user);
						invitations=invitationsService.saveOrUpdateInvitations(invitations);
						//往交情表添加一条记录
						UserFocus focus=new UserFocus();
						focus.setMe(invitations.getUser());
						focus.setWho(invitations.getInviteuser());
						focus.setRelation(1);
						userFocusService.saveOrUpdate(focus);
						
						//需要通知接受者 receiveuserid
						NoticeMsg msg=new NoticeMsg();
						msg.setActiontype(NoticeActionTypeValue.ANSWER.value);
						msg.setContents(pu_user.getNickname()+"分享了一杯咖啡给你");
						//msg.setContents(pu_user.getNickname()+"分享了一杯咖啡给你,并对你说:"+ speaktext+"");
						msg.setIcon("coffee");
						msg.setMsgtype(NoticeMsgTypeValue.COFFEE.value);
						msg.setParam(receiveuserid);
						Set set=new HashSet<User>();
						set.add(re_user);
						msg.setTags(set);
						noticeMsgService.saveOrUpdate(msg);
						
						JSONObject msg_extras = new JSONObject();
						msg_extras.put("type", NoticeMsgTypeValue.COFFEE.value);//协商 跳个人钱包的限时劵列表
						msg_extras.put("parameter", receiveuserid);
						msg_extras.put("icon", "coffee");
						msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
						pushService.push(pu_user.getNickname()+"分享了一杯咖啡给你",
								"收到咖啡",msg_extras, Platform.android_ios(), new String[]{receiveuserid}, new String[]{"version4","userstart0"});
						
					}else{
						jr.setCord(5);
						jr.setMsg("已分享过了");
					}
				}
			}else{
				jr.setCord(3);
				jr.setMsg("无分享权限");
			}
		}else{
			jr.setCord(2);
			jr.setMsg("无效卷");
		}
		
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
