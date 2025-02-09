package org.drift.common.feign;

import org.drift.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:03
 */
@FeignClient(name = "drift-post-service")
public interface PostServiceClient {
    @GetMapping("/drift/collection/collected_count")
    CommonResult<Long> getUserCollectedCount(@RequestParam("user_id") Long userId);

    @GetMapping("/drift/like/liked_count")
    CommonResult<Long> getUserLikedCount(@RequestParam("user_id") Long userId);
}
