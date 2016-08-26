package org.kajip.aws.access_key_tools.rotate;

import org.kajip.aws.access_key_tools.library.service.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class Runner implements CommandLineRunner {

    @Autowired
    AwsService service;

    /**
     * アプリケーションの開始
     * @param args コマンド引数
     */
    @Override
    public void run(String... args) throws Exception {
        service.rotateAccessKey();
    }
}
