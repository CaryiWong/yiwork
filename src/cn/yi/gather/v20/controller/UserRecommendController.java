package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Joinapplication;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Order;
import cn.yi.gather.v20.entity.Spaceshow;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.User.UserRoot;
 


import cn.yi.gather.v20.service.IUserService;

import com.common.Jr;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Statement;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("userRecommendControllerV20")
@RequestMapping(value="v20/recommend")
public class UserRecommendController {

	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource; 
	
	@RequestMapping(value="get_user_recommend")
	public void getSpaceshow(HttpServletRequest request, HttpServletResponse response,Integer page,Integer size,String type,String userid,Integer gettype){
		long t1 = System.currentTimeMillis(); // 排序前取得当前时间  
		Jr jr = new Jr();
		jr.setMethod("get_user_recommend");
		
		if(page==null||page==null||page<0||size==null||size<0 ||userid==null){//非法传值
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else{
		Connection con=null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		List userFocusList=new ArrayList();  //用户当前关注列表
		List userRelationList = new ArrayList(); //有关系的所有用户
		Map userRelationAllFoucslist = new HashMap(); //有关系的所有用户的所有关注列表
		Map userRelationTempList= new HashMap(); // temp 缓存MAP
		
		Map resultMap = new HashMap(); //最终的结果集
		try {
			con =  dataSource.getConnection();
			pstat=con.prepareStatement("select * from ref_user_focus where user_id=? ");
			pstat.setString(1,userid);
			rs=pstat.executeQuery();
			while(rs.next()){
				String recommendId=rs.getString("labels_id");
				userFocusList.add(recommendId);
			}
			rs.close();
			con.close();
		
			//2迭代当前关注列表去取所有有关系的用户
			for(int i=0;i<userFocusList.size();i++){
				con =  dataSource.getConnection();
				String labels_id=(String)userFocusList.get(i);
				PreparedStatement ps = con.prepareStatement("select * from ref_user_focus where labels_id=? and user_id!=?");
				ps.setString(1, labels_id);
				ps.setString(2, userid);
				ResultSet rst = ps.executeQuery();
				while(rst.next()){
					String relationUserId=rst.getString("user_id");
					userRelationList.add(relationUserId);
				}
				rst.close();
				con.close();
			}
			
			//3有关联的用户的所有关注数
			for(int i=0;i<userRelationList.size();i++){
				con =  dataSource.getConnection();
				String user_id=(String)userRelationList.get(i);
				//PreparedStatement ps = con.prepareStatement("select * from ref_user_focus where user_id=?");
				PreparedStatement ps = con.prepareStatement("select * from user inner join ref_user_focus on user.id=ref_user_focus.user_id where id=? ");
				ps.setString(1, user_id);
				ResultSet rst = ps.executeQuery();
				
				List relationUserFoucsList = new ArrayList();
				while(rst.next()){
					User userTemp = new User();
					userTemp.setSex(rst.getInt("sex"));
					userTemp.setNickname(rst.getString("nickname"));
					userTemp.setMinimg(rst.getString("minimg"));
					userTemp.setRoot(rst.getInt("root"));
					userTemp.setUserstart(rst.getInt("userstart"));
					userTemp.setId(user_id);
					
					userRelationTempList.put(user_id, userTemp);
					String relationFocusId=rst.getString("labels_id");
					relationUserFoucsList.add(relationFocusId);
				}
				userRelationAllFoucslist.put(user_id, relationUserFoucsList);  //存放，键值对
				rst.close();
				con.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//第四部，算法，算出和当前用户的相似度
		resultMap=calculateRelationSocre(userRelationAllFoucslist, userFocusList,userRelationTempList);
		//第五步，去除收藏
		resultMap = removeFcousUser(resultMap, userid);
		
		if(resultMap.isEmpty()|| gettype==1){  //没有推荐的了,怎么办呢? 随机给  gettype=1 随机推荐  gettype=0，优先取算法算出来的
			List<User> userlistResult=new ArrayList<User>();
			if(size<15){
				size=15;
			}
			
			try {
				con =  dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement("select distinct * from user inner join ref_user_focus on user.id=ref_user_focus.user_id  inner join ref_user_job on user.id=ref_user_job.user_id inner join  labels on labels.id=ref_user_job.labels_id where user.minimg!='' ORDER BY RAND()  limit 1,100 ");
				ResultSet rst = ps.executeQuery();
				while(rst.next()){
					User user = new User();
					user.setSex(rst.getInt("sex"));
					user.setNickname(rst.getString("nickname"));
					user.setMinimg(rst.getString("minimg"));
					user.setRoot(rst.getInt("root"));
					user.setUserstart(rst.getInt("userstart"));
					user.setId(rst.getString("id"));
					user.setCheckactnum(0);
					Labels lables=  new Labels();
					lables.setZname(rst.getString("zname"));
					 
					lables.setId(rst.getLong("labels.id"));
					Set<Labels> set = new HashSet<Labels>(); 
					set.add(lables);
					user.setJob(set);
					userlistResult.add(user);
				}
				rst.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//去除随机里面的重复的
			List<User> ab=  new ArrayList<User>();
			for(User i :userlistResult){
				if(!ab.contains(i)){
					ab.add(i);  
				}
			}
			System.out.println("sb.size()="+ab.size());
			
			List<User> listlast=removeFcousUser(ab, userid);
			System.out.println("去除收藏的之后的个数"+listlast.size());
			
			
			//随机的时候返回分页
			int per = ab.size()/size+(ab.size()%size==0?0:1) ;
			if(page>=per)
				page=per;
			 
			List<User> us=  new ArrayList<User>();
			for(int i=(page*size);i<((page+1)*size);i++){
				if(i>=ab.size())
					break;
				User user=ab.get(i);
				us.add(user);
			}
			
			System.out.println(userlistResult.size());
			
			jr.setCord(0);
			jr.setMsg("获取成功");
			jr.setPagenum(page+1);
			Set<String> set=new HashSet<String>();
			set.add("id");
			set.add("nickname");//妮称
			set.add("minimg");//头像
			set.add("userstart");//   用户状态 0 求勾搭 1 暂时不勾搭
			set.add("ifspace");
			set.add("sex");//性别 0 男
			set.add("job.zname");//职业
			set.add("root");//权限 2 是会员 3 非会员
			set.add("checkactnum");  //装的是相似度
			
			jr.setData2("随机推荐");
			jr.setPagecount(resultMap.size());
			jr.setPagesum(size);
			jr.setData(BeanUtil.getFieldValueMapForList(us, set));
			jr.setTotal(per);
			
		}else{
		//排序，相似度从大到小排序，并且返回前五个
		TreeMap treeMap =  new TreeMap();
		treeMap.putAll(resultMap);
		ArrayList<Entry<String,User>> list = new ArrayList<Entry<String,User>>(resultMap.entrySet());   
		Collections.sort(list, new Comparator<Object>(){   
		          public int compare(Object e1, Object e2){   
		        int v1 =(Integer)((User)((Entry)e1).getValue()).getCheckactnum();
		        int v2 = (Integer)((User)((Entry)e2).getValue()).getCheckactnum(); 
		        //System.out.println(v2+"="+v1);
		        return v2-v1;   
		    }   
		});   
		
		/*for (Entry<String, User> e: list){   
		    User u =  (User)e.getValue();
			System.out.println(u.getNickname()+"相似度=="+u.getCheckactnum()+"==="+u.getId());
		}
		*/
		long t2 = System.currentTimeMillis(); // 排序后取得当前时间  
	/*	System.out.println("当前用户的关注数="+userFocusList.size());
		System.out.println("有关系的所有用户数="+userRelationList.size());
		System.out.println("有关系的所有MAP="+userRelationAllFoucslist.size());
		System.out.println("最终的结果个数="+resultMap.size());*/
		
		//处理分页
		int per = userRelationAllFoucslist.size()/size+(userRelationAllFoucslist.size()%size==0?0:1) ;
		if(page>=per)
			page=per;
		 
		List<User> us=  new ArrayList<User>();
		for(int i=(page*size);i<((page+1)*size);i++){
			if(i>=list.size())
				break;
			Entry<String, User> e=list.get(i);
			User user =e.getValue();
			us.add(user);
		}
		
		
		
		System.out.println("总页数=="+per+",==当前页"+page);
		//返回分页
		jr.setCord(0);
		jr.setMsg("获取成功");
		jr.setPagenum(page+1);
		Set<String> set=new HashSet<String>();
		set.add("id");
		set.add("nickname");//妮称
		set.add("minimg");//头像
		set.add("userstart");//   用户状态 0 求勾搭 1 暂时不勾搭
		set.add("ifspace");
		set.add("sex");//性别 0 男
		set.add("job.zname");//职业
		set.add("root");//权限 2 是会员 3 非会员
		set.add("checkactnum");
		set.add("job.id");//职业
		
		jr.setData2("会员推荐");
		jr.setPagecount(resultMap.size());
		jr.setPagesum(size);
		jr.setData(BeanUtil.getFieldValueMapForList(us, set));
		jr.setTotal(per);
		
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t2 - t1);

		System.out.println("耗时: " + c.get(Calendar.MINUTE) + "分 "
				+ c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)
				+ " 毫秒");  
		
		}

		}
		
		JSONUtil jsonUtil = new JSONUtil();			
		try {
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 去除已经收藏的了会员 
	 * @param userList  所有的关系用户列表
	 * @param userId  根据当前用户
	 * @return
	 */
	public Map removeFcousUser(Map usermap ,String userId){
		ArrayList<Entry<String,User>> result= null;
		List temp = new ArrayList();
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement pt= con.prepareStatement("select distinct who_id from userfocus where me_id=? ");
			pt.setString(1, userId);
			ResultSet rs = pt.executeQuery();
			while(rs.next()){
				temp.add(rs.getString("who_id"));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Iterator it =usermap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,User> j =(Entry<String,User>)it.next();
			for(int i=0;i<temp.size();i++){
				String id =(String)temp.get(i);
				if(j.getKey().equals(id)){
					it.remove();
					usermap.remove(j);
				}
			}
		}
		return usermap;
	}
	
	/**
	 * 去除已经收藏的会员  根据LIst
	 * @param userlist
	 * @param userId
	 * @return
	 */
	public List<User> removeFcousUser(List<User> userlist ,String userId){
		ArrayList<Entry<String,User>> result= null;
		List<User> results = new ArrayList<User>();
		List temp = new ArrayList();
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement pt= con.prepareStatement("select distinct who_id from userfocus where me_id=? ");
			pt.setString(1, userId);
			ResultSet rs = pt.executeQuery();
			while(rs.next()){
				temp.add(rs.getString("who_id"));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(temp.size()+"===");
		
		if(temp.size()<=0){
			return userlist;
		}
		
		for(User user : userlist){
			for(int i=0;i<temp.size();i++){
				String id =(String)temp.get(i);
				if(!(user.getId().equals(id))){
					results.add(user);
				}
			}
		}
		
		//去重
		results= removeRepeat(results);
		
		return results;
	}
	
	/**
	 * 去除list 里面重复的项
	 * @param userRelationList
	 * @return
	 */
	public List removeRepeat(List userRelationList){
		// 去除重复的用户
		for (int i = 0; i < userRelationList.size() - 1; i++) {
			for (int j = userRelationList.size() - 1; j > i; j--) {
				if (userRelationList.get(j).equals(userRelationList.get(i))) {
					userRelationList.remove(j);
				}
			}
		}
		return userRelationList;
	}
	
	/**
	 * 计算亲密度，并且返回MAP
	 * @param map 所有的有关系的用户map
	 * @param list
	 * @param userTemp
	 */
	public Map calculateRelationSocre(Map map,List list,Map userTemp){
		 Map resultMap =  new HashMap(); 
		 Iterator it = map.entrySet().iterator();
		 while(it.hasNext()){
			 java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
			 String user_id = (String)entry.getKey();   //键
			 List relationUserFoucsList =(List) entry.getValue();  //值
			
			 List temp = new ArrayList();
			 temp=copyList(list);//copy一份
			 if(temp.size()>=relationUserFoucsList.size()){
				 List temp2= new ArrayList();
				 temp2=copyList(list); //copy一份
				 
				 temp.retainAll(relationUserFoucsList);
				 temp2.removeAll(relationUserFoucsList); //去重复，才能并集
				 temp2.addAll(relationUserFoucsList);
				 
				 double res = (double)temp.size()/temp2.size()*1000;
				// System.out.println("交集个数="+temp.size()+"并集个数="+temp2.size()+"result="+(int)res);
				 User u =userService.findById(user_id);
				 
				// User user=(User)userTemp.get(user_id);
				 u.setCheckactnum((int)res); //返回用户对象
				 resultMap.put(user_id, u);
			 }else{
				 List relationTemp1 = new ArrayList();
				 List relationTemp2 = new ArrayList();
				 relationTemp1=copyList(relationUserFoucsList);
				 relationTemp2=copyList(relationUserFoucsList);
				 
				 relationTemp1.retainAll(list);  //交集
				 
				 relationTemp2.removeAll(list); //去重复，才能并集
				 relationTemp2.addAll(list);    //并集
				// System.out.println("交集个数="+relationTemp1.size()+"并集个数="+relationTemp2.size());
				 double res = ((double)relationTemp1.size()/relationTemp2.size())*1000;
				 int result = (int)res;
				 User u =userService.findById(user_id);
				// User user=(User)userTemp.get(user_id);
				 u.setCheckactnum((int)res); //返回用户对象
				 
				 resultMap.put(user_id, u);
			 }
		 }
		 return resultMap;
	}
	
	
	
	
	 
	
	/**
	 * copy list
	 * @param list
	 * @return
	 */
	public List copyList(List list){
		List temp = new ArrayList();
		for(int i=0;i<list.size();i++){
			temp.add(list.get(i));
		}
		return temp;
	}
	
	/**
	 * 根据list分页 通用
	 * @return
	 */
	public Jr returnJrByList(List<User> list,int page,int size){
		
		return null;
	}
	
}
