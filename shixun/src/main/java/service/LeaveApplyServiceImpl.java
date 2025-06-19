package service;

import entity.LeaveApply;
import mapper.LeaveApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class LeaveApplyServiceImpl implements LeaveApplyService {

    @Autowired
    private LeaveApplyMapper leaveApplyMapper;

    @Override
    public List<LeaveApply> getMyLeaves(Integer empId) {
        return leaveApplyMapper.selectByEmpId(empId);
    }

    @Override
    public List<LeaveApply> getPendingApprovals(Integer managerId) {
        return leaveApplyMapper.selectByManagerId(managerId);
    }

    @Override
    public boolean submitLeave(LeaveApply leave) {
        leave.setStatus("0"); // 待审批
        return leaveApplyMapper.insert(leave) > 0;
    }

    @Override
    public boolean approveLeave(Integer applyId, String status, Integer managerId) {
        return leaveApplyMapper.updateStatus(applyId, status, managerId, new Date()) > 0;
    }
}
