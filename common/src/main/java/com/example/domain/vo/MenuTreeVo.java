package com.example.domain.vo;

import com.example.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeVo {
    private Long id;
    private List<Menu> children;
    private String label;
    private String parentId;
}
