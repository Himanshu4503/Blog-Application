package com.codewithhimanshu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithhimanshu.blog.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
