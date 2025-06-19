package mapper;

import entity.LeaveApply;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface LeaveApplyMapper {
    List<LeaveApply> selectByEmpId(Integer empId);
    List<LeaveApply> selectByManagerId(Integer managerId);
    List<LeaveApply> selectAll();
    LeaveApply selectById(Integer applyId);
    int insert(LeaveApply leave);
    int update(LeaveApply leave);
    int updateStatus(Integer applyId, String status, Integer managerId, Date approveTime);
}