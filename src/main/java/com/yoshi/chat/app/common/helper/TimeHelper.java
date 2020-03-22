package com.yoshi.chat.app.common.helper;

import lombok.*;
import org.springframework.stereotype.*;

import java.time.*;

@Component
@AllArgsConstructor
public class TimeHelper {
    private final Clock clock;
    private final ZoneId zoneId;

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public long toEpochTime(@NonNull LocalDateTime localDateTime) {
        return localDateTime.atZone(zoneId).toEpochSecond();
    }
}