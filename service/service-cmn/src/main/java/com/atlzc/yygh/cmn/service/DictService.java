package com.atlzc.yygh.cmn.service;

import com.atlzc.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    //根据数据id查询子数据列表
    List findChlidData(Long id);

    //导出
    void exportData(HttpServletResponse response);
    //导入
    void importData(MultipartFile file);
}
