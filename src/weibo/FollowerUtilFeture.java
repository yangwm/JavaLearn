/**
 * 
 */
package weibo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Follower Util Feture
 * 
 * @author yangwm Oct 25, 2010 10:17:42 AM
 */
public class FollowerUtilFeture {
    
    public static ConcurrentMap<Long, Future<List<Long>>> followersMap = new ConcurrentHashMap<Long, Future<List<Long>>>();
    
    /**
     * get friends to followersMap 
     * 
     * @param uid
     */
    public static void getFriendsToFollowersMap(long uid) {
        List<Long> friends = getFriends(uid);
        //System.out.println("friends: " + friends);
        for (final Long friend : friends) {
            friendToFollowersMap(uid, friend);
        }
    }
    
    /**
     * get friends(test data)
     * 
     * friends: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
     * friends: [1, 3, 5, 7, 9]
     * friends: [1, 4, 7, 10]
     * friends: [1, 5, 9]
     * friends: [1, 6]
     * friends: [1, 7]
     * friends: [1, 8]
     * friends: [1, 9]
     * friends: [1, 10]
     * friends: [1]
     * 
     * @param uid
     * @return
     */
    public static List<Long> getFriends(long uid) {
        List<Long> result = new ArrayList<Long>();
        
        int uidSize = 10;
        for (int i = 1; i <= uidSize; i++) {
            for (long followerUid = 1; followerUid <= uidSize; followerUid+=uid) {
                result.add(followerUid);
            }
            break;
        }
        return result;
    }

    /**
     * friend to followersMap 
     * 
     * @param uid
     * @param friend
     * @throws InterruptedException
     */
    public static void friendToFollowersMap(long uid, long friend) {
        while (true) {
            Future<List<Long>> followersFuture = followersMap.get(friend);
            if (followersFuture == null) {
                Callable<List<Long>> eval = new Callable<List<Long>>() {
                    @Override
                    public List<Long> call() {
                        List<Long> followers = new CopyOnWriteArrayList<Long>();
                        return followers;
                    }
                };
                FutureTask<List<Long>> followersFutureTask = new FutureTask<List<Long>>(eval);
                followersFuture = followersMap.putIfAbsent(friend, followersFutureTask);
                if (followersFuture == null) {
                    followersFuture = followersFutureTask;
                    followersFutureTask.run();
                }
            }
            try {
                followersFuture.get().add(uid);
                return ;
            } catch (CancellationException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }

}
