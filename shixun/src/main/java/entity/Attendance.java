package entity;

import lombok.Data;
import java.util.Date;

@Data
public class Attendance {
    private Integer id;          // 考勤记录ID
    private Integer empId;       // 员工ID
    private Date clockTime;      // 打卡时间
    private String clockType;    // 打卡类型（1-上班，2-下班）
    private String location;     // 打卡位置
    private String status;       // 状态（0-正常，1-迟到，2-早退）

    // 扩展字段（显示用）
    private String empName;      // 员工姓名
    private String deptName;     // 部门名称
}
