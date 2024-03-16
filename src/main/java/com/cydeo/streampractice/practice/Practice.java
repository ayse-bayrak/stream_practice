package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import com.sun.source.tree.ContinueTree;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }
    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    } // given example

    // Display all the countries
    public static List<Country> getAllCountries() {
        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
        return jobService.readAll();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {
        return locationService.readAll();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        return regionService.readAll();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        return getAllEmployees().stream()
                .map(Employee::getFirstName)
                .toList(); // .collect(Collectors.toList());

//         other solution
//         getAllEmployees().stream()
//        .collect(Collectors.mapping(Employee::getFirstName, Collectors.toList()));

    } //I am putting Employee in, I need first name of that Employee out... use map
    // I am gonna give you one object and i want to give me some another object
    // put something get something else
    //give something take something else ==> Function Interface

    // Display all the countries' names
    public static List<String> getAllCountryNames() {

        return getAllCountries().stream()
                .map(Country::getCountryName)
                .toList(); // .collect(Collectors.toList());
    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {
//        return getAllDepartments().stream()  //  O(2n) ==> O(n)
//                .map(Department::getManager) // n
//                .map(Employee::getFirstName) // n
//                .toList(); // .collect(Collectors.toList());
        return getAllDepartments().stream()
                .map(department -> department.getManager().getFirstName()) // O(n) // chaining
                .toList(); // .collect(Collectors.toList());
    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        return getAllDepartments().stream()
              .filter(p-> p.getManager().getFirstName().equalsIgnoreCase("steven"))
                .toList(); // .collect(Collectors.toList());
    }
    // we start Department and at the end need to Department, so we don't use map (because return different),
    // we use filter

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
        return getAllDepartments().stream()
                .filter(p-> p.getLocation().getPostalCode().equals("98199"))
                .toList(); // .collect(Collectors.toList());
    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
        //TODO Implement the method
        return getAllDepartments().stream()
                .filter(p->p.getDepartmentName().equals("IT"))
                .map(Department::getLocation)
                .map(Location::getCountry)
                .map(Country::getRegion)
                .findFirst().orElseThrow(); // instead of get() and isPresent() we can use orElseThrow()

 /*       findAny()
         Returns an Optional describing some element of the stream,
         Optional is special class to get rid of nullPointerException.
        get() or orElseThrow() method return actual data type in the Stream
         or an empty Optional if the stream is empty.*/

    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        //TODO Implement the method
        return getAllDepartments().stream()
                .filter(p->p.getLocation().getCountry().getRegion().getRegionName().equals("Europe"))
                .toList();
    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
        //TODO Implement the method

     return  !getAllEmployees().stream()
            .anyMatch(employee -> employee.getSalary()<1000);
//             getAllEmployees().stream()
//                .noneMatch(employee -> employee.getSalary()>1000);

//                getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .allMatch(p -> p >1000);

//        getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .noneMatch(p -> p < 1000);
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(p->p.getDepartment().getDepartmentName().equals("IT"))
                .allMatch(p -> p.getSalary() >2000);
    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(p->p.getSalary()<5000)
                .toList();
    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(p->p.getSalary()<7000 && p.getSalary()>6000)
                .toList();
    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(p->p.getFirstName().equalsIgnoreCase("douglas") &&p.getLastName().equalsIgnoreCase("grant"))
                .findFirst().orElseThrow().getSalary();// instead of get() and isPresent() we can use orElseThrow()

        //findAny()
        //Returns an Optional describing some element of the stream,
        // or an empty Optional if the stream is empty.
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
//        return getAllEmployees().stream()
//                .max(Comparator.comparing(Employee::getSalary))
//                .orElseThrow().getSalary(); // instead of get() and isPresent() we can use orElseThrow()

//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .collect(Collectors.maxBy(Comparator.comparing(Long::longValue)))
//                .get();

        return getAllEmployees().stream()
                .mapToLong(Employee::getSalary)
                .max().orElseThrow();
    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
        return getAllEmployees().stream()
                .filter(e -> {
                    try {
                        return e.getSalary().equals(getMaxSalary());
                    } catch (Exception ex) {
                        throw new RuntimeException();
                    }
                })
                .toList();
    }
//              .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .limit(1)
//                .toList();
    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        //TODO Implement the method
        return getAllEmployees().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(1)
                .map(Employee::getJob)
                .findAny().orElseThrow();
        //  .findAny().orElseThrow() together returns original Data Type within in the Stream in last line
    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(p-> p.getDepartment()
                        .getLocation()
                        .getCountry()
                        .getRegion()
                        .getRegionName().equalsIgnoreCase("Americas"))
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(1)
                .map(Employee::getSalary)
                .findAny().orElseThrow();
    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        //TODO Implement the method

        long maxSalaryNumber = getMaxSalaryEmployee().size();
        return getAllEmployees().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())  // sorts in descending order
                .skip(maxSalaryNumber)
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow().getSalary();
//        return
//                getAllEmployees().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .skip(1)
//                .findAny().orElseThrow().getSalary(); // my solution is wrong

    }

    // Display the employee(s) who gets the second maximum salary

    public static List<Employee> getSecondMaxSalaryEmployee() {
        //TODO Implement the method
       return getAllEmployees().stream()
               .filter(p-> {
                   try {
                       return p.getSalary().equals(getSecondMaxSalary());
                   } catch (Exception e) {
                       throw new RuntimeException(e);
                   }
               })
                .toList();
//        return   getAllEmployees().stream()
//                .filter(employee -> employee.getSalary().equals(secondMaxSalary))
//                .collect(Collectors.toList());
    }


    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        //TODO Implement the method
        return getAllEmployees().stream()
                .min(Comparator.comparing(Employee::getSalary))
                .orElseThrow().getSalary();

    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        //TODO Implement the method
        return  getAllEmployees().stream()
                .min(Comparator.comparing(Employee::getSalary))
                .stream().collect(Collectors.toList());
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
        //TODO Implement the method
        return  getAllEmployees().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .skip(1)
                .findAny().get().getSalary();
    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee()  {
        //TODO Implement the method
        return  getAllEmployees().stream()
                .filter(p-> {
                    try {
                        return p.getSalary().equals(getSecondMinSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {

        return getAllEmployees().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {

        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary()>getAverageSalary())
                .toList();
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary()<getAverageSalary())
                .toList();
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        //TODO Implement the method----
        return
                getAllEmployees().stream()
                        .collect(Collectors.groupingBy(s-> s.getDepartment().getId()));
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        //TODO Implement the method
        return (long) getAllDepartments().size();
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(p->p.getFirstName().equalsIgnoreCase("Alyssa")
                        &&p.getManager().getFirstName().equalsIgnoreCase("Eleni")
                        &&p.getDepartment().getDepartmentName().equalsIgnoreCase("Sales"))
                .findAny().orElseThrow();
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        //TODO Implement the method
        return getAllJobHistories().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .toList();
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {

        return
                getAllJobHistories().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .toList();
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {

        return getAllJobHistories().stream()
                .filter(p->p.getStartDate().isAfter(LocalDate.of(2005, 1, 1)))
                .toList();
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        //TODO Implement the method
        return getAllJobHistories().stream()
                .filter(p->p.getEndDate().equals(LocalDate.of(2007, 12, 31))
                        &&p.getJob().getJobTitle().equalsIgnoreCase("Programmer"))
                .toList();
    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        //TODO Implement the method
        return getAllJobHistories().stream()
                .filter(p->p.getStartDate().equals(LocalDate.of(2007, 1, 1))
                        &&p.getEndDate().equals(LocalDate.of(2007,12,31))
                        && p.getDepartment().getDepartmentName().equalsIgnoreCase("Shipping"))
                        .map(JobHistory::getEmployee)
                .findAny().orElseThrow();
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        //TODO Implement the method
        return
                getAllEmployees().stream()
                .filter(p->p.getFirstName().startsWith("A"))
                .toList();
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(p->p.getJob().getId().contains("IT"))
                .toList();
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(p->p.getJob().getJobTitle().equalsIgnoreCase("programmer")
                        &&p.getDepartment().getDepartmentName().equalsIgnoreCase("IT"))
                .count();
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(p->p.getDepartment().getId().equals(50L)
                        ||p.getDepartment().getId().equals(80L)
                        ||p.getDepartment().getId().equals(100L))
                .toList();
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .map(employee -> employee.getFirstName().charAt(0)  +""+ employee.getLastName().charAt(0))
                .toList();
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .map(employee -> employee.getFirstName() + " " + employee.getLastName())
                .toList();
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        //TODO Implement the method
        return getAllEmployeesFullNames().stream()
                .max(Comparator.comparing(String::length))
                .orElseThrow().length();
    }
    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        return getAllEmployees().stream()
                .filter(p -> {
                    try {
                        return (p.getFirstName() + p.getLastName()).length() + 1 == getLongestNameLength();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(employee -> employee.getDepartment().getId()==90
                        ||employee.getDepartment().getId()==60
                ||employee.getDepartment().getId()==100||employee.getDepartment().getId()==120||employee.getDepartment().getId()==130)
                .toList();
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        //TODO Implement the method
        return getAllEmployees().stream()
                .filter(employee -> employee.getDepartment().getId()!=90&&employee.getDepartment().getId()!=60
                        &&employee.getDepartment().getId()!=100&&employee.getDepartment().getId()!=120&&employee.getDepartment().getId()!=130)
                .toList();
    }

}
