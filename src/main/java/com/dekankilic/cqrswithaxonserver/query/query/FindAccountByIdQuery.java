package com.dekankilic.cqrswithaxonserver.query.query;

import lombok.Data;

@Data
public class FindAccountByIdQuery { // This class is for query request
    private String accountId;

    public FindAccountByIdQuery(String accountId) {
        this.accountId = accountId;
    }
}
