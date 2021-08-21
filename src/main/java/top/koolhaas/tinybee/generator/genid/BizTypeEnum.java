package top.koolhaas.tinybee.generator.genid;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum BizTypeEnum {

    /**
     * 当事人
     */
    CIF_PARTY("0000", "CIF_PARTY", "当事人", "当事人"),
    ;

    /**
     * 代码
     */
    @Getter
    private final String code;
    /**
     * 英文名
     */
    @Getter
    private final String englishName;
    /**
     * 中文名
     */
    @Getter
    private final String chineseName;
    /**
     * 描述
     */
    @Getter
    private final String description;

    /**
     * 构造函数。
     *
     * @param code        代码
     * @param englishName 英文名
     * @param chineseName 中文名
     * @param description 描述
     */
    BizTypeEnum(String code, String englishName, String chineseName, String description) {
        this.code = code;
        this.englishName = englishName;
        this.chineseName = chineseName;
        this.description = description;
    }

    /**
     * 根据编码查询枚举。
     *
     * @param code 编码。
     * @return 枚举。
     */
    public static BizTypeEnum getByCode(String code) {
        for (BizTypeEnum value : BizTypeEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
