package victor.bonneau.kata.bankAccount.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.bonneau.kata.bankAccount.dto.UserDto;
import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.mappeur.UserMappeur;
import victor.bonneau.kata.bankAccount.model.User;
import victor.bonneau.kata.bankAccount.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
	private final UserMappeur userMappeur;
    private final UserService userService;
    
    public UserController(UserMappeur userMappeur, UserService userService) {
        this.userMappeur = userMappeur;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(){
        return ResponseEntity.ok(userMappeur.usersToDtos(userService.getAll()));
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserDto userDto){
        if (userDto.getId() != 0) {
            return ResponseEntity.badRequest().body("The Id in body must be equal to 0 or not presente");
        }
        User user = userMappeur.dtoToUser(userDto);
        UserDto userDtoCreate = userMappeur.userToDto(userService.create(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoCreate);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable int id) throws ObjectNotFoundException{
        return ResponseEntity.ok(userMappeur.userToDto(userService.getById(id)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody UserDto userDto){
        if(id != userDto.getId()) {
            return ResponseEntity.badRequest().body("The Id in parameter must be the same in the body of the request");
        }
        if(userDto.getId() == 0) {
            return ResponseEntity.badRequest().body("The Id in body must be diferente to 0");
        }
        User user = userMappeur.dtoToUser(userDto);
        UserDto userDtoUpdate = userMappeur.userToDto(userService.update(user));
        return ResponseEntity.ok(userDtoUpdate);
    }
}
