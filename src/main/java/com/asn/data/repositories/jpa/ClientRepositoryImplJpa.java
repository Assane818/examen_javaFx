package com.asn.data.repositories.jpa;


import com.asn.core.repository.impl.RepositoryJpaImpl;
import com.asn.data.entities.Client;
import com.asn.data.repositories.ClientRepository;

public class ClientRepositoryImplJpa extends RepositoryJpaImpl<Client>  implements ClientRepository{
    
    

    public ClientRepositoryImplJpa() {
        super(Client.class);
    }

    

    @Override
    public Client selectByPhone(String phone) {
        try {
            String sql = String.format("Select u FROM %s u WHERE u.phone = :phone", entityName);
            return this.em.createQuery(sql, clazz)
                        .setParameter("phone", phone)
                        .getSingleResult();
        } catch (Exception e) {
           return null;
        }
    }

    @Override
    public Client selectBySurname(String surname) {
        try {
            String sql = String.format("Select u FROM %s u WHERE u.surname = :surname", entityName);
            return this.em.createQuery(sql, clazz)
                        .setParameter("surname", surname)
                        .getSingleResult();
        } catch (Exception e) {
           return null;
        }
    }

}
