package com.cg.qingcheng.controller.file;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @program: qingcheng_parent->UploadController
 * @description:
 * @author: cg
 * @create: 2020-02-22 20:17
 **/

@RestController
@RequestMapping("/upload")
public class UploadController {

    /**
     * Bucket 域名
     */
    final private String BUCKET_NAME = "chenyumeng-qingcheng";

    /**
     * 地域节点
     */
    final private String END_POINT = "oss-cn-shenzhen.aliyuncs.com";

    @Autowired
    private OSSClient ossClient;

    @PostMapping("/oss")
    public String ossUpload(@RequestParam("file") MultipartFile file, String folder) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String fileName = folder + "/" + formatter.format(date) + (UUID.randomUUID() + "").substring(1, 10) + ".jpg";
        try {
            ossClient.putObject(BUCKET_NAME, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "https://" + BUCKET_NAME + "." + END_POINT + "/" + fileName;
    }

}
