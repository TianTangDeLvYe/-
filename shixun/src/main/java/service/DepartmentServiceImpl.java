package service;

import entity.Department;
import mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.selectAll();
    }

    @Override
    public List<Department> getDeptTree() {
        List<Department> allDepts = departmentMapper.selectAll();
        List<Department> rootDepts = new ArrayList<>();
        // 递归组装树形结构（父ID=0为根节点）
        for (Department dept : allDepts) {
            if (dept.getParentId() == 0) {
                rootDepts.add(dept);
                buildChildren(dept, allDepts);
            }
        }
        return rootDepts;
    }

    // 递归构建子部门
    private void buildChildren(Department parent, List<Department> allDepts) {
        for (Department dept : allDepts) {
            if (dept.getParentId().equals(parent.getDeptId())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(dept);
                buildChildren(dept, allDepts); // 递归子节点
            }
        }
    }

    @Override
    public boolean addDept(Department dept) {
        // 计算层级：父部门levelDepth +1（父部门为0时，层级为1）
        if (dept.getParentId() != 0) {
            Department parent = departmentMapper.selectById(dept.getParentId());
            dept.setLevelDepth(parent.getLevelDepth() + 1);
        } else {
            dept.setLevelDepth(1);
        }
        return departmentMapper.insert(dept) > 0;
    }
}