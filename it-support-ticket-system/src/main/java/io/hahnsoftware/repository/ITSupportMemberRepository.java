package io.hahnsoftware.repository;

import io.hahnsoftware.entity.Employee;
import io.hahnsoftware.entity.ITSupportMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITSupportMemberRepository extends JpaRepository<ITSupportMember, Long> {
    Optional<ITSupportMember> findByEmail(String email);
}