package org.gear.framework.core.entity.component.collider;

import java.util.Objects;

public class CollisionHash {
    private final String hash;

    public CollisionHash(Collider firstCollider, Collider secondCollider) {
        String firstUUID = firstCollider.getParent().getUUID().toString().replaceAll("-", "");
        String secondUUID = secondCollider.getParent().getUUID().toString().replaceAll("-", "");

        StringBuilder builder = new StringBuilder();

        if (firstUUID.compareTo(secondUUID) <= 0) {
            builder.append(firstUUID).append(secondUUID);
        } else {
            builder.append(secondUUID).append(firstUUID);
        }

        this.hash = builder.toString();
    }

    public String getHash() {
        return this.hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CollisionHash other = (CollisionHash) obj;
        return Objects.equals(hash, other.hash);
    }

    @Override
    public String toString() {
        return this.hash;
    }
}
