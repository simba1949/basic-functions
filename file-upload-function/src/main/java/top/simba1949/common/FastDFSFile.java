package top.simba1949.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Theodore
 * @Date 2019/10/23 16:44
 */
@Data
public class FastDFSFile implements Serializable {

    private static final long serialVersionUID = -6643290550500487894L;
    /** 文件名称 */
    private String name;
    /** 文件内容 */
    private byte[] content;
    /** 文件扩展名 */
    private String ext;
    /** 文件 MD5 摘要值 */
    private String md5;
    /** 文件创建作者 */
    private String author;

    public FastDFSFile(String name, byte[] content, String ext, String md5, String author) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.md5 = md5;
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public FastDFSFile() {
    }
}
