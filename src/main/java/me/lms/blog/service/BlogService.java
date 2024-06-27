package me.lms.blog.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.lms.blog.dto.AddArticleRequest;
import me.lms.blog.dto.UpdateArticleRequest;
import me.lms.blog.entity.Article;
import me.lms.blog.repo.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다." +id ));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다." +id ));
        article.update(request.getTitle(), request.getContent());
        return article;
    }

}
