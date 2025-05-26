package com.dam.web_cocina.repository;

import com.dam.web_cocina.entity.UserAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {

    List<UserAvatar> findByUserId(Long userId);

}
