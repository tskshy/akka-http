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
	 * 想通过配置文件定位配置文件的位置
	 * 无奈这涉及到“先有鸡还是先有蛋”
	 * 哈哈 竟然能遇到一个这么有哲学的问题
	 */
	val info: Config = ConfigFactory.defaultApplication()
}
