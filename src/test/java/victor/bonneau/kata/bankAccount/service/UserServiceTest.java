package victor.bonneau.kata.bankAccount.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.model.User;
import victor.bonneau.kata.bankAccount.repository.UserRepository;


public class UserServiceTest {

    private User user;
    private UserRepository userRepository;
    private UserService service;
    
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        
        userRepository = mock(UserRepository.class);
        service = new UserService(userRepository);
    }
    
    /*--------------------get all--------------------*/
    @Test
    void getAllShouldRetunUserList() {
        
        when(userRepository.getAll()).thenReturn(Collections.singletonList(user));
        
        List<User> results = service.getAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(user);
    }
    
    /*--------------------create--------------------*/
    @Test
    void createRetunUser() {
        
        when(userRepository.saveOrUpdate(user)).thenReturn(user);
        
        User result = service.create(user);
        assertThat(result).isEqualTo(user);
    }
    
    /*--------------------getById--------------------*/
    @Test
    void getByIdRetunUser() throws ObjectNotFoundException {
        int id = 1;
        
        when(userRepository.getById(id)).thenReturn(user);
        
        User result = service.getById(id);
        assertThat(result).isEqualTo(user);
    }
    
    /*--------------------update--------------------*/
    @Test
    void updateRetunUser() {
        
        when(userRepository.saveOrUpdate(user)).thenReturn(user);
        
        User result = service.update(user);
        assertThat(result).isEqualTo(user);
    }
}
