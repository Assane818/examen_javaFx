package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.Article;
import com.asn.data.entities.Detail;

public interface ArticleService extends Service<Article> {
    void update(Article article, double quantite);
    Article getByLibelle(String libelle);

}
