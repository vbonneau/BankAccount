package victor.bonneau.kata.bankAccount.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.model.User;

@Repository
public class UserRepository {

    private static final String HQL_ALL = "FROM User";
    private static final String HQL_GET_BY_ID = "FROM User WHERE id = :id";
    
    @PersistenceContext
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public List<User> getAll(){
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery(HQL_ALL, User.class);
        return query.getResultList();
    }
    
    public User getById(int id) throws ObjectNotFoundException {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query<User> query = session.createQuery(HQL_GET_BY_ID, User.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new ObjectNotFoundException("User");
        }
    }
    
    public User saveOrUpdate(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(user);
        return user;
    }
    
}