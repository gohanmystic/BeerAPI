package com.BeerAPI.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.BeerAPI.common.Constants;
import com.BeerAPI.common.exception.SystemException;
import com.BeerAPI.dto.prm.category.PrmFetchCategory;
import com.BeerAPI.entity.Category;
import com.BeerAPI.repository.custom.CategoryRepositoryCustom;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> fetchCategory(PrmFetchCategory fetchParam) {

        Session session = null;
        if (entityManager == null
                || (session = entityManager.unwrap(Session.class)) == null) {
            throw new SystemException();
        }

        List<Category> result = null;

        if (fetchParam != null) {

            Integer categoryId = fetchParam.getCategoryId();
            String categoryName = fetchParam.getCategoryName();

            try {

                StringBuilder queryString = new StringBuilder(Constants.EMPTY_STRING);
                queryString.append("SELECT a FROM Category a WHERE (1 = 1)");

                if (categoryId != null) {
                    queryString.append(" AND a.categoryId LIKE :categoryId");
                }
                if (categoryName != null) {
                    queryString.append(" AND a.categoryName LIKE :categoryName");
                }

                Query query = session.createQuery(queryString.toString());

                if (categoryId != null) {
                    query.setParameter("categoryId", "%"+categoryId+"%");
                }
                if (categoryName != null) {
                    query.setParameter("categoryName", "%"+categoryName+"%");
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
