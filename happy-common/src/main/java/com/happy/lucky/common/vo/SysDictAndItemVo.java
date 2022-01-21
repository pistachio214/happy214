package com.happy.lucky.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Pengsy
 */
@Data
public class SysDictAndItemVo {
    private Long id;

    private String type;

    private String name;

    List<SysDictItemVo> items;
}
