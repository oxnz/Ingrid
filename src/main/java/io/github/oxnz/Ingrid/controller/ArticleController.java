package io.github.oxnz.Ingrid.controller;

import io.github.oxnz.Ingrid.entity.Article;
import io.github.oxnz.Ingrid.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<List<Article>> list() {
        List<Article> articles = articleRepository.list();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Article> get(@PathVariable long id) {
        Article article = articleRepository.get(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Article> create(@PathVariable long id) {
        Article article = new Article(id, "name", String.valueOf(Instant.now()));
        articleRepository.put(article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }
}
