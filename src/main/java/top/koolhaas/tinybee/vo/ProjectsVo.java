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
public class ProjectsVo {

    private String ssh_url_to_repo;
    private boolean issues_enabled;
    private boolean only_allow_merge_if_all_discussions_are_resolved;
    private LinksBean _links;
    private boolean request_access_enabled;
    private int open_issues_count;
    private boolean snippets_enabled;
    private String description;
    private String created_at;
    private String import_status;
    private String path;
    private boolean archived;
    private PermissionsBean permissions;
    private String last_activity_at;
    private boolean shared_runners_enabled;
    private String id;
    private boolean container_registry_enabled;
    private boolean lfs_enabled;
    private String visibility;
    private boolean printing_merge_request_link_enabled;
    private String path_with_namespace;
    private boolean resolve_outdated_diff_discussions;
    private boolean merge_requests_enabled;
    private boolean jobs_enabled;
    private String http_url_to_repo;
    private boolean only_allow_merge_if_pipeline_succeeds;
    private String readme_url;
    private String merge_method;
    private String web_url;
    private boolean wiki_enabled;
    private boolean public_jobs;
    private String name;
    private NamespaceBean namespace;
    private int creator_id;
    private String default_branch;
    private String name_with_namespace;
    private int star_count;
    private int forks_count;
    private List<?> tag_list;
    private List<?> shared_with_groups;

    @NoArgsConstructor
    @Data
    public static class LinksBean {
    }

    @NoArgsConstructor
    @Data
    public static class PermissionsBean {
    }

    @NoArgsConstructor
    @Data
    public static class NamespaceBean {

        private String kind;
        private String path;
        private String name;
        private int id;
        private String full_path;
    }
}
