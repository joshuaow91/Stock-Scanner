package com.server.watchlists.repository;

import com.server.watchlists.entity.Watchlists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistsRepository extends JpaRepository<Watchlists, Long> {

    @Query(value = "INSERT INTO watchlists (name, user_id) VALUES (:watchlistName, :userId)", nativeQuery = true)
    Watchlists createWatchlist(@Param("watchlistName") String watchlistName, @Param("userId") int userId);
}
