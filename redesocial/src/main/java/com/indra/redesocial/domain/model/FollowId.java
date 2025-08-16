package com.indra.redesocial.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class FollowId implements Serializable {
    private Long following;
    private Long followed;

    public FollowId() {
    }

    public FollowId(Long following, Long followed) {
        this.following = following;
        this.followed = followed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowId)) return false;
        FollowId that = (FollowId) o;
        return Objects.equals(following, that.following) &&
                Objects.equals(followed, that.followed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(following, followed);
    }
}
