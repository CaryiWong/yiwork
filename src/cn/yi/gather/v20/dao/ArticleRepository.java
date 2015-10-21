package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Article;

@Component("articleRepositoryV20")
public interface ArticleRepository extends JpaRepository<Article, String>,JpaSpecificationExecutor<Article>{

}
