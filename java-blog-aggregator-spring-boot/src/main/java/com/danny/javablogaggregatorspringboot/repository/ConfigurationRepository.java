package com.danny.javablogaggregatorspringboot.repository;

import com.danny.javablogaggregatorspringboot.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

    @Transactional
    @Modifying
    @Query("update Configuration c set c.icon = ?1")
    void saveIcon(byte[] icon);

    @Transactional
    @Modifying
    @Query("update Configuration c set c.favicon = ?1")
    void saveFavicon(byte[] icon);

    @Transactional
    @Modifying
    @Query("update Configuration c set c.appleTouchIcon = ?1")
    void saveAppleTouchIcon(byte[] icon);

}
