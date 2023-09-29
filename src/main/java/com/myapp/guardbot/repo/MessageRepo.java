package com.myapp.guardbot.repo;

import com.myapp.guardbot.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {

}
