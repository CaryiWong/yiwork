package cn.yi.gather.v20.service.serviceImpl;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.dao.ActivityJoinRepository;
import cn.yi.gather.v20.dao.UserRepository;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.entity.EmailTemplate;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.service.IActivityJoinService;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.IAppversionService;
import cn.yi.gather.v20.service.IEmailTemplateService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.IRecommendUserService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IVipNumLogService;

import com.common.R;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tools.utils.EmailEntity;
import com.tools.utils.EmailUtil;

/**
 * 推荐业务
 * @author kcm
 * @time 
 *
 */
@Service("recommendUserServiceV20")
public class RecommendUserService implements IRecommendUserService{
	
	@Resource(name = "userRepositoryV20")
	private UserRepository repository;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "activityJoinServiceV20")
	private IActivityJoinService activityJoinService;
	
	@Resource(name = "activityServiceV20")
	private IActivityService activityService;
	
	@Resource(name = "activityJoinRepositoryV20")
	private ActivityJoinRepository actjoinrepository;
	
	@Resource(name = "vipNumLogServiceV20")
	private IVipNumLogService vipNumLogService;
	
	@Resource(name = "entityManagerFactoryV20")
	private EntityManagerFactory emf;
	
	@Resource(name = "emailTemplateServiceV20")
	private IEmailTemplateService emailTemplateService;
	
	@Resource(name = "appversionServiceV20")
	private IAppversionService appversionService;

	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource; 
	
	
	@Override
	public Page<User> getRecommendByUser(String login_id) {
		long t1 = System.currentTimeMillis(); // 排序前取得当前时间  
		
		Connection con=null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		List userFocusList=new ArrayList();  //用户当前关注列表
		List userRelationList = new ArrayList(); //有关系的所有用户
		Map userRelationAllFoucslist = new HashMap(); //有关系的所有用户的所有关注列表
		Map resultMap = new HashMap(); //最终的结果集
		try {
			con =  dataSource.getConnection();
			pstat=con.prepareStatement("select * from ref_user_focus where user_id=? ");
			pstat.setString(1,login_id);
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
				ps.setString(2, login_id);
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
				PreparedStatement ps = con.prepareStatement("select * from ref_user_focus where user_id=?");
				ps.setString(1, user_id);
				ResultSet rst = ps.executeQuery();
				List relationUserFoucsList = new ArrayList();
				while(rst.next()){
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
		resultMap=calculateRelationSocre(userRelationAllFoucslist, userFocusList);;
		//排序，从大到小排序，并且返回前五个
		TreeMap treeMap =  new TreeMap();
		treeMap.putAll(resultMap);
		
		ArrayList<Entry<String,Integer>> list = new ArrayList<Entry<String,Integer>>(resultMap.entrySet());   
		  
		Collections.sort(list, new Comparator<Object>(){   
		          public int compare(Object e1, Object e2){   
		        int v1 =(Integer)((Entry)e1).getValue();
		        int v2 = (Integer)((Entry)e2).getValue();   
		        return v2-v1;   
		    }   
		});   
		
		for (Entry<String, Integer> e: list){   
		    System.out.println(e.getKey()+"  "+e.getValue());   
		}
		
		
		long t2 = System.currentTimeMillis(); // 排序后取得当前时间  
		
		System.out.println("当前用户的关注数="+userFocusList.size());
		System.out.println("有关系的所有用户数="+userRelationList.size());
		System.out.println("有关系的所有MAP="+userRelationAllFoucslist.size());
		System.out.println("最终的结果个数="+resultMap.size());
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t2 - t1);

		System.out.println("耗时: " + c.get(Calendar.MINUTE) + "分 "
				+ c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)
				+ " 毫秒");
		
		return null;
	}

	/**
	 * 去除重复的用户
	 * @param userRelationList
	 * @return
	 */
	public List removeRepeat(List userRelationList){
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
	 * @param map
	 * @param list
	 * @return
	 */
	public Map calculateRelationSocre(Map map,List list){
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
				 resultMap.put(user_id, (int)res);
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
				 resultMap.put(user_id, result);
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
	
}
