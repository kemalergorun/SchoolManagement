package com.techproed.schoolmanagementbackendb326.repository.business;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonProgramRepository extends JpaRepository<LessonProgram,Long> {
}
