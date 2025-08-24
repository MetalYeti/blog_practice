package ru.yandex.practicum.blog_practice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PostControllerIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Очистка и заполнение тестовых данных в базе
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("SELECT setval(pg_get_serial_sequence('posts', 'id'), 4, false);");

        // Добавление тестовых данных
        jdbcTemplate.execute("INSERT INTO posts (id, caption, content, tags, likes) VALUES (1, 'Первый пост', 'Текст1', 'Тэг1', 15)");
        jdbcTemplate.execute("INSERT INTO posts (id, caption, content, tags, likes) VALUES (2, 'Второй пост', 'Текст2', 'Тег2', 24)");
        jdbcTemplate.execute("INSERT INTO posts (id, caption, content, tags, likes) VALUES (3, 'Третий пост', 'Текст3', 'Тэг3', 62)");
    }

    @Test
    void getPosts_shouldReturnHtmlWithPosts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(xpath("//table/tbody/tr").nodeCount(3))
                .andExpect(xpath("//table/tbody/tr[3]/td[1]/h2").string("Третий пост"));
    }

    @Test
    void save_shouldAddPostToDatabaseAndRedirect() throws Exception {
        mockMvc.perform(post("/posts").contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .param("title", "Тестовый пост")
                        .param("tags", "ТэгТест")
                        .param("text", "Текст тест"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void delete_shouldRemovePostFromDatabaseAndRedirect() throws Exception {
        mockMvc.perform(post("/posts/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void getPost_shouldReturnHTMLWithPost() throws Exception {
        mockMvc.perform(get("/posts/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(xpath("//table/tbody/tr[1]/td[1]/h2").string("Второй пост"));
    }
}
