package org.kajip.aws.access_key_tools.library.datasource;

import com.amazonaws.services.identitymanagement.model.AccessKey;
import org.kajip.aws.access_key_tools.library.domain.Credentials;
import org.kajip.aws.access_key_tools.library.domain.CredentialsRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;

import static java.nio.file.StandardOpenOption.*;

@Component
public class CredentialsDatasourceFile implements CredentialsRepository {

    public final static String CREDENTIAL_FILE_NAME = "credentials";

    /** バックアップファイルのディレクトリ名 */
    public final static String BACKUP_DIRECTORY_NAME = "backup";

    /** バックアップファイルのフォーマット。[例: credential.20160826083005.123] */
    public final static String BACKUP_FILENAME_FORMAT = "%s.%2$tY%2$tm%2$td%2$tH%2$tM%2$tS.%2$tL";

    @Autowired
    @Qualifier("credentialsFileDirectoryPath")
    private Path credentialsFileDirectoryPath;


    @Override
    public void saveCredentials(String userName, Credentials credentials) {
        _saveCredentials(userName + ".credentials", credentials);
    }

    @Override
    public void updateCredentials(Credentials credentials) {
        _saveCredentials(CREDENTIAL_FILE_NAME, credentials);
    }

    private void _saveCredentials(String fileName, Credentials credentials) {
        Path credentialsFilePath = credentialsFileDirectoryPath.resolve(fileName);
        LoggerFactory.getLogger(getClass()).info(credentialsFilePath.toString());

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(credentialsFilePath, CREATE, TRUNCATE_EXISTING);
            PrintWriter out = new PrintWriter(bufferedWriter)) {
            credentials.getAccessKeys().entrySet().forEach( entry -> {
                AccessKey accessKey = entry.getValue();
                out.println(String.format("[%s]", entry.getKey()));
                out.println(String.format("aws_access_key_id = %s", accessKey.getAccessKeyId()));
                out.println(String.format("aws_secret_access_key = %s", accessKey.getSecretAccessKey()));
            });

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void backupCredentials() {
        Path credentialFilePath = credentialsFileDirectoryPath.resolve(CREDENTIAL_FILE_NAME);
        Path backupFilePath = credentialsFileDirectoryPath
            .resolve(BACKUP_DIRECTORY_NAME)
            .resolve(String.format(BACKUP_FILENAME_FORMAT, CREDENTIAL_FILE_NAME, Calendar.getInstance()));

        try {
            Files.createDirectories(backupFilePath.getParent());
            Files.copy(credentialFilePath, backupFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
