package io.github.oxnz.Ingrid;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ArticleRepository {
    public static final String KEY = "article";
    private RedisTemplate<Long, Article> redisTemplate;
    private HashOperations hashOperations;

    public ArticleRepository(RedisTemplate<Long, Article> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public Map<Long, Article> list() {
        return hashOperations.entries(KEY);
    }

    public Article get(long id) {
        return (Article) hashOperations.get(KEY, id);
    }

    public void set(Article article) {
        hashOperations.put(KEY, article.getId(), article);
    }

    public void delete(long id) {
        hashOperations.delete(KEY, id);
    }
}
