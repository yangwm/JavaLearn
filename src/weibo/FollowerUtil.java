/**
 * 
 */
package weibo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Follower Util 
 * 
 * @author yangwm Oct 24, 2010 9:59:15 PM
 */
public class FollowerUtil {
    
    public static ConcurrentMap<Long, List<Long>> followersMap = new ConcurrentHashMap<Long, List<Long>>();
    
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
        /*
         * 确保线程安全， 但synchronized导致性能太差 
         * 
        synchronized (followersMap) {
            List<Long> followers = followersMap.get(friend);
            if (followers == null) {
                followers = new CopyOnWriteArrayList<Long>();
                followersMap.putIfAbsent(friend, followers);
            }
            followers.add(uid);
        } */

        List<Long> followers = followersMap.get(friend);
        if (followers == null) {
            List<Long> newFollowers = new CopyOnWriteArrayList<Long>();
            followers = followersMap.putIfAbsent(friend, newFollowers);
            if (followers == null) { // insure thread safe 
                followers = newFollowers;
            }
        }
        followers.add(uid);
    }

}
