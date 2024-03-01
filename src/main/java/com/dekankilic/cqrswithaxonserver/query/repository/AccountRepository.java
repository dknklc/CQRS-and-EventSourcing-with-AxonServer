package com.dekankilic.cqrswithaxonserver.query.repository;

import com.dekankilic.cqrswithaxonserver.query.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}
