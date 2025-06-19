$(document).ready(function() {
    // 检查登录状态
    if (!api.checkLogin()) return;

    const user = JSON.parse(localStorage.getItem('user'));
    $('#userInfo').text(`欢迎，${user.empName}`);

    // 加载请假记录
    loadLeaveRecords();

    // 提交请假申请按钮事件
    $('#submitLeaveBtn').click(function() {
        submitLeaveApply();
    });

    // 退出登录
    $('#logoutBtn').click(function() {
        localStorage.removeItem('user');
        window.location.href = `${api.contextPath}/index.html`;
    });

    // 加载请假记录
    function loadLeaveRecords() {
        api.get('/api/leave/myLeaves', { empId: user.empId })
            .then(leaves => {
                const tableBody = $('#leaveTableBody');
                tableBody.empty();

                if (leaves.length === 0) {
                    tableBody.append('<tr><td colspan="7" class="text-center">暂无请假记录</td></tr>');
                    return;
                }

                leaves.forEach(leave => {
                    const statusText = getLeaveStatus(leave.status);
                    const badgeClass = getStatusBadgeClass(leave.status);

                    const row = $('<tr>');
                    row.html(`
                        <td>${leave.leaveType}</td>
                        <td>${api.formatDate(leave.startTime)}</td>
                        <td>${api.formatDate(leave.endTime)}</td>
                        <td>${leave.days}</td>
                        <td>${leave.reason}</td>
                        <td><span class="badge ${badgeClass}">${statusText}</span></td>
                        <td>${leave.approveTime ? api.formatDate(leave.approveTime) : '-'}</td>
                    `);
                    tableBody.append(row);
                });
            })
            .catch(error => {
                console.error('获取请假记录失败:', error);
                alert('获取请假记录失败，请重试');
            });
    }

    // 获取请假状态文本
    function getLeaveStatus(status) {
        const statusMap = {
            '0': '待审批',
            '1': '已批准',
            '2': '已拒绝'
        };
        return statusMap[status] || '未知';
    }

    // 获取状态徽章样式
    function getStatusBadgeClass(status) {
        const classMap = {
            '0': 'badge-warning',
            '1': 'badge-success',
            '2': 'badge-danger'
        };
        return classMap[status] || 'badge-secondary';
    }
});