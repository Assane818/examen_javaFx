package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.Client;

public interface ClientService extends Service<Client> {
    Client getByPhone(String phone);
    Client getBySurname(String surname);
   
}
