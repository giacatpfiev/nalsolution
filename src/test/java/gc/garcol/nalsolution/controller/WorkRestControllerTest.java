package gc.garcol.nalsolution.controller;

import gc.garcol.nalsolution._configuration.NalsolutionApplicationTest;
import gc.garcol.nalsolution.controller.helper.UserInfoFaker;
import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.enums.WorkStatus;
import gc.garcol.nalsolution.payload.req.RequestSocialSignIn;
import gc.garcol.nalsolution.payload.req.RequestWork;
import gc.garcol.nalsolution.service.FirebaseService;
import gc.garcol.nalsolution.utils.codec.JacksonUtil;
import gc.garcol.nalsolution.utils.codec.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author thai-van
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NalsolutionApplicationTest.class)
@AutoConfigureMockMvc
public class WorkRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FirebaseService firebaseService;

    @SneakyThrows
    public String generateJWT(String basicName) {
        when(firebaseService.extractUserInfo(any(String.class)))
                .thenReturn(
                        UserInfoFaker.buildFaker(basicName)
                );

        RequestSocialSignIn req  = new RequestSocialSignIn();
        req.setTokenId("token");

        MvcResult result = mvc.perform(
                post("/api/auth/social-sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.JSON.toJson(req))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    @SneakyThrows
    @Test
    public void createWork() {
        String jwt = generateJWT("WorkRestControllerTest -> createWork");

        RequestWork req = new RequestWork();
        req.setStatus(WorkStatus.PLANNING);
        req.setName("createWork");
        req.setStartTime(System.currentTimeMillis() - 1_000_000L);
        req.setEndTime(System.currentTimeMillis());

        MvcResult result = mvc.perform(
                post("/api/work")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.JSON.toJson(req))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        log.info("WorkRestControllerTest -> createWork. Result: {}", result.getResponse().getContentAsString());
    }

    @SneakyThrows
    @Test
    public void updateWorkWithValidUser() {
        String jwt = generateJWT("WorkRestControllerTest -> updateWorkWithValidUser");

        RequestWork req = new RequestWork();
        req.setStatus(WorkStatus.PLANNING);
        req.setName("createWork");
        req.setStartTime(System.currentTimeMillis() - 1_000_000L);
        req.setEndTime(System.currentTimeMillis());

        MvcResult result = mvc.perform(
                post("/api/work")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.JSON.toJson(req))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        String workStr = result.getResponse().getContentAsString();
        log.info("WorkRestControllerTest -> createWork. Result: {}", workStr);
        Work work = JacksonUtil.fromJson(workStr, Work.class);

        log.info(" ====== TEST ======");
        req.setId(work.getId());
        req.setName("new name -> updateWorkWithValidUser");

        MvcResult updateResult = mvc.perform(
                put("/api/work")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.JSON.toJson(req))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

    @SneakyThrows
    @Test
    public void updateWorkWithInvalidUser() {
        String jwt = generateJWT("WorkRestControllerTest -> updateWorkWithInvalidUser");

        RequestWork req = new RequestWork();
        req.setStatus(WorkStatus.PLANNING);
        req.setName("createWork");
        req.setStartTime(System.currentTimeMillis());
        req.setEndTime(System.currentTimeMillis());

        MvcResult result = mvc.perform(
                post("/api/work")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.JSON.toJson(req))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        String workStr = result.getResponse().getContentAsString();
        log.info("WorkRestControllerTest -> createWork. Result: {}", workStr);
        Work work = JacksonUtil.fromJson(workStr, Work.class);

        log.info(" ====== TEST ======");
        req.setId(work.getId());
        req.setName("new name -> updateWorkWithInvalidUser");

        String otherUserJwt = generateJWT("WorkRestControllerTest -> updateWorkWithInvalidUser otherUser");

        MvcResult updateResult = mvc.perform(
                put("/api/work")
                        .header("Authorization", "Bearer " + otherUserJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.JSON.toJson(req))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

    }

    @SneakyThrows
    @Test
    public void deleteWorkWithValidAccount() {
        String jwt = generateJWT("WorkRestControllerTest -> deleteWorkWithValidAccount");

        RequestWork req = new RequestWork();
        req.setStatus(WorkStatus.PLANNING);
        req.setName("createWork");
        req.setStartTime(System.currentTimeMillis() - 1_000L);
        req.setEndTime(System.currentTimeMillis());

        MvcResult result = mvc.perform(
                post("/api/work")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.JSON.toJson(req))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        String workStr = result.getResponse().getContentAsString();
        log.info("WorkRestControllerTest -> deleteWorkWithValidAccount. Result: {}", workStr);
        Work work = JacksonUtil.fromJson(workStr, Work.class);

        log.info(" ====== TEST ======");

        mvc.perform(
                delete("/api/work/" + work.getId())
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();
    }

    @SneakyThrows
    @Test
    public void testDeleteWithInvalidUser() {
        String jwt = generateJWT("WorkRestControllerTest -> deleteWorkWithValidAccount");
        mvc.perform(
                delete("/api/work/" + -1)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
                .andReturn();
    }

}
