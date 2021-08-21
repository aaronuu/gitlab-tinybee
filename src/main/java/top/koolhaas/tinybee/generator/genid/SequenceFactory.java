package top.koolhaas.tinybee.generator.genid;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Component
public class SequenceFactory {

    private Sequence sequence = new Sequence();

    public String generate(BizTypeEnum bizType) {
        /** 生成流水号序列 */
        StringBuilder sub = new StringBuilder(FigureConstants.FORTY);
        /** 8位日期*/
        sub.append(OneDateUtil.formatSimple(new Date()));
        /** 4位事件码*/
        sub.append(bizType.getCode());
        try {
            long seq = sequence.nextId();
            return String.format("%s%019d", sub, seq);
        } catch (Exception e) {
        }
        return null;
    }

    public String generatePaymentSq(BizTypeEnum bizType) {
        /** 生成流水号序列 */
        StringBuilder sub = new StringBuilder(FigureConstants.EIGHTEEN);
        /** 6位日期*/
        sub.append(OneDateUtil.formatSimpleYear(new Date()));
        /** 事件码*/
        sub.append(bizType.getCode());
        try {
            long seq = sequence.nextId();
            return String.format("%s%019d", sub, seq);
        } catch (Exception e) {
        }
        return null;
    }
}
