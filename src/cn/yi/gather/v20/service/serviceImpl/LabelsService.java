package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.dao.LabelsRepository;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.ILabelsService;

/**
 * 标签业务接口
 * @author Lee.J.Eric
 * @time 2014年5月29日上午10:36:47
 */
@Service("labelsServiceV20")
public class LabelsService implements ILabelsService {
	
	@Resource(name = "labelsRepositoryV20")
	private LabelsRepository repository;
	
	@Resource(name = "entityManagerFactoryV20")
	private EntityManagerFactory emf;

	@Override
	public Labels findByID(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Labels> getAllLabels() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<Labels> getLabelsByids(String ids) {
		// TODO Auto-generated method stub
		String[] idStrings = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < idStrings.length; i++) {
			idList.add(Long.valueOf(idStrings[i]));
		}
		return repository.findByIdIn(idList);
	}

	@Override
	public Set<Labels> getLabelsByIds(String ids) {
		String[] idStrings = ids.split(",");
		Set<Long> idSet = new HashSet<Long>();
		for (int i = 0; i < idStrings.length ; i++) {
			idSet.add(Long.valueOf(idStrings[i]));
		}
		return repository.findByIdIn(idSet);
	}

	@Override
	public List<Labels> getLabelsByList(List<Long> ids) {
		// TODO Auto-generated method stub
		return repository.findByIdIn(ids);
	}

	@Override
	public List<Labels> getLabelsByType(Integer type) {
		// TODO Auto-generated method stub
		return repository.findByLabeltype(type);
	}

	@Override
	public Labels findByLabeltypeAndZnameAndPid(String labeltype, String zname,Long pid) {
		// TODO Auto-generated method stub
		return repository.findByLabeltypeAndZnameAndPid(labeltype, zname, pid);
	}

	@Override
	public Page<Labels> getLabels(Integer page, Integer size,Integer labeltype) {
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "hot"));
		return repository.findAll(pageRequest);
	}

	@Transactional
	@Override
	public void deleteLabels(String zname) {
		repository.deleteByzname(zname);
	}

	@Override
	public Labels saveOrUpdate(Labels entity) {
		repository.save(entity);
		return findByID(entity.getId());
	}

	@Override
	public Page<Labels> labelList(final String labeltype,final Long pid, Integer page,
			Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size);
		Specification<Labels> spec = new Specification<Labels>() {
			
			@Override
			public Predicate toPredicate(Root<Labels> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.notEqual(root.<Integer>get("isdel"), 1));
				if(pid!=null){
					if(pid == 1L) {
						if(labeltype!=null){
							if(labeltype.equals("focus")){
								ps.add(cb.equal(root.<String>get("labeltype"), labeltype));
							}else{
								ps.add(cb.equal(root.<String>get("labeltype"), labeltype));
								ps.add(cb.notEqual(root.<Long>get("pid"),0L));
							}
						}else{
						}
					}else {
						ps.add(cb.equal(root.<Long>get("pid"), pid));
						if(labeltype!=null){
							ps.add(cb.equal(root.<String>get("labeltype"), labeltype));
						}
					}
				}else{
					if(labeltype!=null){
						ps.add(cb.equal(root.<String>get("labeltype"), labeltype));
					}
				}
				if(ps.size()==0){
					ps.add(cb.isNotNull(root.get("id")));
				}
				Predicate[] prr=new Predicate[ps.size()];
				ps.toArray(prr);
				query.where(cb.and(prr)).orderBy(cb.desc(root.get("hot")));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public void updateHot() {
		// TODO Auto-generated method stub
//		System.out.println("updateHot---"+new Date());
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String update_sql = "update labels set hot=0";
		Query query = em.createNativeQuery(update_sql,Labels.class);
		query.executeUpdate();
		
		StringBuffer sql = new StringBuffer();
		sql.append("update labels,(")
		.append("select count(*) d,u.labels_id from ")
		.append("(select user_id,labels_id from ref_user_favourite ")
		.append("union all ")
		.append("select  user_id,labels_id from ref_user_focus ")
		.append("union all ")
		.append("select  user_id,labels_id from ref_user_job) u ")
		.append("group by u.labels_id ) c ")
		.append(" set labels.hot=c.d ")
		.append("where labels.id=c.labels_id");
//		EntityManager em = emf.createEntityManager();
//		em.getTransaction().begin();
		Query query1 = em.createNativeQuery(sql.toString(),Labels.class);
		query1.executeUpdate();
		em.getTransaction().commit();
		em.clear();
		em.close();
	}
	
	@Override
	public void deleteLabel(Long id) {
		repository.delete(id);
	}
	
}
