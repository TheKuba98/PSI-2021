package com.pwr.psi.mockservice.storage;

import com.pwr.psi.mockservice.dto.StudentDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStorage {

    private final Map<String, StudentDto> students = new ConcurrentHashMap<>();

    public String save(StudentDto student) {
        students.put(student.getIndexNumber(), student);
        return student.getIndexNumber();
    }

    public Collection<StudentDto> findAll() {
        return students.values();
    }

    public void clear() {
        students.clear();
    }
}
