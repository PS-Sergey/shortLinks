package com.shortlinks.dao;

import com.shortlinks.entity.LinkHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkHolderRepository extends JpaRepository<LinkHolder, Long> {
}
