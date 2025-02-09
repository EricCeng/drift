package org.drift.common.feign;

import org.drift.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:14
 */
@FeignClient(name = "drift-comment-service")
public interface CommentServiceClient {
    @GetMapping("/drift/comment/comment_count")
    CommonResult<Long> getPostCommentCount(@RequestParam("post_id") Long postId);
}
