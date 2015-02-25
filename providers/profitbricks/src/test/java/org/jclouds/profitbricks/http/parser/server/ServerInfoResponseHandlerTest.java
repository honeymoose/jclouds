/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.profitbricks.http.parser.server;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.jclouds.date.DateCodec;
import org.jclouds.date.DateCodecFactory;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.profitbricks.domain.AvailabilityZone;
import org.jclouds.profitbricks.domain.Firewall;
import org.jclouds.profitbricks.domain.Nic;
import org.jclouds.profitbricks.domain.OsType;
import org.jclouds.profitbricks.domain.ProvisioningState;
import org.jclouds.profitbricks.domain.Server;
import org.jclouds.profitbricks.domain.Storage;
import org.jclouds.profitbricks.http.parser.BaseResponseHandlerTest;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

@Test(groups = "unit", testName = "ServerInfoResponseHandlerTest")
public class ServerInfoResponseHandlerTest extends BaseResponseHandlerTest<Server> {

   @Override
   protected ParseSax<Server> createParser() {
      return factory.create(injector.getInstance(ServerInfoResponseHandler.class));
   }

   protected DateCodecFactory createDateParser() {
      return injector.getInstance(DateCodecFactory.class);
   }

   @Test
   public void testParseResponseFromGetServer() {
      ParseSax<Server> parser = createParser();

      Server actual = parser.parse(payloadFromResource("/server/server.xml"));
      assertNotNull(actual, "Parsed content returned null");

      DateCodec dateParser = createDateParser().iso8601();

      Server expected = Server.builder()
              .id("qwertyui-qwer-qwer-qwer-qwertyyuiiop")
              .name("facebook-node")
              .cores(4)
              .ram(4096)
              .hasInternetAccess(true)
              .state(ProvisioningState.AVAILABLE)
              .status(Server.Status.RUNNING)
              .creationTime(dateParser.toDate("2014-12-04T07:09:23.138Z"))
              .lastModificationTime(dateParser.toDate("2014-12-12T03:08:35.629Z"))
              .osType(OsType.LINUX)
              .availabilityZone(AvailabilityZone.AUTO)
              .isCpuHotPlug(true)
              .isRamHotPlug(true)
              .isNicHotPlug(true)
              .isNicHotUnPlug(true)
              .isDiscVirtioHotPlug(true)
              .isDiscVirtioHotUnPlug(true)
              .storages(ImmutableList.<Storage>of(
                              Storage.builder()
                              .bootDevice(Boolean.TRUE)
                              .busType(Storage.BusType.VIRTIO)
                              .deviceNumber(1)
                              .size(40f)
                              .id("qswdefrg-qaws-qaws-defe-rgrgdsvcxbrh")
                              .name("facebook-storage")
                              .build()
                      )
              )
              .nics(ImmutableList.<Nic>of(
                              Nic.builder()
                              .dataCenterId("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
                              .id("qwqwqwqw-wewe-erer-rtrt-tytytytytyty")
                              .lanId(1)
                              .internetAccess(true)
                              .serverId("qwertyui-qwer-qwer-qwer-qwertyyuiiop")
                              .ip("173.252.120.6")
                              .macAddress("02:01:09:cd:f0:b0")
                              .firewall(Firewall.builder()
                                      .active(false)
                                      .id("wqwqwqwq-ewew-rere-trtr-ytytytytytyt")
                                      .nicId("qwqwqwqw-wewe-erer-rtrt-tytytytytyty")
                                      .state(ProvisioningState.AVAILABLE)
                                      .build())
                              .dhcpActive(true)
                              .gatewayIp("173.252.120.1")
                              .state(ProvisioningState.AVAILABLE)
                              .build()
                      ))
              .build();

      assertEquals(actual, expected);
   }

}
