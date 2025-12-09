package codehelper.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${spring.upload.base-path}")  // 正确写法
    private String basePath;

    // 图片访问的基础URL（例如：http://localhost:8080/upload/）
    @Value("${spring.upload.access-url}")
    private String accessUrl;

    /**
     * 图片上传接口（仅管理员可访问）
     */
    @PostMapping("/image")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // 1. 校验文件类型（仅允许图片）
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpeg")) {
            throw new RuntimeException("仅支持jpg、png、jpeg格式图片");
        }

        // 2. 生成唯一文件名（避免重名）
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 3. 创建上传目录（若不存在则创建）
        // 在保存文件前添加以下代码
        File uploadDir = new File(basePath);
        if (!uploadDir.exists()) {
            boolean mkdirs = uploadDir.mkdirs(); // 递归创建目录
            if (!mkdirs) {
                throw new RuntimeException("创建上传目录失败，请检查路径权限：" + basePath);
            }
        }

        // 4. 保存文件到服务器
        file.transferTo(new File(basePath + fileName));

        // 5. 返回图片访问URL（供前端添加Banner时使用）
        return accessUrl + fileName;
    }
}