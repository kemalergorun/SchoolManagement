package com.techproed.schoolmanagementbackendb326.repository.business;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meet,Long> {

    List<Meet>findByStudentList_IdEquals(Long studentId);

    List<Meet>getByAdvisoryTeacher_IdEquals(Long teacherId);




}
