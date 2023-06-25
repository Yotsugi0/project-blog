package com.example.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.example.domain.ResponseResult;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.service.UploadService;
import com.example.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class OssUploadService implements UploadService {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if(originalFilename.endsWith(".png")||originalFilename.endsWith(".jpg")){
            //如果判断通过上传文件到OSS
            String filePath = PathUtils.generateFilePath(originalFilename);
            String url = uploadOss(img,filePath);//  2099/2/3/wqeqeqe.png
            return ResponseResult.okResult(url);
        }else {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
    }

    private String uploadOss(MultipartFile img, String filePath) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String objectName = filePath;
        try {
            InputStream inputStream = img.getInputStream();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
            return "https://sg-blog-o.oss-cn-hangzhou.aliyuncs.com/"+filePath;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            //ignore
        }
        finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "www";
    }
}
