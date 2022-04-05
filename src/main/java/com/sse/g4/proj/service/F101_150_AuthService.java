package com.sse.g4.proj.service;


import com.sse.g4.proj.common.packer.ParamMap;
import com.sse.g4.proj.common.packer.ResultPack;
import com.sse.g4.proj.dao.F101_150_AuthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class F101_150_AuthService {

    @Autowired
    private F101_150_AuthDao authDao;

    /**
     * 获取用户信息
     */
    public ResultPack f1100101(String staffAcc) {
        ResultPack resultPack = new ResultPack();
        List<ParamMap<String, Object>> resultLst;

        resultLst = authDao.f2100101(staffAcc);

        resultPack.setResultLst(resultLst);
        return resultPack;
    }


    /**
     * 获取用户菜单权限
     * @param staffAcc 用户账户
     * @return 用户菜单列表
     */
    public ResultPack f1100102(String staffAcc) {
        ResultPack resultPack = new ResultPack();
        List<ParamMap<String, Object>> resultLst;

        try {
            resultLst = authDao.f2100102(staffAcc);
        } catch(DataAccessException e) {
            e.printStackTrace();
            resultPack.setErrorNo(81020000);
            resultPack.setErrorInfo("获取用户菜单权限失败");
            return resultPack;
        }

        if (resultLst.size() == 0) {
            resultPack.setErrorNo(81020001);
            resultPack.setErrorInfo("用户菜单权限未设置");
            return resultPack;
        }
        resultPack.setResultLst(resultLst);

        return resultPack;
    }

//    /**
//     * 获取用户可操作营业部
//     * @param operatorAcc 用户账户
//     * @return 用户可操作营业部类别
//     */
//    public ResultPack f1102001(String operatorAcc) {
//        ResultPack resultPack = new ResultPack();
//        List<ParamMap<String, Object>> resultLst = new ArrayList<ParamMap<String, Object>>();
//        ParamMap paramMap = new ParamMap();
//        paramMap.put("operatorAcc", operatorAcc);
//
//        try {
//            resultLst = accContrlDao.f2102001(paramMap);
//        } catch(DataAccessException e) {
//            e.printStackTrace();
//            resultPack.setErrorNo(81020002);
//            resultPack.setErrorInfo("获取用户可操作营业部失败");
//            return resultPack;
//        }
//
//        if (resultLst.size() == 0) {
//            resultPack.setErrorNo(81020003);
//            resultPack.setErrorInfo("用户可操作营业部未设置");
//            return resultPack;
//        }
//
//        resultPack.setResultLst(resultLst);
//
//        return resultPack;
//    }
//
//    /**
//     * 获取用户信息
//     * @param operatorAcc 用户账户
//     * @return 用户信息
//     */
//    public ResultPack f1102002(String operatorAcc) {
//        ResultPack resultPack = new ResultPack();
//        List<ParamMap<String, Object>> resultLst = new ArrayList<ParamMap<String, Object>>();
//        ParamMap paramMap = new ParamMap();
//        paramMap.put("operatorAcc", operatorAcc);
//
//        try {
//            resultLst = accContrlDao.f2102002(paramMap);
//        } catch(DataAccessException e) {
//            e.printStackTrace();
//            resultPack.setErrorNo(81020007);
//            resultPack.setErrorInfo("查询操作员表失败");
//            return resultPack;
//        }
//
//        if (resultLst.size() == 0) {
//            resultPack.setErrorNo(81020008);
//            resultPack.setErrorInfo("系统不存在该用户");
//            return resultPack;
//        }
//
//        resultPack.setResultLst(resultLst);
//
//        return resultPack;
//    }
//
//    /**
//     * 修改密码
//     * @param operatorAcc 用户账户
//     * @param password 密码
//     * @return
//     */
//    public ResultPack f1102003(String operatorAcc, String password) {
//        ResultPack resultPack = new ResultPack();
//
//        try {
//            accContrlDao.f2102003(operatorAcc,password);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultPack.setErrorNo(81020010);
//            resultPack.setErrorInfo("修改用户密码失败");
//            return resultPack;
//        }
//
//        return resultPack;
//    }
//
//    public ResultPack f1102004(String enBranchNo) {
//        ResultPack resultPack = new ResultPack();
//        List<ParamMap<String, Object>> resultLst = null;
////        System.out.println("opBranchNo is: " + enBranchNo);
//        try {
//            resultLst = accContrlDao.f2102004(enBranchNo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultPack.setErrorNo(81020007);
//            resultPack.setErrorInfo("查询操作员表失败");
//            return resultPack;
//        }
//        resultPack.setResultLst(resultLst);
//        return resultPack;
//    }




}
