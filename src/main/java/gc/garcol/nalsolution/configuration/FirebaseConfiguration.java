package gc.garcol.nalsolution.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

import static gc.garcol.nalsolution.utils.ConfigUtil.CONFIG_UTIL;

/**
 * @author thai-van
 **/
@Configuration
public class FirebaseConfiguration {

    @SneakyThrows
    @Bean
    FirebaseApp firebaseApp() {
        InputStream serviceAccount = CONFIG_UTIL.getInputStream("firebase/adminsdk.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();


        FirebaseApp instance = FirebaseApp.initializeApp(options);
        return instance;
    }

}
