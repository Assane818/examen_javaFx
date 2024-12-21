package com.asn.data.repositories;

import java.util.List;

import com.asn.core.repository.Repository;
import com.asn.data.entities.Article;
import com.asn.data.entities.Detail;

public interface ArticleRepository extends Repository<Article> {
    void update(Article article, double quantite);
    Article selectByLibelle(String libelle);
}
