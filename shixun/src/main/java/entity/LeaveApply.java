package entity;

import lombok.Data;
import java.util.Date;
import java.math.BigDecimal;

@Data
public class LeaveApply {
    private Integer applyId;     // 申请ID
    private Integer empId;       // 申请人ID
    private Integer deptId;      // 申请人部门ID
    private Integer managerId;   // 审批人ID
    private String leaveType;    // 请假类型
    private Date startTime;      // 开始时间
    private Date endTime;        // 结束时间
    private BigDecimal days;     // 请假天数
    private String reason;       // 请假事由
    private String status;       // 状态
    private Date approveTime;    // 审批时间

    // 扩展字段（显示用）
    private String empName;      // 申请人姓名
    private String deptName;     // 部门名称
    private String managerName;  // 审批人姓名
}