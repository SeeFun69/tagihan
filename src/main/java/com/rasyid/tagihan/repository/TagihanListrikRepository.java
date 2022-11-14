package com.rasyid.tagihan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rasyid.tagihan.entity.TagihanListrik;

@Repository
public interface TagihanListrikRepository extends JpaRepository<TagihanListrik, Long> {
	
}
