package com.example.java8;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.stream.Stream;

import java.util.Optional;

public class Java8MethodCheatSheet {

    public static void main(String[] args) {
        List<Employee> employees = EmployeeDataBase.getEmployees();
        // System.out.println("Total Employees: " + employees.size());
        // employees.forEach(System.out::println);

        List<Employee> employeeFromDevDepartment = employees.stream()
                .filter(e -> e.getDept().equals("Development") && e.getSalary() > 80000)
                .collect(Collectors.toList());
        // System.out.println("Employees in Development with salary > 80000:");
        // System.out.println(employeeFromDevDepartment);

        Map<Integer, String> developmentEmployees = employees.stream()
                .filter(e -> e.getDept().equals("Development") && e.getSalary() > 80000)
                .collect(Collectors.toMap(Employee::getId, Employee::getName));
        // System.out.println(developmentEmployees);

        // map (if we want to extract specific data from the objects)
        // distinct (if we want to remove duplicates)
        // sorted (if we want to sort the data)
        // collect (if we want to collect the data)
        List<String> depts = employees.stream()
                .map(Employee::getDept)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        // System.out.println(depts);

        List<Stream<String>> projectNames = employees.stream()
                .map(e -> e.getProjects().stream().map(Project::getName))
                .collect(Collectors.toList());
        // System.out.println(projectNames);

        // flatmap
        List<String> flatProjectNames = employees.stream()
                .flatMap(e -> e.getProjects().stream())
                .map(Project::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        // System.out.println(flatProjectNames);

        // ascending sorted
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .collect(Collectors.toList());

        // sortedEmployees.forEach(System.out::println);

        // System.out.println(sortedEmployees);

        // descending sorted
        List<Employee> sortedEmployeesDesc = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .collect(Collectors.toList());

        // sortedEmployeesDesc.forEach(System.out::println);

        // min & max
        Optional<Employee> minSalaryEmployee = employees.stream()
                .min(Comparator.comparing(Employee::getSalary));

        // minSalaryEmployee.ifPresent(System.out::println);

        Optional<Employee> maxSalaryEmployee = employees.stream()
                .max(Comparator.comparing(Employee::getSalary));

        // maxSalaryEmployee.ifPresent(System.out::println);

        // groupingBy
        Map<String, List<Employee>> employeesByGender = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender));

        // System.out.println(employeesByGender);

        // Gender -> [Names]
        Map<String, List<String>> employeesGroupedByGender = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.mapping(Employee::getName, Collectors.toList())));

        // System.out.println(employeesGroupedByGender);

        Map<String, Long> employeesGroupedByGenderCount = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));

        // System.out.println(employeesGroupedByGenderCount);

        // findFirst
        Employee firstEmployee = employees.stream()
                .filter(e -> e.getDept().equals("Development"))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No Employee Found"));

        // System.out.println(firstEmployee);

        // findAny
        Employee anyEmployee = employees.stream()
                .filter(e -> e.getDept().equals("Development"))
                .findAny().orElseThrow(() -> new IllegalArgumentException("No Employee Found"));

        // System.out.println(anyEmployee);

        // allMatch(Predicate), anyMatch(Predicate), noneMatch(Predicate)
        boolean allMatch = employees.stream()
                .allMatch(e -> e.getSalary() > 50000);

        // System.out.println(allMatch);

        // Limit(long)
        List<Employee> limitEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(3)
                .collect(Collectors.toList());

        // limitEmployees.forEach(e -> System.out.println(e.getName()));

        // skip(long)
        List<Employee> skipEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .skip(5)
                .collect(Collectors.toList());

        skipEmployees.forEach(e -> System.out.println(e.getName()));

    }

}
