package controller;

import entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DeptController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/tree")
    public List<Department> getDeptTree() {
        return departmentService.getDeptTree();
    }

    @PostMapping("/add")
    public boolean addDept(@RequestBody Department dept) {
        return departmentService.addDept(dept);
    }
}