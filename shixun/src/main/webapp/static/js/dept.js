$(document).ready(function() {
    // 检查登录状态
    if (!api.checkLogin()) return;

    const user = JSON.parse(localStorage.getItem('user'));
    $('#userInfo').text(`欢迎，${user.empName}`);

    // 加载部门树
    loadDeptTree();

    // 添加部门按钮事件
    $('#addDeptBtn').click(function() {
        showAddDeptModal();
    });

    // 退出登录
    $('#logoutBtn').click(function() {
        localStorage.removeItem('user');
        window.location.href = `${api.contextPath}/index.html`;
    });

    // 加载部门树结构
    function loadDeptTree() {
        api.get('/api/dept/tree')
            .then(departments => {
                const treeContainer = $('#deptTree');
                treeContainer.empty();

                if (departments.length === 0) {
                    treeContainer.append('<div class="text-center">暂无部门数据</div>');
                    return;
                }

                const ul = $('<ul>').addClass('list-unstyled');
                departments.forEach(dept => {
                    ul.append(buildDeptNode(dept));
                });
                treeContainer.append(ul);
            })
            .catch(error => {
                console.error('获取部门树失败:', error);
                alert('获取部门数据失败，请重试');
            });
    }

    // 构建部门树节点
    function buildDeptNode(dept) {
        const li = $('<li>').addClass('mb-2');
        const node = $('<div>')
            .addClass('dept-node d-flex justify-content-between align-items-center')
            .html(`
                <span>${dept.deptName}</span>
                <div class="btn-group">
                    <button data-id="${dept.deptId}" class="btn btn-sm btn-info edit-dept">编辑</button>
                    <button data-id="${dept.deptId}" class="btn btn-sm btn-success add-child">添加子部门</button>
                </div>
            `);
        li.append(node);

        if (dept.children && dept.children.length > 0) {
            const childrenUl = $('<ul>').addClass('list-unstyled ms-4');
            dept.children.forEach(child => {
                childrenUl.append(buildDeptNode(child));
            });
            li.append(childrenUl);
        }

        return li;
    }
});