package posmy.interview.boot;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import posmy.interview.boot.entities.Role;
import posmy.interview.boot.entities.User;
import posmy.interview.boot.entities.enumeration.RoleEnum;
import posmy.interview.boot.entities.enumeration.UserStatus;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserTests {
Logger logger = LoggerFactory.getLogger(UserTests.class);
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
    public void setup() {
    	mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) 
                .build();
    }
	
	@Test
    @DisplayName("Member get users.")
    @WithMockUser(roles = {"MEMBER"}, username = "member")
    public void testMemberGetUser() throws Exception {
        mockMvc.perform(get("/users"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*").exists());
    }
	
	@Test
    @DisplayName("Librarian get users.")
    @WithMockUser(roles = {"LIBRARIAN"}, username = "librarian")
    public void testLibrarianGetUser() throws Exception {
        mockMvc.perform(get("/users"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*").isArray());
    }
	
	@Test
    @DisplayName("Member create users.")
    @WithMockUser(roles = {"MEMBER"}, username = "member")
    public void testMemberCreateUser() throws Exception {
        mockMvc.perform(post("/users"))
               .andExpect(status().isForbidden());
    }
	
	@Test
    @DisplayName("Librarian create users.")
    @WithMockUser(roles = {"LIBRARIAN"}, username = "librarian")
    public void testLibrarianCreateUser() throws Exception {
		User testUser = new User();
		testUser.setLogin("abc");
		testUser.setPassword("def");
		testUser.setStatus(UserStatus.A);
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(RoleEnum.MEMBER.name()));
		testUser.setRoles(roles);
		
		String content = new ObjectMapper().writeValueAsString(testUser);
		
        mockMvc.perform(post("/users")
        	   .contentType(MediaType.APPLICATION_JSON)
        	   .content(content))
               .andExpect(status().isOk());
    }
	
	@Test
    @DisplayName("Member update users.")
    @WithMockUser(roles = {"MEMBER"}, username = "member")
    public void testMemberUpdateUser() throws Exception {
        mockMvc.perform(put("/users"))
               .andExpect(status().isForbidden());
    }
	
	@Test
    @DisplayName("Librarian put users.")
    @WithMockUser(roles = {"LIBRARIAN"}, username = "librarian")
    public void testLibrarianPutUser() throws Exception {
		User testUser = new User();
		testUser.setId(3L);
		testUser.setLogin("asd");
		testUser.setPassword("def");
		testUser.setStatus(UserStatus.A);
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(RoleEnum.LIBRARIAN.name()));
		testUser.setRoles(roles);
		
		String content = new ObjectMapper().writeValueAsString(testUser);
		
        mockMvc.perform(put("/users")
        	   .contentType(MediaType.APPLICATION_JSON)
        	   .content(content))
               .andExpect(status().isOk());
    }
	
	@Test
    @DisplayName("Member delete users.")
    @WithMockUser(roles = {"MEMBER"}, username = "del1")
    public void testMemberDeleteUser() throws Exception {
        mockMvc.perform(delete("/users"))
               .andExpect(status().isOk());
    }
	
	@Test
    @DisplayName("Librarian delete users.")
    @WithMockUser(roles = {"LIBRARIAN"}, username = "librarian")
    public void testLibrarianDeleteUser() throws Exception {
		
        mockMvc.perform(delete("/users")
        	   .contentType(MediaType.APPLICATION_JSON)
        	   .content("del2"))
               .andExpect(status().isOk());
    }
}
