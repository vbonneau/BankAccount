package victor.bonneau.kata.bankAccount.mappeur;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import victor.bonneau.kata.bankAccount.dto.UserDto;
import victor.bonneau.kata.bankAccount.model.User;

@Component
public class UserMappeur {

    public UserDto userToDto(User user) {
        if(user == null) return null;
        
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    
    public List<UserDto> usersToDtos(List<User> users) {
        if(users == null) return new ArrayList<UserDto>();
        return users.stream().filter(Objects::nonNull).map(user -> userToDto(user))
                .collect(Collectors.toList());
    }
    
    public User dtoToUser(UserDto userDto) {
        if(userDto == null) return null;
        
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
    
    public List<User> dtosToUsers(List<UserDto> userDtos){
        if(userDtos == null) return new ArrayList<User>();
        return userDtos.stream().filter(Objects::nonNull).map(userDto -> dtoToUser(userDto))
                .collect(Collectors.toList());
        
    }
}

