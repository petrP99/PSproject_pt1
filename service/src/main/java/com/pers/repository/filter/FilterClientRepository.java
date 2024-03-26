package com.pers.repository.filter;

import com.pers.dto.filter.*;
import com.pers.entity.*;

import java.util.*;

public interface FilterClientRepository {

    List<Client> findAllByFilter(ClientFilterDto filter);

}