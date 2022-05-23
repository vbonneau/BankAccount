package victor.bonneau.kata.bankAccount.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import victor.bonneau.kata.bankAccount.dto.UserDto;
import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.mappeur.UserMappeur;
import victor.bonneau.kata.bankAccount.model.User;
import victor.bonneau.kata.bankAccount.service.UserService;

public class UserControllerTest {
    private User user;
    private UserDto userDto;
    private MockMvc mockMvc;
    private UserMappeur mappeur;
    private UserService service;
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

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

        service = mock(UserService.class);
        mappeur = mock(UserMappeur.class);
        UserController controller = new UserController(mappeur, service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomGlobalExceptionHandler())
                .build();
    }

    /*--------------------get all--------------------*/
    @Test
    void getAllShouldReturnStatusOkAndUsersList() throws Exception {
        List<User> users = Collections.singletonList(user);

        when(service.getAll()).thenReturn(users);
        when(mappeur.usersToDtos(users)).thenReturn(Collections.singletonList(userDto));

        String mockResult = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserDto> result = Arrays.asList(objectMapper.readValue(mockResult, UserDto[].class));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(userDto);
    }

    /*--------------------create--------------------*/
    @Test
    void createShouldReturnOkAndTheUser() throws Exception {
        userDto.setId(0);
        user.setId(0);
        when(mappeur.dtoToUser(userDto)).thenReturn(user);
        when(service.create(user)).thenReturn(user);
        when(mappeur.userToDto(user)).thenReturn(userDto);

        String mockResult = mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(service, times(1)).create(user);
        verifyNoMoreInteractions(service);

        UserDto result = objectMapper.readValue(mockResult, UserDto.class);
        assertThat(result).isEqualTo(userDto);
    }
    
    @Test
    void createShouldReturn400WhenIddifferent0() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenUsernameIsNull() throws Exception {
        userDto.setUsername(null);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenUsernameIsEmpty() throws Exception {
        userDto.setUsername("");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenUsernameIsBlank() throws Exception {
        userDto.setUsername(" ");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenPasswordIsNull() throws Exception {
        userDto.setPassword(null);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenPasswordIsEmpty() throws Exception {
        userDto.setPassword("");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenPasswordIsBlank() throws Exception {
        userDto.setPassword(" ");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    /*----------------------getById------------------------------*/
    @Test
    void getByIdShouldReturnUserDto() throws Exception {
        int id = 1;

        when(service.getById(id)).thenReturn(user);
        when(mappeur.userToDto(user)).thenReturn(userDto);

        String mockResult = mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto result = objectMapper.readValue(mockResult, UserDto.class);
        assertThat(result).isEqualTo(userDto);
    }
    
    @Test
    void getByIdShouldReturn404WhenUserNotFound() throws Exception {
        int id = 1;

        when(service.getById(id)).thenThrow(ObjectNotFoundException.class);
        
        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound());
            
    }

    /*----------------------update ------------------------------*/
    @Test
    void updateShouldRetunUserDtoUpdate() throws Exception {
        int id = 1;

        when(mappeur.dtoToUser(userDto)).thenReturn(user);
        when(service.update(user)).thenReturn(user);
        when(mappeur.userToDto(user)).thenReturn(userDto);

        String mockResult = mockMvc.perform(put("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto result = objectMapper.readValue(mockResult, UserDto.class);
        assertThat(result).isEqualTo(userDto);
    }

    @Test
    void updateShouldRetun400WhenIdIsDifferent() throws Exception {
        int id = 2;

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateShouldRetun400WhenIdIsEqualToZero() throws Exception {
        int id = 0;
        userDto.setId(0);

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateShouldReturn400WhenUsernameIsNull() throws Exception {
        int id = 1;
        userDto.setUsername(null);

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenUsernameIsEmpty() throws Exception {
        int id = 1;
        userDto.setUsername("");

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenUsernameIsBlank() throws Exception {
        int id = 1;
        userDto.setUsername(" ");

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenPasswordIsNull() throws Exception {
        int id = 1;
        userDto.setPassword(null);

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenPasswordIsEmpty() throws Exception {
        int id = 1;
        userDto.setPassword("");

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenPasswordIsBlank() throws Exception {
        int id = 1;
        userDto.setPassword(" ");

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }
}
