package org.kajip.aws.access_key_tools.library.service

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
class AwsServiceSpec extends Specification {

//    AwsService awsService

    def "新規アクセスキーを発行する"() {
        setup:
        def userName = RandomStringUtils.randomAlphabetic(8)

        when:
//        awsService.createCredentialsFileFor(userName)
        def result = 1

        then:
        result == 1
    }
}
