package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {
    @Autowired
    private UploadProperties uploadProperties;

    public static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg","image/png","image/bmp");

    @Autowired
    private FastFileStorageClient storageClient;

    public String uploadImage(MultipartFile file) {
        try {
            //校验文件
            String contentType = file.getContentType();
            if(!ALLOW_TYPES.contains(contentType)){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
//            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
//            return uploadProperties.getBaseUrl()+storePath.getFullPath();
            return "http://image.leyou.com/"+storePath.getFullPath();
            /*File dest = new File("C:\\Users\\TianXiang\\Desktop\\leyou",file.getOriginalFilename());
            //保存文件到本地
            file.transferTo(dest);
            return "http://image.leyou.com/"+file.getOriginalFilename();*/
        } catch (IOException e) {
            log.error("上传失败！",e);
            throw new LyException(ExceptionEnum.UPLOAD_ERROR);
        }
    }
}
