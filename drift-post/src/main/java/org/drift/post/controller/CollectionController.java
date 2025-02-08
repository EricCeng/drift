package org.drift.post.controller;

import org.drift.common.api.CommonResult;
import org.drift.post.service.CollectionService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:40
 */
@RestController
@RequestMapping("/drift/collection")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping("/collect")
    public CommonResult<?> collect(@RequestParam("post_id") Long postId,
                                   @RequestParam("author_id") Long authorId) {
        collectionService.collect(postId, authorId);
        return CommonResult.success();
    }

    @PostMapping("/cancel")
    public CommonResult<?> cancel(@RequestParam("post_id") Long postId,
                                  @RequestParam("author_id") Long authorId) {
        collectionService.cancel(postId, authorId);
        return CommonResult.success();
    }

    @GetMapping("/collected_count")
    public CommonResult<Long> getUserCollectedCount(@RequestParam("author_Id") Long authorId) {
        return CommonResult.success(collectionService.getUserCollectedCount(authorId));
    }
}
