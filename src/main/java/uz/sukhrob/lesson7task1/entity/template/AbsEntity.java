package uz.sukhrob.lesson7task1.entity.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.sukhrob.lesson7task1.entity.User;

import javax.persistence.*;
import java.sql.Timestamp;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;
}
