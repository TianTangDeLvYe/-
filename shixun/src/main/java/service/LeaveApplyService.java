package service;

import entity.LeaveApply;
import java.util.List;

public interface LeaveApplyService {
    List<LeaveApply> getMyLeaves(Integer empId);
    List<LeaveApply> getPendingApprovals(Integer managerId);
    boolean submitLeave(LeaveApply leave);
    boolean approveLeave(Integer applyId, String status, Integer managerId);
}
