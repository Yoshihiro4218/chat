package com.yoshi.chat.domain.repository;

import com.yoshi.chat.domain.entity.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.jdbc.core.*;
import org.springframework.test.context.junit4.*;
import org.springframework.transaction.annotation.*;

import java.sql.*;
import java.time.*;
import java.time.temporal.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ChatLogRepositoryTest {
    @Autowired
    private ChatLogRepository target;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws SQLException {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
    }

    @Test
    public void findLimit() {
        long id1 = 1L;
        long id2 = 2L;
        long id3 = 3L;
        long userId1 = 177L;
        long userId2 = 178L;
        long userId3 = 179L;
        String message1 = "testMessage1";
        String message2 = "testMessage2";
        String message3 = "testMessage3";
        LocalDateTime now1 = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime now2 = LocalDateTime.now().minusSeconds(5L).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime now3 = LocalDateTime.now().minusSeconds(10L).truncatedTo(ChronoUnit.SECONDS);
        ChatLog chatLog1 = ChatLog.builder()
                                  .id(id1)
                                  .userId(userId1)
                                  .message(message1)
                                  .chattedAt(now1)
                                  .build();
        ChatLog chatLog2 = ChatLog.builder()
                                  .id(id2)
                                  .userId(userId2)
                                  .message(message2)
                                  .chattedAt(now2)
                                  .build();

        jdbcTemplate.update("INSERT INTO `chat_log` SET id = ?, user_id = ?, message = ?, chatted_at = ?, created_at = NOW(), updated_at = NOW()",
                            id1, userId1, message1, now1);
        jdbcTemplate.update("INSERT INTO `chat_log` SET id = ?, user_id = ?, message = ?, chatted_at = ?, created_at = NOW(), updated_at = NOW()",
                            id2, userId2, message2, now2);
        jdbcTemplate.update("INSERT INTO `chat_log` SET id = ?, user_id = ?, message = ?, chatted_at = ?, created_at = NOW(), updated_at = NOW()",
                            id3, userId3, message3, now3);

        assertThat(target.findLimit(2)).containsExactly(chatLog1, chatLog2);
    }
}
