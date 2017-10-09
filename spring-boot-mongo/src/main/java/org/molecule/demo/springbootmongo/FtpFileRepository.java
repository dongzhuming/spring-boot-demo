package org.molecule.demo.springbootmongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FtpFileRepository extends MongoRepository<FtpFile, String> {

}
