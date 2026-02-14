package com.interviewmate.interviewservice.repository.redisrepo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.interviewmate.interviewservice.dto.response.UserReportCache;


@Repository
public interface UserReportRedisRepository
        extends CrudRepository<UserReportCache, String> {
                List<UserReportCache> findAllByUserId(String userId);
}
