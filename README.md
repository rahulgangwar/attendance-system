# ms-domain-exercise

## Mvn command for sonar analysis

### EmployeeService
mvn clean verify sonar:sonar \
-Dsonar.projectKey=EmployeeService \
-Dsonar.projectName='EmployeeService' \
-Dsonar.host.url=http://localhost:9000 \
-Dsonar.token=sqp_ec0ad8782aa5509c6820b7bda964d261a6cf90f3

### AttendanceService
mvn clean verify sonar:sonar \
-Dsonar.projectKey=AttendanceService \
-Dsonar.projectName='AttendanceService' \
-Dsonar.host.url=http://localhost:9000 \
-Dsonar.token=sqp_7d560e1b4784818f4510e7725ecfa37180a92ffc
