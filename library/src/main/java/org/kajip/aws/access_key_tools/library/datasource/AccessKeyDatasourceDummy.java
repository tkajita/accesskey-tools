package org.kajip.aws.access_key_tools.library.datasource;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AccessKey;
import com.amazonaws.services.identitymanagement.model.AccessKeyMetadata;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.StatusType;
import org.apache.commons.lang3.RandomStringUtils;
import org.kajip.aws.access_key_tools.library.domain.AccessKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Profile("test")
public class AccessKeyDatasourceDummy implements AccessKeyRepository {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public AccessKey createAccessKey() {
        return _createAccessKey();
    }

    @Override
    public AccessKey createAccessKey(String userName) {
        return _createAccessKey().withUserName(userName);
    }

    private AccessKey _createAccessKey() {
        return new AccessKey()
            .withAccessKeyId(RandomStringUtils.randomAlphanumeric(10))
            .withSecretAccessKey(RandomStringUtils.randomAlphanumeric(20))
            .withStatus(StatusType.Active)
            .withCreateDate(new Date());
    }

    @Override
    public List<AccessKeyMetadata> listAccessKeys() {
        return Arrays.asList();
    }

    @Override
    public void deleteAccessKeys(List<String> accessKeyIds) {
        logger.info("{}", accessKeyIds);
    }

    @Override
    public void invalidateAccessKeys(List<String> accessKeyIds) {
        logger.info("{}", accessKeyIds);
    }
}
