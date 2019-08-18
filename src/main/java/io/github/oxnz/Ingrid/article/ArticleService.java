package io.github.oxnz.Ingrid.article;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> list() {
        return articleRepository.list();
    }

    public Article find(long id) {
        return articleRepository.get(id);
    }

    public void save(Article article) {
        articleRepository.put(article);
    }
}
