package uz.sukhrob.lesson7task1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.sukhrob.lesson7task1.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends AbsEntity {

    @Column(nullable = false,columnDefinition = "text")
    private String title;

    @Column(nullable = false,columnDefinition = "text")
    private String text;

    @Column(nullable = false,columnDefinition = "text")
    private String url;
}
