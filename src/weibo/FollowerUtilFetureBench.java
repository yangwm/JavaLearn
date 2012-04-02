/**
 * 
 */
package weibo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import junit.framework.Assert;

/**
 * Follower Util Feture Bench
 * 
 * @author yangwm Oct 25, 2010 10:17:25 AM
 */
public class FollowerUtilFetureBench {

    /**
     * @param args
     * @throws InterruptedException 
     * @throws ExecutionException 
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int uidSize = 10;
        final CountDownLatch beginLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(uidSize);
        System.out.println("------FollowerUtilFeture.getFriendsToFollowersMap-------");
        
        for (long i = 1; i <= uidSize; i++) {
            final long uid = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        beginLatch.await();
                        FollowerUtilFeture.getFriendsToFollowersMap(uid);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        endLatch.countDown();
                    }
                }
            }).start();
        }
        beginLatch.countDown();
        
        long begin = System.nanoTime();
        endLatch.await();
        long end = System.nanoTime();
        System.out.println("cosume: " + (end - begin) + " ns");
        System.out.println("FollowerUtilFeture.followersMap: " + FollowerUtilFeture.followersMap);
        for (long i = 1; i <= uidSize; i++) {
            final long uid = i;
            System.out.println("FollowerUtilFeture.followersMap.get(new Long(" + "uid)).get(): " 
                    + FollowerUtilFeture.followersMap.get(new Long(uid)).get());
        }
        
        /*
         * unit test 
         */
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(1)).get().size() == 10);
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(2)).get().size() == 1);
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(3)).get().size() == 2);
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(4)).get().size() == 2);
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(5)).get().size() == 3);
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(6)).get().size() == 2);
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(7)).get().size() == 4);
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(8)).get().size() == 2);
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(9)).get().size() == 4);
        Assert.assertTrue(FollowerUtilFeture.followersMap.get(new Long(10)).get().size() == 3);
        
    }

}

/*
------FollowerUtilFeture.getFriendsToFollowersMap-------
cosume: 5024102 ns
FollowerUtilFeture.followersMap: {6=java.util.concurrent.FutureTask@1dd7056, 5=java.util.concurrent.FutureTask@fa3ac1, 7=java.util.concurrent.FutureTask@276af2, 8=java.util.concurrent.FutureTask@1de3f2d, 2=java.util.concurrent.FutureTask@5d173, 9=java.util.concurrent.FutureTask@1f9dc36, 10=java.util.concurrent.FutureTask@e86da0, 1=java.util.concurrent.FutureTask@1754ad2, 3=java.util.concurrent.FutureTask@1833955, 4=java.util.concurrent.FutureTask@291aff}
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [8, 2, 4, 3, 10, 7, 9, 5, 6, 1]
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [1]
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [2, 1]
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [3, 1]
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [4, 2, 1]
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [5, 1]
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [3, 2, 6, 1]
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [7, 1]
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [8, 4, 2, 1]
FollowerUtilFeture.followersMap.get(new Long(uid)).get(): [3, 9, 1]

*/

