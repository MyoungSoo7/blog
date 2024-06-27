package me.lms.blog.control;


import lombok.RequiredArgsConstructor;
import me.lms.blog.dto.AddArticleRequest;
import me.lms.blog.dto.ArticleResponse;
import me.lms.blog.dto.UpdateArticleRequest;
import me.lms.blog.entity.Article;
import me.lms.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor



public class BlogApiController {
    private final BlogService blogService;
    @PostMapping("/api/article")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request); 
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/article")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articles);
    }

    @GetMapping("/api/article/{id}")
    public ResponseEntity<ArticleResponse> findArticleById(@PathVariable Long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/article/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id) {
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/article/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok().body(updatedArticle);
    }

}
