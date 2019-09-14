# imgsearch

实现一个基本的搜索框架，参照reference写出。

Deeplearning4j + Java Web

## Deployment

因为maven仓库已经移除了LSH算法，所以需要自己编译后使用，参考[TarsosLSH](https://github.com/JorenSix/TarsosLSH)

## References

### projects

项目名|优缺点|框架
--|--|--
[自制AI图像搜索引擎](https://meta.box.lenovo.com/link/view/6aded57a2d2c4c25ab030f02f8ec9583),密码:1aaa|使用局部敏感哈希算法加快查找，模型基于VGG16，教学足够，实践还是欠缺|deeplearning4j
[DeepImageSearchEngine](https://github.com/pratheeksh/Deep-Image-Search-Engine)|a reverse image and text search engine|Pytorch+NLTK
[SmartSearch](https://github.com/sethuiyer/Image-to-Image-search)| a reverse image search engine which finds similar images by generating captions and comparing those captions.|Keras +Elasticsearch
[sis](https://github.com/matsui528/sis)|a simple image-based image search engine  |Keras + Flask
### course
https://cs.nyu.edu/courses/spring17/CSCI-GA.3033-006/