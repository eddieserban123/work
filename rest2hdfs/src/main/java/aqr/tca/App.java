package aqr.tca;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

/**
 * https://creativedata.atlassian.net/wiki/spaces/SAP/pages/52199514/Java+-+Read+Write+files+with+HDFS
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        FileSystem fs = initHdfs(" hdfs://namenodedns:port/user/hdfs/folder/file.csv")
        createFolder(fs,"");
        String newFolderPath="";
        String fileName="";
        writeToHDFS(fs, newFolderPath, fileName);

    }

    private static FileSystem  initHdfs(String hdfsUri) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hdfsUri);
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP_USER_NAME", "hdfs");
        System.setProperty("hadoop.home.dir", "/");
        return FileSystem.get(URI.create(hdfsUri), conf);


    }

    private static void writeToHDFS(FileSystem fs, String newFolderPath, String fileName) throws IOException {
        //==== Write file
        System.out.println("Begin Write file into hdfs");
//Create a path
        Path hdfswritepath = new Path(newFolderPath + "/" + fileName);
//Init output stream
        FSDataOutputStream outputStream=fs.create(hdfswritepath);
//Cassical output stream usage
        outputStream.writeBytes(fileContent);
        outputStream.close();
        System.out.println("End Write file into hdfs");
    }

    private static void createFolder(FileSystem fs, String path) throws IOException {
        Path workingDir=fs.getWorkingDirectory();
        Path newFolderPath= new Path(path);
        if(!fs.exists(newFolderPath)) {
            // Create new Directory
            fs.mkdirs(newFolderPath);
            System.out.println("Path "+path+" created.");
        }
    }
}
