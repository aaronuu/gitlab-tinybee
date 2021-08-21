package top.koolhaas.tinybee.core.constants;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
public class GitlabUrlConstants {

    private static String PROJECT_URL = "%s/api/v4/projects?private_token=%s&per_page=100&page=%s&order_by=last_activity_at";
    private static String BRANCHE_URL = "%s/api/v4/projects/%s/repository/branches?private_token=%s";
    private static String COMMIT_URL = "%s/api/v4/projects/%s/repository/commits?page=%s&per_page=100&ref_name=%s&since=%s&until=%s&private_token=%s";
    private static String COMMIT_DETAIL_URL = "%s/api/v4/projects/%s/repository/commits/%s?private_token=%s";

    /**
     * 获取 100 个项目 每页最多100个
     *
     * @param url
     * @param page
     * @param token
     * @return
     */
    public static String getProjectUrl(String url, String page, String token) {
        String s = String.format(PROJECT_URL, url, page, token);
        //        log.info("{}", s);
        return s;
    }

    /**
     * 获取项目所有分支
     *
     * @param url
     * @param projectId
     * @param token
     * @return
     */
    public static String getBrancheUrl(String url, String projectId, String token) {
        String s = String.format(BRANCHE_URL, url, projectId, token);
        //        log.info("{}", s);
        return s;
    }

    /**
     * 获取项目提交记录 每页100条
     *
     * @param url
     * @param projectId
     * @param page
     * @param brancheName
     * @param since
     * @param until
     * @param token
     * @return
     */
    public static String getCommitUrl(String url, String projectId, String page, String brancheName, String since, String until, String token) {
        String s = String.format(COMMIT_URL, url, projectId, page, brancheName, since, until, token);
        //        log.info("{}", s);
        return s;
    }

    /**
     * 获取提交详细
     *
     * @param url
     * @param projectId
     * @param commitId
     * @param token
     * @return
     */
    public static String getCommitDetailUrl(String url, String projectId, String commitId, String token) {
        String s = String.format(COMMIT_DETAIL_URL, url, projectId, commitId, token);
        //        log.info("{}", s);
        return s;
    }
}
