package com.noah.demo.springboot;

import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.fs.FsShell;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientCallback;
import org.springframework.data.hadoop.hive.HiveTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	private HiveTemplate hiveTemplate;
	
	@Autowired
	private FsShell shell;

	@Override
	public void run(String... args) {
		for (FileStatus s : shell.lsr("/")) {
			System.out.println("> " + s.getPath());
		}
		List<String> list = getDbs();
		System.out.println(list.get(0));
	}

  public List<String> getDbs() {
      return hiveTemplate.execute(new HiveClientCallback<List<String>>() {
         @Override
         public List<String> doInHive(HiveClient hiveClient) throws Exception {
            return hiveClient.execute("select userid, movieid from u_data");
         }
      });
  }

	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "root");
		SpringApplication.run(Application.class, args);
	}
}
