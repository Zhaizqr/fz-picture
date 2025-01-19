/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fangz.picture.controller;

import com.fangz.picture.common.BaseResponse;
import com.fangz.picture.common.ResultUtils;
import com.fangz.picture.exception.ErrorCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class BasicController {

    @GetMapping("/testSuccess")
    public BaseResponse<String> testSuccess() {
        return ResultUtils.success("success");
    }

    @PostMapping("/testError")
    public BaseResponse<String> testError() {
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }

    @PostMapping("/testException")
    public BaseResponse<String> testException() {
        System.out.println(1/0);
        return null;
    }
}
