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
public class BranchesVo {

    private boolean developers_can_merge;
    private CommitBean commit;
    private boolean merged;
    private boolean defaultX;
    private boolean protectedX;
    private boolean developers_can_push;
    private String name;
    private boolean can_push;

    @NoArgsConstructor
    @Data
    public static class CommitBean {
        private String author_name;
        private String authored_date;
        private String committer_email;
        private String created_at;
        private String short_id;
        private String title;
        private String message;
        private String committer_name;
        private String committed_date;
        private String author_email;
        private String id;
    }
}
