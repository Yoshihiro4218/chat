package com.yoshi.chat.domain.service;

import com.yoshi.chat.domain.entity.*;
import com.yoshi.chat.domain.repository.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatLogServiceTest {
    @InjectMocks
    private ChatLogService target;
    @Mock
    private ChatLogRepository chatLogRepository;

    @Test
    public void findAllLimit() {
        List<ChatLog> chatLogs = Arrays.asList(mock(ChatLog.class), mock(ChatLog.class));
        when(chatLogRepository.findLimit(10)).thenReturn(chatLogs);

        assertThat(target.findAllLimit(10)).isEqualTo(chatLogs);
    }
}
