package mapper;

import entity.Department;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DepartmentMapper {
    // 查询所有部门（用于构建树形结构）
    List<Department> selectAll();
    // 新增部门
    int insert(Department dept);
    // 根据ID查询部门（用于计算层级）
    Department selectById(Integer deptId);
}
