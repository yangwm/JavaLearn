/**
 * 
 */
package base;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 满足ClientVM的方法JIT编译阈值CompileThreshold=1500次的要求。
 * VM参数"-XX:+PrintCompilation"打开以后，列出了JVM在运行时进行过JIT编译的方法。
 * 
 * 大方法的执行性能与调优过程小记： http://rdc.taobao.com/team/jm/archives/552 
 * compilationPolicy.cpp中：当DontCompileHugeMethods=true且代码长度大于HugeMethodLimit时，方法不会被编译。
 * HugeMethodLimit = 8000，上面两个参数说明了Hotspot对字节码超过8000字节的大方法有JIT编译限制
 * VM参数"-XX:-DontCompileHugeMethods"来强迫JVM编译大方法，
 * 缺点是JVM会尝试编译所遇到的所有大方法，者会使JIT编译任务负担更重，而且需要占用更多的Code Cache区域去保存编译后的代码。
 * 
 * @author yangwm Dec 5, 2010 3:44:09 PM
 */
public class HugeMethodDemo {

    public static void main(String[] args) throws Exception {
        HugeMethodDemo demo = new HugeMethodDemo();

        int warmup = 2000;
        demo.run(warmup);

        int loop = 200000;
        double total = demo.run(loop);
        double avg = total / loop / 1e6 * 1e4;

        System.out.println(String.format("Loop=%d次, " + "avg=%.2f毫秒/万次", loop, avg));
    }

    private long run(int loop) throws Exception {
        long total = 0L;

        for (int i = 0; i < loop; i++) {
            Map theWorld = buildTheWorld();
            StringWriter console = new StringWriter();

            long start = System.nanoTime();
            play(theWorld, console);
            long end = System.nanoTime();
            total += (end - start);
        }

        return total;
    }

    private Map buildTheWorld() {
        Map context = new HashMap();
        context.put("name", "D&D");
        context.put("version", "1.0");

        Map game = new HashMap();
        context.put("game", game);

        Map player = new HashMap();
        game.put("player", player);
        player.put("level", "26");
        player.put("name", "jifeng");
        player.put("job", "paladin");
        player.put("address", "heaven");
        player.put("weapon", "sword");
        player.put("hp", 150);

        String[] bag = new String[] { "world_map", "dagger", "magic_1", "potion_1", "postion_2", "key" };
        player.put("bag", bag);
        return context;
    }

    private void play(Map theWorld, Writer console) throws Exception {
        // 重复拷贝的开始位置
        if (true) {
            String name = String.valueOf(theWorld.get("name"));
            String version = String.valueOf(theWorld.get("version"));
            console.append("Game ").append(name).append(" (v").append(version).append(")\n");
            Map game = (Map) theWorld.get("game");
            if (game != null) {
                Map player = (Map) game.get("player");
                if (player != null) {
                    String level = String.valueOf(player.get("level"));
                    String job = String.valueOf(player.get("job"));
                    String address = String.valueOf(player.get("address"));
                    String weapon = String.valueOf(player.get("weapon"));
                    String hp = String.valueOf(player.get("hp"));
                    console.append("  You are a ").append(level).append(" level ").append(job).append(" from ").append(
                            address).append(". \n");
                    console.append("  Currently you have a ").append(weapon).append(" in hand, ").append("your hp: ")
                            .append(hp).append(". \n");
                    console.append("  Here are items in your bag: \n");
                    for (String item : (String[]) player.get("bag")) {
                        console.append("     * ").append(item).append("\n");
                    }
                } else {
                    console.append("\tPlayer not login.\n");
                }
            } else {
                console.append("\tGame not start yet.\n");
            }
        }
        // 重复拷贝的结束位置
    }
}
