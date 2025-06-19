package controller;

import entity.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.AttendanceService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/list")
    public List<Attendance> getDailyAttendance(@RequestParam Date date) {
        return attendanceService.getDailyAttendance(date);
    }

    @GetMapping("/my")
    public List<Attendance> getEmployeeAttendance(@RequestParam Integer empId) {
        return attendanceService.getEmployeeAttendance(empId);
    }

    @PostMapping("/clockIn")
    public boolean clockIn(@RequestParam Integer empId, @RequestParam String location) {
        return attendanceService.clockIn(empId, location);
    }

    @PostMapping("/clockOut")
    public boolean clockOut(@RequestParam Integer empId, @RequestParam String location) {
        return attendanceService.clockOut(empId, location);
    }
}