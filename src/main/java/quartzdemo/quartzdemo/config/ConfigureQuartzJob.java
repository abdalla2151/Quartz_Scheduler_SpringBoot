package quartzdemo.quartzdemo.config;

import java.util.UUID;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import quartzdemo.quartzdemo.job.JobOne;
import quartzdemo.quartzdemo.job.JobTwo;

@Configuration
public class ConfigureQuartzJob {
	
	@Bean
	  public JobDetail jobADetails() {
	    return JobBuilder.newJob(JobOne.class).withIdentity("sampleJobA").storeDurably().build();
	  }
	  @Bean
	  public Trigger jobATrigger(JobDetail jobADetails) {
	    return TriggerBuilder.newTrigger()
	        .forJob(jobADetails)
	        .withIdentity("TriggerA")
	        .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMinutes(1))
//	        .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * ? * * *"))
	        .build();
	  }
	  
	  
	@Bean
	  public JobDetail jobBDetails() {
	    return JobBuilder.newJob(JobTwo.class).withIdentity("sampleJobB").storeDurably().build();
	  }
	  @Bean
	  public Trigger jobBTrigger(JobDetail jobBDetails) {
	    JobDataMap jobDataMap = new JobDataMap();
	    jobDataMap.put("somedata", UUID.randomUUID().toString());
	    return TriggerBuilder.newTrigger()
	        .forJob(jobBDetails)
	        .withIdentity("TriggerB")
	        .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * ? * * *"))
	        .usingJobData(jobDataMap)
	        .build();
	  }

}
