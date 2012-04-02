/**
 * 
 */
package weibo;

import java.util.concurrent.CountDownLatch;

import junit.framework.Assert;

/**
 * Follower Util Bench
 * 
 * @author yangwm Oct 24, 2010 10:30:52 PM
 */
public class FollowerUtilBench {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        int uidSize = 10;
        final CountDownLatch beginLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(uidSize);
        System.out.println("------FollowerUtilSync.getFriendsToFollowersMap-------");
        
        for (long i = 1; i <= uidSize; i++) {
            final long uid = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        beginLatch.await();
                        FollowerUtil.getFriendsToFollowersMap(uid);
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
        System.out.println("FollowerUtilSync.followersMap: " + FollowerUtil.followersMap);
        
        /*
         * unit test 
         */
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(1)).size() == 10);
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(2)).size() == 1);
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(3)).size() == 2);
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(4)).size() == 2);
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(5)).size() == 3);
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(6)).size() == 2);
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(7)).size() == 4);
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(8)).size() == 2);
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(9)).size() == 4);
        Assert.assertTrue(FollowerUtil.followersMap.get(new Long(10)).size() == 3);
        
    }

}

/*
------FollowerUtilSync.getFriendsToFollowersMap-------
cosume: 2937244 ns
FollowerUtilSync.followersMap: {6=[5, 1], 5=[4, 2, 1], 7=[3, 2, 6, 1], 8=[7, 1], 2=[1], 9=[4, 8, 2, 1], 10=[9, 3, 1], 1=[9, 4, 3, 5, 8, 2, 7, 1, 6, 10], 3=[2, 1], 4=[3, 1]}

*/
