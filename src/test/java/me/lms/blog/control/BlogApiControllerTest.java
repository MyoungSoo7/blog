package me.lms.blog.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.lms.blog.dto.AddArticleRequest;
import me.lms.blog.dto.UpdateArticleRequest;
import me.lms.blog.entity.Article;
import me.lms.blog.repo.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        blogRepository.deleteAll();
    }

    @DisplayName("블로그 글 작성 테스트")
    @Test
    public void addArticle() throws Exception{
        // given
        final String url = "/api/article";
        final String title = "제목";
        final String content = "내용";
        final AddArticleRequest addArticleRequest = new AddArticleRequest(title, content);

        // 직렬화
        final String requestBody = objectMapper.writeValueAsString(addArticleRequest); // 객체를 json 문자열로 직렬화

        // when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        resultActions.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }


    @DisplayName("블로그 글 조회 테스트")
    @Test
    public void findArticle() throws Exception{
        // given
        final String url = "/api/article/{id}";
        final String title = "제목";
        final String content = "내용";
        Article article = blogRepository.save(Article.builder().title(title).content(content).build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, article.getId()));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("title").value(title))
                .andExpect(jsonPath("content").value(content));

    }

    @DisplayName("블로그 글 수정 테스트")
    @Test
    public void updateArticle() throws Exception{
        // given
        final String url = "/api/article/{id}";
        final String title = "제목";
        final String content = "내용";

        Article article = blogRepository.save(Article.builder().title(title).content(content).build());
        final String updateTitle = "수정된 제목";
        final String updateContent = "수정된 내용";

        final UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(updateTitle, updateContent);

        // when
        final ResultActions resultActions = mockMvc.perform(put(url, article.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateArticleRequest)));

        // then
        resultActions.andExpect(status().isOk());

        Article updatedArticle = blogRepository.findById(article.getId()).get();

        assertThat(updatedArticle.getTitle()).isEqualTo(updateTitle);
        assertThat(updatedArticle.getContent()).isEqualTo(updateContent);

    }


    @DisplayName("블로그 글 삭제 테스트")
    @Test
    void deleteArticle() throws Exception{
        // given
        final String url = "/api/article/{id}";
        final String title = "제목";
        final String content = "내용";
        Article article = blogRepository.save(Article.builder().title(title).content(content).build());

        // when
        mockMvc.perform(delete(url, article.getId())).andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }




}