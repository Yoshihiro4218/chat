package com.yoshi.chat.app.controller;

import com.yoshi.chat.app.properties.*;
import com.yoshi.chat.domain.entity.*;
import com.yoshi.chat.domain.service.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

import java.time.*;
import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ChatFormController.class)
public class ChatFormControllerTest {
    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @MockBean
    private ChatLogService chatLogService;
    @MockBean
    private ChatLogProperties chatLogProperties;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                 .build();
    }

    @Test
    public void form() throws Exception {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 12, 30, 30);
        ChatLog chatLog1 = ChatLog.builder()
                                  .id(1L)
                                  .userId(100L)
                                  .message("testMessage1")
                                  .chattedAt(now)
                                  .build();
        ChatLog chatLog2 = ChatLog.builder()
                                  .id(2L)
                                  .userId(101L)
                                  .message("testMessage2")
                                  .chattedAt(now.plusMinutes(1L))
                                  .build();
        List<ChatLog> chatLogs = Arrays.asList(chatLog1, chatLog2);
        when(chatLogProperties.getLimit()).thenReturn(10);
        when(chatLogService.findAllLimit(10)).thenReturn(chatLogs);

        mockMvc.perform(get(("/form")))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Chat Service (^o^)")))
               .andExpect(content().string(containsString("testMessage1")))
               .andExpect(content().string(containsString("testMessage2")));

        verify(chatLogProperties).getLimit();
        verify(chatLogService).findAllLimit(10);
    }
}
