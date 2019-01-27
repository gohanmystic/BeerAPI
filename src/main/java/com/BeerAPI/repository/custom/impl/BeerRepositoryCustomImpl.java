package com.BeerAPI.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.BeerAPI.common.Constants;
import com.BeerAPI.common.exception.SystemException;
import com.BeerAPI.dto.prm.beer.PrmFetchBeer;
import com.BeerAPI.entity.Beer;
import com.BeerAPI.repository.custom.BeerRepositoryCustom;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class BeerRepositoryCustomImpl implements BeerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Beer> fetchBeer(PrmFetchBeer fetchParam) {

        Session session = null;
        if (entityManager == null
                || (session = entityManager.unwrap(Session.class)) == null) {
            throw new SystemException();
        }

        List<Beer> result = null;

        if (fetchParam != null) {

            Integer beerId = fetchParam.getBeerId();
            String beerName = fetchParam.getBeerName();

            try {

                StringBuilder queryString = new StringBuilder(Constants.EMPTY_STRING);
                queryString.append("SELECT a FROM Beer a WHERE (1 = 1)");

                if (beerId != null) {
                    queryString.append(" AND a.beerId LIKE :beerId");
                }
                if (beerName != null) {
                    queryString.append(" AND a.beerName LIKE :beerName");
                }

                Query query = session.createQuery(queryString.toString());

                if (beerId != null) {
                    query.setParameter("beerId", "%"+beerId+"%");
                }
                if (beerName != null) {
                    query.setParameter("beerName", "%"+beerName+"%");
                }

                result = query.getResultList();
            } catch (Exception e) {

                log.error(e.getMessage(), e);
                throw new SystemException("", e);
            } finally {

                session.close();
            }
        }

        return result;
    }
}
