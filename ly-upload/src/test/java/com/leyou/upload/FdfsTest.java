package com.leyou.upload;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FdfsTest {
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private ThumbImageConfig thumbImageConfig;
    
    @Test
    public void testUpload() throws FileNotFoundException {
        File file = new File("C:\\Users\\TianXiang\\Desktop\\1.PNG");
//        this.storageClient.uploadImageAndCrtThumbImage() //传输缩略图
        StorePath storePath = this.storageClient.uploadFile(new FileInputStream(file), file.length(), "png", null);
        System.out.println("storePath.getFullPath() = " + storePath.getFullPath());
        System.out.println("storePath.getPath() = " + storePath.getPath());
        //获取缩略图
//        this.thumbImageConfig.getThumbImagePath(storePath.getPath());
    }
    
}
