package com.sunveee.xiucailv.web.common.jpa;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseJpaEntity implements Serializable {

    private static final long serialVersionUID = -2599601132380959177L;
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_datetime", columnDefinition = "datetime", updatable = false, nullable = false)
    protected LocalDateTime createDatetime;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_datetime", columnDefinition = "datetime", nullable = true)
    protected LocalDateTime updateDatetime;


}
