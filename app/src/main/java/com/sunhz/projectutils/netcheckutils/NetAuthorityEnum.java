/**
 * Copyright (c) 2015, Spencer , ChinaSunHZ (www.spencer-dev.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunhz.projectutils.netcheckutils;

/**
 * 网络类型枚举类
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
public enum NetAuthorityEnum {
    unNetConnect, // 无网连接
    WifiConnect, // wifi网络
    Net2GConnect, // 2G网络
    Net3GConnect, // 3G网络
    Net4GConnect, // 4G网络
    NotknowNetType; // 无法判断其网络类型
}