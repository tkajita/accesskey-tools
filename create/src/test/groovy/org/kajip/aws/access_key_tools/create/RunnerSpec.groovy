package org.kajip.aws.access_key_tools.create

import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
class RunnerSpec extends Specification {

    def "ユーザを登録する"() {
        setup:
        def request = 1

        when:
        def result = request + 1

        then:
        result == 2
    }
}
