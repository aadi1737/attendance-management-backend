package com.nvs.Mavli.repository;

import com.nvs.Mavli.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByClassNameAndSection(String className, String section);
    List<Student> findByHouse(String house);

    // ✅ Add this method
//    List<Student> findAll(); // This is already provided by JpaRepository, no need to add!

    @Query("SELECT DISTINCT s.className, s.section FROM Student s")
    List<Object[]> findDistinctClassSections();

    @Query("SELECT DISTINCT s.house FROM Student s")
    List<String> findDistinctHouses();
}