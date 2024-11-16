package com.bitspilani.coursemasterservice.repository;

import com.bitspilani.coursemasterservice.model.CourseMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CourseMasterRepository extends JpaRepository<CourseMaster, Long> {

    @Transactional
    void deleteByCourseId(Long courseId);

    boolean existsByCourseId(Long courseId);

    List<CourseMaster> findByCourseId(Long courseId);
}
