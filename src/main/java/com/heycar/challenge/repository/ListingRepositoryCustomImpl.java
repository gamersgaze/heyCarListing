package com.heycar.challenge.repository;

import com.heycar.challenge.controller.request.FetchListingRequest;
import com.heycar.challenge.entities.ListingEntity;

import com.heycar.challenge.utils.HeyCarUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.heycar.challenge.utils.HeyCarUtil.escapeSql;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ListingRepositoryCustomImpl implements ListingRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ListingEntity> searchListingByDealerId(FetchListingRequest request) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT listing FROM ListingEntity listing ")
                .append(buildWhereClause(request));

        Query selectQuery = em.createQuery(sql.toString());

        //set pagination values
        selectQuery.setFirstResult(request.getStartAt());
        selectQuery.setMaxResults(request.getMaxResults());
        return selectQuery.getResultList();
    }

    private String buildWhereClause(FetchListingRequest request) {
        List<String> clauses = new ArrayList<>();
        /*
         * these are conditional where clause , they will be added only if user has passed query parameter
         * %s is for String.format.  other extra two %% in like clause is for escaping of %
         */

        if (isNotBlank(request.getMake())) {
            clauses.add(String.format(" listing.make LIKE '%%%s%%' ", escapeSql(request.getMake())));
        }
        if (isNotBlank(request.getModel())) {
            clauses.add(String.format(" listing.model LIKE '%%%s%%' ", escapeSql(request.getModel())));
        }
        if (isNotBlank(request.getColor())) {
            clauses.add(String.format(" listing.color LIKE '%%%s%%' ", escapeSql(request.getColor())));
        }
        if (nonNull(request.getYear())) {
            clauses.add(String.format(" listing.year =%d", request.getYear()));
        }

        //join all the clauses with 'AND'
        if (isNotEmpty(clauses)) {
            return new StringBuilder().append(" WHERE  ").append(StringUtils.join(clauses, " AND ")).toString();
        }

        //return empty String
        return "";
    }
}
