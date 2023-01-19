package com.njustjjy.tpk2.dao;

import com.njustjjy.tpk2.entity.Record;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordMapper {
    /**add(增加)*/
    Integer addRecord(Record record);

    /**update(修改)*/
    Integer updateRecordByRid(Record record);


    /**delete(删除)*/


    /**query(查询)*/
    Record queryRecordByidCard(String idCard);

    Record queryRecordByRecordNumber(String recordNumber);

    Record queryRecordByRid(Integer rid);
}
