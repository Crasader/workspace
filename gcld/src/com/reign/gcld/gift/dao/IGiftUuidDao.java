package com.reign.gcld.gift.dao;

import com.reign.framework.mybatis.*;
import com.reign.gcld.gift.domain.*;
import java.util.*;

public interface IGiftUuidDao extends IBaseDao<GiftUuid>
{
    GiftUuid read(final int p0);
    
    GiftUuid readForUpdate(final int p0);
    
    List<GiftUuid> getModels();
    
    int getModelSize();
    
    int create(final GiftUuid p0);
    
    int deleteById(final int p0);
    
    int modifyUuid(final int p0, final int p1);
}