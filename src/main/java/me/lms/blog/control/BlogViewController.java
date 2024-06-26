package me.lms.blog.control;

import lombok.RequiredArgsConstructor;
import me.lms.blog.dto.ArticleListViewResponse;
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

}
