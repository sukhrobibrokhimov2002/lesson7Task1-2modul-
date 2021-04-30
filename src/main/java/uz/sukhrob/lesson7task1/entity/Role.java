package uz.sukhrob.lesson7task1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.sukhrob.lesson7task1.entity.enums.Permissions;
import uz.sukhrob.lesson7task1.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbsEntity {
    private String name;
    private String description;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Permissions> permissions;
}
