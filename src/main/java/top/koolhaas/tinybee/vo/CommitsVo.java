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
public class CommitsVo {

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
    private List<String> parent_ids;
}
