package com.asn.data.services.impl;

import java.util.List;

import com.asn.data.entities.Commande;
import com.asn.data.repositories.CommandeRepository;
import com.asn.data.services.CommandeService;

public class CommandeServiceImpl implements CommandeService {

    private CommandeRepository commandeRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public int save(Commande object) {
        return commandeRepository.insert(object);
    }

    @Override
    public List<Commande> show() {
        return commandeRepository.selectAll(Commande.class);
    }

    @Override
    public Commande getById(int id) {
        return commandeRepository.selectById(id);
    }

    
}
