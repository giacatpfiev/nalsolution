package gc.garcol.nalsolution.controller;

import gc.garcol.nalsolution._configuration.NalsolutionApplicationTest;
import gc.garcol.nalsolution.payload.req.RequestSocialSignIn;
import gc.garcol.nalsolution.service.core.AuthService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author thai-van
 **/

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NalsolutionApplicationTest.class)
@AutoConfigureMockMvc
public class AuthRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    AuthService authService;

    @SneakyThrows
    @Test
    public void signInWithValidSocialToken() {
        String token = "validJWT";
        String res = "jwt";

        RequestSocialSignIn req  = new RequestSocialSignIn();
        req.setTokenId(token);

        when(authService.signInWithSocialToken(any(String.class)))
                .thenReturn(res);

        MvcResult result = mvc.perform(
                post("/api/auth/social-sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.JSON.toJson(req))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
         .andExpect(content().string(res))
                .andDo(print())
         .andReturn();

        log.info("AuthRestControllerTest -> signInWithValidSocialToken: {}", result.getResponse().getContentAsString());
    }

    /**
     * null tokenId in req
     */
    @SneakyThrows
    @Test
    public void signInWithInvalidToken() {
        RequestSocialSignIn req  = new RequestSocialSignIn();
        mvc.perform(
                post("/api/auth/social-sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.JSON.toJson(req))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
    }

}
