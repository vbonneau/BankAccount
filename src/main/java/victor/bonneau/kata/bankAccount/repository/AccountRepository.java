package victor.bonneau.kata.bankAccount.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.model.Account;

@Repository
public class AccountRepository {
	
	private static final String HQL_GET_BY_ID = "FROM Account WHERE id = :id";
	
	@PersistenceContext
    private EntityManager entityManager;
	
    public AccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	
	public Account saveOrUpdate(Account account) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(account);
        return account;
    }

	public Account getAccount(int id) throws ObjectNotFoundException {
		try {
            Session session = entityManager.unwrap(Session.class);
            Query<Account> query = session.createQuery(HQL_GET_BY_ID, Account.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new ObjectNotFoundException("Account");
        }
	}


}
