$(document).ready(function() {
    // 登录按钮点击事件
    $('#loginBtn').click(function() {
        const loginName = $('#loginName').val().trim();
        const password = $('#password').val();

        if (!loginName || !password) {
            alert('用户名和密码不能为空');
            return;
        }

        // 显示加载状态
        $(this).html('<span class="loading"></span> 登录中...').prop('disabled', true);

        api.postForm('/api/employee/login', {
            loginName: loginName,
            password: password
        })
            .then(employee => {
                if (employee.empId) {
                    // 存储用户信息
                    localStorage.setItem('user', JSON.stringify(employee));
                    // 跳转到员工管理页
                    window.location.href = `${api.contextPath}/employee.html`;
                } else {
                    alert('用户名或密码错误');
                }
            })
            .catch(error => {
                console.error('登录失败:', error);
                alert('登录失败，请稍后再试');
            })
            .finally(() => {
                // 恢复按钮状态
                $('#loginBtn').html('登录').prop('disabled', false);
            });
    });
});