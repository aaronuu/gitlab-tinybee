package top.koolhaas.tinybee.core.constants.enums;

import lombok.Getter;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Getter
public enum AuthChannelEnum {

    GITLAB("GITLAB", "GITLAB");

    AuthChannelEnum(String channel, String description) {
        this.channel = channel;
        this.description = description;
    }

    private String channel;

    private String description;
}
