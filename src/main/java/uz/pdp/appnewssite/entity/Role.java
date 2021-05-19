package uz.pdp.appnewssite.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appnewssite.entity.enums.Permission;
import uz.pdp.appnewssite.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.*;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role extends AbstractEntity {

    @Column(nullable = false,unique = true)
    private String name;                //ADMIN, USER, BOSHQALAR

    private String description;

    @Enumerated(EnumType.STRING)
    @ElementCollection                  //ManyToMany
    private List<Permission> permissions;
    
}

