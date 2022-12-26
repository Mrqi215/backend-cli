/*
*  Copyright 2019-2020 Init
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package com.software.modules.search.rest;

import com.software.annotation.Log;
import com.software.modules.search.domain.SearchTest;
import com.software.modules.search.service.SearchTestService;
import com.software.modules.search.service.dto.SearchTestQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author mrqi
* @date 2022-12-26
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "查询测试管理")
@RequestMapping("/api/searchTest")
public class SearchTestController {

    private final SearchTestService searchTestService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('searchTest:list')")
    public void exportSearchTest(HttpServletResponse response, SearchTestQueryCriteria criteria) throws IOException {
        searchTestService.download(searchTestService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询查询测试")
    @ApiOperation("查询查询测试")
    @PreAuthorize("@el.check('searchTest:list')")
    public ResponseEntity<Object> querySearchTest(SearchTestQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(searchTestService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增查询测试")
    @ApiOperation("新增查询测试")
    @PreAuthorize("@el.check('searchTest:add')")
    public ResponseEntity<Object> createSearchTest(@Validated @RequestBody SearchTest resources){
        return new ResponseEntity<>(searchTestService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改查询测试")
    @ApiOperation("修改查询测试")
    @PreAuthorize("@el.check('searchTest:edit')")
    public ResponseEntity<Object> updateSearchTest(@Validated @RequestBody SearchTest resources){
        searchTestService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除查询测试")
    @ApiOperation("删除查询测试")
    @PreAuthorize("@el.check('searchTest:del')")
    public ResponseEntity<Object> deleteSearchTest(@RequestBody Long[] ids) {
        searchTestService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}