package com.leyou.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author bystander
 * @date 2018/9/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfo {
    private Long id;
    private String name;
}
