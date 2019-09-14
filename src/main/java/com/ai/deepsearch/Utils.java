/**
 * 版权所有 (c) 2018，《自制AI图像搜索引擎》作者。

 * 您可以下载和使用该软件。但它仅供个人学习和研究使用，禁止任何商业用途。

 * 您必须在此软件的所有副本（包括任何修改或衍生版本）中保留此许可证文件，且不能对其有任何修改。

 * 欢迎购买《自制AI图像搜索引擎》——一本全面介绍AI图像搜索引擎原理与实现的书。如有任何意见或建议，请发送邮件至imgsearch@126.com。
 */
package com.ai.deepsearch;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Arrays;

/**
 * 工具类
 */
public class Utils {
    public static double[] INDArray2DoubleArray(INDArray indArr) {
        String indArrStr = indArr.toString().replace("[", "").replace("]", "");
        String[] strArr = indArrStr.split(",");
        int len = strArr.length;
        double[] doubleArr = new double[len];
        for (int i = 0; i < len; i++) {
            doubleArr[i] = Double.parseDouble(strArr[i]);
        }
        return doubleArr;
    }

    public static double[] normalizeL2(double[] vec) {
        double norm2 = 0;
        for (int i = 0; i < vec.length; i++) {
            norm2 += vec[i] * vec[i];
        }
        norm2 = (double) Math.sqrt(norm2);
        if (norm2 == 0) {
            Arrays.fill(vec, 1);
        } else {
            for (int i = 0; i < vec.length; i++) {
                vec[i] = vec[i] / norm2;
            }
        }
        return vec;
    }
}
