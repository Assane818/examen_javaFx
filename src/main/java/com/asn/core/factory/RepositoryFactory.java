package com.asn.core.factory;

import com.asn.core.repository.Repository;

public interface RepositoryFactory<T> {
    Object createRepositoryInstance(String className);
    Repository<T> getRepository();
}
