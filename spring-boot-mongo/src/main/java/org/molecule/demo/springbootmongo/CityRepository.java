package org.molecule.demo.springbootmongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CityRepository extends MongoRepository<City, String> {


    List<City> findByName(String name);
}
