package com.david.crudjwt.repositories;

import com.david.crudjwt.models.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<Producer,Long>
{
    List<Producer> findAll();
}
