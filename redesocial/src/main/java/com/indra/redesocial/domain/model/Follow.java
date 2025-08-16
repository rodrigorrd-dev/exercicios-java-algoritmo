package com.indra.redesocial.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
@IdClass(FollowId.class)
public class Follow {
    @Id
    @ManyToOne
    @JoinColumn(name = "following_user_id", nullable = false)
    private User following;

    @Id
    @ManyToOne
    @JoinColumn(name = "followed_user_id", nullable = false)
    private User followed;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
