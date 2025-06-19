package mapper;

import entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Date;

@Mapper
public interface AttendanceMapper {
    List<Attendance> selectByEmpId(Integer empId);
    List<Attendance> selectByDate(Date date);
    List<Attendance> selectAll();
    Attendance selectLatestByEmpId(Integer empId);
    int insert(Attendance attendance);
}