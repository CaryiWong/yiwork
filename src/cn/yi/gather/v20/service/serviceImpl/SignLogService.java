package cn.yi.gather.v20.service.serviceImpl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.dao.SignLogRepository;
import cn.yi.gather.v20.entity.CurrentMsg;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.SignLog;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.WorkSpaceInfo;
import cn.yi.gather.v20.service.ICurrentMsgService;
import cn.yi.gather.v20.service.ISignLogService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IWorkSpaceInfoService;

/**
 * 签到/签出
 * @author Lee.J.Eric
 *
 */
@Service("signLogServiceV20")
public class SignLogService implements ISignLogService{

	@Resource(name = "signLogRepositoryV20")
	private SignLogRepository repository;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "currentMsgServiceV20")
	private ICurrentMsgService currentMsgService;
	
	@Resource(name = "entityManagerFactoryV20")
	private EntityManagerFactory emf;
	
	@Resource(name="workSpaceInfoService")
	private IWorkSpaceInfoService spaceInfoService;
	
	@Override
	public SignLog signLogSaveOrUpdate(SignLog signLog) {
		// TODO Auto-generated method stub
		return repository.save(signLog);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean userSignLog(String userid, String signtype,String gps) {
		// TODO Auto-generated method stub
			User user = userService.findById(userid);
			WorkSpaceInfo spaceInfo=new WorkSpaceInfo();
			if(gps!=null){
				if(gps.equals("1")){//暂时不传GPS  1 为一起   2 为 柴火  3 为 wedo
					spaceInfo=spaceInfoService.findByCode("yiqi");
				}else if(gps.equals("2")){
					spaceInfo=spaceInfoService.findByCode("ch");
				}else if(gps.equals("3")){
					spaceInfo=spaceInfoService.findByCode("wedo");
				}
			}
			SignLog signLog = new SignLog(user, signtype);
			signLog.setSpaceInfo(spaceInfo);
			repository.save(signLog);
			user.setSpacesettime(new Date());
			if(signtype.equalsIgnoreCase("in")){//签到
				if(user.getIfspace()==0){//未签到，添加now信息
					CurrentMsg msg = new CurrentMsg();
					StringBuffer sb = new StringBuffer();
					sb.append(user.getNickname()).append(" 来到空间了");
					msg.setContents(sb.toString());
					currentMsgService.saveOrUpdate(msg);
				}
				user.setIfspace(1);
			}else if (signtype.equalsIgnoreCase("out")) {//签出
				user.setIfspace(0);
				user.setUserstart(0);
			}
			userService.userSaveOrUpdate(user);
			return true;
	}

	@Override
	public void signNumByMoth(String userid) {
		/*
		 * update user set signnum=(select  count(*) from (
			select * from signlog 
			where signtype='in' and user_id='？' and month(signtime)=4
			group by to_days(signtime) 
			order by signtime
			) a) where id='？'
		 */
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		StringBuffer sql = new StringBuffer();
		Calendar c = Calendar.getInstance();
		Integer m= c.get(Calendar.MONTH)+1;
		sql.append("update user set signnum=( ")
		.append(" select  count(*) from ( ")
		.append(" select * from signlog ")
		.append(" where signtype='in' and user_id='")
		.append(userid.trim())
		.append("'")
		.append(" and month(signtime)=")
		.append(m)
		.append(" group by to_days(signtime) ")
		.append(" order by signtime ")
		.append(" ) a) where id='")
		.append(userid.trim())
		.append("'");
		System.out.println(sql.toString());
		Query query = em.createNativeQuery(sql.toString(),User.class);
		query.executeUpdate();
		em.getTransaction().commit();
		em.clear();
		em.close();
	}

	
	
}
