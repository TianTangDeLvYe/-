package service;


import entity.Post;
import java.util.List;

public interface PostService {
    // 获取所有岗位（带部门名称）
    List<Post> getAllPosts();

    // 根据ID获取岗位
    Post getPostById(Integer postId);

    // 新增岗位
    boolean addPost(Post post);

    // 修改岗位
    boolean updatePost(Post post);

    // 删除岗位
    boolean deletePost(Integer postId);

    // 检查岗位编码是否唯一
    boolean isCodeUnique(String postCode);

    // 检查岗位名称是否唯一
    boolean isNameUnique(String postName, Integer deptId);
}