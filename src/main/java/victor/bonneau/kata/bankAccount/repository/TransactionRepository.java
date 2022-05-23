package victor.bonneau.kata.bankAccount.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.model.Transaction;

@Repository
public class TransactionRepository {

	private static final String HQL_ALL_FOR_ACCOUNT = "FROM TRANSITION WHERE accunt_id = :accountId";
	
	@PersistenceContext
    private EntityManager entityManager;
	
    public TransactionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public List<Transaction> getAllForAccount(int accontId) throws ObjectNotFoundException{
    	try {
	        Session session = entityManager.unwrap(Session.class);
	        Query<Transaction> query = session.createQuery(HQL_ALL_FOR_ACCOUNT, Transaction.class);
	        query.setParameter("accontId", accontId);
	        return query.getResultList();
    	} catch (NoResultException e) {
            throw new ObjectNotFoundException("Transaction");
        }
    }

    public Transaction saveOrUpdate(Transaction transaction) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(transaction);
        return transaction;
    }
    
}
