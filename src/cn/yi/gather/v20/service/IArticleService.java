package cn.yi.gather.v20.service;

import com.common.Page;

import cn.yi.gather.v20.entity.Article;

public interface IArticleService {

	public Article saveOrupdateArticle(Article article);
	
	public Article findById(String id);
	
	public Page<Article> articleQueryPage(Integer page,Integer size,String keyword);
	
}
