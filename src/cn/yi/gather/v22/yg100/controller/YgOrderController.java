package cn.yi.gather.v22.yg100.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v20.service.ISmsInfoService;
import cn.yi.gather.v22.yg100.entity.GoodsInfo;
import cn.yi.gather.v22.yg100.entity.YgItemInstance;
import cn.yi.gather.v22.yg100.entity.OrderInfo;
import cn.yi.gather.v22.yg100.entity.WxUser;
import cn.yi.gather.v22.yg100.service.IGoodsInfoService;
import cn.yi.gather.v22.yg100.service.IWxUserService;
import cn.yi.gather.v22.yg100.service.IYgItemInstanceService;
import cn.yi.gather.v22.yg100.service.IYgOrderService;

import com.common.Jr;
import com.common.R;
import com.tools.utils.JSONUtil;

@Controller("ygOrderController")
@RequestMapping(value="/yg/order")
public class YgOrderController {

	@Resource(name="goodsInfoService")
	private IGoodsInfoService goodsInfoService;
	
	@Resource(name="wxUserService")
	private IWxUserService wxUserService;
	
	@Resource(name="ygOrderService")
	private IYgOrderService ygOrderService;
	
	@Resource(name="ygItemInstanceService")
	private IYgItemInstanceService instanceService;
	
	@Resource(name = "smsinfoServiceV20")
	private ISmsInfoService smsService;
	
