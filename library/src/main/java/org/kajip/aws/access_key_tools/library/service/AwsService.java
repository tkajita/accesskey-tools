package org.kajip.aws.access_key_tools.library.service;

import com.amazonaws.services.identitymanagement.model.AccessKey;
import com.amazonaws.services.identitymanagement.model.AccessKeyMetadata;
import com.amazonaws.services.identitymanagement.model.StatusType;
import com.google.common.collect.ImmutableMap;
import org.kajip.aws.access_key_tools.library.domain.AccessKeyRepository;
import org.kajip.aws.access_key_tools.library.domain.Credentials;
import org.kajip.aws.access_key_tools.library.domain.CredentialsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import static com.amazonaws.services.identitymanagement.model.StatusType.*;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class AwsService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AccessKeyRepository  accessKeyRepository;
    @Autowired
    CredentialsRepository credentialsRepository;

    @Async
    public void createCredentialsFileFor(String userName) {
        AccessKey accessKey = accessKeyRepository.createAccessKey(userName);
        credentialsRepository.saveCredentials(userName, new Credentials(ImmutableMap.of("default", accessKey)));
    }

    /**
     * アクセスキーのローテーション処理
     */
    public void rotateAccessKey() {

        // 既存のアクセスキーの一覧を取得
        List<AccessKeyMetadata> currentAccessKeys = accessKeyRepository.listAccessKeys();
        logger.info("current access keys: {}", currentAccessKeys);

        deleteInactivedAccessKeys(currentAccessKeys);
        createNewAccessKey();

        waitForAccessKeyActived(5000L);

        invalidateActiveAccessKeys(currentAccessKeys);
    }


    /**
     * 無効化済みアクセスキーの削除
     * @param accessKeyList
     */
    private void deleteInactivedAccessKeys(List<AccessKeyMetadata> accessKeyList) {
        accessKeyRepository.deleteAccessKeys(
            accessKeyList.stream()
                .filter(a -> StatusType.valueOf(a.getStatus()) == Inactive)
                .map(AccessKeyMetadata::getAccessKeyId)
                .collect(Collectors.toList()));
    }

    /**
     * 新しいアクセスキー を生成し、credentialsファイルを更新
     */
    private void createNewAccessKey() {
        AccessKey accessKey = accessKeyRepository.createAccessKey();
        credentialsRepository.backupCredentials();
        credentialsRepository.updateCredentials(new Credentials(ImmutableMap.of("default", accessKey)));
    }

    /**
     * AWS側に新しいアクセスキーが反映されるまで若干時間がかかるので少しだけ待つ
     * @param timeout 待ち時間
     */
    private void waitForAccessKeyActived(long timeout) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            logger.info(e.getMessage(), e);
        }
    }

    /**
     * 古いアクティブなアクセスキーの無効化
     * @param accessKeyList
     */
    private void invalidateActiveAccessKeys(List<AccessKeyMetadata> accessKeyList) {
        accessKeyRepository.invalidateAccessKeys(
            accessKeyList.stream()
                .filter(a -> StatusType.valueOf(a.getStatus()) == Active)
                .map(AccessKeyMetadata::getAccessKeyId)
                .collect(Collectors.toList()));

    }
}
