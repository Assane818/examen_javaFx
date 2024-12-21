package com.asn.data.services.impl;

import java.util.List;

import com.asn.data.entities.Article;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.services.ArticleService;

public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    public int save(Article article) {
        return this.articleRepository.insert(article);
    }

    public List<Article> show() {
        return articleRepository.selectAll(Article.class);
    }

    

    public Article getById(int id) {
        return articleRepository.selectById(id);
    }

    public void update(Article article, double quantite) {
        articleRepository.update(article, quantite);
    }

    
    @Override
    public Article getByLibelle(String libelle) {
        return articleRepository.selectByLibelle(libelle);
    }
    

}
