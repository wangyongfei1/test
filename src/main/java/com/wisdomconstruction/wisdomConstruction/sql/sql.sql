CREATE TABLE `construction_site_dust` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `project_no` varchar(64) DEFAULT NULL COMMENT '项目编号',
  `temperature` varchar(64) DEFAULT NULL COMMENT '温度',
  `humidity` varchar(64) DEFAULT NULL COMMENT '湿度',
  `air_pressure` varchar(64) DEFAULT NULL COMMENT '气压',
  `wind_speed` varchar(64) DEFAULT NULL COMMENT '风速',
  `direction_wind` varchar(64) DEFAULT NULL COMMENT '风向',
  `particulate_25` varchar(64) DEFAULT NULL COMMENT 'PM2.5',
  `particulate_10` varchar(64) DEFAULT NULL COMMENT 'PM10',
  `suspended_particles` varchar(64) DEFAULT NULL COMMENT '悬浮颗粒(TSP)',
  `noise` varchar(64) DEFAULT NULL COMMENT '噪音',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除  1-已删除',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '更新人',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='扬尘设备表';


CREATE TABLE `construction_site_elevator` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `project_no` varchar(64) DEFAULT NULL COMMENT '项目编号',
  `load_weight` varchar(64) DEFAULT NULL COMMENT '载重重量',
  `highly` varchar(64) DEFAULT NULL COMMENT '高度',
  `speed` varchar(64) DEFAULT NULL COMMENT '速度',
  `state_front_door` varchar(64) DEFAULT NULL COMMENT '前门状态',
  `state_back_door` varchar(64) DEFAULT NULL COMMENT '后门状态',
  `state_door` varchar(64) DEFAULT NULL COMMENT '当前状态',
  `recognition_state` varchar(64) DEFAULT NULL COMMENT '人脸识别状态',
  `angle` varchar(64) DEFAULT NULL COMMENT '倾角',
  `wind_speed` varchar(64) DEFAULT NULL COMMENT '风速',
  `number` varchar(64) DEFAULT NULL COMMENT '人数',
  `safety_helmets` varchar(64) DEFAULT NULL COMMENT '安全帽个数',
  `floor` varchar(64) DEFAULT NULL COMMENT '楼层',
  `realtime_power` varchar(64) DEFAULT NULL COMMENT '实时功率',
  `running_state` tinyint(4) DEFAULT '0' COMMENT '运行状态 0-正常 1设备异常',
  `calling_floor` varchar(64) DEFAULT NULL COMMENT '当前呼叫楼层',
  `realtime_signal` varchar(64) DEFAULT NULL COMMENT '当前重量实时信号',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除  1-已删除',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '更新人',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='升降机设备表';


CREATE TABLE `construction_site_tower` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `project_no` varchar(64) DEFAULT NULL COMMENT '项目编号',
  `load_weight` varchar(64) DEFAULT NULL COMMENT '吊重重量',
  `amplitude` varchar(64) DEFAULT NULL COMMENT '幅度数据',
  `height` varchar(64) DEFAULT NULL COMMENT '高度数据',
  `turning_angle` varchar(64) DEFAULT NULL COMMENT '回转角度',
  `rated_weight` varchar(64) DEFAULT NULL COMMENT '额定重量',
  `percentage` varchar(64) DEFAULT NULL COMMENT '力矩百分比',
  `wind_speed` varchar(64) DEFAULT NULL COMMENT '风速',
  `before_angle` varchar(64) DEFAULT NULL COMMENT '前倾角',
  `status_tower` varchar(64) DEFAULT NULL COMMENT '塔机的状态',
  `weight_signal` varchar(64) DEFAULT NULL COMMENT '重量实时信号',
  `amplitude_signal` varchar(64) DEFAULT NULL COMMENT '幅度实时信号',
  `highly_signal` varchar(64) DEFAULT NULL COMMENT '高度实时信号',
  `rotary_signal` varchar(64) DEFAULT NULL COMMENT '回转实时信号',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除  1-已删除',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '更新人',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='塔机设备表';


