package mapper;

import entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    // 查询所有岗位（带部门名称）
    List<Post> selectAllWithDept();

    // 根据ID查询岗位
    Post selectById(Integer postId);

    // 新增岗位
    int insert(Post post);

    // 修改岗位
    int update(Post post);

    // 删除岗位
    int delete(Integer postId);

    // 检查岗位编码是否唯一
    int checkCodeUnique(String postCode);

    // 检查岗位名称是否唯一
    int checkNameUnique(@Param("postName") String postName, @Param("deptId") Integer deptId);
}