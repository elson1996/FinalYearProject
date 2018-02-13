package tweet4analysis.Topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;
import tweet4analysis.Bolt.SentimentCalculatorBolt;
import tweet4analysis.Spout.TweetSpout;
import tweet4analysis.Utils.Constants;

public class MainTopology {

    public static void main(String[] args) {

        // create the topology
        TopologyBuilder builder = new TopologyBuilder();

        // now create the tweet spout with the credentials and
        // attach the tweet spout to the topology - parallelism of 1
        builder.setSpout(Constants.BOLT_NAMES.TWEET_SPOUT, new TweetSpout(), 1);
        builder.setBolt(Constants.BOLT_NAMES.TWEET_SENTIMENT_BOLT, new SentimentCalculatorBolt(),
                10)
                .shuffleGrouping(Constants.BOLT_NAMES.TWEET_SPOUT);

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
            } catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
                e.printStackTrace();
            }
        }
        else {
            // run it in a simulated local cluster

            // set the number of threads to run - similar to setting number of workers in live cluster
            conf.setMaxTaskParallelism(3);

            // create the local cluster instance
            LocalCluster cluster = new LocalCluster();

            // submit the topology to the local cluster
            cluster.submitTopology(Constants.TOPOLOGY_NAME, conf, builder.createTopology());

            // let the topology run for 300 seconds. note topologies never terminate!
            Utils.sleep(300000);

            // now kill the topology
            cluster.killTopology(Constants.TOPOLOGY_NAME);

            // we are done, so shutdown the local cluster
            cluster.shutdown();
        }
    }
}
