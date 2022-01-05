package me.whiteship.springtest;

import me.whiteship.springtest.sample.SampleController;
import me.whiteship.springtest.sample.SampleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SampleControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    SampleService mockSampleService;

    @Test//내장톰켓 사용X
    public void hello() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/hello");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().string("helloJS"))
                .andDo(print());
    }

    @Test//내장톰켓 사용O
    public void helloTestRestTemPlate(){
        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("helloJS");
    }

    @Test//내장톰켓 사용O
    public void helloMockSampleService(){
        when(mockSampleService.getName()).thenReturn("JS");

        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("helloJS");
    }


}
