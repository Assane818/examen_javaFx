package com.asn.core.factory.Impl;

import com.asn.core.factory.ServiceFactory;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Commande;
import com.asn.data.entities.Detail;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.CommandeRepository;
import com.asn.data.repositories.DetailRepository;
import com.asn.data.services.Service;
import com.asn.data.services.impl.ArticleServiceImpl;
import com.asn.data.services.impl.ClientServiceImpl;
import com.asn.data.services.impl.CommandeServiceImpl;
import com.asn.data.services.impl.DetailServiceImpl;


public class ServiceFactoryImpl<T> implements ServiceFactory<T> {
    private Service<T> service;
  
    public ServiceFactoryImpl(Class<T> entity, Repository<T> repository) {
        if (Client.class.isAssignableFrom(entity)) {
            service = (Service<T>) new ClientServiceImpl((ClientRepository)repository);
        }  else if (Article.class.isAssignableFrom(entity)) {
            service = (Service<T>) new ArticleServiceImpl((ArticleRepository)repository);
        } else if (Commande.class.isAssignableFrom(entity)) {
            service = (Service<T>) new CommandeServiceImpl((CommandeRepository)repository);
        } else if (Detail.class.isAssignableFrom(entity)) {
            service = (Service<T>) new DetailServiceImpl((DetailRepository)repository);
        } 
    }

    @Override
    public Service<T> getService() {
        return service;
    }

    
}
