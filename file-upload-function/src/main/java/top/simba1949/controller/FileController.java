package top.simba1949.controller;


import org.csource.common.MyException;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.simba1949.common.FastDFSFile;
import top.simba1949.util.FastDFSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Theodore
 * @Date 2019/10/23 16:43
 */
@RestController
@RequestMapping("file")
public class FileController {

    @PostMapping("upload")
    public String upload(MultipartFile file) throws IOException, MyException {
        String originalFilename = file.getOriginalFilename();
        String[] split = originalFilename.split("\\.");

        byte[] content = file.getBytes();
        FastDFSFile dfsFile = new FastDFSFile();
        // 文件内容
        dfsFile.setContent(content);
        if (split.length > 1){
            // 原始文件名
            dfsFile.setName(originalFilename.substring(0, originalFilename.length() - split[split.length-1].length() - 1));
            // 原始扩展名
            dfsFile.setExt(split[split.length-1]);
        }else{
            // 如果没有文件扩展名，直接设置名称即可
            dfsFile.setName(split[0]);
        }

        // 文件上传到 FastDFS
        String url = FastDFSUtils.upload(dfsFile);

        return url;
    }

    /**
     * http://192.168.8.46:80/group1/M00/00/00/wKgILl2wFjKAdc4cADPjrn6gMSM998.jpg
     * @param groupName 指的是 group1
     * @param remoteFileName 指的是 M00/00/00/wKgILl2wFjKAdc4cADPjrn6gMSM998.jpg
     * @return
     * @throws IOException
     * @throws MyException
     */
    @GetMapping("file-info")
    public FileInfo getFileInfo(String groupName, String remoteFileName) throws IOException, MyException {
        FileInfo fileInfo = FastDFSUtils.getFileInfo(groupName, remoteFileName);
        return fileInfo;
    }

    @GetMapping("download")
    public void download(String groupName, String remoteFileName, HttpServletResponse rsp) throws IOException, MyException {
        byte[] bytes = FastDFSUtils.download(groupName, remoteFileName);

        // 设置
        String fileName = "下载文件名.jpg";
        fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
        rsp.addHeader("Content-Type", "application/octet-stream");
        rsp.addHeader("Content-Disposition", "attachment; filename=" + fileName);

        ServletOutputStream os = rsp.getOutputStream();
        os.write(bytes);
        os.flush();
    }

    @GetMapping("delete")
    public String delete(String groupName, String remoteFileName) throws IOException, MyException {
        int delete = FastDFSUtils.delete(groupName, remoteFileName);
        return String.valueOf(delete);
    }

    @GetMapping("storage")
    public String getStorage() throws IOException {
        String storage = FastDFSUtils.getStorage();
        return storage;
    }

    @GetMapping("get-storage-group-info")
    public ServerInfo[] getStorageGroupInfo(String groupName, String remoteFileName) throws IOException {
        ServerInfo[] storageGroupInfo = FastDFSUtils.getStorageGroupInfo(groupName, remoteFileName);
        return storageGroupInfo;
    }

    @GetMapping("get-tracker-info")
    public String getTrackerInfo() throws IOException {
        String trackerInfo = FastDFSUtils.getTrackerInfo();
        return trackerInfo;
    }
}
