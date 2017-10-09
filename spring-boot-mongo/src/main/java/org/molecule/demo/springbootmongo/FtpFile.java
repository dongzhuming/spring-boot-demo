package org.molecule.demo.springbootmongo;

import com.mongodb.gridfs.GridFSFile;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class FtpFile implements Serializable {
    private String path;
    private GridFSFile file;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public GridFSFile getFile() {
        return file;
    }

    public void setFile(GridFSFile file) {
        this.file = file;
    }
}
