import org.testng.Assert;
import org.testng.annotations.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class Performance {

    /*
    Following example uses 2 threads (concurrent users) which send 10 HTTP GET requests each to https://www.google.com.
    Additionally, it logs collected statistics (response times, status codes, etc.)
    to a timestamped file (for later analysis if needed) and checks that the response time 99 percentile is less than 5 seconds.
     */
    @Test
    public void googleTest() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup(2, 10,
                        httpSampler("https://www.google.com")
                ),
                //this is just to log details of each request stats
                jtlWriter("test" + Instant.now().toString().replace(":", "-") + ".jtl")
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void picsartTst() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup(3, 10,
                        httpSampler("https://picsart.com")
                ),
                //this is just to log details of each request stats
                jtlWriter("test" + Instant.now().toString().replace(":", "-") + ".jtl")
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }
}
