package me.lms.blog.control;

import lombok.RequiredArgsConstructor;
import me.lms.blog.dto.ArticleListViewResponse;
import me.lms.blog.dto.ArticleViewResponse;
import me.lms.blog.entity.Article;
import me.lms.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model){
        List<ArticleListViewResponse> articles = blogService.findAll()
                        .stream()
                        .map(ArticleListViewResponse::new)
                        .toList();

        model.addAttribute("articles",articles);
        return "articleList";
    }


    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticlee(@RequestParam(required = false) Long id, Model model){
        if(id == null){
            model.addAttribute("article", new ArticleViewResponse());
        }else{
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";
    }

}
