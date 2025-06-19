$(document).ready(function() {
    // 检查登录状态
    if (!api.checkLogin()) return;

    const user = JSON.parse(localStorage.getItem('user'));
    $('#userInfo').text(`欢迎，${user.empName}`);

    // 加载员工列表
    loadEmployeeList();

    // 添加员工按钮事件
    $('#addEmployeeBtn').click(function() {
        window.location.href = `${api.contextPath}/add-employee.html`; // 实际需创建添加页面
    });

    // 退出登录
    $('#logoutBtn').click(function() {
        localStorage.removeItem('user');
        window.location.href = `${api.contextPath}/index.html`;
    });

    // 加载员工列表
    function loadEmployeeList() {
        api.get('/api/employee/list')
            .then(employees => {
                const tableBody = $('#employeeTableBody');
                tableBody.empty();

                if (employees.length === 0) {
                    tableBody.append('<tr><td colspan="6" class="text-center">暂无员工数据</td></tr>');
                    return;
                }

                employees.forEach(emp => {
                    const row = $('<tr>');
                    row.html(`
          <td>${emp.empId}</td>
          <td>${emp.empName}</td>
          <td>${emp.deptName}</td>
          <td>${emp.postName}</td>
          <td>${emp.phone}</td>
          <td>
            <button data-id="${emp.empId}" class="btn btn-sm btn-info edit-btn">编辑</button>
            <button data-id="${emp.empId}" class="btn btn-sm btn-danger delete-btn">删除</button>
          </td>
        `);
                    tableBody.append(row);
                });

                // 绑定编辑和删除事件
                bindEditDeleteEvents();
            })
            .catch(error => {
                console.error('获取员工列表失败:', error);
                alert('获取员工数据失败，请重试');
            });
    }

    // 绑定编辑和删除事件
    function bindEditDeleteEvents() {
        $('.edit-btn').click(function() {
            const empId = $(this).data('id');
            alert(`编辑员工ID: ${empId}`);
            // 实际开发中跳转到编辑页面
        });

        $('.delete-btn').click(function() {
            const empId = $(this).data('id');
            if (confirm('确定要删除该员工吗？')) {
                api.post(`/api/employee/delete/${empId}`, {})
                    .then(result => {
                        if (result) {
                            alert('删除成功');
                            loadEmployeeList();
                        } else {
                            alert('删除失败，该岗位下存在员工');
                        }
                    })
                    .catch(error => {
                        console.error('删除员工失败:', error);
                        alert('删除失败，请重试');
                    });
            }
        });
    }
});