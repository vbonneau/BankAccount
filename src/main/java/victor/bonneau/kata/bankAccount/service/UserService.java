package victor.bonneau.kata.bankAccount.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.model.User;
import victor.bonneau.kata.bankAccount.repository.UserRepository;

	@Service
	public class UserService {
	    
	    private final UserRepository userRepository;
	    
	    public UserService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }

	    @Transactional
	    public List<User> getAll(){
	        return userRepository.getAll();
	    }
	    
	    @Transactional
	    public User create(User user) {
	        return userRepository.saveOrUpdate(user);
	    }
	    
	    @Transactional
	    public User getById(int id) throws ObjectNotFoundException {
	        return userRepository.getById(id);
	    }
	    
	    @Transactional
	    public User update(User user) {
	        return userRepository.saveOrUpdate(user);
	    }
	}