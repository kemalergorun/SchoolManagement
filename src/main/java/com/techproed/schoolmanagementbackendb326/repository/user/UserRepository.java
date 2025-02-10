package com.techproed.schoolmanagementbackendb326.repository.user;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.concretes.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("select u from User u where u.userRole.roleName = :userRole")
    Page<User> findUserByUserRoleQuery(String userRole, Pageable pageable);
}