CREATE TABLE `construction_site_electric_meter` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `project_no` varchar(64) DEFAULT NULL COMMENT '项目编号',
  `format_type` varchar(64) DEFAULT NULL COMMENT '格式类型',
  `a_phase_current` varchar(64) DEFAULT NULL COMMENT 'A相电流',
  `b_phase_current` varchar(64) DEFAULT NULL COMMENT 'B相电流',
  `c_phase_current` varchar(64) DEFAULT NULL COMMENT 'C相电流',
  `a_phase_voltage` varchar(64) DEFAULT NULL COMMENT 'A相电压',
  `b_phase_voltage` varchar(64) DEFAULT NULL COMMENT 'B相电压',
  `c_phase_voltage` varchar(64) DEFAULT NULL COMMENT 'C相电压',
  `total_active_power` varchar(64) DEFAULT NULL COMMENT '总有功功率',
  `total_reactive_power_KW` varchar(64) DEFAULT NULL COMMENT '总无功功率KW',
  `total_apparent_power_KVA` varchar(64) DEFAULT NULL COMMENT '总视在功率KVA',
  `grid_frequency_Hz` varchar(64) DEFAULT NULL COMMENT '电网频率Hz',
  `total_active_power_KWH` varchar(64) DEFAULT NULL COMMENT '总有功电能KWH',
  `total_reactive_energy_KVarh` varchar(64) DEFAULT NULL COMMENT '总无功电能KVarh',
  `total_power_factor` varchar(64) DEFAULT NULL COMMENT '总功率因数 cosφt',
  `active_power_phase_A` varchar(64) DEFAULT NULL COMMENT 'A相有功功率：Pa（KW）',
  `active_power_phase_B` varchar(64) DEFAULT NULL COMMENT 'B相有功功率：Pa（KW）',
  `active_power_phase_C` varchar(64) DEFAULT NULL COMMENT 'C相有功功率：Pa（KW）',
  `A_phase_reactive_power` varchar(64) DEFAULT NULL COMMENT 'A相无功功率：Qa（KVar）',
  `B_phase_reactive_power` varchar(64) DEFAULT NULL COMMENT 'B相无功功率：Qa（KVar）',
  `C_phase_reactive_power` varchar(64) DEFAULT NULL COMMENT 'C相无功功率：Qa（KVar）',
  `A_looks_at_power` varchar(64) DEFAULT NULL COMMENT 'A相视在功率：Sa（KVA）',
  `B_looks_at_power` varchar(64) DEFAULT NULL COMMENT 'B相视在功率：Sa（KVA）',
  `C_looks_at_power` varchar(64) DEFAULT NULL COMMENT 'C相视在功率：Sa（KVA）',
  `A_phase_power_factor` varchar(64) DEFAULT NULL COMMENT 'A相功率因数：cosφa',
  `B_phase_power_factor` varchar(64) DEFAULT NULL COMMENT 'B相功率因数：cosφa',
  `C_phase_power_factor` varchar(64) DEFAULT NULL COMMENT 'C相功率因数：cosφa',
  `A_electric_energy` varchar(64) DEFAULT NULL COMMENT 'A相有功电能：Wpa（KWh）',
  `B_electric_energy` varchar(64) DEFAULT NULL COMMENT 'B相有功电能：Wpa（KWh）',
  `C_electric_energy` varchar(64) DEFAULT NULL COMMENT 'C相有功电能：Wpa（KWh）',
  `A_reactive_electrical_energy` varchar(64) DEFAULT NULL COMMENT 'A相无功电能：Wqa(KVarh) ',
  `B_reactive_electrical_energy` varchar(64) DEFAULT NULL COMMENT 'B相无功电能：Wqa(KVarh) ',
  `C_reactive_electrical_energy` varchar(64) DEFAULT NULL COMMENT 'C相无功电能：Wqa(KVarh) ',
  `phase_failure_condition` varchar(64) DEFAULT NULL COMMENT '断相状况: DX',
  `cable_temperature` varchar(64) DEFAULT NULL COMMENT '线缆温度',
  `leakage_current` varchar(64) DEFAULT NULL COMMENT '漏电电流',
  `environment_temperature` varchar(64) DEFAULT NULL COMMENT '环境温度',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除  1-已删除',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '更新人',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='智能电表表';




