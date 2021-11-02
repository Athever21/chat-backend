package chat.app.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import chat.app.models.Channel;

public interface ChannelRepository extends MongoRepository<Channel, String> {
  @Query("{name: ?0}")
  Channel findByName(String name);

  @Query("{'memebers.$id': ?0}}")
  List<Channel> findByMember(String name);

  @DeleteQuery(value = "{createdBy: ?0}")
  void deleteByUser(String createdBy);
}
