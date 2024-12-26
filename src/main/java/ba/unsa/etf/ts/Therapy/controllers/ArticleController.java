package ba.unsa.etf.ts.Therapy.controllers;

import ba.unsa.etf.ts.Therapy.exceptions.ArticleNotFoundException;
import ba.unsa.etf.ts.Therapy.models.Article;
import ba.unsa.etf.ts.Therapy.exceptions.*;
import ba.unsa.etf.ts.Therapy.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable String id) {
        try {
            Article article = articleService.getArticleById(id);
            return ResponseEntity.ok(article);
        } catch (ArticleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("validation", ex.getMessage()));
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteArticleById(@PathVariable String id) {
        try {
            articleService.deleteArticleById(id);
            return ResponseEntity.ok(new ErrorResponse("No", "Article with ID " + id + " has been deleted successfully"));
        } catch (ArticleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("validation", ex.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable String id, @Valid @RequestBody Article updatedArticle, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce("", String::concat);
            return ResponseEntity.badRequest().body(new ErrorResponse("validation", errorMessage));
        }

        if (!articleService.existsArticleById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("validation", "Article not found"));
        }

        updatedArticle.setId(id);
        Article savedArticle = articleService.updateArticle(updatedArticle);
        return ResponseEntity.ok(savedArticle);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createArticle(@Valid @RequestBody Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .reduce("", String::concat);
            return ResponseEntity.badRequest().body(new ErrorResponse("validation", errorMessage));
        }

        Article savedArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        System.out.println("Fetching all articles...");
        if (articles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No data", "No articles found"));
        }
        return ResponseEntity.ok(articles);
    }
    @GetMapping("/allPsychologists")
    public ResponseEntity<?> getAllPsychologistsArticles(String id) {
        List<Article> articles = articleService.getAllPsychologistsArticles(id);
        System.out.println("Fetching all articles...");
        if (articles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No data", "No articles found"));
        }
        return ResponseEntity.ok(articles);
    }
    @GetMapping("/byKeyword/{keyword}")
    public ResponseEntity<?> getArticlesByKeyword(@PathVariable String keyword) {
        List<Article> articles = articleService.findArticlesByKeyword(keyword);
        if (articles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No data", "No articles found containing keyword: " + keyword));
        }
        return ResponseEntity.ok(Map.of("articles", articles));
    }

    @GetMapping("/allArticles")
    public ResponseEntity<List<Article>> getAllArticlesWithoutAuthors() {
        List<Article> articles = articleService.getAllArticles();
        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articles);
    }
}