package entity;

import lombok.Data;
import java.util.Date;

@Data
public class Employee {
    private Integer empId;      // 员工ID
    private String empName;     // 姓名
    private Integer deptId;     // 所属部门ID
    private Integer postId;     // 岗位ID
    private String loginName;   // 登录账号
    private String password;    // 加密密码
    private String phone;       // 手机号
    private Date createTime;    // 入职时间
    private String salt;        // 盐值（加密用）
    private String deptName;
    private String postName;    // 岗位名称
}