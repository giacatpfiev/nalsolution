package gc.garcol.nalsolution.controller;

import gc.garcol.nalsolution._configuration.NalsolutionApplicationTest;
import gc.garcol.nalsolution.controller.helper.UserInfoFaker;
import gc.garcol.nalsolution.payload.req.RequestSocialSignIn;
import gc.garcol.nalsolution.service.FirebaseService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author thai-van
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NalsolutionApplicationTest.class)
@AutoConfigureMockMvc
public class AuthRestControllerRealTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FirebaseService firebaseService;

    @SneakyThrows
    @Test
    public void testSignInWithValidTokenId() {

        when(firebaseService.extractUserInfo(any(String.class)))
                .thenReturn(
                        UserInfoFaker.buildFaker("AuthRestControllerRealTest -> testSignInWithValidTokenId")
                );

        RequestSocialSignIn req  = new RequestSocialSignIn();
        req.setTokenId("token");

        MvcResult result = mvc.perform(
                post("/api/auth/social-sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.JSON.toJson(req))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        log.info("AuthRestControllerRealTest -> testSignInWithValidTokenId. Result: {}", result.getResponse().getContentAsString());
    }

}
