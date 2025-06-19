package controller;

import entity.LeaveApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.LeaveApplyService;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveApplyController {

    @Autowired
    private LeaveApplyService leaveApplyService;

    @GetMapping("/myLeaves")
    public List<LeaveApply> getMyLeaves(@RequestParam Integer empId) {
        return leaveApplyService.getMyLeaves(empId);
    }

    @GetMapping("/pending")
    public List<LeaveApply> getPendingApprovals(@RequestParam Integer managerId) {
        return leaveApplyService.getPendingApprovals(managerId);
    }

    @PostMapping("/submit")
    public boolean submitLeave(@RequestBody LeaveApply leave) {
        return leaveApplyService.submitLeave(leave);
    }

    @PostMapping("/approve/{applyId}")
    public boolean approveLeave(@PathVariable Integer applyId, @RequestParam String status, @RequestParam Integer managerId) {
        return leaveApplyService.approveLeave(applyId, status, managerId);
    }
}