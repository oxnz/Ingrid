package io.github.oxnz.Ingrid.controller;

import io.github.oxnz.Ingrid.Article;
import io.github.oxnz.Ingrid.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<Map<Long, Article>> list() {
        Map<Long, Article> items = articleRepository.list();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<Article> get(@PathVariable long id) {
        Article item = articleRepository.get(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
