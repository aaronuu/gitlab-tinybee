package top.koolhaas.tinybee.core.util;

import com.baomidou.mybatisplus.core.toolkit.Sequence;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
public final class IdGenerator {

    private static Sequence sequence = new Sequence();

    public static String id() {
        return String.valueOf(sequence.nextId());
    }
}
