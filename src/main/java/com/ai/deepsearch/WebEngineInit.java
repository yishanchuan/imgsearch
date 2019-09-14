/**
 * 版权所有 (c) 2018，《自制AI图像搜索引擎》作者。

 * 您可以下载和使用该软件。但它仅供个人学习和研究使用，禁止任何商业用途。

 * 您必须在此软件的所有副本（包括任何修改或衍生版本）中保留此许可证文件，且不能对其有任何修改。

 * 欢迎购买《自制AI图像搜索引擎》——一本全面介绍AI图像搜索引擎原理与实现的书。如有任何意见或建议，请发送邮件至imgsearch@126.com。
 */
package com.ai.deepsearch;

import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;

/**
 *  引擎初始化
 */

@WebListener
public class WebEngineInit implements ServletContextListener {
    public static ComputationGraph vgg16Model;

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Web initialized");
        try {
            String modelFilePath=WebEngineInit.class.getClassLoader().getResource("vgg16.zip").getPath().substring(1);
            File vgg16ModelFile=new File(modelFilePath);
            vgg16Model= ModelSerializer.restoreComputationGraph(vgg16ModelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Web destroyed");
        vgg16Model=null;
    }
}
