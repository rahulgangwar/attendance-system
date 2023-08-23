package com.example.enums;

public enum AttendanceType {
    ABSENT, // Less than 4 hrs
    HALF_DAY, // Between 4 and 8 hrs
    PRESENT; // More than 8 hrs

    public static AttendanceType getAttendanceTypeFromHrs(long totalHrs) {
        int hrsBase = (int) totalHrs / 4;
        switch (hrsBase) {
            case 0:
                return AttendanceType.ABSENT;
            case 1:
                return AttendanceType.HALF_DAY;
            default:
                return AttendanceType.PRESENT;
        }
    }
}
