package mandelbrotSet;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MandelbrotSetDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		//入力ディレクトリと出力ディレクトリの両方が指定されていない場合は実行しない
		if (args.length != 2) {
			System.err.printf("Usage: %s [generic options] <input> <output>\n",
					getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}

		Job job = new Job(getConf(), "MandelbrotSet");
		job.setJarByClass(getClass());

		//入力ディレクトリ
		FileInputFormat.addInputPath(job, new Path(args[0]));
		//出力ディレクトリ
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		//map処理で使用するクラス
		job.setMapperClass(MandelbrotSetMapper.class);
		//reduce処理で使用するクラス
		job.setReducerClass(MandelbrotSetReducer.class);

		//MapperとReducerで出力する型を指定する
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(DoubleWritable.class);
		job.setOutputValueClass(DoubleWritable.class);

		//ジョブの実行
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new MandelbrotSetDriver(), args);
		System.exit(exitCode);
	}

}