package org.kajip.aws.access_key_tools.create;

import org.kajip.aws.access_key_tools.library.service.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("default")
public class Runner implements CommandLineRunner {

    @Autowired
    AwsService awsService;

    /**
     * アプリケーションの開始
     * @param args コマンド引数
     */
    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            System.err.println("usage: java -jar xxx <username> ...");
            return;
        }

        Arrays.asList(args).forEach(awsService::createCredentialsFileFor);
    }
}
