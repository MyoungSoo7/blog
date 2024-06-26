package me.lms.blog.control;


import lombok.RequiredArgsConstructor;
import me.lms.blog.dto.AddArticleRequest;
import me.lms.blog.entity.Article;
import me.lms.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class BlogApiController {

    private final BlogService blogService;


    @PostMapping("/api/article")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request); 
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

}
