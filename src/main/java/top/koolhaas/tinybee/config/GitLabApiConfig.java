package top.koolhaas.tinybee.config;

import top.koolhaas.tinybee.config.properties.GitLabApiProperties;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@Configuration
public class GitLabApiConfig {

    /**
     * 初始化 GitLabApi
     *
     * @param gitLabApiProperties
     * @return
     */
    @Bean
    public GitLabApi gitLabApi(GitLabApiProperties gitLabApiProperties) {
        return new GitLab4JApiBuilder(gitLabApiProperties.getHostUrl()).build(gitLabApiProperties.getPrivateToken());
    }

    /**
     * GitLab4J自定义构建器来封装GitLabApi创建
     */
    private class GitLab4JApiBuilder {

        @NonNull
        private String hostUrl;

        private GitLab4JApiBuilder(String hostUrl) {
            this.hostUrl = hostUrl;
        }

        /**
         * 构建
         *
         * @param privateToken
         * @return
         */
        private GitLabApi build(@NonNull String privateToken) {
            return new GitLabApi(hostUrl, privateToken);
        }
    }
}
