package com.stylefeng.guns.api.base;

/**
 * @author ztkj-hzb
 * @Date 2019/8/27 14:39
 * @Description
 */
public class Test {

    public static void main(String[] args) {


        BusinessException businessException = new BusinessException(EmBusinessErrorModel.USER_NOT_EXISTS);
        System.out.println(businessException.getErrMsg());
        BusinessException businessException1 = new BusinessException(EmBusinessErrorModel.USER_NOT_EXISTS.setErrMsg("测试修改异常"));

        CommonErrorModel businessException2 = new BusinessException(EmBusinessErrorModel.USER_NOT_EXISTS).setErrMsg("测试修改异常222");
        System.out.println(businessException1.getErrMsg());
        System.out.println(businessException2.getErrMsg());

    }


}
