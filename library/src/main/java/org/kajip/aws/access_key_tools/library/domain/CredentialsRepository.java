package org.kajip.aws.access_key_tools.library.domain;

public interface CredentialsRepository {

    /**
     * 別ユーザ配布用にcredentialsファイルを生成する
     * @param userName
     * @param credentials
     */
    void saveCredentials(String userName, Credentials credentials);

    /**
     * credentialsファイルを更新する。credentialsファイルがなければ新規作成する
     * @param credentials
     */
    void updateCredentials(Credentials credentials);

    /**
     * 既存のcredentialsファイルのバックアップを生成する
     */
    void backupCredentials();
}
