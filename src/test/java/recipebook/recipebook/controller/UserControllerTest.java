package recipebook.recipebook.controller;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import recipebook.RecipebookApplication;
import recipebook.user.User;
import recipebook.user.UserService;

import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RecipebookApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void create() throws Exception {

        Random random = new Random();

        int randomNumber = random.nextInt(1000);

        String exampleUserJson = "{\"name\":\"Test\",\"email\":\"test"+randomNumber+"@test.com\",\"password\":\"1122334455\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleUserJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value("User account created successfully"))
                .andDo(print());
    }

    @Test
    public void createWithEmailAlreadyRegistered() throws Exception {

        Random random = new Random();

        int randomNumber = random.nextInt(1000);

        User user = new User("Test", "test"+randomNumber+"@test.com", "1122334455");

        userService.create(user);

        String exampleUserJson = "{\"name\":\"Test\",\"email\":\"test"+randomNumber+"@test.com\",\"password\":\"1122334455\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleUserJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.message").value("There is an account with that email address: '"+user.getEmail()+"'"))
                .andDo(print());
    }

    @Test
    public void createWithoutName() throws Exception {

        Random random = new Random();

        int randomNumber = random.nextInt(1000);

        String exampleUserJson = "{\"email\":\"test"+randomNumber+"@test.com\",\"password\":\"1122334455\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleUserJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void createWithoutPassword() throws Exception {

        Random random = new Random();

        int randomNumber = random.nextInt(1000);

        String exampleUserJson = "{\"name\":\"Test\",\"email\":\"test"+randomNumber+"@test.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleUserJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void createWithoutEmail() throws Exception {
        String exampleUserJson = "{\"name\":\"Test\",\"password\":\"1122334455\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleUserJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}