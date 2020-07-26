package posmy.interview.boot;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BookTests {
	Logger logger = LoggerFactory.getLogger(BookTests.class);
	
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
    @DisplayName("Member get books.")
    @WithMockUser(roles = {"MEMBER"})
    public void testMemberGetBook() throws Exception {
        mockMvc.perform(get("/book"))
               .andDo(print())
               .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Librarian get books.")
    @WithMockUser(roles = {"LIBRARIAN"})
    public void testLibrarianGetBook() throws Exception {
        mockMvc.perform(get("/book"))
               .andDo(print())
               .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Random user get books.")
    @WithMockUser
    public void testSomeoneGetBook() throws Exception {
        mockMvc.perform(get("/book"))
               .andDo(print())
               .andExpect(status().isForbidden());
    }
    
    @Test
    @DisplayName("Member create books.")
    @WithMockUser(roles = {"MEMBER"})
    public void testMemberCreateBook() throws Exception {
        mockMvc.perform(post("/book"))
               .andDo(print())
               .andExpect(status().isForbidden());
    }
    
    @Test
    @DisplayName("Librarian create books.")
    @WithMockUser(roles = {"LIBRARIAN"})
    public void testLibrarianCreateBook() throws Exception {
        mockMvc.perform(post("/book"))
               .andDo(print())
               .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Random user create books.")
    @WithMockUser
    public void testSomeoneCreateBook() throws Exception {
        mockMvc.perform(post("/book"))
               .andDo(print())
               .andExpect(status().isForbidden());
    }
    
    
    @Test
    @DisplayName("Member delete books.")
    @WithMockUser(roles = {"MEMBER"})
    public void testMemberDeleteBook() throws Exception {
        mockMvc.perform(delete("/book"))
               .andDo(print())
               .andExpect(status().isForbidden());
    }
    
    @Test
    @DisplayName("Librarian delete books.")
    @WithMockUser(roles = {"LIBRARIAN"})
    public void testLibrarianDeleteBook() throws Exception {
        mockMvc.perform(delete("/book"))
               .andDo(print())
               .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Random user delete books.")
    @WithMockUser
    public void testSomeoneDeleteBook() throws Exception {
        mockMvc.perform(delete("/book"))
               .andDo(print())
               .andExpect(status().isForbidden());
    }
    
    @Test
    @DisplayName("Member update books.")
    @WithMockUser(roles = {"MEMBER"})
    public void testMemberUpdateBook() throws Exception {
        mockMvc.perform(put("/book"))
               .andDo(print())
               .andExpect(status().isForbidden());
    }
    
    @Test
    @DisplayName("Librarian update books.")
    @WithMockUser(roles = {"LIBRARIAN"})
    public void testLibrarianUpdateBook() throws Exception {
        mockMvc.perform(put("/book"))
               .andDo(print())
               .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Random user update books.")
    @WithMockUser
    public void testSomeoneUpdateBook() throws Exception {
        mockMvc.perform(put("/book"))
               .andDo(print())
               .andExpect(status().isForbidden());
    }
}
