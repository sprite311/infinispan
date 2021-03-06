package org.infinispan.server.hotrod

import org.infinispan.test.SingleCacheManagerTest
import test.HotRodClient
import org.infinispan.AdvancedCache
import test.HotRodTestingUtil._
import io.netty.channel.ChannelFuture
import org.infinispan.test.fwk.TestCacheManagerFactory
import org.infinispan.manager.EmbeddedCacheManager
import org.infinispan.server.core.test.ServerTestingUtil._
import org.testng.annotations.{Test, AfterClass}
import java.lang.reflect.Method
import org.infinispan.server.hotrod.OperationStatus._
import org.testng.AssertJUnit._

/**
 * Test class for setting an alternate default cache
 *
 * @author Tristan Tarrant
 * @since 6.0
 */
@Test(groups = Array("functional"), testName = "server.hotrod.HotRodDefaultCacheTest")
class HotRodDefaultCacheTest extends HotRodSingleNodeTest {
   override def createStartHotRodServer(cacheManager: EmbeddedCacheManager) = startHotRodServer(cacheManager, cacheName)

   def testPutOnDefaultCache(m: Method) {
      val resp = client.execute(0xA0, 0x01, "", k(m), 0, 0, v(m), 0, 1, 0)
      assertStatus(resp, Success)
      assertHotRodEquals(cacheManager, cacheName, k(m), v(m))
      assertFalse(cacheManager.getCache().containsKey(k(m)))
   }
}
