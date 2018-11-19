package com.reign.kf.gw.kfwd.dao;

import com.reign.framework.hibernate.dao.*;
import com.reign.kf.gw.kfwd.domain.*;
import java.util.*;

public interface IKfwdTicketMarketDao extends IBaseDao<KfwdTicketMarket, Integer>
{
    List<KfwdTicketMarket> getAllInfo();
}