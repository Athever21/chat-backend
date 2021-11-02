package chat.app.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import chat.app.models.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
  @Query(value = "{channel: ?0 }}", fields = "{channel: 0}")
  List<Message> findAllInChannel(String channelId, Pageable page);

  @DeleteQuery(value = "{channel: ?0}")
  void deleteByChannel(String channel);

  @DeleteQuery(value = "{user: ?0}")
  void deleteByUser(String channel);
}
