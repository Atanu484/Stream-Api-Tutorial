package com.example.streamapitutorial;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class StreamApiTutorialApplication {

    static List<Employee> employees = new ArrayList<>();
    static {
        employees.add(
                new Employee("Atanu" , "Biswas" , 5000.0 , List.of("Project 1", "Project 2"))
        );
        employees.add(
                new Employee("Ayan" , "Sarkar" , 4000.0 , List.of("Project 1", "Project 2"))
        );
        employees.add(
                new Employee("Shuvo" , "Banerjee" , 3000.0 , List.of("Project 3", "Project 4"))
        );
    }

    public static void main(String[] args) {
        // SpringApplication.run(StreamApiTutorialApplication.class, args);
        employees.stream().
                forEach(employee -> System.out.println(employee));

        //map
        //collect
       Set<Employee> increasedSalary =
               employees.stream().
                map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.10,
                        employee.getProjects()
                ))
                .collect(Collectors.toSet());

        System.out.println(increasedSalary);

        //filter
        List<Employee> filterEmployee =
                employees.
                stream().
                filter(employee -> employee.getSalary() > 3000.0).
                map(
                        employee -> new Employee(
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getSalary() * 1.10,
                                employee.getProjects()
                        )
                ).collect(Collectors.toList());

        System.out.println(filterEmployee);

        Employee firstEmployee =
                employees.
                        stream().
                        filter(employee -> employee.getSalary() > 7000.0).
                        map(employee -> new Employee(
                                        employee.getFirstName(),
                                        employee.getLastName(),
                                        employee.getSalary() * 1.10,
                                        employee.getProjects()
                                ))
                        .findFirst()
                        .orElse(null);
        System.out.println(firstEmployee);

        //flatMap
        String projects =
                employees.stream().
                map(employee -> employee.getProjects())
                .flatMap(strings -> strings.stream())
                .collect(Collectors.joining(","));

        System.out.println(projects);

        //short Circuit operators
        List<Employee> shortCircuit =
                employees
                        .stream()
                        .skip(1)
                        .limit(1)
                        .collect(Collectors.toList());
        System.out.println(shortCircuit);


        //Finite Data
        Stream.generate(Math::random)
                .limit(5)
                .forEach(value -> System.out.println(value));

        //sorting
        List<Employee> sortedEmployee =
                employees
                .stream()
                .sorted((o1, o2) -> o1.getFirstName()
                        .compareToIgnoreCase(o2.getFirstName()))
                .collect(Collectors.toList());

        System.out.println(sortedEmployee);

        //min or max
        employees
                .stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(NoSuchElementException::new);

        // reduce
        Double totalSal =
        employees
                .stream()
                .map(employee -> employee.getSalary())
                .reduce(0.0,Double::sum);

        System.out.println(totalSal);
    }
}
