package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

 


import cn.yi.gather.v20.dao.ArticleRepository;
import cn.yi.gather.v20.entity.Article;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IArticleService;

@Service("articleServiceV20")
public class ArticleService implements IArticleService{

	@Resource(name="articleRepositoryV20")
	private ArticleRepository repository;
	
	@Override
	public Article saveOrupdateArticle(Article article) {
		return repository.save(article);
	}

	@Override
	public Article findById(String id) {
		return repository.findOne(id);
	}

	@Override
	public com.common.Page<Article> articleQueryPage(Integer page,
			Integer size,final String keyword) {
		// TODO Auto-generated method stub
		com.common.Page<Article> result = new com.common.Page<Article>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Article> spec = new Specification<Article>() {
			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> conditions = new ArrayList<Predicate>();
				// TODO Auto-generated method stub
				if(StringUtils.isNotBlank(keyword)){
					Predicate[] ors = new Predicate[2];
					ors[0] = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
					ors[1] = cb.like(cb.lower(root.<String>get("linkurl")), "%"+keyword.toLowerCase()+"%");
					conditions.add(cb.or(ors));
				}
				Predicate[] predicates = new Predicate[conditions.size()];
				for (int i = 0; i < conditions.size(); i++) {
					predicates[i] = conditions.get(i);
 				}
				query.where(cb.and(predicates));
				return query.getRestriction();
			}
		};
		Page<Article> list = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(list.getNumber());
		result.setPageSize(list.getSize());
		result.setResult(list.getContent());
		result.setTotalCount((int)list.getTotalElements());
		return result;
	}

}
