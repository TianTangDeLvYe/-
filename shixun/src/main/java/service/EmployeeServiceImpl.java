package service;


import entity.Employee;
import mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public int countByPostId(Integer postId) {
        return employeeMapper.countByPostId(postId);
    }

    @Override
    public Employee login(String loginName, String password) {
        Employee employee = employeeMapper.selectByLoginName(loginName);
        if (employee != null) {
            // 验证密码：MD5(盐 + 明文密码)
            String encryptedPwd = encryptPassword(password, employee.getSalt());
            if (encryptedPwd.equals(employee.getPassword())) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeMapper.selectAll();
    }

    @Override
    public boolean addEmployee(Employee employee) {
        // 生成随机盐值
        String salt = UUID.randomUUID().toString().replace("-", "");
        employee.setSalt(salt);

        // 加密密码
        employee.setPassword(encryptPassword(employee.getPassword(), salt));

        // 设置创建时间
        employee.setCreateTime(new Date());

        return employeeMapper.insert(employee) > 0;
    }

    // MD5加密方法
    private String encryptPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String input = salt + password;
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }
}
