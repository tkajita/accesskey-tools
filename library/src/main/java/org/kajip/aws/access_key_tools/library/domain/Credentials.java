package org.kajip.aws.access_key_tools.library.domain;

import com.amazonaws.services.identitymanagement.model.AccessKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class Credentials {
    private final Map<String,AccessKey> accessKeys;
}

