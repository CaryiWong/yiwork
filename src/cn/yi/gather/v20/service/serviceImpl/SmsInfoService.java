package cn.yi.gather.v20.service.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.common.Jr;
import com.common.R;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.yi.gather.v20.dao.ApplyvipRepository;
import cn.yi.gather.v20.dao.EnterpriseRepository;
import cn.yi.gather.v20.dao.SmsInfoRepository;
import cn.yi.gather.v20.dao.TribeRepository;
import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Enterprise;
import cn.yi.gather.v20.entity.SmsInfo;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.service.IApplyvipService;
import cn.yi.gather.v20.service.IEnterpriseService;
import cn.yi.gather.v20.service.ISmsInfoService;
import cn.yi.gather.v20.service.ITribeService;

@Service("smsinfoServiceV20")
public class SmsInfoService implements ISmsInfoService{
	@Resource(name = "smsinfoRepositoryV20")
	private SmsInfoRepository repository;

	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource;
	
	@Override
	public SmsInfo SaveOrUpdate(SmsInfo obj) {
		// TODO Auto-generated method stub
		return repository.save(obj);
	}

	@Override
	public SmsInfo findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	/**
	 * 发送短信，判断是否可以发送
	 * return result  
	 * 0 短信发送成功 
	 * 1 短信发送失败  
	 * 201 今天发送量已超标(配置为准)
	 * 202 你的验证码已发送,如未收到 请多少秒后重试 
	 * 204 IP超过限制
	 * 203 平台发送已满
	 */
	public Integer sendSms(String telnum,HttpServletRequest request) {
		//验证是否这个号码发送过多
		PreparedStatement pstat = null;
		ResultSet result = null;
		int total_count = 0;
		String ipaddress=this.getRemoteIpAddr(request);
		
		total_count=this.getTodayCountSms(telnum, 3);  //查看平台的发送量
		
		if(total_count>R.Sms.DAYLIMITALLCOUNT){
			return 203;
		}
		
		
		total_count=this.getTodayCountSms(ipaddress, 4);  //查看这个Ip的发送量 防恶意用户
		if(total_count>R.Sms.IPLIMITDAYCOUNT){
			return 204;
		}
		
		total_count=this.getTodayCountSms(telnum, 5);  //查看有效期内的短信条数 分钟
		
		if(total_count>=1){
			return 202;
		}
		
		total_count=this.getTodayCountSms(telnum, 1);  //查看今天所有
		
		// 其次进入发送,返回对象，写入数据库
		if(total_count<R.Sms.DAYLIMITSINGLECOUNT){
			try {
				SmsInfo entity = this.SMS(telnum);
				entity.setIpaddress(ipaddress); // 获取IP
				this.SaveOrUpdate(entity);
				return 0;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    //过多直接返回
		}else{
			return 201;
		}
		return total_count;
	}

	@Override
	public Jr send(String telnum,HttpServletRequest request) {
		Jr jr=new Jr();
		jr.setCord(1);
		//验证是否这个号码发送过多
		PreparedStatement pstat = null;
		ResultSet result = null;
		int total_count = 0;
		String ipaddress=this.getRemoteIpAddr(request);
		
		total_count=this.getTodayCountSms(telnum, 3);  //查看平台的发送量
		
		if(total_count>R.Sms.DAYLIMITALLCOUNT){
			jr.setCord(203);
			return jr;
		}
		
		
		total_count=this.getTodayCountSms(ipaddress, 4);  //查看这个Ip的发送量 防恶意用户
		if(total_count>R.Sms.IPLIMITDAYCOUNT){
			jr.setCord(204);
			return jr;
		}
		
		total_count=this.getTodayCountSms(telnum, 5);  //查看有效期内的短信条数 分钟
		
		if(total_count>=1){
			jr.setCord(202);
			return jr;
		}
		
		total_count=this.getTodayCountSms(telnum, 1);  //查看今天所有
		
		// 其次进入发送,返回对象，写入数据库
		if(total_count<R.Sms.DAYLIMITSINGLECOUNT){
			try {
				SmsInfo entity = this.SMS(telnum);
				entity.setIpaddress(ipaddress); // 获取IP
				this.SaveOrUpdate(entity);
				jr.setCord(0);
				jr.setData(entity.getValidacode());
				return jr;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    //过多直接返回
		}else{
			jr.setCord(201);
			return jr;
		}
		return jr;
	}

	public static void main(String[] args) {
        SmsInfoService s = new SmsInfoService();
       // System.out.println(s.SMS("18565157059"));
        Random random = new Random();
        String content="验证码为998877(一起开工社区的工作人员绝对不会索取，切勿告知他人)请在页面中输入以完成。【一起开工社区】";
        try {
			String   mytext2   =   java.net.URLEncoder.encode(content,   "utf-8");
			System.out.println(mytext2);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        
     
	}
	
 
	/**
	 * 调用服务器发送短信
	 * @return  0 成功  1 连接失败  1013 账号密码不正确或账号状态异常
	 * @throws UnsupportedEncodingException 
	 */
	public SmsInfo SMS(String telnum) throws UnsupportedEncodingException {
		Integer code=1;
		String msgid="null";
		SmsInfo entity = new SmsInfo();
		//String postUrl = "http://cf.lmobile.cn/submitdata/Service.asmx/g_Submit?sname=dlgzyqwl&spwd=Gd04YQzg&scorpid=&sprdid=1012888&sdst=18565157059&smsg=%e9%aa%8c%e8%af%81%e7%a0%81%e4%b8%ba998877(%e4%b8%80%e8%b5%b7%e5%bc%80%e5%b7%a5%e7%a4%be%e5%8c%ba%e7%9a%84%e5%b7%a5%e4%bd%9c%e4%ba%ba%e5%91%98%e7%bb%9d%e5%af%b9%e4%b8%8d%e4%bc%9a%e7%b4%a2%e5%8f%96%ef%bc%8c%e5%88%87%e5%8b%bf%e5%91%8a%e7%9f%a5%e4%bb%96%e4%ba%ba)%e8%af%b7%e5%9c%a8%e9%a1%b5%e9%9d%a2%e4%b8%ad%e8%be%93%e5%85%a5%e4%bb%a5%e5%ae%8c%e6%88%90%e3%80%82%e3%80%90%e4%b8%80%e8%b5%b7%e5%bc%80%e5%b7%a5%e7%a4%be%e5%8c%ba%e3%80%91";
		Random random = new Random();
		Integer randomNum=random.nextInt(899999)+100000;  //六位随机数
		String content=R.Sms.SMSHEADCONTENT+randomNum+R.Sms.SMSFOOTCONTENT;
		String lastContent=java.net.URLEncoder.encode(content,"utf-8");
		String postUrl =R.Sms.SERVICEURL+"/g_Submit?sname="+R.Sms.SNAME+"&spwd="+R.Sms.SPWD+"&scorpid=&sprdid="+R.Sms.SCOIRPID+"&sdst="+telnum+"&smsg="+lastContent;
		
		
		try {
			// 发送POST请求
			URL url = new URL(postUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			// 获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("connect failed!");
				code = 101;
				entity.setSendstatus(code);
				return entity;
			}
			// 获取响应内容体
			String line, result = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
			 
			//把字符串转换成XML再读取,方便
			List xmlList =this.xmlbuilder(result);
			
			for(Object e : xmlList){
				Element el=(Element)e;
				if(el.getName().equals("State"))
					code=Integer.parseInt(el.getValue());
				if(el.getName().equals("MsgID"))
					msgid=el.getValue();
			}
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		
		entity.setMsgid(msgid);
		entity.setReceivenum(telnum); //收件号码
		entity.setValidacode(randomNum.toString());
		entity.setSmscontent(content);
		entity.setSendstatus(code);
		entity.setGetdate(new Date());

		return entity;
	}
	
	

	/**
	 * 根据手机号 获取今天的下发的短信总条数
	 * @param telnum
	 * @param type
	 * 1 获取手机号今天的全部
	 * 2 获取手机号在有效期内待验证的个数
	 * 3 获取今天总台总的发送量(是否超标)
	 * 4 查看IP一天的发送量 
	 * 5 查看符合间隔内短信条数
	 * @return
	 */
	@Override
	public Integer getTodayCountSms(String telnum,Integer type) {
		PreparedStatement pstat = null;
		ResultSet result = null;
		int total_count = 0;

		long current = System.currentTimeMillis();// 当前时间毫秒数
		long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24)
				- TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
		long twelve = zero + 24 * 60 * 60 * 1000 - 1;// 今天23点59分59秒的毫秒数
		long yesterday = System.currentTimeMillis() - 24 * 60 * 60 * 1000;// 昨天的这一时间的毫秒数
//		System.out.println(new Timestamp(current));// 当前时间
//		System.out.println(new Timestamp(yesterday));// 昨天这一时间点
//		System.out.println(new Timestamp(zero));// 今天零点零分零秒
//		System.out.println(new Timestamp(twelve));// 今天23点59分59秒

		String start_date = (new Timestamp(zero)).toString();
		String end_date = (new Timestamp(twelve)).toString();
		Connection conn=null;
		try {
			conn = dataSource.getConnection();
			if(type==1){  //获取今天的全部
			pstat = conn
					.prepareStatement("select count(*) as total_count from smsinfo where receivenum=? and senddate>=? AND senddate<=? ");
			pstat.setString(1, telnum);
			pstat.setString(2, start_date);
			pstat.setString(3, end_date);
			result = pstat.executeQuery();
			}else if(type==2){ //获取在有效期内待验证的个数
				pstat = conn
						.prepareStatement("select count(*) as total_count from smsinfo where validatestatus=0 and receivenum=? and senddate>=? AND senddate<=? and now()-senddate <= ? ");
				pstat.setString(1, telnum);
				pstat.setString(2, start_date);
				pstat.setString(3, end_date);
				pstat.setString(4, (R.Sms.VALIDATIONHOURS*10000)+"");
				result = pstat.executeQuery();
			}else if(type==3){  //平台今日发送量
				pstat = conn
						.prepareStatement("select count(*) as total_count from smsinfo where  senddate>=? AND senddate<=? ");
				pstat.setString(1, start_date);
				pstat.setString(2, end_date);
				result = pstat.executeQuery();
			}else if(type==4){  //查看IP的今日量
				pstat = conn
						.prepareStatement("select count(*) as total_count from smsinfo where ipaddress=? and senddate>=? AND senddate<=? ");
				pstat.setString(1, telnum);
				pstat.setString(2, start_date);
				pstat.setString(3, end_date);
				result = pstat.executeQuery();
			}else if(type==5){ //查看符合规定间隔的条数
				pstat = conn
						.prepareStatement("select count(*) as total_count from smsinfo  where validatestatus=0 and receivenum=? and senddate>=? AND senddate<=? and now()-senddate<=?");
				pstat.setString(1, telnum);
				pstat.setString(2, start_date);
				pstat.setString(3, end_date);
				pstat.setString(4, (R.Sms.VALIDATIONINTERVAL*100)+"");
				result = pstat.executeQuery();
			}
			if (result.next()) {
				total_count = result.getInt("total_count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//关闭连接
		finally{
			try {
				result.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return total_count;
	}

	@Override
	public Integer validateCodeByTel(String telnum, String code) {
		// TODO Auto-generated method stub
		int total_count=0;
		int result_cord=0; //返回结果
		String smsid="";  //smsID
		PreparedStatement pstat = null;
		ResultSet result = null;
		Connection conn=null;
		
		//时间
		long current = System.currentTimeMillis();// 当前时间毫秒数
		long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24)
				- TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
		long twelve = zero + 24 * 60 * 60 * 1000 - 1;// 今天23点59分59秒的毫秒数
		String start_date = (new Timestamp(zero)).toString();
		String end_date = (new Timestamp(twelve)).toString();
		
		
		total_count=this.getTodayCountSms(telnum,1); //看今天是否有下发过
		 
		if(total_count<=0){
			result_cord=-3;
			return result_cord;
		}
		
		total_count=this.getTodayCountSms(telnum,2);  //查看有效期内的
		if(total_count<=0){
			result_cord=-4;
			return result_cord;
		}
		
		//验证码必须是最新的那条发送记录
		try {
			conn = dataSource.getConnection();
			pstat = conn
					.prepareStatement("select id from smsinfo where receivenum=? group by getdate desc  limit 0,1");
			pstat.setString(1, telnum);
			result = pstat.executeQuery();
			while (result.next()) {
				 smsid =result.getString("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				result.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		 
		
		
		if(smsid!=null && smsid!=""){
			SmsInfo entity = this.findById(smsid); //验证码只能用一次
			if(entity.getValidacode().equals(code) && entity.getValidatestatus().equals(0)){
				entity.setValidatetime(new Date());   //验证时间
				entity.setValidatestatus(1);
				this.SaveOrUpdate(entity);
				return 0;
			}else{
				return 1;
			}
		
		}else{
			return 1;
		}
		
		
	}
	
	
	
	/**
	 * 获取真实的客户IP 屏蔽代理
	 * @param request
	 * @return
	 */
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
	
	/**
	 * 把字符串转换成XML  这样提取参数比较方便
	 * @param str
	 * @return
	 */
	public static List  xmlbuilder(String str){
		//创建一个新的字符串
        StringReader read = new StringReader(str);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            System.out.println(root.getName());//输出根元素的名称（测试）
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Namespace ns = root.getNamespace();
            Element et = null;
            for(int i=0;i<jiedian.size();i++){
                et = (Element) jiedian.get(i);//循环依次得到子元素
            }
            return jiedian;
        } catch (JDOMException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
         
        return null;
	}

	 

}
