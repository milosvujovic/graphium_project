package uk.ac.cardiff.team5.graphium.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.cardiff.team5.graphium.api.controllers.FileSearchAPIController;
import uk.ac.cardiff.team5.graphium.service.impl.UserServiceImpl;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Sql(scripts={"/schema-test.sql", "/script-test.sql"})
@ActiveProfiles("MariaDB")
@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
public class FileAPIControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private FileSearchAPIController fileSearchAPIController;
    @Test

    public void getAllPublicFiles() throws Exception{
        mvc.perform(get("/api/publicFiles").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
