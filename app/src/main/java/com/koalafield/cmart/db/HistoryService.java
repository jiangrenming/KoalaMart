package com.koalafield.cmart.db;

import android.content.Context;
import android.util.Log;

import com.koalafield.cmart.bean.HistoryContent;
import com.koalafield.cmart.utils.StringUtils;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/30.
 */

public class HistoryService extends BaseDao<HistoryContent>implements IHistoryService{


    public HistoryService(Context context) {
        super(context);
    }

    @Override
    public long addKeyWords(HistoryContent historyContent) throws DuplicatedTraceException {
        if (historyContent == null){
            Log.i("插入的数据为NUll","null");
            return -1;
        }
        if (this.findContent(historyContent.getContent()) != null){
            throw new DuplicatedTraceException("重复的搜索次，添加失败:" + historyContent.getContent());

        }
        long rows;
        if ((rows = (this.insert(historyContent)))< 1){
            throw new RuntimeException("添加搜索词失败");
        }
        return  rows;
    }

    @Override
    public List<HistoryContent> findAllHistory() {
        return super.findAll();
    }

    @Override
    public int deletAllKeys() {
        return super.delete("", new String[]{});
    }

    @Override
    public HistoryContent findContent(String keys) {
        if (StringUtils.isEmpty(keys)){
            return  null;
        }
        List<HistoryContent> content = super.query("content=?", new String[]{keys}, null);
        return content.size() > 0 ? content.get(0) : null;
    }
}
