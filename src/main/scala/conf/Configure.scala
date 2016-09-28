package conf

import com.typesafe.config.{Config, ConfigFactory}
/**
 * Created by nero on 16/6/1.
 */
object Configure {
	/**
	 * The convenience method ConfigFactory.load() loads the following (first-listed are higher priority):
	 * system properties
	 * application.conf (all resources on classpath with this name)
	 * application.json (all resources on classpath with this name)
	 * application.properties (all resources on classpath with this name)
	 * reference.conf (all resources on classpath with this name)
	 *
	 * String resource = System.getProperty("config.resource");
	 * String file = System.getProperty("config.file");
	 * String url = System.getProperty("config.url");
	 */

	/**
	 * 加载build.sbt里设置的配置文件
	 * 开发环境：application.deve.conf
	 * 运行环境：application.prod.conf
	 */
	val info: Config = ConfigFactory.defaultApplication()
}
