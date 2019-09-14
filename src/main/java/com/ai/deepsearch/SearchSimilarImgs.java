/**
 * 版权所有 (c) 2018，《自制AI图像搜索引擎》作者。

 * 您可以下载和使用该软件。但它仅供个人学习和研究使用，禁止任何商业用途。

 * 您必须在此软件的所有副本（包括任何修改或衍生版本）中保留此许可证文件，且不能对其有任何修改。

 * 欢迎购买《自制AI图像搜索引擎》——一本全面介绍AI图像搜索引擎原理与实现的书。如有任何意见或建议，请发送邮件至imgsearch@126.com。
 */
package com.ai.deepsearch;

import be.tarsos.lsh.LSH;
import be.tarsos.lsh.Vector;
import be.tarsos.lsh.families.CosineHashFamily;
import be.tarsos.lsh.families.DistanceMeasure;
import be.tarsos.lsh.families.EuclideanDistance;
import be.tarsos.lsh.families.HashFamily;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 查找相似图像
 */
public class SearchSimilarImgs {

    public static List<Vector> getVectorListFromDB(String dbPath) {
        DB db = DBMaker.fileDB(dbPath).make();
        ConcurrentMap<String, double[]> map = db.hashMap("feat_map", Serializer.STRING, Serializer.DOUBLE_ARRAY).open();
        List<Vector> vecs = new ArrayList<Vector>();
        for (String key : map.keySet()) {
            double[] val = map.get(key);
            // norm2
            val = Utils.normalizeL2(val);
            Vector vec = new Vector(key, val);
            vecs.add(vec);
        }
        db.close();
        return vecs;
    }

    public static Set<String> search(boolean linear, String dbPath, String imgName, INDArray fc2Feat) {
        Set<String> similarImgsName = new LinkedHashSet<String>();
        List<Vector> dataset = getVectorListFromDB(dbPath);
        int dimension = dataset.get(0).getDimensions();
        HashFamily cosHashFamily = new CosineHashFamily(dimension);

        double[] queryFeat = Utils.INDArray2DoubleArray(fc2Feat);
        // norm2
        queryFeat = Utils.normalizeL2(queryFeat);
        Vector queryVec = new Vector(imgName, queryFeat);
        int numOfNeighbours = 5;
        DistanceMeasure disMeasure = new EuclideanDistance();

        if (linear) {
            List<Vector> neighbours = LSH.linearSearch(dataset, queryVec, numOfNeighbours, disMeasure);
            for (Vector neighbour : neighbours) {
                similarImgsName.add(neighbour.getKey());
            }
        } else {
            LSH lsh = new LSH(dataset, cosHashFamily);
            int numOfHashes = 3;
            int numOfHashTables = 2;
            lsh.buildIndex(numOfHashes, numOfHashTables);

            List<Vector> neighbours = lsh.query(queryVec, numOfNeighbours);
            for (Vector neighbour : neighbours) {
                similarImgsName.add(neighbour.getKey());
            }
        }
        return similarImgsName;
    }
}
