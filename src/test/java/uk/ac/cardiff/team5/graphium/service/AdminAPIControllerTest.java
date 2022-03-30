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
//public class AdminAPIControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private AdminService adminService;
//
//    @Test
//    @WithUserDetails("csmith")
//    public void get3PotentialPartners() throws Exception{
//        mvc.perform(get("/api/admin/potentialPartners").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)));
//    }
//    @Test
//    @WithUserDetails("aramsdale")
//    public void get1PotentialPartners() throws Exception{
//        mvc.perform(get("/api/admin/potentialPartners").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//    @Test
//    @WithUserDetails("csmith")
//    public void get0CurrentSharingPartners() throws Exception{
//        mvc.perform(get("/api/admin/currentPartners").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(0)));
//    }
//    @Test
//    @WithUserDetails("aramsdale")
//    public void get2CurrentSharingPartners() throws Exception{
//        mvc.perform(get("/api/admin/currentPartners").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)));
//    }
//    @Test
//    @WithUserDetails("aramsdale")
//    public void get1CurrentViewingPartners() throws Exception{
//        mvc.perform(get("/api/admin/partnersThatYouCanView").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//    @Test
//    @WithUserDetails("csmith")
//    public void get3CurrentViewingPartners() throws Exception{
//        mvc.perform(get("/api/admin/partnersThatYouCanView").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)));
//    }
//}
