INSERT IGNORE INTO chat.user
SET id = 1,
    cookie_value = 'chat_service_system_user_cookie_value',
    user_name = '[システム(^o^)]',
    issued_at = now(),
    created_at = NOW(),
    updated_at = NOW();
