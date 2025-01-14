package org.drift.common.context;

/**
 * @author jiakui_zeng
 * @date 2025/1/8 17:34
 */
public class UserContextHolder {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();

    public static Long getUserContext() {
        return USER_ID.get();
    }

    public static void setUserContext(Long userId) {
        USER_ID.set(userId);
    }

    public static void clear() {
        USER_ID.remove();
    }
}
