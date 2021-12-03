package uk.ac.cardiff.team5.graphium.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import uk.ac.cardiff.team5.graphium.api.controllers.FileSearchAPIController;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Sql(scripts={"/schema-test.sql", "/data-test.sql"})
@ActiveProfiles("MariaDB")
@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
public class ReadFileTests {
    @Autowired
    private FileSearchAPIController fileSearchAPIController;

    @Autowired
    MockMvc mvc;

    @Test
    public void shouldGet2PublicFileAsJSON() throws Exception{
        System.out.println(fileSearchAPIController.findPublicFiles());
        mvc.perform(get("/api/publicFiles").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


    @Test
    @WithMockUser("user")
    public void shouldGet1FileUserAsJSON() throws Exception{
        mvc.perform(get("/api/myFiles").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        }

    @Test
    @WithMockUser("user")
    public void shouldGet2FileUserAllFilesAsJSON() throws Exception{
        mvc.perform(get("/api/allFiles").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}

