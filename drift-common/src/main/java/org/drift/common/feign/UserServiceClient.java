package org.drift.common.feign;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.user.UserInfoResponse;
import org.drift.common.pojo.user.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 12:41
 */
@FeignClient(name = "drift-user-service")
public interface UserServiceClient {
    @GetMapping("/drift/user/basic_info_list")
    CommonResult<List<UserInfoResponse>> getUserBasicInfoList(@RequestBody UserRequest request);
}
