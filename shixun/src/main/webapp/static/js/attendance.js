$(document).ready(function() {
    // 检查登录状态
    if (!api.checkLogin()) return;

    const user = JSON.parse(localStorage.getItem('user'));
    $('#userInfo').text(`欢迎，${user.empName}`);
    $('#empId').val(user.empId).prop('disabled', true); // 自动填充当前用户ID

    // 加载考勤记录
    loadAttendanceHistory(user.empId);

    // 退出登录
    $('#logoutBtn').click(function() {
        localStorage.removeItem('user');
        window.location.href = `${api.contextPath}/index.html`;
    });

    // 上班打卡
    $('#clockInBtn').click(function() {
        const empId = $('#empId').val();
        const location = $('#location').val().trim();

        if (!location) {
            alert('打卡位置不能为空');
            return;
        }

        $(this).html('<span class="loading"></span> 打卡中...').prop('disabled', true);

        api.postForm('/api/attendance/clockIn', {
            empId: empId,
            location: location
        })
            .then(result => {
                if (result) {
                    alert('上班打卡成功');
                    loadAttendanceHistory(empId);
                } else {
                    alert('打卡失败，请重试');
                }
            })
            .catch(error => {
                console.error('打卡失败:', error);
                alert('打卡失败，请稍后再试');
            })
            .finally(() => {
                $(this).html('上班打卡').prop('disabled', false);
            });
    });

    // 下班打卡
    $('#clockOutBtn').click(function() {
        const empId = $('#empId').val();
        const location = $('#location').val().trim();

        if (!location) {
            alert('打卡位置不能为空');
            return;
        }

        $(this).html('<span class="loading"></span> 打卡中...').prop('disabled', true);

        api.postForm('/api/attendance/clockOut', {
            empId: empId,
            location: location
        })
            .then(result => {
                if (result) {
                    alert('下班打卡成功');
                    loadAttendanceHistory(empId);
                } else {
                    alert('打卡失败，请重试');
                }
            })
            .catch(error => {
                console.error('打卡失败:', error);
                alert('打卡失败，请稍后再试');
            })
            .finally(() => {
                $(this).html('下班打卡').prop('disabled', false);
            });
    });

    // 加载考勤记录
    function loadAttendanceHistory(empId) {
        api.get('/api/attendance/my', { empId: empId })
            .then(attendances => {
                const tableBody = $('#attendanceHistoryBody');
                tableBody.empty();

                if (attendances.length === 0) {
                    tableBody.append('<tr><td colspan="4" class="text-center">暂无考勤记录</td></tr>');
                    return;
                }

                attendances.forEach(att => {
                    const statusText = att.status === '0' ? '正常' :
                        att.status === '1' ? '迟到' : '早退';
                    const typeText = att.clockType === '1' ? '上班' : '下班';
                    const badgeClass = api.getBadgeClass(att.status);

                    const row = $('<tr>');
                    row.html(`
          <td>${api.formatDate(att.clockTime)}</td>
          <td>${typeText}</td>
          <td>${att.location}</td>
          <td><span class="badge ${badgeClass}">${statusText}</span></td>
        `);
                    tableBody.append(row);
                });
            })
            .catch(error => {
                console.error('获取考勤记录失败:', error);
                alert('获取考勤数据失败，请重试');
            });
    }
});