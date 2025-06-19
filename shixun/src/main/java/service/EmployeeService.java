package service;

import entity.Employee;
import java.util.List;

public interface EmployeeService {
    // 员工登录校验
    Employee login(String loginName, String password);

    // 获取所有员工
    List<Employee> getAllEmployees();

    // 新增员工
    boolean addEmployee(Employee employee);

    //统计员工数量
    int countByPostId(Integer postId);
}