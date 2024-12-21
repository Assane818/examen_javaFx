package com.asn.core.factory.Impl;

import java.util.Map;

import com.asn.core.factory.RepositoryFactory;
import com.asn.core.repository.Repository;
import com.asn.core.services.YamlService;
import com.asn.core.services.impl.YamlServiceImpl;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Commande;
import com.asn.data.entities.Detail;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.DetailRepository;


public class RepositoryFactoryImpl<T> implements RepositoryFactory<T> {
    private Repository<T> repository;

    public RepositoryFactoryImpl(Class<T> entity) {
        YamlService yamlService = new YamlServiceImpl();
        Map<String, Object> mapYaml = yamlService.loadYaml();
        if (Article.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("articleRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh);
        } else if (Client.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("clientRepository");
            String yh1 = (String)((Map<String, Object>) mapYaml.get("repository")).get("userRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh);
        } else if (Commande.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("commandeRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh);    
        } else if (Detail.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("detailRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh);
        }
    }



    public Object createRepositoryInstance(String className) {
    try {
        Class<?> clazz = Class.forName(className);
        var constructors = clazz.getDeclaredConstructors();

        // Essayer de trouver un constructeur sans paramètre
        for (var constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return constructor.newInstance();
            }
    }
        // Aucune correspondance trouvée
        throw new RuntimeException("No suitable constructor found for: " + className);

    } catch (ClassNotFoundException e) {
        throw new RuntimeException("Class not found: " + e.getMessage(), e);
    } catch (Exception e) {
        throw new RuntimeException("Failed to create repository instance for: " + className, e);
    }
}




    @Override
    public Repository<T> getRepository() {
        return this.repository;
    }

}

