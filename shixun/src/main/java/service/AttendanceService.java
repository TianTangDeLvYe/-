package service;

import entity.Attendance;
import java.util.Date;
import java.util.List;

public interface AttendanceService {
    List<Attendance> getEmployeeAttendance(Integer empId);
    List<Attendance> getDailyAttendance(Date date);
    boolean clockIn(Integer empId, String location);
    boolean clockOut(Integer empId, String location);
}