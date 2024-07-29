package com.server.aggregates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatesRepository extends JpaRepository<Aggregates, Long> {

}
