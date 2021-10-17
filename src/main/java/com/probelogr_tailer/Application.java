package com.probelogr_tailer;

import com.probelogr_tailer.services.tailer.ProbelogrTailer;
import com.probelogr_tailer.utils.KingKonfig;
import java.io.FileNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private final static KingKonfig config = KingKonfig.getInstance();

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        SpringApplication.run(Application.class, args);

        String tag = config.getValue("wfm-backen-log-tag");
        String accessToken = config.getValue("wfm-backend-log-access-token");

        ProbelogrTailer.startBuilding()
                .setFile(config.getValue("wfm-backend-log-path"))
                .setAccessToken(accessToken)
                .setTag(tag)
                .setShouldPersist(false)
                .shouldLog(false)
                .run();

    }

}
