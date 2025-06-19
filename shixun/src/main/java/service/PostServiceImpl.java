package service;

import entity.Post;
import mapper.EmployeeMapper;
import mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements service.PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Post> getAllPosts() {
        return postMapper.selectAllWithDept();
    }

    @Override
    public Post getPostById(Integer postId) {
        return postMapper.selectById(postId);
    }

    @Override
    @Transactional
    public boolean addPost(Post post) {
        // 检查编码唯一性
        if (postMapper.checkCodeUnique(post.getPostCode()) > 0) {
            throw new RuntimeException("岗位编码已存在");
        }
        // 检查名称唯一性
        if (postMapper.checkNameUnique(post.getPostName(), post.getDeptId()) > 0) {
            throw new RuntimeException("同部门下岗位名称已存在");
        }
        post.setCreateTime(new Date());
        post.setStatus("1"); // 默认为正常状态
        return postMapper.insert(post) > 0;
    }

    @Override
    @Transactional
    public boolean updatePost(Post post) {
        // 检查名称唯一性（排除自身）
        Post oldPost = postMapper.selectById(post.getPostId());
        if (!oldPost.getPostName().equals(post.getPostName()) ||
                !oldPost.getDeptId().equals(post.getDeptId())) {
            if (postMapper.checkNameUnique(post.getPostName(), post.getDeptId()) > 0) {
                throw new RuntimeException("同部门下岗位名称已存在");
            }
        }
        return postMapper.update(post) > 0;
    }

    @Override
    @Transactional
    public boolean deletePost(Integer postId) {
        int employeeCount = employeeMapper.countByPostId(postId);
        if (employeeCount > 0) {
            throw new RuntimeException("该岗位下存在员工，无法删除");
        }
    return postMapper.delete(postId) > 0;
    }

    @Override
    public boolean isCodeUnique(String postCode) {
        return postMapper.checkCodeUnique(postCode) == 0;
    }

    @Override
    public boolean isNameUnique(String postName, Integer deptId) {
        return postMapper.checkNameUnique(postName, deptId) == 0;
    }
}
