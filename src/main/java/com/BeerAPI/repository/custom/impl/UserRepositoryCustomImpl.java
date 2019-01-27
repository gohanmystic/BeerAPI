package com.BeerAPI.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.BeerAPI.common.Constants;
import com.BeerAPI.common.exception.SystemException;
import com.BeerAPI.dto.prm.user.PrmFetchUser;
import com.BeerAPI.entity.User;
import com.BeerAPI.repository.custom.UserRepositoryCustom;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> fetchUser(PrmFetchUser fetchParam) {

        Session session = null;
        if (entityManager == null
                || (session = entityManager.unwrap(Session.class)) == null) {
            throw new SystemException();
        }

        List<User> result = null;

        if (fetchParam != null) {

            String userId = fetchParam.getUserId();
            String fullName = fetchParam.getFullName();

            try {

                StringBuilder queryString = new StringBuilder(Constants.EMPTY_STRING);
                queryString.append("SELECT a FROM User a WHERE (1 = 1)");

                if (userId != null) {
                    queryString.append(" AND a.userId LIKE :userId");
                }
                if (fullName != null) {
                    queryString.append(" AND a.fullName LIKE :fullName");
                }

                Query query = session.createQuery(queryString.toString());

                if (userId != null) {
                    query.setParameter("userId", "%"+userId+"%");
                }
                if (fullName != null) {
                    query.setParameter("fullName", "%"+fullName+"%");
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
