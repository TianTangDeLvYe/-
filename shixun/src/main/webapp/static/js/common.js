// 公共API调用函数
const api = {
    // 获取上下文路径（需在HTML中通过JSP动态设置）
    contextPath: '<%= request.getContextPath() %>',

    // GET请求
    get(url, params = {}) {
        const query = new URLSearchParams(params).toString();
        return fetch(`${this.contextPath}${url}?${query}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token') || ''
            }
        })
            .then(response => this._handleResponse(response));
    },

    // POST请求
    post(url, data = {}) {
        return fetch(`${this.contextPath}${url}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token') || ''
            },
            body: JSON.stringify(data)
        })
            .then(response => this._handleResponse(response));
    },

    // 表单数据POST请求
    postForm(url, data = {}) {
        const formData = new FormData();
        for (const key in data) {
            formData.append(key, data[key]);
        }
        return fetch(`${this.contextPath}${url}`, {
            method: 'POST',
            headers: {
                'Authorization': localStorage.getItem('token') || ''
            },
            body: formData
        })
            .then(response => this._handleResponse(response));
    },

    // 处理响应
    _handleResponse(response) {
        if (!response.ok) {
            throw new Error(`HTTP错误! 状态码: ${response.status}`);
        }
        return response.json();
    },

    // 检查登录状态
    checkLogin() {
        const user = localStorage.getItem('user');
        if (!user) {
            window.location.href = `${this.contextPath}/index.html`;
            return false;
        }
        return true;
    },

    // 格式化日期
    formatDate(dateStr) {
        if (!dateStr) return '';
        const date = new Date(dateStr);
        return date.toLocaleString();
    },

    // 获取状态对应的徽章类
    getBadgeClass(status) {
        switch(status) {
            case '0': return 'badge-success';
            case '1': return 'badge-warning';
            case '2': return 'badge-danger';
            default: return 'badge-secondary';
        }
    }
};