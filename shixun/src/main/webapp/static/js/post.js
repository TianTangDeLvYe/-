$(document).ready(function() {
    // 检查登录状态
    if (!api.checkLogin()) return;

    const user = JSON.parse(localStorage.getItem('user'));
    $('#userInfo').text(`欢迎，${user.empName}`);

    // 加载岗位列表
    loadPostList();

    // 添加岗位按钮事件
    $('#addPostBtn').click(function() {
        // 显示添加岗位的模态框
        showAddPostModal();
    });

    // 退出登录
    $('#logoutBtn').click(function() {
        localStorage.removeItem('user');
        window.location.href = `${api.contextPath}/index.html`;
    });

    // 加载岗位列表
    function loadPostList() {
        api.get('/api/post/list')
            .then(posts => {
                const tableBody = $('#postTableBody');
                tableBody.empty();

                if (posts.length === 0) {
                    tableBody.append('<tr><td colspan="6" class="text-center">暂无岗位数据</td></tr>');
                    return;
                }

                posts.forEach(post => {
                    const row = $('<tr>');
                    row.html(`
                        <td>${post.postId}</td>
                        <td>${post.postCode}</td>
                        <td>${post.postName}</td>
                        <td>${post.deptName}</td>
                        <td>${post.salaryMin} - ${post.salaryMax}</td>
                        <td>
                            <button data-id="${post.postId}" class="btn btn-sm btn-info edit-btn">编辑</button>
                            <button data-id="${post.postId}" class="btn btn-sm btn-danger delete-btn">删除</button>
                        </td>
                    `);
                    tableBody.append(row);
                });

                // 绑定编辑和删除事件
                bindPostEvents();
            })
            .catch(error => {
                console.error('获取岗位列表失败:', error);
                alert('获取岗位数据失败，请重试');
            });
    }

    // 绑定岗位编辑和删除事件
    function bindPostEvents() {
        $('.edit-btn').click(function() {
            const postId = $(this).data('id');
            editPost(postId);
        });

        $('.delete-btn').click(function() {
            const postId = $(this).data('id');
            if (confirm('确定要删除该岗位吗？')) {
                deletePost(postId);
            }
        });
    }
});