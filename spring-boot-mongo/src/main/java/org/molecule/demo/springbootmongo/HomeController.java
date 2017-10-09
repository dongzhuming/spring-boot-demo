package org.molecule.demo.springbootmongo;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

@RestController
public class HomeController {
    private final CityRepository cityRepository;
    private final FtpFileRepository ftpFileRepository;

    private final GridFsOperations operations;

    public HomeController(CityRepository cityRepository, FtpFileRepository ftpFileRepository, GridFsOperations operations) {
        this.cityRepository = cityRepository;
        this.ftpFileRepository = ftpFileRepository;
        this.operations = operations;
    }

    @RequestMapping("/")
    public String greeting() {
        return "hi";
    }

    @RequestMapping("/city/{name}")
    public List<City> getCity(@PathVariable String name) {
        return cityRepository.findByName(name);
    }

    @RequestMapping("/cities")
    public Iterable<City> getAllCities() {
        return cityRepository.findAll();
    }

    @RequestMapping("/test")
    public ResponseEntity<?> test() throws Exception {
        List<City> cities = new ArrayList<>();
        for(int i=0;i<200000;i++) {
            cities.add(new City(""+i));
        }
        cityRepository.save(cities);
        return ResponseEntity.created(new URI("ok")).build();
    }

    @RequestMapping("/new-city/{name}")
    public ResponseEntity<?> createCity(@PathVariable String name) throws Exception {
        City city = new City(name);
        cityRepository.save(city);
        return ResponseEntity.created(new URI("name")).build();
    }

    @RequestMapping("/file")
    public ResponseEntity<?> createFile() throws Exception {
        File file = new File("d:\\IntelliJIDEALicenseServer_windows_amd64.exe");
        GridFSFile gridFSFile = operations.store(new FileInputStream(file), file.getName());
        FtpFile ftpFile = new FtpFile();
        ftpFile.setPath("/xx/ww/aaa");
        ftpFile.setFile(gridFSFile);
        ftpFileRepository.save(ftpFile);
        return ResponseEntity.created(new URI("name")).build();
    }

    @RequestMapping("/file/{count}")
    public ResponseEntity<?> createMultiFiles(@PathVariable Long count) throws Exception {
        File file = new File("C:\\Users\\dell\\Downloads\\Thunder9.1.41.914baidu.exe");
        List<Long> timeElasped = new ArrayList<>();
        Long startTime = System.currentTimeMillis();
        for(int i=0; i<count; i++) {
            GridFSFile gridFSFile = operations.store(new FileInputStream(file), file.getName());
            FtpFile ftpFile = new FtpFile();
            ftpFile.setPath("/xx/ww/aaa");
            ftpFile.setFile(gridFSFile);
            ftpFileRepository.save(ftpFile);
            Long currentTime =  System.currentTimeMillis();
            timeElasped.add(currentTime - startTime);
            startTime = currentTime;
        }
        return ResponseEntity.created(new URI("name")).body(timeElasped);
    }

    @RequestMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
        long startTime = System.currentTimeMillis();
        List<GridFSDBFile> list = operations.find(query(whereFilename().is("Thunder9.1.41.914baidu.exe")));
        GridFsResource[] files = operations.getResources("*.exe");
        GridFSDBFile file = list.get(0);
        response.setHeader("Content-disposition","attachment;filename*=UTF-8''"+ URLEncoder.encode(file.getFilename(),"UTF-8"));
//        response.setHeader("Content-Type", "application/msword");
        file.writeTo(response.getOutputStream());
//        return ResponseEntity.ok().body(String.format("%d, %d", System.currentTimeMillis() - startTime, files.length));
    }
}
