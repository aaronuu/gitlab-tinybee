package top.koolhaas.tinybee.generator.run;

import lombok.Builder;
import lombok.Data;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Data
@Builder
public class TransferCodeConfig {
    private String outPath4VO;
    private String outPath4Transfer;
    private String outPath4Controller;
    private String package4VO;
    private String package4Transfer;
    private String vuePath;
    private String appName;
    private boolean entityLombokModel;
}
