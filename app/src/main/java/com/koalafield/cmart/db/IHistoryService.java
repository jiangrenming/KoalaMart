package com.koalafield.cmart.db;

import com.koalafield.cmart.bean.HistoryContent;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/30.
 */

public interface IHistoryService {

    long addKeyWords(HistoryContent historyContent) throws DuplicatedTraceException;
    List<HistoryContent> findAllHistory();
    int deletAllKeys();
    HistoryContent findContent(String keys);

}
