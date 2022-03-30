//package uk.ac.cardiff.team5.graphium.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.jdbc.SqlConfig;
//import org.springframework.test.context.jdbc.SqlGroup;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import uk.ac.cardiff.team5.graphium.api.controllers.FileSearchAPIController;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SqlGroup({
//        @Sql("/schema-test.sql"),
//        @Sql(scripts = "/storedprocedure-test.sql",
//                config = @SqlConfig(encoding = "utf-8", separator = "$$")),
//        @Sql("/data-test.sql")
//})
//
//@DirtiesContext
//@SpringBootTest
//
//@AutoConfigureMockMvc
//public class FileAPIControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private FileSearchAPIController fileSearchAPIController;
//
//    @Test
//    @WithUserDetails("adavies")
//    public void getAllPublicFiles() throws Exception {
//        mvc.perform(get("/api/user/publicFiles").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//
//    @Test
//    @WithUserDetails("csmith")
//    public void getAllPublicFilesDiffernetUser() throws Exception{
//        mvc.perform(get("/api/user/publicFiles").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//
//    @Test
//    @WithUserDetails("adavies")
//    public void getAllUsersFiles() throws Exception {
//        mvc.perform(get("/api/user/myFiles").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)));
//    }
//
//    @Test
//    @WithUserDetails("csmith")
//    public void getAllUsersFilesDiffernetUser() throws Exception{
//        mvc.perform(get("/api/user/myFiles").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(0)));
//    }
//    @Test
//    @WithUserDetails("adavies")
//    public void getMyOrganisationFiles() throws Exception{
//        mvc.perform(get("/api/user/myOrgFiles").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(4)));
//    }
//
//    @Test
//    @WithUserDetails("adavies")
//    public void getPartnersFiles() throws Exception {
//        mvc.perform(get("/api/user/myPartnerFiles").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)));
//    }
//
//    @Test
//    @WithUserDetails("adavies")
//    public void getAllFiles() throws Exception {
//        mvc.perform(get("/api/user/allFiles").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(7)));
//    }
//
//    @Test
//    @WithUserDetails("sbayrami")
//    public void unapprovedUserCannotViewFiles() throws Exception {
//        mvc.perform(get("/api/user/publicFiles").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithUserDetails("adavies")
//    public void testSearchFiles() throws Exception {
//        mvc.perform(get("/api/user/searchFiles/sport").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//
//    @Test
//    @WithUserDetails("adavies")
//    public void testSearchFilesCovid() throws Exception {
//        mvc.perform(get("/api/user/searchFiles/covid").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)));
//    }
//}
