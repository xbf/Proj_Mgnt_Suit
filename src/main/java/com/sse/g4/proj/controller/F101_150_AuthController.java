package com.sse.g4.proj.controller;

import com.sse.g4.proj.Menu;
import com.sse.g4.proj.common.packer.ParamMap;
import com.sse.g4.proj.common.packer.ResultPack;
import com.sse.g4.proj.service.F101_150_AuthService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/g4/auth", produces = {"application/json;charset=UTF-8"})
public class F101_150_AuthController implements ApplicationContextAware {

    private ApplicationContext context;

    @Autowired
    private F101_150_AuthService authService;

    @PostMapping("/f100101")
    public ResultPack f100101(@RequestParam(name = "staffAcc", defaultValue = " ") String staffAcc,
                              @RequestParam(name = "password", defaultValue = " ") String password) {
        ResultPack resultPack;
        ParamMap<String, Object> paramMap;
        List<ParamMap<String, Object>> userInfoLst;
        List<ParamMap<String, Object>> menuMapLst;
        List<ParamMap<String, Object>> resultLst = new ArrayList<>();

//        String encryptPassword = new SimpleHash("md5", password, ByteSource.Util.bytes(md5Salt),2).toHex();

        resultPack = authService.f1100101(staffAcc); // 获取用户信息
        if (resultPack.getErrorNo() != 0) {
            return resultPack;
        }
        userInfoLst = resultPack.getResultLst();
        if (userInfoLst == null || userInfoLst.size() == 0) {
            resultPack.setErrorNo(88888);
            resultPack.setErrorInfo("无此用户信息");
            return resultPack;
        }
        paramMap = userInfoLst.get(0);

        if (!password.equals(paramMap.get("password"))) {
            resultPack.setErrorNo(81020009);
            resultPack.setErrorInfo("用户密码校验失败");
            return resultPack;
        }

        ParamMap<String, Object> staffInfoMap;
        staffInfoMap = (ParamMap<String, Object>) paramMap.clone();
        staffInfoMap.put("password", " ");

        paramMap.clear();
        paramMap.put("staffInfo", staffInfoMap);

        resultPack = authService.f1100102(staffAcc);
        if (resultPack.getErrorNo() != 0) {
            return resultPack;
        }

        menuMapLst = resultPack.getResultLst();

        List<Menu> menus = createMenu(menuMapLst, 0);
        paramMap.put("menus", menus);


        List<ParamMap<String, Object>> routes = createRoute(menuMapLst);
        paramMap.put("routes", routes);

        resultLst.add(paramMap);
        resultPack.setResultLst(resultLst);

        return resultPack;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * 创建菜单列表递归函数，实现一次遍历完成多级菜单结构的生成
     * @param menuLst  可访问菜单列表，已按照字典序排序
     * @param index 当前列表位置，默认从0开始
     * @return 多级菜单结构列表
     */
    private List<Menu> createMenu(List<ParamMap<String, Object>> menuLst, int index) {
        List<Menu> menus = new ArrayList<>();
        ParamMap<String, Object> paramMap = menuLst.get(index);
        String menuSite = (String)paramMap.get("menuSite");
        int currMenuLength = menuSite.length();

        for (int i = index; i < menuLst.size(); i++) {
            paramMap = menuLst.get(i);
            Menu menu = new Menu();
            menu.setMenuId(String.valueOf(paramMap.get("menuId")));
            menu.setMenuName(String.valueOf(paramMap.get("menuName")));
            menu.setMenuSite(String.valueOf(paramMap.get("menuSite")));
            menu.setMenuPath(String.valueOf(paramMap.get("menuPath")));
            menu.setComponent(String.valueOf(paramMap.get("component")));
            menu.setIcon(String.valueOf(paramMap.get("icon")));
            menus.add(menu);

            if (i + 1 < menuLst.size()) {
                paramMap = menuLst.get(i + 1);
                int nextMenuLength = String.valueOf(paramMap.get("menuSite")).length();

                if (currMenuLength < nextMenuLength) {
                    List<Menu> subMenus = createMenu(menuLst, i + 1);
                    menu.setChildren(subMenus);
                    i = i + subMenus.size();
                } else if (currMenuLength > nextMenuLength) {
                    return menus;
                }
            }
        }
        return menus;
    }

    private List<ParamMap<String, Object>> createRoute(List<ParamMap<String, Object>> menuLst) {
        List<ParamMap<String, Object>> routes = new ArrayList<>();

        for (ParamMap<String, Object> menuMap : menuLst) {
            ParamMap<String, Object> paramMap = new ParamMap<>();

            String component = String.valueOf(menuMap.get("component"));
            if (component.trim().length() == 0) {
                continue;
            }

//            {
//                path: '/user',
//                        name: 'user',
//                    meta: {
//                title: '个人中心'
//            },

            paramMap.put("path", menuMap.get("menuPath"));
            paramMap.put("name", menuMap.get("menuPath"));
            paramMap.put("component", menuMap.get("component"));
            String menuName = String.valueOf(menuMap.get("menuName"));
            ParamMap<String, String> metaMap = new ParamMap<>();
            metaMap.put("title", menuName);
            paramMap.put("meta", metaMap);

            routes.add(paramMap);
        }
        return routes;

    }

}
