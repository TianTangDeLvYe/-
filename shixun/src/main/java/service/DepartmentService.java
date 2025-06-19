package service;

import entity.Department;
import java.util.List;

public interface DepartmentService {
    // 获取所有部门列表
    List<Department> getAllDepartments();
    // 获取部门树形结构（递归组装）
    List<Department> getDeptTree();
    // 新增部门（自动计算层级）
    boolean addDept(Department dept);
}
