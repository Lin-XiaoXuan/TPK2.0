package com.njustjjy.tpk2.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobUtils {
    // 音量 0-100
    private Integer volume;
    // 语音朗读速度 -10 到 +10
    private Integer rate;

    //私有化实例：保证只有一个实例
    private static volatile JacobUtils jacobUtils;

    //提供构造器--私有化构造器
    private JacobUtils(){}

    //外部获取单利方法
    public static JacobUtils getInstance(){
        if(jacobUtils == null){
            synchronized (JacobUtils.class){
                if (jacobUtils == null){
                    jacobUtils = new JacobUtils();
                }
            }
        }
        return jacobUtils;
    }

    //语音播报方法--保证播报语言单利
    public void sing(String text){
        //判断赋值
        if(this.volume == null){
            this.volume = 100;
        }
        else if(this.rate == null){
            this.rate = -2;
        }

        ActiveXComponent ax = null;

        try {
            ax = new ActiveXComponent("Sapi.SpVoice");

            // 运行时输出语音内容
            Dispatch spVoice = ax.getObject();
            // 音量 0-100
            ax.setProperty("Volume", new Variant(this.volume));
            // 语音朗读速度 -10 到 +10
            ax.setProperty("Rate", new Variant(this.rate));
            // 执行朗读
            Dispatch.call(spVoice, "Speak", new Variant(text));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
