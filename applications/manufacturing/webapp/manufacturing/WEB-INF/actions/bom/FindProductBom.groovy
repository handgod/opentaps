/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/* This file has been modified by Open Source Strategies, Inc. */

import org.ofbiz.base.util.*;
import org.ofbiz.entity.util.*;
import org.ofbiz.entity.condition.*;
import javolution.util.FastList;
import javolution.util.FastSet;
import javolution.util.FastMap;

if (!parameters.productId && !parameters.productIdTo && !parameters.productAssocTypeId) {
    cond = EntityCondition.makeCondition([EntityCondition.makeCondition("productAssocTypeId", EntityOperator.EQUALS, "ENGINEER_COMPONENT"),
                                          EntityCondition.makeCondition("productAssocTypeId", EntityOperator.EQUALS, "MANUF_COMPONENT")
                                          ], EntityOperator.OR);
    findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
    fieldToSelect = UtilMisc.toSet("productId","internalName","productAssocTypeId");
    bomListIterator = delegator.find("ProductAndAssoc", cond, null, fieldToSelect, null, findOpts);
} else {
    condList = [];
    if (parameters.productId) {
        cond = EntityCondition.makeCondition("productId", EntityOperator.EQUALS, parameters.productId);
        condList.add(cond);
    }
    if (parameters.productIdTo) {
        cond = EntityCondition.makeCondition("productIdTo", EntityOperator.EQUALS, parameters.productIdTo);
        condList.add(cond);
    }
    if (parameters.productAssocTypeId) {
        cond = EntityCondition.makeCondition("productAssocTypeId", EntityOperator.EQUALS, parameters.productAssocTypeId);
        condList.add(cond);
    } else {
        cond = EntityCondition.makeCondition([EntityCondition.makeCondition("productAssocTypeId", EntityOperator.EQUALS, "ENGINEER_COMPONENT"),
                                              EntityCondition.makeCondition("productAssocTypeId", EntityOperator.EQUALS, "MANUF_COMPONENT")
                                              ], EntityOperator.OR);
        condList.add(cond);
    }
    cond =  EntityCondition.makeCondition(condList, EntityOperator.AND);
    findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
    fieldToSelect = UtilMisc.toSet("productId","internalName","productAssocTypeId");
    bomListIterator = delegator.find("ProductAndAssoc", cond, null, fieldToSelect, null, findOpts);
}
context.ListProductBom = bomListIterator;