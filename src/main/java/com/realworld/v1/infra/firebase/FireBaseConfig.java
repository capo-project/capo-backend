package com.realworld.v1.infra.firebase;//package com.realworld.infra.firebase;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.realworld.global.utils.FileUtil;
//import jakarta.annotation.PostConstruct;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//public class FireBaseConfig {
//
//    @PostConstruct
//    public FirebaseApp firebaseApp() throws IOException {
//        FileUtil fileUtil = new FileUtil();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            String firebaseKeyPath = fileUtil.osFilePathSeparation();
//            FileInputStream fis = new FileInputStream(firebaseKeyPath + java.io.File.separator + "photocard-firebase-adminsdk.json");
//
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(GoogleCredentials.fromStream(fis))
//                    .build();
//
//            return FirebaseApp.initializeApp(options);
//        }
//        return FirebaseApp.getInstance();
//
//    }
//
//    @Bean
//    public FirebaseMessaging firebaseMessaging() throws IOException {
//        return FirebaseMessaging.getInstance(firebaseApp());
//    }
//}