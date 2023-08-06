package org.borave.repository;

import org.borave.model.Profile;

public interface ProfileRepositoryCustom {
    public <S extends Profile> S save(S profile);
}
