package me.lms.blog.service;


import lombok.RequiredArgsConstructor;
import me.lms.blog.dto.AddArticleRequest;
import me.lms.blog.entity.Article;
import me.lms.blog.repo.BlogRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

}
