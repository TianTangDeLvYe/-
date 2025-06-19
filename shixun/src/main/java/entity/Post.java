package entity;

import lombok.Data;
import java.util.Date;

@Data
public class Post {
    private Integer postId;       // 岗位ID
    private String postCode;      // 岗位编码（唯一）
    private String postName;      // 岗位名称
    private String postLevel;     // 岗位级别（如"高级"）
    private String salaryRange;   // 薪资范围（如"8k-15k"）
    private String description;   // 岗位描述
    private String status;        // 状态（1-正常，0-停用）
    private Date createTime;      // 创建时间
    private Integer deptId;       // 所属部门ID
    private String deptName;      // 所属部门名称（冗余字段，用于展示）
}