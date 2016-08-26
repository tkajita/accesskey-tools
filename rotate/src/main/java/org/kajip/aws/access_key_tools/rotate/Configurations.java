package org.kajip.aws.access_key_tools.rotate;

import org.kajip.aws.access_key_tools.library.LibraryConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableAsync
@Import({
    LibraryConfiguration.class
})
public class Configurations {

    static final Path USER_HOME_DIRECTORY = Paths.get(System.getProperty("user.home"));

    @Bean
    public Path credentialsFileDirectoryPath() {
        return USER_HOME_DIRECTORY.resolve(".aws");
    }
}
