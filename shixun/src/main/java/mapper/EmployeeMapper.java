package mapper;

import entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    // 根据登录名查询员工（用于登录校验）
    Employee selectByLoginName(String loginName);

    // 查询所有员工（带部门和岗位信息）
    List<Employee> selectAll();

    // 新增员工
    int insert(Employee employee);

    //统计员工数量
    int countByPostId(Integer postId);
}