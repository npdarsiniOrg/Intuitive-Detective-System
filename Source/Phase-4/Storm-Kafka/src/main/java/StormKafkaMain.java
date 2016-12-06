import org.apache.log4j.BasicConfigurator;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.UUID;

public class StormKafkaMain {
    private static final String KAFKA_TOPIC ="featuresToStorm";
    public static void main(String[] args) {
        BasicConfigurator.configure();

        if (args != null && args.length > 0)
        {
            try {
                StormSubmitter.submitTopology(
                        args[0],
                        createConfig(false),
                        createTopology());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(
                    "Surveillance_Team6",
                    createConfig(true),
                    createTopology());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cluster.shutdown();
        }
    }

    private static StormTopology createTopology()
    {
        SpoutConfig kafkaConf = new SpoutConfig(
                new ZkHosts("127.0.0.1:2181"),
                KAFKA_TOPIC,
                "/" + KAFKA_TOPIC,
                UUID.randomUUID().toString());
        kafkaConf.useStartOffsetTimeIfOffsetOutOfRange = true;
        kafkaConf.ignoreZkOffsets = true;
        kafkaConf.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
        kafkaConf.scheme = new SchemeAsMultiScheme(new StringScheme());
        TopologyBuilder topology = new TopologyBuilder();
        topology.setSpout("kafka_spout_audioFeatures", new KafkaSpout(kafkaConf), 4);
        topology.setBolt("priya", new priyaBolt(), 4).shuffleGrouping("kafka_spout_audioFeatures");
        topology.setBolt("harsha", new harshaBolt(), 4).shuffleGrouping("kafka_spout_audioFeatures");
        topology.setBolt("aish", new aishBolt(), 4).shuffleGrouping("kafka_spout_audioFeatures");
  
        return topology.createTopology();
    }


    private static Config createConfig(boolean local)
    {
        int workers = 1;
        Config conf = new Config();
        conf.setDebug(true);
        if (local)
            conf.setMaxTaskParallelism(workers);
        else
            conf.setNumWorkers(workers);
        return conf;
    }
}
