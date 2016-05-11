package org.course.project.model.system;

import javax.persistence.*;

@Entity
@Table(name = "ABSTRACT_ENTITY")
public abstract class AbstractEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return this.id;
    }

}

