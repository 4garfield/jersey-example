package garfield.java.jersey.example.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

public interface IBaseDao<T, Id extends Serializable> {
    
    public List<T> findAll();
    
    public T find(Long id);
    
    public T save(T t);
    
    public T update(T t);
    
    public void delete(Long id);
    
    public void delete(T t);
    
    public Long countAll();
    
    public boolean existEntity(Long id);
    
    public TypedQuery<T> createEntityNamedQuery(String queryName, Map<String, Object> paramMap);
    
    public Query createNamedQuery(String queryName, Map<String, Object> paramMap);
    
    public Query createNativeQuery(String queryString, Map<String, Object> paramMap);
    
    public void flush();
    
    public void clear();
}
