package com.javatest.domain.PhototypePo;

import java.util.HashMap;

public class ProtoTypeManager {
    // 维护一个注册表
    private HashMap<String,Mask> ptm = new HashMap<>();

    //管理器初始化时加载已知原型到注册表中
    public ProtoTypeManager(){
        ptm.put("N95",new N95());
        ptm.put("Medical",new Medical());
    }
    // 提供获取新实例的方法
    public Mask getMaskClone(String key) throws CloneNotSupportedException {
        Mask proto = ptm.get(key);
        if (proto== null) {
            System.out.println("没有找到对应的原型！");
            return proto;
        } else {
            return proto.clone();
        }
    }
    // 提供新增新实例的方法
    public void addMaskProto(String key,Mask mask) {
        ptm.put(key,mask);
    }
}
