package victor.bonneau.kata.bankAccount.mappeur;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import victor.bonneau.kata.bankAccount.dto.UserDto;
import victor.bonneau.kata.bankAccount.mappeur.UserMappeur;
import victor.bonneau.kata.bankAccount.model.User;

public class UserMappeurTest {

    private UserMappeur mappeur = new UserMappeur();
    private User user;
    private UserDto userDto;
    
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        
        userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("test");
        userDto.setPassword("test");
    }
    
    /*------------------ dtoToUser ----------------------------------*/
    @Test
    void dtoToUserWithNullShouldReturnNull() {
        
        User userMapper = mappeur.dtoToUser(null);
        assertThat(userMapper).isNull();
    }
    
    @Test
    void dtoToUserWithDtoShouldRetunUserMappedFieldByField() {
        User userMapper = mappeur.dtoToUser(userDto);
        assertThat(userMapper).isEqualTo(user);
    }
    
    /*------------------ dtoToUsers ----------------------------------*/
    @Test
    void dtoToUsersWithNullListShouldReturnEmptyList() {
        
        List<User> usersMapper = mappeur.dtosToUsers(null);
        assertThat(usersMapper).isEmpty();
    }
    
    @Test
    void dtoToUsersWithEmptyListListShouldReturnEmptyList() {
        List<UserDto> userDtos = new ArrayList<UserDto>();
        
        List<User> usersMapper = mappeur.dtosToUsers(userDtos);
        assertThat(usersMapper).isEmpty();
    }
    
    @Test
    void dtoToUsersWithNoEmptyListListShouldReturnNoEmptyListWithSameSize() {
        List<UserDto> userDtos = Collections.singletonList(userDto);
        
        List<User> usersMapper = mappeur.dtosToUsers(userDtos);
        assertThat(usersMapper).hasSize(1);
        User userMapper = usersMapper.get(0);
        assertThat(userMapper).isEqualTo(user);
    }
    
    /*------------------ userToDto ----------------------------------*/
    @Test
    void userToDtoWihtNullShouldReturnEmptyList() {
        UserDto userDtoMapper = mappeur.userToDto(null);
        assertThat(userDtoMapper).isNull();
    }
    
    @Test
    void userToDtoWihthUserShouldRetunDtoMappedFieldByField() {
        UserDto userDtoMapper = mappeur.userToDto(user);
        assertThat(userDtoMapper).isEqualTo(userDto);
    }
    
    /*------------------ userToDtos ----------------------------------*/
    @Test
    void userToDtosWithNullListShouldReturnEmptyList() {
        List<UserDto> userDtosMapper = mappeur.usersToDtos(null);
        assertThat(userDtosMapper).isEmpty();
    }
    
    @Test
    void userToDtosWithEmptyListListShouldReturnEmptyList() {
        List<User> users = new ArrayList<User>();
        
        List<UserDto> userDtosMapper = mappeur.usersToDtos(users);
        assertThat(userDtosMapper).isEmpty();
    }
    
    @Test
    void userToDtosWithNoEmptyListListShouldReturnNoEmptyListWithSameSize() {
        List<User> users = Collections.singletonList(user);
        
        List<UserDto> userDtosMapper = mappeur.usersToDtos(users);
        assertThat(userDtosMapper).hasSize(1);
        UserDto userDtoMapper = userDtosMapper.get(0);
        assertThat(userDtoMapper).isEqualTo(userDto);
    }
}