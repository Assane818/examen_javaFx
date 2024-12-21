package com.asn.data.repositories.jpa;

import java.util.List;

import com.asn.core.repository.impl.RepositoryJpaImpl;

import com.asn.data.entities.Detail;
import com.asn.data.repositories.DetailRepository;


public class DetailRepositoryImplJpa extends RepositoryJpaImpl<Detail> implements DetailRepository {
    public DetailRepositoryImplJpa() {
        super(Detail.class);
    }
}
