package com.sse.g4.proj;

import lombok.Data;

import java.util.List;

@Data
public class Menu {
    private String menuId;
    private String menuSite;
    private String menuName;
    private String menuPath;
    private String component;
    private String icon;
    private List<Menu> children;
}
