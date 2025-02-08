package org.drift.common.feign;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.user.UserInfoResponse;
import org.drift.common.pojo.user.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 12:41
 */
@FeignClient(name = "drift-user-service")
public interface UserServiceClient {
    @PostMapping("/drift/user/basic_info_list")
    CommonResult<List<UserInfoResponse>> getUserBasicInfoList(@RequestBody UserRequest request);

    @GetMapping("/drift/user/basic_info")
    CommonResult<UserInfoResponse> getUserBasicInfo(@RequestParam(value = "user_id", required = false) Long authorId);
}
