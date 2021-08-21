package top.koolhaas.tinybee.config.properties;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gitlab.api")
public class GitLabApiProperties {

    @NonNull
    private String hostUrl;

    private String privateToken;
}
