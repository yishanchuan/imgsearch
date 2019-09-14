/**
 * 版权所有 (c) 2018，《自制AI图像搜索引擎》作者。

 * 您可以下载和使用该软件。但它仅供个人学习和研究使用，禁止任何商业用途。

 * 您必须在此软件的所有副本（包括任何修改或衍生版本）中保留此许可证文件，且不能对其有任何修改。

 * 欢迎购买《自制AI图像搜索引擎》——一本全面介绍AI图像搜索引擎原理与实现的书。如有任何意见或建议，请发送邮件至imgsearch@126.com。
 */
package com.ai.deepsearch;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.datavec.image.loader.NativeImageLoader;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  搜索请求Servlet
 */
@WebServlet(value = "/search")
public class SearchImageServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("SearchImageServlet servlet handling post");
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            File f=new File("E:\\storetest");
            factory.setRepository(f);
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            String uploadDir = request.getSession().getServletContext().getRealPath("/upload_imgs");
            List<FileItem> fileItems = fileUpload.parseRequest(request);
            System.out.println("file items size:"+fileItems.size());
            for (FileItem item : fileItems) {
                if(!item.isFormField()) {
                    String fileName=item.getName();
                    if(fileName.lastIndexOf("\\")>=0) {
                        fileName=fileName.substring(fileName.lastIndexOf("\\"));
                    } else {
                        fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
                    }
                    File uploadFile=new File(uploadDir+"/"+fileName);
                    if(!uploadFile.exists()) {
                        uploadFile.getParentFile().mkdirs();
                    }
                    uploadFile.createNewFile();
                    item.write(uploadFile);
                    item.delete();

                    NativeImageLoader loader=new NativeImageLoader(224,224,3);
                    INDArray imageArray=loader.asMatrix(uploadFile);
                    DataNormalization scaler=new VGG16ImagePreProcessor();
                    scaler.transform(imageArray);

                    Map<String,INDArray> map=WebEngineInit.vgg16Model.feedForward(imageArray,false);
                    INDArray feature=map.get("fc2");

                    String imagesDb = request.getSession().getServletContext().getRealPath("/WEB-INF/images.db");

                    Set<String> result=SearchSimilarImgs.search(true,imagesDb,fileName,feature);

                    response.setContentType("text/html;charset=utf-8");
                    PrintWriter writer=response.getWriter();
                    writer.println("<html>");
                    writer.println("<head>");
                    writer.println("<title>查询结果</title>");
                    writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"./css/style.css\" />");
                    writer.println("</head>");
                    writer.println("<body>");
                    writer.println("<div class=\"search\">");
                    writer.println("<div class=\"back_btn\"><a href=\"http://localhost:8080/imgsearch/\">回主页</a></div>");
                    writer.println("<div class=\"title_search\"><span>查询图像</span></div>");
                    writer.println("</div>");
                    writer.println("<div>");
                    writer.println("<div class=\"search_img\"><img src=\"./upload_imgs/"+fileName+"\"></div>");
                    writer.println("</div>");
                    writer.println("<div class=\"line\"></div>");
                    writer.println("<div class=\"simi\">");
                    writer.println("<div class=\"title_simi\"><span>相似图像</span></div>");
                    writer.println("</div>");
                    writer.println("<div id=\"result\">");
                    for(String r:result) {
                        writer.println("<div class=\"simi_img\"><img src=\"./image/"+r+"\"></div>");
                        System.out.println(r);
                    }
                    writer.println("</div>");
                    writer.println("</body>");
                    writer.println("</html>");
                    writer.flush();
                    writer.close();
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) { // item.write(uploadImages)
            e.printStackTrace();
        }
    }
}