	/**
	 *  1 阳光100 微信下单  提交订单 等待后台确定订单
	 * @param response
	 * @param userid  微信用户ID
	 * @param usertype wx
	 * @param type   web
	 * @param buynum  几天
	 * @param buypeople  几人
	 * @param openid   微信openid
	 * @param tel      订单联系电话
	 * @param goodid   商品ID
	 * @param comeintime 入驻时间
	 * @param peoplename 联系人称呼
	 */
	@RequestMapping(value = "buyyginn")
	public void buyYgInn(HttpServletResponse response,String userid,String usertype, String type,Integer buynum,Integer buypeople, String openid,String tel,String goodid,String comeintime,String peoplename){
		Jr jr = new Jr();
		jr.setMethod("buyyginn");
		try {
			if(buynum!=null&&buynum>0&&buypeople!=null&&buypeople>0){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				
				GoodsInfo good=goodsInfoService.findOne(goodid);
				if(good!=null){
					/* 创建订单 */
					OrderInfo order=new OrderInfo();
					order.setBuynum(buynum);
					order.setBuypeople(buypeople);
					order.setUserid(userid);
					order.setUsertype(usertype);
					order.setPeoplename(peoplename);
					order.setCheckintime(formatter.parse(comeintime));
					if("wx".equals(usertype)){
						WxUser wxUser=wxUserService.findById(userid);
						order.setUsername(wxUser.getNickname());
						order.setUserimg(wxUser.getHeadimgurl());
						order.setOpenid(openid);
					}else{
						//查询一起账户用户
					}
					order.setTotalprice(buynum*buypeople*good.getPrice()*(1-0.02));
					order.setOrderprice(buynum*buypeople*good.getPrice());
					order.setTel(tel);
					order.setMemo(usertype+"用户:"+order.getUsername()+"购买"+good.getName()+"("+buypeople+"人"+buynum+"天)");
					order=ygOrderService.saveOrUpdate(order);
					if(order!=null){
						//创建商品实例
						YgItemInstance instance=new YgItemInstance();
						instance.setGoods(good);
						instance.setOrderInfo(order);
						instance.setUserid(userid);
						instance.setMemo(usertype+"用户:"+order.getUsername()+"拍下"+good.getName()+"("+buypeople+"人"+buynum+"天)");
						instance.setItemstatus("nosend");//未发货
						instanceService.saveOrUpdate(instance);
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
	
	/**
	 * 2 后台获取订单列表
	 * @param response
	 * @param type
	 * @param userid   微信用户ID
	 * @param usertype wx
	 * @param status   订单提交(待确定 submit)  订单确定(待付款 sure)  订单成功(待消费 success)  订单取消（订单作废 cancel）
	 * @param page     当前页
	 * @param size     每页大小
	 * @param openid   微信OPENID
	 * @param tel      订单的联系电话
	 */
	@RequestMapping(value = "orderlist")
	public void orderList(HttpServletResponse response,String type,String userid,String usertype,String status,Integer page,Integer size,String openid,String tel) {
		Jr jr = new Jr();
		jr.setMethod("orderlist");
		if(page==null||size==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		} else {
			Page<OrderInfo> list = ygOrderService.findList(userid, usertype, tel, openid, page, size, status);
			List<Map> data=new ArrayList<Map>();
			//List<Order> ods=new ArrayList<Order>();
			if(list!=null&&list.getContent()!=null){
				for (int i = 0; i < list.getContent().size(); i++) {
					Map<String,Object> map=new HashMap<String, Object>();
					
					map.put("orderid", list.getContent().get(i).getId());//订单号
					map.put("price", list.getContent().get(i).getOrderprice());//订单价格
					map.put("ordertatus", list.getContent().get(i).getOrderstatus());//订单状态
					map.put("itemstatus", "");//劵状态
					map.put("couponnumber", "");//卷号
					map.put("tel", list.getContent().get(i).getTel());
					map.put("img", "");//商品图
					map.put("name", "");//商品标题
					map.put("comeintime",list.getContent().get(i).getCheckintime());//入住时间
					map.put("createtime",list.getContent().get(i).getCreatetime());
					map.put("buypeople",list.getContent().get(i).getBuypeople());//几人
					map.put("buynum",list.getContent().get(i).getBuynum());//几天
					map.put("peoplename", list.getContent().get(i).getPeoplename());//联系人称呼
					
					map.put("timestamp", list.getContent().get(i).getTimestamp());
					map.put("nonceStr", list.getContent().get(i).getNonce_str());
					map.put("package", list.getContent().get(i).getFinalpackage());
					map.put("sign", list.getContent().get(i).getFinalsign());
					
					List<YgItemInstance> instances=instanceService.findByOrder(list.getContent().get(i));
					if(instances!=null&&instances.size()>0){
						map.put("couponnumber", instances.get(0).getCouponnumber());
						map.put("itemstatus", instances.get(0).getItemstatus());
						GoodsInfo goodsInfo=goodsInfoService.findOne(instances.get(0).getGoods().getId());
						map.put("img", goodsInfo.getTitleimg());
						map.put("name", goodsInfo.getName());
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
	 * 3 后台电话联系后确认之后 发送付款验证码
	 * @param response
	 * @param type
	 * @param orderid
	 */
	@RequestMapping(value = "sendvcode")
	public void sendVcode(HttpServletResponse response,String type,String orderid,HttpServletRequest request) {
		Jr jr = new Jr();
		jr.setMethod("sendvcode");
		OrderInfo orderInfo=ygOrderService.findById(orderid);
		if(orderInfo!=null){
			if("submit".equals(orderInfo.getOrderstatus())){
				//调用短信通道  发送验证码
				Jr jr0=smsService.send(orderInfo.getTel(), request);
				if(jr0.getCord()==0){
					orderInfo.setOrderstatus("sure");
					orderInfo.setOrdervcode(jr0.getData()+"");
					orderInfo.setUpdatetime(new Date());
					Calendar c = Calendar.getInstance(); 
					c.setTime(new Date());
					c.add(Calendar.HOUR, 1);
					orderInfo.setCanceltime(c.getTime());
					orderInfo=ygOrderService.saveOrUpdate(orderInfo);
					jr.setCord(0);
					jr.setMsg("发送成功");
					jr.setData(orderInfo);
				}else{
					jr.setCord(1);
					switch (jr0.getCord()) {
					case -1:
						jr.setMsg("号码格式错误");
						break;
					case 201:
						jr.setMsg("今天发送次数已满 如有需要请致电客服");
						break;
					case 202:
						jr.setMsg("你的验证码已发送,如未收到请"+R.Sms.VALIDATIONINTERVAL+"分钟后重试 ");
						break;
					case 203:
						jr.setMsg("平台今日发送量已满 请联系工作人员");
						break;
					case 204:
						jr.setMsg("该IP超限 请明日再试");
						break;
					}
				}
			}
		}else{
			jr.setCord(-1);
			jr.setMsg("订单不存在");
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
