package top.koolhaas.tinybee.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@NoArgsConstructor
@Data
public class CommitDetailVo {

    private String author_name;
    private String authored_date;
    private String committer_email;
    private String created_at;
    private String short_id;
    private String title;
    private String message;
    private String committer_name;
    private String committed_date;
    private StatsBean stats;
    private int project_id;
    private String author_email;
    private Object last_pipeline;
    private String id;
    private Object status;
    private List<String> parent_ids;

    @NoArgsConstructor
    @Data
    public static class StatsBean {

        private int additions;
        private int deletions;
        private int total;
    }
}
