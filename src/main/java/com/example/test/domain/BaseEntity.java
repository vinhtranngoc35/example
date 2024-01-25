package com.example.test.domain;


import com.example.test.domain.enumration.EStatusDelete;
import jakarta.persistence.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.time.LocalDateTime;

@MappedSuperclass
@Where(clause = "deleted <> 'DELETED'")
public abstract class BaseEntity {

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column( nullable = false)
    private LocalDateTime updatedAt;
    @Enumerated(value = EnumType.STRING)
    private EStatusDelete deleted = EStatusDelete.NON_DELETED;

    private String deletedBy;

    private String createdBy;

    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        createdBy = getCurrentUsername();
        updatedBy = getCurrentUsername();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        updatedBy = getCurrentUsername();
    }


    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public EStatusDelete getDeleted() {
        return deleted;
    }

    public void setDeleted(EStatusDelete deleted) {
        this.deleted = deleted;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}