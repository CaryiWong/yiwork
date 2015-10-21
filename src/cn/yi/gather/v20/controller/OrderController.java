package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v20.entity.Invitations;
import cn.yi.gather.v20.entity.ItemClass;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceStatus;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceTypeName;
import cn.yi.gather.v20.entity.ItemInstanceLog;
import cn.yi.gather.v20.entity.Order;
import cn.yi.gather.v20.entity.Order.OrderStatus;
import cn.yi.gather.v20.entity.Order.OrderType;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.SkuInventory;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.entity.UserAccount.MoneyType;
import cn.yi.gather.v20.entity.YigatherItemInventoryLog;
import cn.yi.gather.v20.service.IItemInstanceLogService;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IItemService;
import cn.yi.gather.v20.service.IOrderService;
import cn.yi.gather.v20.service.ISkuInventoryService;
import cn.yi.gather.v20.service.ISkuService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IYigatherItemInventoryLogService;
import cn.yi.gather.v20.service.IinvitationsService;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayCore;
import com.alipay.util.AlipaySubmit;
import com.common.Jr;
import com.common.R;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tools.utils.JSONUtil;
import com.weixin.util.WxConfig;

@Controller("orderControllerV20")
@RequestMapping(value = "v20/order")
public class OrderController {
	private static Logger log = Logger.getLogger(OrderController.class);
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource; 
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name="invitationsServiceV20")
	private IinvitationsService invitationsService;
	
	@Resource(name="itemServiceV20")
	private IItemService itemService;
	
	@Resource(name="skuServiceV20")
	private ISkuService skuService;
	
	@Resource(name="skuInventoryServiceV20")
	private ISkuInventoryService skuInventoryService;

	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	
	@Resource(name = "itemInstanceLogServiceV20")
	private IItemInstanceLogService itemInstanceLogService;
	
	@Resource(name ="yigatherItemInventoryLogServiceV20")
	private IYigatherItemInventoryLogService yigatherItemInventoryLogService;
	
	/**
	 * 查看我的订单列表
	 */
	@Deprecated
	@RequestMapping(value="get_order_list")
	public void getOrderList(HttpServletResponse response, String user_id){
		Jr jr = new Jr();
		jr.setMethod("get_order_list");
		try {
			Connection conn = dataSource.getConnection();
			List<Order> order_list = orderService.getOrderList(conn, user_id);
			jr.setCord(0);
			jr.setData(order_list);
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			log.warn(
				String.format(
					"failed to get_order_list, exception: %s, user_id=%s",
					e.toString(), user_id));
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
	 * 查看订单
	 */
	@RequestMapping(value="get_order")
	public void getOrder(HttpServletResponse response, String order_id){
		Jr jr = new Jr();
		jr.setMethod("get_order");
		try {
//			Connection conn = dataSource.getConnection();
			Order order = orderService.findById(order_id);
//			conn.close();
			jr.setCord(0);
			jr.setData(order);
		} catch (Exception e) {
			// TODO: handle exception
			log.warn(
				String.format(
					"failed to get_order, exception: %s, order_id=%s",
					e.toString(), order_id));
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
	 * 创建购物订单
	 */
	@RequestMapping(value="create_order")
	public void createOrder(HttpServletResponse response, String user_id, String cart){
		Jr jr = new Jr();
		jr.setMethod("create_order");
		try {
			Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
			String[] cartArray = cart.split(",");
			for(int i = 0; i < cartArray.length; i++) {
				String itemStr = cartArray[i];
				String[] tmpArray = itemStr.split(":");
				if (tmpArray.length == 2) {
					String sku_id = tmpArray[0];
					Integer amount = Integer.valueOf(tmpArray[1]);
					ht.put(sku_id, amount);
				}
			}
			Connection conn = dataSource.getConnection();
			try {
				conn.setAutoCommit(false);
				Order order = orderService.createOrder(conn, user_id, ht);
				conn.commit();
				log.info(
					String.format(
						"succeed to create order, user_id=%s, cart=%s",
						user_id, cart));
				jr.setCord(0);
				jr.setData(order);
			}
			catch (Exception e) {
				conn.rollback();
				throw e;
			}
			finally {
				conn.close();
			}
		} catch (Exception e) {
			log.warn(
				String.format(
					"failed to create order, exception: %s, user_id=%s, cart=%s",
					e.toString(), user_id, cart));
			jr.setCord(1);
			jr.setMsg(e.getMessage());
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
	 * 创建充值订单
	 */
	@RequestMapping(value="create_deposit_order")
	public void createDepositOrder(HttpServletResponse response, String user_id, Double money){
		Jr jr = new Jr();
		jr.setMethod("create_deposit_order");
		try {
//			Connection conn = dataSource.getConnection();
			try {
//				conn.setAutoCommit(false);
//				Order order = orderService.createDepositOrder(conn, user_id, money);
				Order order = orderService.createDepositOrder(user_id, money);
//				conn.commit();
				log.info(
					String.format(
						"succeed to create deposit order, user_id=%s, money=%f",
						user_id, money));
				jr.setCord(0);
				jr.setData(order);
			}
			catch (Exception e) {
//				conn.rollback();
				throw e;
			}
//			finally {
//				conn.close();
//			}
		} catch (Exception e) {
			// TODO: handle exception
			log.warn(
				String.format(
					"failed to create deposit order, exception: %s, user_id=%s, money=%f",
					e.toString(), user_id, money));
			jr.setCord(1);
			jr.setMsg(e.getMessage());
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
	 * 创建会员续费订单
	 * @param response
	 * @param user_id 用户id
	 * @param num 年限
	 * @author Lee.J.Eric
	 * @time 2015年1月5日 上午10:42:50
	 */
	@RequestMapping(value="create_renewal_order")
	public void createMembershipRenewalOrder(HttpServletResponse response, String user_id,Integer n){
		Jr jr = new Jr();
		jr.setMethod("create_renewal_order");
		try {
			Order order = orderService.createMembershipRenewalOrder(user_id,n);
			log.info(
					String.format(
							"succeed to create membership renewal order, user_id=%s, money=%f",
							user_id, order.getTotalPrice()));
			jr.setCord(0);
			jr.setData(order);
		} catch (Exception e) {
			// TODO: handle exception
			log.warn(
				String.format(
					"failed to create membership renewal order, exception: %s, user_id=%s",
					e.toString(), user_id));
			jr.setCord(1);
			jr.setMsg("续费下单失败");
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
	 * 取消订单
	 */
	@RequestMapping(value="cancel_order")
	public void cancelOrder(HttpServletResponse response, String user_id, String order_id){
		Jr jr = new Jr();
		jr.setMethod("cancel_order");
		try {
			Connection conn = dataSource.getConnection();
			try {
				conn.setAutoCommit(false);
				if (user_id != orderService.getUserIdOfOrder(conn, order_id)) {
					throw new Exception("没有权限取消订单");
				}
				orderService.cancelOrder(conn, order_id, "用户取消订单");
				conn.commit();
				log.info(
					String.format(
						"succeed to cancel order, user_id=%s, order_id=%s",
						user_id, order_id));
				jr.setCord(0);
			}
			catch (Exception e) {
				conn.rollback();
				throw e;
			}
			finally {
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.warn(
				String.format(
					"failed to cancel order, exception: %s, user_id=%s, order_id=%s",
					e.toString(), user_id, order_id));
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
	 * 支付订单(暂不支付混合支付)
	 * 1:RMB,2:虚拟币
	 */
	@RequestMapping(value="pay_for_order")
	public void payForOrder(HttpServletResponse response, String user_id, String order_id, String payment,String type){
		Jr jr = new Jr();
		jr.setMethod("pay_for_order");
		String[] payArray = payment.split(":");
		Integer m_type = Integer.valueOf(payArray[0]);
		if(m_type==MoneyType.YIGATHER.getCode()){//虚拟币支付
			try {
				Hashtable<Integer, Double> ht = new Hashtable<Integer, Double>();
				String[] paymentArray = payment.split(",");
				for(int i = 0; i < paymentArray.length; i++) {
					String itemStr = paymentArray[i];
					String[] tmpArray = itemStr.split(":");
					if (tmpArray.length == 2) {
						Integer money_type = Integer.valueOf(tmpArray[0]);
						Double money = Double.valueOf(tmpArray[1]);
						ht.put(money_type, money);
					}
				}
//				Connection conn = dataSource.getConnection();
				try {
//					conn.setAutoCommit(false);
					orderService.payForOrder(user_id, order_id, ht);
//					conn.commit();
					log.info(
							String.format(
									"succeed to pay for order, user_id=%s, order_id=%s",
									user_id, order_id));
					jr.setCord(0);
				}
				catch (Exception e) {
//					conn.rollback();
					throw e;
				}
//				finally {
//					conn.close();
//				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				log.warn(
						String.format(
								"failed to pay for order, exception: %s, user_id=%s, order_id=%s",
								e.toString(), user_id, order_id));
				jr.setCord(1);
			}
		}else {//RMB支付
			//wap端支付宝支付
			try {
//				Connection conn = dataSource.getConnection();
				try {
//					conn.setAutoCommit(false);
					Order order = orderService.findById(order_id);
//					conn.commit();
					
					if (order == null) {
						jr.setCord(1);
						jr.setMsg("此订单不存在");
					}else if (order.getStatus() != OrderStatus.UNPAID.getCode() &&
						order.getStatus() != OrderStatus.CONFIRMING.getCode()) {
						jr.setCord(1);
						jr.setMsg("此订单不能支付了");
					}else {
						/////待添加订单超时判断
						//////需要根据请求来源端请求不同的网关，返回相应的页面地址
						if("web".equals(type)){//网页请求
							// 把请求参数打包成数组
							Map<String, String> sParaTemp = new HashMap<String, String>();
							sParaTemp.put("service", "create_direct_pay_by_user");
							sParaTemp.put("partner", R.AliPay.PATNER);
							sParaTemp.put("_input_charset", R.AliPay.INPUT_CHARSET);
							sParaTemp.put("payment_type", R.AliPay.PAYMENT_TYPE);
							sParaTemp.put("notify_url", R.AliPay.NOTIFY_WEB_URL);
//						sParaTemp.put("return_url", R.Common.BASEPATH+R.AliPay.CALLBACK_WEB_URL);
							sParaTemp.put("seller_email", R.AliPay.SELLER_EMAIL);
							sParaTemp.put("out_trade_no", order.getId());
							sParaTemp.put("subject", order.getOrderTypeName());
							sParaTemp.put("total_fee", order.getTotalPrice().toString());
//						sParaTemp.put("body", body);
//						sParaTemp.put("show_url", show_url);
							sParaTemp.put("anti_phishing_key", R.AliPay.ANTI_PHISHING_KEY);
							sParaTemp.put("exter_invoke_ip", R.AliPay.EXTER_INVOKE_IP);
							
							// 建立请求
							String sHtmlText = AlipaySubmit.buildRequest(R.AliPay.ALIPAY_GATEWAY_NEW_WEB,sParaTemp, "get", "确认");
//						response.setContentType("text/html;charset=UTF-8");  
//						response.setHeader("Cache-Control","no-cache");
//						PrintWriter out = response.getWriter();
							//System.out.println(sHtmlText);
//						out.print(sHtmlText);
							
							jr.setCord(0);
							jr.setData(sHtmlText);
							jr.setMsg("操作成功");
						}else {//wap请求
							//请求业务参数详细
							String req_dataToken = "<direct_trade_create_req><notify_url>"
									+ R.AliPay.NOTIFY_WAP_URL
									+ "</notify_url>"
									+ "<call_back_url>"+R.Common.BASEPATH+R.AliPay.CALLBACK_WAP_URL+"</call_back_url>"
									+"<seller_account_name>"
									+ R.AliPay.SELLER_EMAIL
									+ "</seller_account_name><out_trade_no>"
									+ order.getId()
									+ "</out_trade_no><subject>"
									+ order.getOrderTypeName()
									+ "</subject><total_fee>"
									+ order.getTotalPrice().toString()
									+ "</total_fee></direct_trade_create_req>";
							//必填
							
//////////////////////////////////////////////////////////////////////////////////
							
							//把请求参数打包成数组
							Map<String, String> sParaTempToken = new HashMap<String, String>();
							sParaTempToken.put("service", R.AliPay.SERVICE_WAP);
							sParaTempToken.put("partner", R.AliPay.PATNER);
							sParaTempToken.put("_input_charset", R.AliPay.INPUT_CHARSET);
							sParaTempToken.put("sec_id", R.AliPay.SIGN_TYPE);
							sParaTempToken.put("format", R.AliPay.FORMAT);
							sParaTempToken.put("v", R.AliPay.V);
							sParaTempToken.put("req_id", R.AliPay.REQ_ID);
							sParaTempToken.put("req_data", req_dataToken);
							
							AlipayCore.paraFilter(sParaTempToken);//空值过滤
							
							String sign = AlipaySubmit.buildRequestMysign(sParaTempToken);//签名
							sParaTempToken.put("sign", sign);
							
							
							//建立请求
							String sHtmlTextToken = AlipaySubmit.buildRequest(R.AliPay.ALIPAY_GATEWAY_NEW_WAP,"", "",sParaTempToken);
							//URLDECODE返回的信息
							sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,AlipayConfig.input_charset);
							//获取token
							String request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);
							
							//业务详细
							String req_data = "<auth_and_execute_req><request_token>" + request_token + "</request_token></auth_and_execute_req>";
							//必填
							
							//把请求参数打包成数组
							Map<String, String> sParaTemp = new HashMap<String, String>();
							sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
							sParaTemp.put("partner", AlipayConfig.partner);
							sParaTemp.put("_input_charset", AlipayConfig.input_charset);
							sParaTemp.put("sec_id", AlipayConfig.sign_type);
							sParaTemp.put("format", R.AliPay.FORMAT);
							sParaTemp.put("v", R.AliPay.V);
							sParaTemp.put("req_data", req_data);
							
							String sign1 = AlipaySubmit.buildRequestMysign(sParaTemp);//签名
							sParaTemp.put("sign", sign1);
							
							//建立请求
							String sHtmlText = AlipaySubmit.buildRequest(R.AliPay.ALIPAY_GATEWAY_NEW_WAP, sParaTemp, "get", "确认");
//						response.setContentType("text/html;charset=UTF-8");  
//						response.setHeader("Cache-Control","no-cache");
//						PrintWriter out = response.getWriter();
							System.out.println(sHtmlText);
//						out.print(sHtmlText);
							jr.setCord(0);
							jr.setData(sHtmlText);
							jr.setMsg("操作成功");
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
//					conn.rollback();
					throw e;
				}
//				finally {
//					conn.close();
//				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.warn(
						String.format(
								"failed to pay for order, exception: %s, user_id=%s, order_id=%s",
								e.toString(), user_id, order_id));
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
	 * 订单列表
	 * @param response
	 * @param type
	 * @param userid 用户id
	 * @param status 订单状态,待付款:0,确认中:1,已取消:2,超时:3,已完成:4
	 * @param page
	 * @param size
	 * @author Lee.J.Eric
	 * @time 2015年1月12日 下午5:21:40
	 * @param openid 微信 openID
	 * @param tel 手机号
	 * @see   Ybao update 2015-07-23
	 */
	@RequestMapping(value = "orderlist")
	public void orderList(HttpServletResponse response,String type,String userid,Integer status,Integer page,Integer size,String openid,String tel){
		Jr jr = new Jr();
		jr.setMethod("orderlist");
		if(type==null||page==null||size==null||(userid==null&&openid==null&&tel==null)){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		} else {
			Page<Order> list = orderService.orderList(userid, status, page, size,openid,tel);
			List<Map> data=new ArrayList<Map>();
			//List<Order> ods=new ArrayList<Order>();
			if(list!=null&&list.getContent()!=null&&openid!=null&&tel!=null){
				for (int i = 0; i < list.getContent().size(); i++) {
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("orderid", list.getContent().get(i).getId());
					map.put("price", list.getContent().get(i).getTotalPrice());
					map.put("couponnumber", "");
					map.put("inntype", list.getContent().get(i).getInntype());
					map.put("img", "");
					map.put("title", "");
					map.put("people",0);
					map.put("date", 0);
					
					map.put("timestamp", list.getContent().get(i).getTimestamp());
					map.put("nonceStr", list.getContent().get(i).getNonce_str());
					map.put("package", list.getContent().get(i).getFinalpackage());
					map.put("sign", list.getContent().get(i).getFinalsign());
					
					List<ItemInstance> inlist=itemInstanceService.findByOrderIdAndStatus(list.getContent().get(i).getId(), null);
					if(inlist!=null){
						if(list.getContent().get(i).getStatus()==OrderStatus.END.getCode()){
							map.put("couponnumber", inlist.get(0).getCouponnumber());
							list.getContent().get(i).setInntype(inlist.get(0).getStatusName());
							map.put("inntype", inlist.get(0).getStatusName());
						}
						map.put("img", inlist.get(0).getSku().getTitleImg());
						map.put("title", inlist.get(0).getSku().getName());
						map.put("people", inlist.get(0).getBuypeople());
						map.put("date", inlist.get(0).getBuynum());
					}
					
					data.add(map);
				}
			}
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(data);
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
			jr.setMsg("获取成功");
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
	 * 统计个人消费总额
	 * @param response
	 * @param userid
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2015年1月15日 上午11:48:30
	 */
	@RequestMapping(value = "count_consume_total")
	public void countConsumeTotal(HttpServletResponse response,String userid,String type){
		Jr jr = new Jr();
		jr.setMethod("count_consume_total");
		if(userid==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(userid);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("此用户不存在");
			}else {
				Double total = orderService.countConsumeTotal(userid);
				jr.setCord(0);
				jr.setMsg("查询成功");
				jr.setData(total);
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
	 * 用户购买咖啡(买一送一)   提交订单  创建订单
	 * @param response
	 * @param userid  用户ID
	 * @param type    
	 * @param cupnum  购买杯数
	 */
	@RequestMapping(value = "buycoffee")
	public void buycoffee(HttpServletResponse response,String userid,String type,Integer cupnum){
		Jr jr = new Jr();
		jr.setMethod("buycoffee");
		try {
		
		if(userid==null||userid.length()<1||cupnum==null||cupnum<1){
			jr.setCord(1);
			jr.setMsg("非法传参");
		}
		User user=userService.findById(userid);
		if(user!=null){
			//查询SKU ID 找出咖啡
			ItemClass item=itemService.findByCode("coffee");
			if(item!=null){
				List<SKU> skulist=skuService.findListByItemClass(item.getId());
				if(skulist!=null&&skulist.size()>0){
					SKU sku0=null;
					SKU sku1=null;
					for (SKU sku : skulist) {
						if("normal".equals(sku.getCoffeetype())){
							sku0=sku;
						}
						if("giveaway".equals(sku.getCoffeetype())){
							sku1=sku;
						}
					}
					//查询SkuInventory  库存
					if(sku0!=null&&sku1!=null){
						SkuInventory inventory0 = skuInventoryService.findById(sku0.getId());
						SkuInventory inventory1 = skuInventoryService.findById(sku1.getId());
						if((!inventory0.getIsUnlimited()&&inventory0.getAmount()<cupnum)||(!inventory1.getIsUnlimited()&&inventory1.getAmount()<cupnum)){//有限&无库存
							//判断咖啡库存(目前咖啡库存是无限的)
							jr.setCord(2);
							jr.setMsg("库存不够");
						}else{
							if(inventory0.getIsUnlimited()&&inventory1.getIsUnlimited()){
								
							}else{
								//若有库存需要减库存(目前走不到)
								inventory0.setAmount(inventory0.getAmount()-cupnum);
								inventory0=skuInventoryService.saveOrUpdate(inventory0);
								inventory1.setAmount(inventory1.getAmount()-cupnum);
								inventory1=skuInventoryService.saveOrUpdate(inventory1);
							}
							//创建Order 订单
							Order order=new Order();
							order.setBusinessType("coffee");
							order.setCreateTime(new Timestamp(System.currentTimeMillis()));
							order.setModifyTime(order.getCreateTime());
							order.setOrderType(OrderType.BUYING.getCode());
							order.setStatus(OrderStatus.UNPAID.getCode());
							order.setUserId(user.getId());
							order.setUserNickname(user.getNickname());
							order.setBusinessTitle("购买咖啡");
							order.setMemo("购买"+cupnum+"杯咖啡");
							if(user.getRoot()==UserRoot.REGISTER.getCode()||user.getRoot()==UserRoot.OVREDUE.getCode()){
								order.setTotalPrice(sku0.getDefaultPrice()*cupnum);//默认价格
							}else{
								order.setTotalPrice(sku0.getMemberPrice()*cupnum);//会员价格
							}
							order=orderService.saveOrUpdate(order);
							if(order!=null){
								//创建咖啡实例
								List<ItemInstance> instances = new ArrayList<ItemInstance>();
								List<ItemInstanceLog> instanceLogs = new ArrayList<ItemInstanceLog>();
								List<YigatherItemInventoryLog> yigatherItemInventoryLogs = new ArrayList<YigatherItemInventoryLog>();
								for (int i = 0; i <cupnum; i++) {
									//商品实例入库 正常咖啡
									ItemInstance instance = new ItemInstance();
									instance.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
									instance.setOrderId(order.getId());
									instance.setSku(sku0);
									if(user.getRoot()==UserRoot.REGISTER.getCode()||user.getRoot()==UserRoot.OVREDUE.getCode()){
										instance.setSalePrice(sku0.getDefaultPrice());
									}else{
										instance.setSalePrice(sku0.getMemberPrice());
									}
									instance.setUserId(user.getId());
									instance.setReceiveuserId(user.getId().trim());
									instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
									instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
									instance.setEffective(0);//自己购买的咖啡无时效性
									instance.setShowname(ItemInstanceTypeName.COFFEE.getName());
									instance.setShowplatform("一起咖啡");
									instance.setItemtype(ItemInstanceTypeName.COFFEE.getCode());
//									itemInstanceService.saveOrUpdate(instance);
									instances.add(instance);
									
									//商品实例记录  正常咖啡
									ItemInstanceLog item_log = new ItemInstanceLog();
									item_log.setItemInstanceId(instance.getId());
									item_log.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
									item_log.setOrderId(order.getId());
									item_log.setSkuId(sku0.getId());
									item_log.setUserId(user.getId());
									item_log.setUserNickname(user.getNickname());
									item_log.setMemo("生成订单"+order.getId()+"，系统自动创建商品实例");
									item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
//									itemInstanceLogService.saveOrUpdate(item_log);
									instanceLogs.add(item_log);
									
									// 记录出货 正常咖啡
									YigatherItemInventoryLog record = new YigatherItemInventoryLog();
									record.setSkuId(sku0.getId());
									record.setAmount(1);
									record.setOrderId(order.getId());
									record.setMemo("生成订单"+order.getId()+", 系统自动出货");
									record.setOpType(YigatherItemInventoryLog.OpType.DELIVERY.getCode());
									record.setDateTime(new Timestamp(System.currentTimeMillis()));
//									yigatherItemInventoryLogService.saveOrUpdate(record);
									yigatherItemInventoryLogs.add(record);
									
									//商品实例入库  赠品咖啡
									ItemInstance instance1 = new ItemInstance();
									instance1.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
									instance1.setOrderId(order.getId());
									instance1.setSku(sku1);
									instance1.setSalePrice(0.0);
									instance1.setUserId(user.getId());
									instance1.setReceiveuserId(user.getId());
									instance1.setCreateTime(new Timestamp(System.currentTimeMillis()));
									instance1.setModifyTime(new Timestamp(System.currentTimeMillis()));
									instance1.setEffective(1);//有时效性，时效初定为一个月
									Calendar c = Calendar.getInstance();
									c.add(Calendar.MONTH, 1);
									instance1.setEndTime(c.getTime());
									instance1.setShowname(ItemInstanceTypeName.SHARECOFFEE.getName());
									instance1.setShowplatform("一起咖啡");
									instance1.setItemtype(ItemInstanceTypeName.SHARECOFFEE.getCode());
//									itemInstanceService.saveOrUpdate(instance1);
									instances.add(instance1);
									
									//商品实例记录  赠品咖啡
									ItemInstanceLog item_log1 = new ItemInstanceLog();
									item_log1.setItemInstanceId(instance1.getId());
									item_log1.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
									item_log1.setOrderId(order.getId());
									item_log1.setSkuId(sku1.getId());
									item_log1.setUserId(user.getId());
									item_log1.setUserNickname(user.getNickname());
									item_log1.setMemo("生成订单"+order.getId()+"，系统自动创建商品实例");
									item_log1.setDateTime(new Timestamp(System.currentTimeMillis()));
//									itemInstanceLogService.saveOrUpdate(item_log1);
									instanceLogs.add(item_log1);
									
									// 记录出货 赠品咖啡
									YigatherItemInventoryLog record1 = new YigatherItemInventoryLog();
									record1.setSkuId(sku1.getId());
									record1.setAmount(1);
									record1.setOrderId(order.getId());
									record1.setMemo("生成订单"+order.getId()+", 系统自动出货");
									record1.setOpType(YigatherItemInventoryLog.OpType.DELIVERY.getCode());
									record1.setDateTime(new Timestamp(System.currentTimeMillis()));
//									yigatherItemInventoryLogService.saveOrUpdate(record1);
									yigatherItemInventoryLogs.add(record1);
								}
								itemInstanceService.saveOrUpdate(instances);
								itemInstanceLogService.saveOrUpdate(instanceLogs);
								yigatherItemInventoryLogService.saveOrUpdate(yigatherItemInventoryLogs);
								jr.setCord(0);
								jr.setData(order);
								jr.setMsg("订单创建成功");
							}else{
								jr.setCord(-1);
								jr.setMsg("订单创建失败");
							}
						}
					}else{
						jr.setCord(23);
						jr.setMsg("找不到咖啡");
					}
				}else{
					jr.setCord(22);
					jr.setMsg("找不到咖啡");
				}
			}else{
				jr.setCord(21);
				jr.setMsg("找不到咖啡");
			}
		}else{
			jr.setCord(2);
			jr.setMsg("用户不存在");
		}
		
		} catch (Exception e) {
			jr.setCord(-1);
			jr.setMsg("服务器异常");
			e.printStackTrace();
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户购买咖啡(买一送一,指定接受者,适用于邀请函模块购买咖啡)   提交订单  创建订单
	 * @param response
	 * @param userid  用户ID
	 * @param type    
	 * @param cupnum  购买杯数
	 * @param invitationsid  邀请函ID
	 */
	@RequestMapping(value = "buycoffeeforyou")
	public void buycoffeeforyou(HttpServletResponse response,String userid,String type,Integer cupnum,String invitationsid){
		Jr jr = new Jr();
		jr.setMethod("buycoffeeforyou");
		try {
		
		if(userid==null||userid.length()<1||cupnum==null||cupnum<1||invitationsid==null||invitationsid.length()<1){
			jr.setCord(1);
			jr.setMsg("非法传参");
		}
		User user=userService.findById(userid);
		//接收者通过邀请函查询 确保指定赠送用户只能是邀请函入口
		Invitations invitations=invitationsService.findInvitations(invitationsid);
		if(user!=null&&invitations!=null){
			String receiveuserId="";
			if(userid.equals(invitations.getUser().getId())){
				receiveuserId=invitations.getInviteuser().getId();
			}else{
				receiveuserId=invitations.getUser().getId();
			}
			//查询SKU ID 找出咖啡
			ItemClass item=itemService.findByCode("coffee");
			if(item!=null){
				List<SKU> skulist=skuService.findListByItemClass(item.getId());
				if(skulist!=null&&skulist.size()>0){
					SKU sku0=null;
					SKU sku1=null;
					for (SKU sku : skulist) {
						if("normal".equals(sku.getCoffeetype())){
							sku0=sku;
						}
						if("giveaway".equals(sku.getCoffeetype())){
							sku1=sku;
						}
					}
					//查询SkuInventory  库存
					if(sku0!=null&&sku1!=null){
						SkuInventory inventory0 = skuInventoryService.findById(sku0.getId());
						SkuInventory inventory1 = skuInventoryService.findById(sku1.getId());
						if((!inventory0.getIsUnlimited()&&inventory0.getAmount()<cupnum)||(!inventory1.getIsUnlimited()&&inventory1.getAmount()<cupnum)){//有限&无库存
							//判断咖啡库存(目前咖啡库存是无限的)
							jr.setCord(2);
							jr.setMsg("库存不够");
						}else{
							if(inventory0.getIsUnlimited()&&inventory1.getIsUnlimited()){
								
							}else{
								//若有库存需要减库存(目前走不到)
								inventory0.setAmount(inventory0.getAmount()-cupnum);
								inventory0=skuInventoryService.saveOrUpdate(inventory0);
								inventory1.setAmount(inventory1.getAmount()-cupnum);
								inventory1=skuInventoryService.saveOrUpdate(inventory1);
							}
							//创建Order 订单
							Order order=new Order();
							order.setPaidMoney(0.0);
							order.setBusinessType("coffee");
							order.setCreateTime(new Timestamp(System.currentTimeMillis()));
							order.setModifyTime(order.getCreateTime());
							order.setOrderType(OrderType.BUYING.getCode());
							order.setStatus(OrderStatus.UNPAID.getCode());
							order.setUserId(user.getId());
							order.setUserNickname(user.getNickname());
							order.setBusinessTitle("购买咖啡");
							order.setMemo("购买"+cupnum+"杯咖啡");
							order.setJsontext(invitationsid);//邀请函ID  当订单支付成功的时候需要修改对应的邀请函里面的coffee="Y" 并给邀请函的接受者发送通知
							
							if(user.getRoot()==UserRoot.REGISTER.getCode()||user.getRoot()==UserRoot.OVREDUE.getCode()){
								order.setTotalPrice(sku0.getDefaultPrice()*cupnum);//默认价格
							}else{
								order.setTotalPrice(sku0.getMemberPrice()*cupnum);//会员价格
							}
							order=orderService.saveOrUpdate(order);
							if(order!=null){
								//创建咖啡实例
								List<ItemInstance> instances = new ArrayList<ItemInstance>();
								List<ItemInstanceLog> instanceLogs = new ArrayList<ItemInstanceLog>();
								List<YigatherItemInventoryLog> yigatherItemInventoryLogs = new ArrayList<YigatherItemInventoryLog>();
								
								for (int i = 0; i <cupnum; i++) {
									//商品实例入库 正常咖啡
									ItemInstance instance = new ItemInstance();
									instance.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
									instance.setOrderId(order.getId());
									instance.setSku(sku0);
									if(user.getRoot()==UserRoot.REGISTER.getCode()||user.getRoot()==UserRoot.OVREDUE.getCode()){
										instance.setSalePrice(sku0.getDefaultPrice());
									}else{
										instance.setSalePrice(sku0.getMemberPrice());
									}
									instance.setUserId(user.getId());
									instance.setReceiveuserId(user.getId());
									instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
									instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
									instance.setEffective(0);//无时效性
									instance.setShowname(ItemInstanceTypeName.COFFEE.getName());
									instance.setShowplatform("一起咖啡");
									instance.setItemtype(ItemInstanceTypeName.COFFEE.getCode());
//									itemInstanceService.saveOrUpdate(instance);
									instances.add(instance);
									
									//商品实例记录  正常咖啡
									ItemInstanceLog item_log = new ItemInstanceLog();
									item_log.setItemInstanceId(instance.getId());
									item_log.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
									item_log.setOrderId(order.getId());
									item_log.setSkuId(sku0.getId());
									item_log.setUserId(user.getId());
									item_log.setUserNickname(user.getNickname());
									item_log.setMemo("生成订单"+order.getId()+"，系统自动创建商品实例");
									item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
//									itemInstanceLogService.saveOrUpdate(item_log);
									instanceLogs.add(item_log);
									
									// 记录出货 正常咖啡
									YigatherItemInventoryLog record = new YigatherItemInventoryLog();
									record.setSkuId(sku0.getId());
									record.setAmount(1);
									record.setOrderId(order.getId());
									record.setMemo("生成订单"+order.getId()+", 系统自动出货");
									record.setOpType(YigatherItemInventoryLog.OpType.DELIVERY.getCode());
									record.setDateTime(new Timestamp(System.currentTimeMillis()));
//									yigatherItemInventoryLogService.saveOrUpdate(record);
									yigatherItemInventoryLogs.add(record);
									
									//商品实例入库  赠品咖啡
									ItemInstance instance1 = new ItemInstance();
									instance1.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
									instance1.setOrderId(order.getId());
									instance1.setSku(sku1);
									instance1.setSalePrice(0.0);
									instance1.setUserId(user.getId());
									instance1.setReceiveuserId(receiveuserId);//赠品咖啡所属者
									instance1.setCreateTime(new Timestamp(System.currentTimeMillis()));
									instance1.setModifyTime(new Timestamp(System.currentTimeMillis()));
									instance1.setEffective(1);//有时效性，时效初定为一个月
									Calendar c = Calendar.getInstance();
									c.add(Calendar.MONTH, 1);
									instance1.setEndTime(c.getTime());
									instance1.setShowname(ItemInstanceTypeName.TIMECOFFEE.getName());
									instance1.setShowplatform("一起咖啡");
									instance1.setItemtype(ItemInstanceTypeName.TIMECOFFEE.getCode());
//									itemInstanceService.saveOrUpdate(instance1);
									instances.add(instance1);
									
									//商品实例记录  赠品咖啡
									ItemInstanceLog item_log1 = new ItemInstanceLog();
									item_log1.setItemInstanceId(instance1.getId());
									item_log1.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
									item_log1.setOrderId(order.getId());
									item_log1.setSkuId(sku1.getId());
									item_log1.setUserId(user.getId());
									item_log1.setUserNickname(user.getNickname());
									item_log1.setMemo("生成订单"+order.getId()+"，系统自动创建商品实例");
									item_log1.setDateTime(new Timestamp(System.currentTimeMillis()));
//									itemInstanceLogService.saveOrUpdate(item_log1);
									instanceLogs.add(item_log1);
									
									
									// 记录出货 赠品咖啡
									YigatherItemInventoryLog record1 = new YigatherItemInventoryLog();
									record1.setSkuId(sku1.getId());
									record1.setAmount(1);
									record1.setOrderId(order.getId());
									record1.setMemo("生成订单"+order.getId()+", 系统自动出货");
									record1.setOpType(YigatherItemInventoryLog.OpType.DELIVERY.getCode());
									record1.setDateTime(new Timestamp(System.currentTimeMillis()));
//									yigatherItemInventoryLogService.saveOrUpdate(record1);
									yigatherItemInventoryLogs.add(record1);
								}
								itemInstanceService.saveOrUpdate(instances);
								itemInstanceLogService.saveOrUpdate(instanceLogs);
								yigatherItemInventoryLogService.saveOrUpdate(yigatherItemInventoryLogs);
								jr.setCord(0);
								jr.setData(order);
								jr.setMsg("订单创建成功");
							}else{
								jr.setCord(-1);
								jr.setMsg("订单创建失败");
							}
						}
					}else{
						jr.setCord(23);
						jr.setMsg("找不到咖啡");
					}
				}else{
					jr.setCord(22);
					jr.setMsg("找不到咖啡");
				}
			}else{
				jr.setCord(21);
				jr.setMsg("找不到咖啡");
			}
		}else{
			jr.setCord(2);
			jr.setMsg("用户/邀请不存在");
		}
		
		} catch (Exception e) {
			jr.setCord(-1);
			jr.setMsg("服务器异常");
			e.printStackTrace();
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 购买阳关100  阿尔勒工作营 
	 * @param response
	 * @param userid  会员购买 会员ID
	 * @param type   IOS WEB 等
	 * @param buynum 购买数量
	 * @param buypeople 购买人数
	 * @param openid  微信登录openID
	 * @param tel     电话
	 * @param itemid  商品ID
	 */
	@RequestMapping(value = "buyyginn")
	public void buyYgInn(HttpServletResponse response,String userid,String type,Integer buynum,Integer buypeople, String openid,String tel,String itemid){
		Jr jr = new Jr();
		jr.setMethod("buyyginn");
		try {
			if(buynum!=null&&buynum>0&&buypeople!=null&&buypeople>0){
				SKU sku=skuService.findById(itemid);
				if(sku!=null){
					/* 创建订单 */
					Order order=new Order();
					order.setCreateTime(new Timestamp(System.currentTimeMillis()));
					order.setModifyTime(order.getCreateTime());
					order.setStatus(OrderStatus.UNPAID.getCode());
					order.setBusinessType("inn");
					order.setBusinessTitle("购买"+sku.getName());
					order.setMemo("购买"+sku.getIntroduction()+" 商品："+buypeople+"人"+buynum+"天");
					order.setOrderType(OrderType.BUYING.getCode());
					order.setTotalPrice(buynum*buypeople*sku.getMemberPrice());
					order.setJsontext("[]");
					if(userid!=null){
						User user=userService.findById(userid);
						if(user!=null){
							//app 下单 会员购买
							order.setUserId(userid);
							order.setUserNickname(user.getNickname());
						}
					}else if(openid!=null||tel!=null){
						//微信下单购买  非会员
						order.setOpenid(openid);
						order.setTel(tel);
					}	
					order=orderService.saveOrUpdate(order);
					if(order!=null){
						//商品实例入库 
						ItemInstance instance = new ItemInstance();
						instance.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
						instance.setOrderId(order.getId());
						instance.setSku(sku);
						instance.setSalePrice(order.getTotalPrice());//一个订单一个实例 一个卷号
						instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
						instance.setModifyTime(instance.getCreateTime());
						instance.setEffective(0);//无时效性
						instance.setBuynum(buynum);
						instance.setBuypeople(buypeople);
						instance.setOpenid(openid);
						instance.setTel(tel);
						instance.setUserId(userid);
						instance.setReceiveuserId(userid);
						instance.setShowname(ItemInstanceTypeName.INN.getName());
						instance.setShowplatform("阳光100");
						instance.setItemtype(ItemInstanceTypeName.INN.getCode());
						itemInstanceService.saveOrUpdate(instance);
						// 记录出货 
						YigatherItemInventoryLog record = new YigatherItemInventoryLog();
						record.setSkuId(sku.getId());
						record.setAmount(buynum);
						record.setOrderId(order.getId());
						record.setMemo("生成订单"+order.getId()+", 系统自动出货");
						record.setOpType(YigatherItemInventoryLog.OpType.DELIVERY.getCode());
						record.setDateTime(new Timestamp(System.currentTimeMillis()));
						yigatherItemInventoryLogService.saveOrUpdate(record);
						jr.setCord(0);
						jr.setData(order);
						jr.setMsg("订单创建成功");
					}else{
						jr.setCord(-1);
						jr.setMsg("服务器异常 创建订单失败");
					}
				}else{
					jr.setCord(-1);
					jr.setMsg("非法传参，商品不存在");
				}
			}else{
				jr.setCord(-1);
				jr.setMsg("非法传参，购买数量 人数异常");
			}
		} catch (Exception e) {
			jr.setCord(-1);
			jr.setMsg("服务器异常");
			e.printStackTrace();
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
