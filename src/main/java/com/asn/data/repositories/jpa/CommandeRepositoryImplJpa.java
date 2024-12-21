package com.asn.data.repositories.jpa;

import com.asn.core.repository.impl.RepositoryJpaImpl;
import com.asn.data.entities.Commande;
import com.asn.data.repositories.CommandeRepository;

public class CommandeRepositoryImplJpa extends RepositoryJpaImpl<Commande> implements CommandeRepository {

    public CommandeRepositoryImplJpa() {
        super(Commande.class);
    }

    
}