CREATE TABLE `construction_site_water_meter` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `project_no` varchar(64) DEFAULT NULL COMMENT '项目编号',
  `current_water_consumption` varchar(64) DEFAULT NULL COMMENT '当前用水总量',
  `average_hourly_water_consumption` varchar(64) DEFAULT NULL COMMENT '小时平均用水量',
  `current_water_rate` varchar(64) DEFAULT NULL COMMENT '当前用水速度',
  `state_valve` tinyint(4) DEFAULT '0' COMMENT '阀门状态 0-开  1-关',
  `state_electric_meter` varchar(64) DEFAULT NULL COMMENT '电表状态',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除  1-已删除',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '更新人',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='智能水表表';


CREATE TABLE `construction_site_loadometer` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `project_no` varchar(64) DEFAULT NULL COMMENT '项目编号',
  `system_status` tinyint(4) DEFAULT '0' COMMENT '系统状态 0：正常 1：设备异常',
  `current_threshold` varchar(64) DEFAULT NULL COMMENT '当前阀值',
  `current_weight` varchar(64) DEFAULT NULL COMMENT '当前重量KG',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除  1-已删除',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '更新人',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='智能地磅表';




CREATE TABLE `construction_equipment` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `project_no` varchar(64) DEFAULT NULL COMMENT '项目编号',
  `device_name` varchar(64) DEFAULT NULL COMMENT '设备名称',
  `device_type` tinyint(4) DEFAULT '0' COMMENT '设备类型 0：扬尘 1：塔机 2：施工升降机 3：深基坑 4：卸料台 5：高支模 6：智能电表 7：智能水表',
  `has_dtu` tinyint(4) DEFAULT '0' COMMENT '是否连接DTU 0：有 1：否',
  `dtu_imei` varchar(64) DEFAULT NULL COMMENT 'dtu唯一标识码imei',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除  1-已删除',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '更新人',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='智慧工地设备表';


INSERT INTO `labor`.`construction_equipment`(`id`, `project_no`, `device_name`, `device_type`,`has_dtu`,  `dtu_imei`, `remark`, `is_delete`, `creator`, `gmt_create`, `modifier`, `gmt_modified`) VALUES (1, 'CPN0000000022', '扬尘监测设备', 0, 0, '867183031120468', '嘉善大西门项目设备', 0, 'system', '2020-05-01 12:49:45', 'system', '2020-05-01 12:49:50');



CREATE TABLE `construction_high_modulus` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `project_no` varchar(64) DEFAULT NULL COMMENT '项目编号',
  `serial_number` varchar(64) DEFAULT NULL COMMENT '设备编号',
  `horizontal_displacement` varchar(64) DEFAULT NULL COMMENT '水平位移',
  `settlement_template` varchar(64) DEFAULT NULL COMMENT '模板沉降',
  `poling_angle` varchar(64) DEFAULT NULL COMMENT '立杆倾角',
  `horizontal_angle` varchar(64) DEFAULT NULL COMMENT '水平杆倾角',
  `bearing` varchar(64) DEFAULT NULL COMMENT '承重',
  `site_status` tinyint(4) DEFAULT NULL COMMENT '当前监测点状态 0：正常；1：水平位移预警；2：水平位移报警；3：沉降预警；4：沉降报警；5：立杆倾角预警；6：立杆倾角报警；7：水平倾角预警；8：水平倾角报警；9：承重预警；10：承重报警；',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除  1-已删除',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '更新人',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='高支模设备表';




