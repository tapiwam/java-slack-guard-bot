package com.myapp.guardbot.repo;

import com.myapp.guardbot.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, String> {
}
