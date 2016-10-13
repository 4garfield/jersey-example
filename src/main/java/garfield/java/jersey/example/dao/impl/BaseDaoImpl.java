package garfield.java.jersey.example.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import garfield.java.jersey.example.common.util.DatabaseUtil;
import garfield.java.jersey.example.dao.IBaseDao;

public abstract class BaseDaoImpl<T, Id extends Serializable> implements IBaseDao<T, Id> {
    
    private Class<T> entity;
    
    @PersistenceContext
    protected EntityManager em;
    
    private static final String WHERE_CLAUSE = "where t.%s=:value and 1=1";
    private static final String COUNT_SQL = "select count(t) from %s t ";
    
    public BaseDaoImpl(Class<T> entity) {
        this.entity = entity;
    }
    
    private void checkIdNull(Long id) {
        if(null == id)
            throw new IllegalArgumentException("the given id must not be null!");
    }
    
    private void checkEntityNull(T t) {
        if(null == t)
            throw new IllegalArgumentException("the given entity must not be null!");
    }
    
    @Override
    public List<T> findAll() {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entity);
        Root<T> entityRoot = query.from(entity);
        query.select(entityRoot);
        return em.createQuery(query).getResultList();
        
//        return em.createQuery(String.format(SELECT_FROM, entity.getSimpleName()), entity).getResultList();
        
    }
    
    @Override
    public T find(Long id) {
        checkIdNull(id);
        return em.find(entity, id);
    }
    
    @Override
    public T save(T t) {
        checkEntityNull(t);
        em.persist(t);
//        em.flush();
        return t;
    }

    @Override
    public T update(T t) {
        checkEntityNull(t);
        return em.merge(t);
    }

    @Override
    public void delete(Long id) {
        checkIdNull(id);
        T t = find(id);
        if(null == t)
            throw new IllegalStateException("cannot find entity: " + entity.getSimpleName() + " with given id: " + id);
        em.remove(t);
    }
    
    @Override
    public void delete(T t) {
        checkEntityNull(t);
        em.remove(em.contains(t) ? t : em.merge(t));
    }
    
    @Override
    public Long countAll() {
        TypedQuery<Long> query = em.createQuery(String.format(COUNT_SQL, entity.getSimpleName()), Long.class);
        return query.getSingleResult();
    }
    
    @Override
    public boolean existEntity(Long id) {
        for(Field field : entity.getDeclaredFields()) {
            if(field.isAnnotationPresent(javax.persistence.Id.class)) {
                final String query = String.format(COUNT_SQL + WHERE_CLAUSE, entity.getSimpleName(), field.getName());
                Long count = em.createQuery(query, Long.class).setParameter("value", id).getSingleResult();
                return DatabaseUtil.checkExists(count);
            }
        }
        throw new IllegalStateException("the target entity: " + entity.getSimpleName() + " has no field annotated by javax.persistence.Id");
    }
    
    private void fillQueryParameters(Map<String, Object> paramMap, Query query) {
        if(null != paramMap && paramMap.size() > 0) {
            for(Entry<String, Object> paramEntry : paramMap.entrySet())
                query.setParameter(paramEntry.getKey(), paramEntry.getValue());
        }
    }
    
    @Override
    public TypedQuery<T> createEntityNamedQuery(String queryName, Map<String, Object> paramMap) {
        TypedQuery<T> query = em.createNamedQuery(queryName, entity);
        fillQueryParameters(paramMap, query);
        return query;
    }
    
    @Override
    public Query createNamedQuery(String queryName, Map<String, Object> paramMap) {
        Query query = em.createNamedQuery(queryName);
        fillQueryParameters(paramMap, query);
        return query;
    }
    
    @Override
    public Query createNativeQuery(String queryString, Map<String, Object> paramMap) {
        Query query = em.createNativeQuery(queryString);
        fillQueryParameters(paramMap, query);
        return query;
    }
    
    @Override
    public void flush() {
        em.flush();
    }
    
    @Override
    public void clear() {
        em.clear();
    }

}
