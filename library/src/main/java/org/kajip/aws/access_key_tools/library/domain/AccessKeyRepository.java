package org.kajip.aws.access_key_tools.library.domain;

import com.amazonaws.services.identitymanagement.model.AccessKey;
import com.amazonaws.services.identitymanagement.model.AccessKeyMetadata;

import java.util.List;

public interface AccessKeyRepository {

    /**
     * 自分のアクセスキーを新規作成する
     * @return
     */
    AccessKey createAccessKey();

    /**
     * 指定されたユーザのアクセスキーを新規作成する
     * @param userName ユーザー
     * @return
     */
    AccessKey createAccessKey(String userName);

    /**
     * 現在の自分のアクセスキー一覧を取得する
     * @return
     */
    List<AccessKeyMetadata> listAccessKeys();

    /**
     * 指定されたアクセスキーを削除する
     * @param accessKeyIds 対象のアクセスキーIDのリスト
     */
    void deleteAccessKeys(List<String> accessKeyIds);

    /**
     * 指定されたアクセスキーを無効化する
     * @param accessKeyIds 対象のアクセスキーIDのリスト
     */
    void invalidateAccessKeys(List<String> accessKeyIds);
}
