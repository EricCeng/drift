package org.drift.common.feign;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.follow.FollowResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:01
 */
@FeignClient(name = "drift-follow-service")
public interface FollowServiceClient {
    @GetMapping("/drift/follow/info")
    CommonResult<FollowResponse> getFollowInfo(@RequestParam("user_id") Long userId);

    @GetMapping("/drift/follow/following_users")
    CommonResult<List<Long>> getFollowingUsers(@RequestParam("user_id") Long userId);
}
