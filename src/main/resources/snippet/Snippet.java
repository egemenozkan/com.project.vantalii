package snippet;

public class Snippet {
	<?xml version="1.0" encoding="UTF-8"?>
	<configuration>
	
		<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		    <layout class="ch.qos.logback.classic.PatternLayout">
			<Pattern>
				%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n
			</Pattern>
		    </layout>
		</appender>
	
		<logger name="org.springframework" level="debug" additivity="false">
			<appender-ref ref="STDOUT" />
		</logger>
		
		<logger name="com.mkyong.helloworld" level="debug" additivity="false">
			<appender-ref ref="STDOUT" />
		</logger>
		 
		<root level="error">
			<appender-ref ref="STDOUT" />
		</root>
	
	</configuration>
	
}

