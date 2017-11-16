package tweet4analysis;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class MainTopology {

    public static void main(String[] args) {

        // create the topology
        TopologyBuilder builder = new TopologyBuilder();

        /*
        * In order to create the spout, you need to get twitter credentials
        * If you need to use Twitter firehose/Tweet stream for your idea,
        * create a set of credentials by following the instructions at
        *
        * https://dev.twitter.com/discussions/631
        **/

        // now create the tweet spout with the credentials
        TweetSpout tweetSpout = new TweetSpout("SHo4lHNWxFvRFMs4XRoFFHXqi", "R3ZX67z2nnPkiPv7TQ45Ajxx6foN2LmnuMhfCfBR8v0IY3fclY", "707193271085195264-fq4yVwn1zvJPxKBlxjOUAaiVkXFp5ew", "VsZnHrtCQVWr3UZHxB6eWrn4u4H1Ptl7NM0BEIc1M0G5J");

        // attach the tweet spout to the topology - parallelism of 1
        builder.setSpout("tweet-spout", tweetSpout, 1);

        // attach the parse tweet bolt using shuffle grouping
        builder.setBolt("parse-tweet-bolt", new ParseTweetBolt(), 1).shuffleGrouping("tweet-spout");

        // attach the report bolt using global grouping - parallelism of 1
        builder.setBolt("report-bolt", new ReportBolt(), 1).globalGrouping("parse-tweet-bolt");

        // create the default config object
        Config conf = new Config();

        // set the config in debugging mode
        conf.setDebug(true);

        if (args != null && args.length > 0) {

            // run it in a live cluster

            // set the number of workers for running all spout and bolt tasks
            conf.setNumWorkers(3);

            // create the topology and submit with config
            try {
                StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            } catch (AuthorizationException e) {
                e.printStackTrace();
            }

        } else {

            // run it in a simulated local cluster

            // set the number of threads to run - similar to setting number of workers in live cluster
            conf.setMaxTaskParallelism(3);

            // create the local cluster instance
            LocalCluster cluster = new LocalCluster();

            // submit the topology to the local cluster
            cluster.submitTopology("tweet-word-count", conf, builder.createTopology());

            // let the topology run for 300 seconds. note topologies never terminate!
            Utils.sleep(300000);

            // now kill the topology
            cluster.killTopology("tweet-word-count");

            // we are done, so shutdown the local cluster
            cluster.shutdown();
        }
    }
}
