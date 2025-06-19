package entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Department {
    private Integer deptId;      // 部门ID
    private String deptName;     // 部门名称
    private Integer parentId;    // 父部门ID（顶级为0）
    private Integer orderNum;    // 排序号
    private String status;       // 状态（1-正常，0-停用）
    private Integer levelDepth;  // 层级深度
    private Date createTime;     // 创建时间
    private List<Department> children; // 子部门（树形结构用）
}