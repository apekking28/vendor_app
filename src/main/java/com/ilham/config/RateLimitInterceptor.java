package com.ilham.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int RATE_LIMIT_PER_SECOND = 1;
    private static final long CLEANUP_INTERVAL = 3600000; // 1 hour
    private final Map<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        TokenBucket tokenBucket = buckets.computeIfAbsent(clientIp, k -> new TokenBucket(RATE_LIMIT_PER_SECOND));

        if (tokenBucket.tryConsume()) {
            return true;
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Try again later.");
            return false;
        }
    }

    @Scheduled(fixedRate = CLEANUP_INTERVAL)
    public void cleanupBuckets() {
        long now = System.currentTimeMillis();
        buckets.entrySet().removeIf(entry -> now - entry.getValue().getLastAccessTime() > CLEANUP_INTERVAL);
    }

    private static class TokenBucket {
        private final int capacity;
        private int tokens;
        private long lastRefillTimestamp;
        private long lastAccessTime;

        public TokenBucket(int capacity) {
            this.capacity = capacity;
            this.tokens = capacity;
            this.lastRefillTimestamp = System.currentTimeMillis();
            this.lastAccessTime = System.currentTimeMillis();
        }

        public synchronized boolean tryConsume() {
            refill();
            if (tokens > 0) {
                tokens--;
                lastAccessTime = System.currentTimeMillis();
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long timePassed = now - lastRefillTimestamp;
            int refill = (int) (timePassed / 1000) * capacity;
            if (refill > 0) {
                tokens = Math.min(capacity, tokens + refill);
                lastRefillTimestamp = now;
            }
        }

        public long getLastAccessTime() {
            return lastAccessTime;
        }
    }
}