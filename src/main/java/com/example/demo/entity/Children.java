package com.example.demo.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author meihaibo
 * @since 2024-08-08
 */
@Getter
@Setter
public class Children implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 上级ID
     */
    private Long userId;
}
