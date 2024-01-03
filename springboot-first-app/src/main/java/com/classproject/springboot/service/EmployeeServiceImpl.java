package com.classproject.springboot.service;

import java.util.List;
import java.util.Optional;
import com.classproject.springboot.model.Employee;
import com.classproject.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl() {
    }

    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    public void saveEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long id) {
        Optional<Employee> optional = this.employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = (Employee)optional.get();
            return employee;
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
    }

    public void deleteEmployeeById(long id) {
        this.employeeRepository.deleteById(id);
    }

    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Direction.ASC.name()) ? Sort.by(new String[]{sortField}).ascending() : Sort.by(new String[]{sortField}).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.employeeRepository.findAll(pageable);
    }
}
