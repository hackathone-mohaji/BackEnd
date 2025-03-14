package com.mohaji.hackathon.common.config;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Configuration
@EnableAsync
public class AsyncConfig {

  @Value("${async.core-pool-size}")
  private int corePoolSize;

  @Value("${async.max-pool-size}")
  private int maxPoolSize;

  @Value("${async.queue-capacity}")
  private int queueCapacity;

  @Bean(name = "customTaskExecutor")
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.setThreadNamePrefix("AsyncThread-");
    executor.setTaskDecorator(new ContextCopyingDecorator());
    executor.initialize();
    return executor;
  }

  public static class ContextCopyingDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
      RequestAttributes context = RequestContextHolder.getRequestAttributes();
      return () -> {
        try {
          RequestContextHolder.setRequestAttributes(context);
          runnable.run();
        } finally {
          RequestContextHolder.resetRequestAttributes();
        }
      };
    }
  }
}
