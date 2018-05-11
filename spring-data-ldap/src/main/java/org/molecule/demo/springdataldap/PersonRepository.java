package org.molecule.demo.springdataldap;


import java.util.List;


import javax.naming.Name;


import org.springframework.data.repository.CrudRepository;


/**
 * Repository interface to manage {@link Person} instances.
 *
 * @author Mark Paluch
 */

public interface PersonRepository extends CrudRepository<Person, Name> {


    /**
     * Find by UserId.
     *
     * @param uid
     * @return
     */

    List<Person> findByUid(String uid);


    /**
     * Prefix search on {@link Person#getLastname()}.
     *
     * @param prefix
     * @return
     */

    List<Person> findByLastnameStartsWith(String prefix);

}