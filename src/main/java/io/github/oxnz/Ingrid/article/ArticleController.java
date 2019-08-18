package io.github.oxnz.Ingrid.article;

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
    private ArticleService articleService;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<List<Article>> list() {
        List<Article> articles = articleService.list();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Article> get(@PathVariable long id) {
        Article article = articleService.find(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Article> create(@PathVariable long id) {
        Article article = new Article(id, "name", String.valueOf(Instant.now()));
        articleService.save(article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }
}
