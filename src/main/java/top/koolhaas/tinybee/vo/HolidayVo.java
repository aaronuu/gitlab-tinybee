package top.koolhaas.tinybee.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@NoArgsConstructor
@Data
public class HolidayVo {

    /**
     * code : 0
     * type : {"type":2,"name":"中秋节","week":4}
     * holiday : {"holiday":true,"name":"中秋节","wage":3,"date":"2020-10-01","rest":9}
     */

    private int code;
    private TypeBean type;
    private HolidayBean holiday;

    @NoArgsConstructor
    @Data
    public static class TypeBean {
        /**
         * type : 2
         * name : 中秋节
         * week : 4
         */

        private int type;
        private String name;
        private int week;
    }

    @NoArgsConstructor
    @Data
    public static class HolidayBean {
        /**
         * holiday : true
         * name : 中秋节
         * wage : 3
         * date : 2020-10-01
         * rest : 9
         */

        private boolean holiday;
        private String name;
        private int wage;
        private String date;
        private int rest;
    }
}
