package io.github.oxnz.Ingrid.user

import java.io.IOException

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.{HashOperations, StringRedisTemplate}
import org.springframework.stereotype.Repository

import scala.jdk.CollectionConverters._

object UserRepository {
  private val KEY = "user"
}

@Repository class UserRepository(var objectMapper: ObjectMapper, var redisTemplate: StringRedisTemplate) {
  private var hashOperations: HashOperations[String, String, String] = redisTemplate.opsForHash()

  private def readUser(s: String) = try objectMapper.readValue(s, classOf[User])
  catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }

  private def writeUser(user: User) = try objectMapper.writeValueAsString(user)
  catch {
    case e: JsonProcessingException =>
      throw new RuntimeException(e)
  }

  def findOne(id: Long): User = {
    val s = hashOperations.get(UserRepository.KEY, String.valueOf(id)).asInstanceOf[String]
    readUser(s)
  }

  def findAll: List[User] = {
    val ss = hashOperations.values(UserRepository.KEY).asScala.toList
    ss.map(readUser)
  }

  def save(user: User): Unit = {
    val s = writeUser(user)
    hashOperations.put(UserRepository.KEY, String.valueOf(user.id), s)
  }
}