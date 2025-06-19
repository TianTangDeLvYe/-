package service;

import entity.Attendance;
import mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public List<Attendance> getEmployeeAttendance(Integer empId) {
        return attendanceMapper.selectByEmpId(empId);
    }

    @Override
    public List<Attendance> getDailyAttendance(Date date) {
        return attendanceMapper.selectByDate(date);
    }

    @Override
    public boolean clockIn(Integer empId, String location) {
        Attendance attendance = new Attendance();
        attendance.setEmpId(empId);
        attendance.setClockTime(new Date());
        attendance.setClockType("1");
        attendance.setLocation(location);

        // 判断是否迟到（示例：9点前为正常）
        Calendar cal = Calendar.getInstance();
        cal.setTime(attendance.getClockTime());
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        attendance.setStatus(hour < 9 ? "0" : "1");

        return attendanceMapper.insert(attendance) > 0;
    }

    @Override
    public boolean clockOut(Integer empId, String location) {
        Attendance attendance = new Attendance();
        attendance.setEmpId(empId);
        attendance.setClockTime(new Date());
        attendance.setClockType("2");
        attendance.setLocation(location);

        // 判断是否早退（示例：18点后为正常）
        Calendar cal = Calendar.getInstance();
        cal.setTime(attendance.getClockTime());
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        attendance.setStatus(hour >= 18 ? "0" : "2");

        return attendanceMapper.insert(attendance) > 0;
    }
}