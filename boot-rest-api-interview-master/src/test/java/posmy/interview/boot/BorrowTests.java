package posmy.interview.boot;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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

import posmy.interview.boot.entities.Book;
import posmy.interview.boot.entities.BorrowEvent;
import posmy.interview.boot.entities.enumeration.BookStatus;
import posmy.interview.boot.repository.BorrowEventRepository;
import posmy.interview.boot.service.BookService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BorrowTests {
Logger logger = LoggerFactory.getLogger(BorrowTests.class);
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BorrowEventRepository borrowEventRepository;

	@BeforeEach
    public void setup() {
    	mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) 
                .build();
    }
	
	@Test
    @DisplayName("Member self borrow books.")
    @WithMockUser(roles = {"MEMBER"}, username = "member")
    public void testMemberBorrowBook() throws Exception {
        mockMvc.perform(post("/self-borrow")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content("2"))
               .andDo(print())
               .andExpect(status().isOk());
        
        Book book = bookService.findOneByIdOrThrow(2L);
        assertThat(book).extracting(x -> x.getStatus()).isEqualTo(BookStatus.B);
    }
	
	@Test
    @DisplayName("Librarian self borrow books.")
    @WithMockUser(roles = {"LIBRARIAN"}, username = "librarian")
    public void testLibrarianBorrowBook() throws Exception {
        mockMvc.perform(post("/through-librarian")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content("{\"userLogin\":\"member\",\"bookId\":\"1\"}"))
               .andDo(print())
               .andExpect(status().isOk());
        
        Book book = bookService.findOneByIdOrThrow(1L);
        assertThat(book).extracting(x -> x.getStatus()).isEqualTo(BookStatus.B);
    }
	
	@Test
    @DisplayName("Return books.")
    @WithMockUser(roles = {"MEMBER"}, username = "member")
    public void testReturnBooks() throws Exception {
        mockMvc.perform(put("/return")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content("1"))
               .andDo(print())
               .andExpect(status().isOk());
        
        Book book = bookService.findOneByIdOrThrow(3L);
        assertThat(book).extracting(x -> x.getStatus()).isEqualTo(BookStatus.A);
        
        Optional<BorrowEvent> eventOpt = this.borrowEventRepository.findById(1L);
        BorrowEvent event = eventOpt.get();
        assertThat(event).extracting(x -> x.getReturn_time()).isNotNull();
    }
}
