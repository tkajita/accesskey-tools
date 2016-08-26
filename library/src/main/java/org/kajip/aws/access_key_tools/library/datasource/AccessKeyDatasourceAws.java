package org.kajip.aws.access_key_tools.library.datasource;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import com.amazonaws.services.identitymanagement.model.*;
import org.kajip.aws.access_key_tools.library.domain.AccessKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("default")
public class AccessKeyDatasourceAws implements AccessKeyRepository {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public AccessKey createAccessKey() {
        AmazonIdentityManagement iam = getClient();
        return iam.createAccessKey().getAccessKey();
    }

    @Override
    public AccessKey createAccessKey(String userName) {
        AmazonIdentityManagement iam = getClient();
        return iam.createAccessKey(
            new CreateAccessKeyRequest().withUserName(userName)).getAccessKey();
    }

    @Override
    public List<AccessKeyMetadata> listAccessKeys() {
        AmazonIdentityManagement iam = getClient();
        return iam.listAccessKeys().getAccessKeyMetadata();
    }

    @Override
    public void deleteAccessKeys(List<String> accessKeyIds) {
        logger.info("{}", accessKeyIds);

        AmazonIdentityManagement iam = getClient();
        accessKeyIds.forEach(accessKeyId ->
            iam.deleteAccessKey(new DeleteAccessKeyRequest().withAccessKeyId(accessKeyId)));
    }

    @Override
    public void invalidateAccessKeys(List<String> accessKeyIds) {
        logger.info("{}", accessKeyIds);

        AmazonIdentityManagement iam = getClient();
        accessKeyIds.forEach(accessKeyId ->
            iam.updateAccessKey(new UpdateAccessKeyRequest(accessKeyId, StatusType.Inactive)));
    }


    private AmazonIdentityManagement getClient() {
        return new AmazonIdentityManagementClient(new DefaultAWSCredentialsProviderChain());
    }
}
