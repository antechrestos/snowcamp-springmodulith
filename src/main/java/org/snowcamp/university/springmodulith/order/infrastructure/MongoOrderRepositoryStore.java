package org.snowcamp.university.springmodulith.order.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

interface MongoOrderRepositoryStore extends MongoRepository<MongoOrder, String>{

}
