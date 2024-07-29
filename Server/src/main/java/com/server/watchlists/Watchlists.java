package com.server.watchlists;

import com.server.users.Users;
import com.server.watchliststock.WatchlistStock;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "watchlists")
public class Watchlists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToMany (mappedBy = "watchlists")
    private List<WatchlistStock> watchlistStock;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<WatchlistStock> getWatchlistStock() {
        return watchlistStock;
    }

    public void setWatchlistStock(List<WatchlistStock> watchlistStock) {
        this.watchlistStock = watchlistStock;
    }
}
