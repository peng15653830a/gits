package xyz.gits.boot.common.core.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.gits.boot.common.core.exception.SystemException;
import xyz.gits.boot.common.core.filesystem.FileSystemProvider;
import xyz.gits.boot.common.core.filesystem.UploadParameter;
import xyz.gits.boot.common.core.response.ResponseCode;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;


/**
 * 文件上传工具
 *
 * @author null
 * @date 2019/12/11/16:47
 */
@Slf4j
public class FileUtil {

    private static FileSystemProvider fileSystemProvider = SpringContextHolder.getBean(FileSystemProvider.class);

    /**
     * 文件上传
     *
     * @param subDirectory  子目录
     * @param multipartFile
     * @return 文件存放全路径 如:/opt/file/81723931-test.java （上传源文件：test.java）
     */
    public static String upload(String subDirectory, MultipartFile multipartFile) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            //兼容ie9:去除文件路径最后'\\'之前所有字符
            if (StrUtil.contains(originalFilename, CharUtil.BACKSLASH)) {
                originalFilename = StrUtil.subAfter(originalFilename, CharUtil.BACKSLASH, true);
            }
            //将文件名加上前缀 如：test.java --->  81723931-test.java , 下载时文件名切掉前缀即可。
            String fileName = IdUtil.simpleUUID().substring(0, 8) + CharUtil.DASHED + originalFilename;
            UploadParameter parameter = UploadParameter.builder()
                    .subDirectory(subDirectory)
                    .fileName(fileName)
                    .inputStream(multipartFile.getInputStream())
                    .build();
            return fileSystemProvider.upload(parameter);
        } catch (IOException e) {
            throw new SystemException(ResponseCode.FILE_UPLOAD_EXCEPTION, e);
        }
    }

    /**
     * 文件下载
     *
     * @param fileKey  文件全路径 如:/opt/file/81723931-test.java
     * @param response
     */
    public static void download(String fileKey, HttpServletResponse response) {
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //将文件名去除前缀 如： 81723931-test.java ---> test.java
            String fileName = cn.hutool.core.io.FileUtil.getName(fileKey).substring(9);
            response.reset();
            response.setContentType("application/octet-stream");
            // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            inputStream = fileSystemProvider.download(fileKey);
            outputStream = response.getOutputStream();
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                throw new SystemException(ResponseCode.FILE_DOES_NOT_EXIST, e);
            }
            throw new SystemException(ResponseCode.FILE_DOWNLOAD_ABNORMAL, e);
        } finally {
            IoUtil.close(inputStream);
            IoUtil.close(outputStream);
        }
    }


    /**
     * 文件下载到本地服务器（如果 fileSystemProvider 的实现是（云服务器上传下载）就是将云文件下载到本地服务器上,如果操作都在一台服务器上相当于复制）
     *
     * @param fileKey 文件全路径 如:/opt/file/test.java
     * @param path    文件下载存放的路径 如:/usr/local/file/test.java
     */
    public static void downloadLocal(String fileKey, String path) {
        try {
            fileSystemProvider.downloadLocal(fileKey, path);
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                throw new SystemException(ResponseCode.FILE_DOES_NOT_EXIST, e);
            }
            throw new SystemException(ResponseCode.FILE_DOWNLOAD_ABNORMAL, e);
        }
    }


}