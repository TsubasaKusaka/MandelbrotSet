package mandelbrotSet;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

public class MandelbrotSetMapperTest {

	@Test
	public void processecValidRecord() throws IOException, InterruptedException {
		//Text value1 = new Text("-1.7499999999999998 0.010000000000000753");
		Text value2 = new Text("-1.2899999999999994 -2");
		int result = 0;
		new MapDriver<LongWritable, Text, IntWritable, Text>()
			.withMapper(new MandelbrotSetMapper())
			.withInputValue(value2)
			.withOutput(new IntWritable(result), new Text("-1.2899999999999994 -2"))
			.runTest();
	}

}
