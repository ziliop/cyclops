/*
 * Copyright (c) 2016. Zuercher Hochschule fuer Angewandte Wissenschaften
 *  All Rights Reserved.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License"); you may
 *     not use this file except in compliance with the License. You may obtain
 *     a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *     WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *     License for the specific language governing permissions and limitations
 *     under the License.
 */
package ch.icclab.cyclops.consume.data.mapping;

import ch.icclab.cyclops.load.Loader;
import org.influxdb.dto.Point;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Oleksii
 * Date: 01/04/2016
 * Description: This class holds the OpenstackEvent data
 */
public class OpenstackEvent {


    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String instanceId;


    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    private String action;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private String service_type;


    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    private Double memory;

    private Double vcpus;

    public void setMemory(Double memory) {this.memory = memory;}

    public void setVcpus(Double vcpus) {this.vcpus = vcpus;}

    public boolean isValid() {
        return action != null;
    }

    /**
     * This public method will access data and create db Point
     *
     * @return db point
     */
    public Point getPoint() {

        Map fields = getFields();
        removeNullValues(fields);

        return Point.measurement(getMeterName())
                .fields(fields)
                .build();
    }

    /**
     * Make sure we are not having any null values
     *
     * @param map original container that has to be changed
     */
    private void removeNullValues(Map<Object, Object> map) {
        map.values().removeAll(Collections.singleton(null));
    }

    /**
     * @return meter name
     */
    private String getMeterName() {
        return Loader.getSettings().getOpenstackSettings().getOpenstackEventTable();
    }

    /**
     * Get fields for point generation
     *
     * @return hashmap
     */
    private Map<String, Object> getFields() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("instanceId", instanceId);
        map.put("clientId", userName);
        map.put("productType", service_type);
        map.put("status", action);
        map.put("memory", memory.toString());
        map.put("cpu", vcpus.toString());


        return map;
    }

}
